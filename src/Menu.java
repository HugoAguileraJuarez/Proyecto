import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//cosas que preguntar: seguir usando object en los metodos de la bibliteca
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
            if (biblio == null) {
                System.out.println("Archivo JSON vacío, creando nueva biblioteca...");
                biblio = new Biblioteca();
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo JSON, creando nueva biblioteca...");
            biblio = new Biblioteca();
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
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            gson.toJson(biblioteca, writer);
            System.out.println("JSON guardado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el JSON: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Biblioteca biblioteca = leerJSON();

        Scanner teclado = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("Menu de la gran biblioteca, elija una opción:" +
                    "\n\t1. Insertar un nuevo material" +
                    "\n\t2. Eliminar un material" +
                    "\n\t3. Mostrar lista de materiales" +
                    "\n\t4. Buscar un material" +
                    "\n\t5. Alquilar" +
                    "\n\t6. Devolver" +
                    "\n\t7. Alquilados" +
                    "\n\t8. Salir");

            int seleccion = teclado.nextInt();
            teclado.nextLine();

            switch (seleccion) {
                case 1:
                    System.out.println("Elija el tipo de material a insertar:" +
                            "\n\t1. Libro" +
                            "\n\t2. Revista" +
                            "\n\t3. DVD");

                    int tipoMaterial = teclado.nextInt();
                    teclado.nextLine();

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

                        if (pregunta.contains("si")) {
                            boolean eliminado = biblioteca.eliminarMaterial(id);
                            if (eliminado) {
                                System.out.println("Material eliminado con éxito.");
                                escribirJSON(biblioteca);
                            }
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Materiales en la biblioteca:");
                    if (biblioteca.mostarBiblio() == null) {
                        System.out.println("La biblioteca está vacía.");
                    } else {
                        for (Materiales entrada : biblioteca.mostarBiblio()) {
                            System.out.println(entrada.getDetalles());
                        }
                    }
                    break;

                case 4:
                    System.out.println("Selecione el tipo de busqueda:" +
                            "\n\t1. Por id" +
                            "\n\t2. Por titulo o nombre" +
                            "\n\t3. Por tipo" );
                    seleccion = teclado.nextInt();
                    teclado.nextLine();
                    if (seleccion == 1){
                        System.out.println("Ingrese la ID del material:");
                        id = teclado.nextInt();
                        System.out.println(biblioteca.buscar(id).getDetalles());

                    }else if(seleccion == 2){
                        System.out.println("Ingrese el titulo o nombre del material: ");
                        String nom = teclado.nextLine();
                        System.out.println(biblioteca.buscarTitulo(nom).getDetalles());
                    }else if (seleccion == 3){
                        System.out.println("Ingrese el tipo de material que quieres buscar");
                        String nom = teclado.nextLine();
                        for (Materiales entrada: biblioteca.buscarTipo(nom)){
                            System.out.println(entrada.getDetalles());
                        }
                    };
                    break;

                case 5:
                    System.out.println("Ingrese la ID del material que deseas alquilar:");
                    id = teclado.nextInt();
                    System.out.println(biblioteca.alquilar(id));
                    escribirJSON(biblioteca);
                    break;

                case 6:
                    System.out.println("Ingrese la ID del material que deseas devolver:");
                    id = teclado.nextInt();
                    System.out.println(biblioteca.devolver(id));
                    escribirJSON(biblioteca);
                    break;

                case 7:
                    System.out.println("Aqui estan todos los materiales alquilados");
                        if (biblioteca.alquilados().isEmpty()){
                        System.out.println("Actualmente no hay ningun material alquilado.");
                    }else {
                        for (Materiales entrada : biblioteca.alquilados()) {
                            System.out.println(entrada.getDetalles());
                        }
                    }
                    break;

                case 8:
                    System.out.println("Saliendo del programa");
                    salir = true;
                    break;

                default:
                    System.out.println("Error: opción inválida.");
            }
        }
    }
}

// comprobar si se pueden añadir algo ya esta metido
// mirar si me renta cambiar los valores de la biblioteca de manera que no devuelvan nada pero si hay un fallo usar el try chach
// la opcion 4 de buscar por especifiacion los dos primeros petan y el tercero se la suda asi que tocara usar ahi el try cach entonces
//