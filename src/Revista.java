public class Revista extends Materiales {
    private String editorial;

    public Revista(String nombre, String descripcion, String editorial) {
        super(nombre, descripcion);
        this.editorial = editorial;
    }

    public String getEditorial(){
        return editorial;
    }

    public void setEditorial(String editorial){
        this.editorial = editorial;
    }

    @Override
    public String getDetalles() {
        return "Id: "+ getId()+ ", Revista: "+ getNombre() + ", Editorial :" + editorial;

    }
}
