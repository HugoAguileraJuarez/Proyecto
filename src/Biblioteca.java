import java.util.ArrayList;
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
        for (Object entrada : materiales.values()){
            lista.add((Materiales) entrada);
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

    //Hacer metodo para buscar por type de material




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

    //Buscar por tipo
    public Materiales buscarTipo(){
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


    public boolean alquilar(int id){
        ArrayList<Materiales> lista = new ArrayList<>();
        for (Materiales entrada: materiales.values()){
            if ((entrada).getId() == id){
                if (entrada instanceof Libro) {
                    Libro libro = (Libro) entrada;
                    if (libro.alquilar()){
                        lista.add(libro);
                        return true;
                    }
                }else if (entrada instanceof DVD){
                    DVD dvd = (DVD) entrada;
                    if (dvd.alquilar()){
                        lista.add(dvd);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean devolver(int id){
        for (Materiales entrada: materiales.values()){
            if (entrada.getId() == id){
                if (entrada instanceof Libro){
                    Libro libro = (Libro) entrada;
                    if (libro.devuelto()){
                        return true;
                    }
                }else if (entrada instanceof DVD){
                    DVD dvd = (DVD) entrada;
                    if (dvd.devuelto()){
                        return true;
                    }
                }
            }
        }
        return false;
    }




}



























