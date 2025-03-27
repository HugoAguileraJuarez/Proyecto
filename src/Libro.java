public class Libro extends Materiales implements Prestable{
    private String autor;
    private int numPaginas;
    private static int prestados;
    private boolean prestado;

    public Libro(String nombre, String descripcion, String autor, int numPaginas) {
        super(nombre, descripcion);
        this.autor = autor;
        this.numPaginas = numPaginas;
        this.prestado = false;
    }

    public String getAutor(){
        return autor;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public int getNumPaginas(){
        return numPaginas;
    }

    public void setNumPaginas(int numPaginas){
        this.numPaginas = numPaginas;
    }

    public int getPrestados(){
        return prestados;
    }

    public boolean getPrestado(){
        return prestado;
    }

    @Override
    public boolean alquilar() {
        if (!prestado){
            prestado = true;
            prestados++;
            return true;
        }
        return false;
    }

    @Override
    public boolean devuelto() {
        if (prestado){
            prestado = false;
            prestados--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + "Libro{" +
                "autor='" + autor + '\'' +
                ", numPaginas=" + numPaginas +
                ", prestado=" + prestado +
                '}';
    }
}
