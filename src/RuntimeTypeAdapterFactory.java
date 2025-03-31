import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.util.HashMap;
import java.util.Map;

public class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {
    private final Class<?> baseType;
    private final String typeFieldName;
    private final Map<String, Class<? extends T>> labelToSubtype = new HashMap<>();
    private final Map<Class<? extends T>, String> subtypeToLabel = new HashMap<>();

    private RuntimeTypeAdapterFactory(Class<?> baseType, String typeFieldName) {
        this.baseType = baseType;
        this.typeFieldName = typeFieldName;
    }

    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
        return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName);
    }

    public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> subtype, String label) {
        labelToSubtype.put(label, subtype);
        subtypeToLabel.put(subtype, label);
        return this;
    }

    @Override
    public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> type) {
        if (!baseType.isAssignableFrom(type.getRawType())) {
            return null;
        }

        Map<String, TypeAdapter<?>> labelToDelegate = new HashMap<>();
        Map<Class<?>, TypeAdapter<?>> subtypeToDelegate = new HashMap<>();

        for (Map.Entry<String, Class<? extends T>> entry : labelToSubtype.entrySet()) {
            TypeAdapter<?> delegate = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
            labelToDelegate.put(entry.getKey(), delegate);
            subtypeToDelegate.put(entry.getValue(), delegate);
        }

        return new TypeAdapter<R>() {
            @Override
            public void write(JsonWriter out, R value) throws java.io.IOException {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                Class<?> clazz = value.getClass();
                String label = subtypeToLabel.get(clazz);
                @SuppressWarnings("unchecked")
                TypeAdapter<R> delegate = (TypeAdapter<R>) subtypeToDelegate.get(clazz);
                if (delegate == null) {
                    throw new IllegalArgumentException("Cannot serialize " + clazz.getName() + "; did you forget to register a subtype?");
                }
                // Convertimos el objeto a un JsonElement
                JsonElement jsonElement = delegate.toJsonTree(value);
                if (!jsonElement.isJsonObject()) {
                    throw new IllegalStateException("Expected a JsonObject but was " + jsonElement);
                }
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                // Agregamos el campo "type"
                jsonObject.addProperty(typeFieldName, label);
                // Escribimos el JsonObject completo
                Streams.write(jsonObject, out);
            }


            @Override
            public R read(JsonReader in) throws java.io.IOException {
                JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();
                JsonElement typeElement = jsonObject.get(typeFieldName);
                if (typeElement == null) {
                    throw new JsonParseException("Cannot deserialize because 'type' field is missing");
                }
                String label = typeElement.getAsString();
                TypeAdapter<?> delegate = labelToDelegate.get(label);
                if (delegate == null) {
                    throw new JsonParseException("Cannot deserialize " + label + "; did you forget to register a subtype?");
                }
                return (R) delegate.fromJsonTree(jsonObject);
            }
        };
    }
}
