import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

public class Menu {
    private static final String RUTA_ARCHIVO = "Biblioteca.json";

    public static Biblioteca leerJSON() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(
                        RuntimeTypeAdapterFactory.of(Materiales.class, "type")
                                .registerSubtype(Libro.class, "Libro")
                                .registerSubtype(Revista.class, "Revista")
                                .registerSubtype(DVD.class, "DVD")
                )
                .create();

        Biblioteca biblio = null;
        try (FileReader reader = new FileReader(RUTA_ARCHIVO)) {
            biblio = gson.fromJson(reader, Biblioteca.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return biblio;
    }

    public static void escribirJSON(Biblioteca biblioteca) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(
                        RuntimeTypeAdapterFactory.of(Materiales.class, "type")
                                .registerSubtype(Libro.class, "Libro")
                                .registerSubtype(Revista.class, "Revista")
                                .registerSubtype(DVD.class, "DVD")
                )
                .setPrettyPrinting()  // Opcional, para un JSON formateado
                .create();

        String json = gson.toJson(biblioteca);
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            writer.write(json);
            System.out.println("JSON guardado y asegurado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

        Biblioteca biblioteca = leerJSON();
        if (biblioteca == null) {
            System.out.println("No se pudo leer el archivo JSON. Se creará una nueva biblioteca.");
            biblioteca = new Biblioteca();
        }
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("Menu de la gran biblioteca, elija una opción:" +
                    "\n\t1. Insertar un nuevo material" +
                    "\n\t2. Eliminar un material" +
                    "\n\t3. Mostrar lista de materiales" +
                    "\n\t4. Buscar información de un material" +
                    "\n\t5. Alquilar" +
                    "\n\t6. Devolver" +
                    "\n\t7. Salir");

            int seleccion = teclado.nextInt();
            teclado.nextLine();

            switch (seleccion) {
                case 1:
                    System.out.println("Elija el tipo de material a insertar:" +
                            "\n\t1. Libro" +
                            "\n\t2. Revista" +
                            "\n\t3. DVD");

                    int tipoMaterial = teclado.nextInt();
                    teclado.nextLine(); // Consumir el salto de línea

                    if (tipoMaterial == 1) {
                        System.out.println("Ingrese el título del libro:");
                        String titulo = teclado.nextLine();

                        System.out.println("Ingrese el autor del libro:");
                        String autor = teclado.nextLine();

                        System.out.println("Ingrese una breve descripción del libro:");
                        String desc = teclado.nextLine();

                        System.out.println("Ingrese el número de páginas del libro:");
                        int numP = teclado.nextInt();

                        biblioteca.addLibro(titulo, autor, desc, numP);
                    } else if (tipoMaterial == 2) {
                        System.out.println("Ingrese el nombre de la revista:");
                        String nom = teclado.nextLine();

                        System.out.println("Ingrese una breve descripción de la revista:");
                        String desc = teclado.nextLine();

                        System.out.println("Ingrese la editorial de la revista:");
                        String edito = teclado.nextLine();

                        biblioteca.addRevistao(nom, desc, edito);
                    } else if (tipoMaterial == 3) {
                        System.out.println("Ingrese el nombre del DVD:");
                        String nom = teclado.nextLine();

                        System.out.println("Ingrese una breve descripción del DVD:");
                        String desc = teclado.nextLine();

                        System.out.println("Ingrese el tipo de tenido del DVD:");
                        String contenido = teclado.nextLine();
                        biblioteca.addDVD(nom, desc, contenido);
                    } else {
                        System.out.println("Error: opción inválida.");
                    }

                    escribirJSON(biblioteca);
                    break;

                case 2:
                    System.out.println("Ingrese la ID del material a eliminar:");
                    int id = teclado.nextInt();
                    teclado.nextLine(); // Evitar problemas con nextInt()

                    Materiales material = biblioteca.buscar(id);
                    if (material == null) {
                        System.out.println("El material con ID " + id + " no existe.");
                    } else {
                        System.out.println("¿Desea eliminar este material? \n\t" + material.getDetalles() + "\n(Escriba 'si' para confirmar)");
                        String pregunta = teclado.nextLine();

                        if (pregunta.equalsIgnoreCase("si")) {
                            boolean eliminado = biblioteca.eliminarMaterial(id);
                            if (eliminado) {
                                System.out.println("Material eliminado con éxito.");
                            } else {
                                System.out.println("No se pudo eliminar el material.");
                            }
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }
                    escribirJSON(biblioteca);
                    break;

                case 3:
                    System.out.println("Materiales en la biblioteca:");
                    if (biblioteca.mostarBiblio() == null || biblioteca.mostarBiblio().isEmpty()) {
                        System.out.println("La biblioteca está vacía.");
                    } else {
                        for (Materiales entrada : biblioteca.mostarBiblio()) {
                            System.out.println(entrada.getDetalles());
                        }
                    }
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    System.out.println("Saliendo del programa...");
                    salir = true;
                    break;

                default:
                    System.out.println("Error: opción inválida.");
            }
        }
    }
}
