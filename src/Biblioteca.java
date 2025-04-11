import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;

public class Biblioteca {
    //  Creacion de HashMap y ArrayList
    private HashMap<String, Materiales> materiales = new HashMap<>();
    private transient ArrayList<Libro> libros = new ArrayList<>();
    private transient ArrayList<DVD> Dvd = new ArrayList<>();
    private transient ArrayList<Revista> revistas = new ArrayList<>();

    //  Coleciones
    public ArrayList<Materiales> mostarBiblio(){
        ArrayList<Materiales> lista = new ArrayList<>();
        for (Materiales entrada : materiales.values()){
            lista.add( entrada);
        }
        return lista;
    }

    public Materiales buscarTitulo(String titulo){
        for (Materiales entrada: materiales.values()){
            if (entrada.getNombre().toLowerCase().contains(titulo.toLowerCase())){
                return entrada;
            }
        }
        return null;
    }

    public ArrayList<Materiales> buscarTipo(String nom){
        ArrayList<Materiales> resultado = new ArrayList<>();
        for (Materiales material : materiales.values()) {
            switch (nom.toLowerCase()) {
                case "dvd":
                    if (material instanceof DVD) {
                        resultado.add(material);
                    }
                    break;
                case "libro":
                    if (material instanceof Libro) {
                        resultado.add(material);
                    }
                    break;
                case "revista":
                    if (material instanceof Revista) {
                        resultado.add(material);
                    }
                    break;
                default:
                    break;
            }
        }
        return resultado;
    }

    public ArrayList<Materiales> alquilados(){
        ArrayList<Materiales> lista = new ArrayList<>();
        for (Materiales entrada: materiales.values()){
            if (entrada instanceof Libro){
                Libro libro =(Libro) entrada;
                if (libro.getPrestado()){
                    lista.add(entrada);
                }
            }else if (entrada instanceof DVD){
                DVD dvd = (DVD) entrada;
                if (dvd.getPrestado()){
                    lista.add(entrada);
                }
            }
        }
        return lista;
    }

    //  Parte del menu
    //  Añadir elemento
    public void addLibro(String nom, String aut, String desc, int numP) {
        Libro libro = new Libro(nom, desc, aut, numP);
        libros.add(libro);
        materiales.put(String.valueOf(libro.getId()), libro);
    }

    public void addRevistao(String nom, String desc, String edio) {
        Revista revista = new Revista(nom,desc,edio);
        revistas.add(revista);
        materiales.put(String.valueOf(revista.getId()), revista);
    }

    public void addDVD(String nom, String desc, String cont){
        DVD dvd = new DVD(nom,desc,cont);
        Dvd.add(dvd);
        materiales.put(String.valueOf(dvd.getId()), dvd);
    }

    // Buscar un elemento
    public Materiales buscar(int id){
        for (Object entrada: materiales.values()){
            if (((Materiales)entrada).getId() == id){
                return (Materiales) entrada;
            }
        }
        return null;
    }

    //  Eliminar un elemento
    public boolean eliminarMaterial(int id) {
        String clave = String.valueOf(id);
        Materiales objecto = null;
        if (materiales.containsKey(clave)){
            objecto = (Materiales) materiales.remove(clave);
            if (objecto instanceof Libro) {
                libros.remove(objecto);
            } else if (objecto instanceof DVD) {
                Dvd.remove(objecto);
            } else if (objecto instanceof Revista) {
                revistas.remove(objecto);
            }
            return true;
        }
        return  false;
    }


    public void alquilar(int id){
        ArrayList<Materiales> lista = new ArrayList<>();
        for (Materiales entrada: materiales.values()){
            if ((entrada).getId() == id){
                if (entrada instanceof Libro) {
                    Libro libro = (Libro) entrada;
                    if (libro.alquilar()){
                        lista.add(libro);
                    }else {
                    throw new IllegalArgumentException("No puedes alquilar un libro que esta prestado");
                    }
                }else if (entrada instanceof DVD){
                    DVD dvd = (DVD) entrada;
                    if (dvd.alquilar()){
                        lista.add(dvd);
                    }else {
                        throw new IllegalArgumentException("No puedes alquilar un dvd que esta prestado");
                    }
                }
            }
        }
    }

    public void devolver(int id) {
        for (Materiales entrada: materiales.values()){
            if (entrada.getId() == id){
                if (entrada instanceof Libro){
                    Libro libro = (Libro) entrada;
                    if (libro.devuelto()){
                    }else {
                        throw new IllegalArgumentException("No puedes devolver un libro que no esta prestado");
                    }
                }else if (entrada instanceof DVD){
                    DVD dvd = (DVD) entrada;
                    if (dvd.devuelto()){
                    }else {
                        throw new IllegalArgumentException("No puedes devolver un DVD que no esta prestado");
                    }
                }
            }
        }
    }




}



























