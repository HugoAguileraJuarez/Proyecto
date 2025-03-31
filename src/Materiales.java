import java.util.concurrent.atomic.AtomicInteger;

public abstract class Materiales {
    private static final AtomicInteger contadorId = new AtomicInteger(1);
    private final int id;
    private String nombre;
    private String descripcion;

    public Materiales(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = contadorId.getAndIncrement();
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public abstract String getDetalles();

}
