import java.util.ArrayList;
import java.util.HashMap;

public class Biblioteca {
    //  Creacion de HashMap y ArrayList
    HashMap<String, Object> materiales = new HashMap<>(); //Ultima modificacion y da error
    ArrayList<Libro> libros = new ArrayList<>();
    ArrayList<DVD> Dvd = new ArrayList<>();
    ArrayList<Revista> revistas = new ArrayList<>();

    //  Coleciones
    public ArrayList<Materiales> mostarBiblio(){
        ArrayList<Materiales> lista = new ArrayList<>();
        for (Object entrada : materiales.values()){
            lista.add((Materiales) entrada);
        }
        return lista;
    }

    public ArrayList<Materiales> buscarTitulo(String titulo){
        ArrayList<Materiales> lista = new ArrayList<>();
        for (Object entrada: materiales.values()){
            if (titulo.contains(((Materiales)entrada).getNombre())){
                lista.add((Materiales) entrada);
            }
        }
        return lista;
    }

    //Hacer metodo para mostrar por elemento


    public ArrayList<Materiales> mostrarPrestados(){
        ArrayList<Materiales> lista = new ArrayList<>();
        for (Object entrada:  materiales.values()){
            if (entrada instanceof Libro) {
                Libro libro = (Libro) entrada;
                if (libro.getPrestado()){
                    lista.add(libro);
                }
            }else if (entrada instanceof DVD){
                DVD dvd = (DVD) entrada;
                if (dvd.getPrestado()){
                    lista.add(dvd);
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






}



























