package co.edu.unbosque.model.persistence;

import java.io.*;
import java.util.Scanner;

/**
 * Esta clase se encarga de implementar la gestion de archivos, como de texto
 * plano, como de texto serializado
 */
public class FileManager {

    // Estos tres son para archivos de texto
    private static Scanner lectorDeArchivo; // Funcion para leer un archivo de texto plano
    private static File archivo; // Una variable del tipo file
    private static PrintWriter escritorDeArchivo; // Nos permite implmentar un escrito de archivos

    private static final String RUTA_CARPETA = "src/archivos"; // Direccion de la carpeta

    // Atributos para serializado
    private static FileOutputStream fos; // Sirve para escribir bytes en un archivo
    private static ObjectOutputStream oos; // Sirve para escribir archivos serializados
    private static FileInputStream fis; // Sirve para leer bytes de archivos
    private static ObjectInputStream ois; // Sirve para leer objetos serializados

    /**
     * Contructor vacio por norma
     */
    public FileManager() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Esta funcion lo que hace es crear una carpeta cuando identifica que no hay
     * una
     */
    public static void crearCarpeta() {
        /*
         * archivo es una clase de java "File", lo que hace es crear un carpeta
         * "New File" en la ruta especificada
         */
        archivo = new File(RUTA_CARPETA);

        /*
         * Se crea un if que identifica si "archivo" no existe, se identifica por
         * ".exists()" || tambien identifica si el archivo no es una carpeta
         */
        if (!archivo.exists() || !archivo.isDirectory()) {
            archivo.mkdir(); // Crea la carpeta ".mkdir()" crea una carpeta en la ruta especificada
        }
    }

    /**
     * Una funcion publica, que no necesita una instancia para que se ejecute, que
     * crea y lee archivos de una carpeta en especifico
     *
     * @param nombreDeArchivo
     * @param contenido
     */
    public static void escribirArchivoDeTexto(String nombreDeArchivo, String contenido) {

        try {
            /*
             * En caso de que no exista, crea un archivo en la ruta especificada, con el
             * nombre por parametro de la funcion
             */
            archivo = new File(RUTA_CARPETA + "/" + nombreDeArchivo);

            // Verifica si el archivo existe con un if y ".exists"
            if (!archivo.exists()) {
                archivo.createNewFile(); // Si el archivo NO existe lo crea ".createNewFile()"
            }

            /*
             * Lo que hace es abrirlo para hacer la escritura y en caso de que ya tenga
             * contenido lo reescribre
             */
            escritorDeArchivo = new PrintWriter(archivo);

            // Lo que hace es escirbir una cadena de texo "String" en el archivo
            escritorDeArchivo.println(contenido);

            // Cuando se abre un archivo, siempre hay que cerrarlo despues de usarlo
            escritorDeArchivo.close();

            // Captura errores de entrada/salida, (permisas, rutas..)
        } catch (IOException e) {
            /*
             * Si llegue hasta aqui es por que el archivo no tiene permisos, la url esta mal
             * o el archivo no existe
             */
            System.out.println("Error al escribir el archivo de texto. (Creacion del archivo)");
            e.printStackTrace(); // Imprimi los detalles del error
        }

    }

    /**
     * Una funcion public que no necesita una instacia para ser ejecuta, que lee
     * archivos en una carpeta especifica, y retorna el contenido de ese archivo
     *
     * @param nombreDeArchivo
     * @return contenido
     */
    public static String leerArchivoDeTexto(String nombreDeArchivo) {

        try {
            // Crea un objeto FILE que va a representar el archivo dentro de "RUTA_CARPETA"
            archivo = new File(RUTA_CARPETA + "/" + nombreDeArchivo);
            if (!archivo.exists()) { // Verifica si existe ".exists"
                archivo.createNewFile(); // En caso de que no exista lo crea ".createNewFile"
            }

            // Abre el archivo y lo prepara con un Scanner para leerlo linea por linea
            lectorDeArchivo = new Scanner(archivo);

            String contenido = "";// Cadena de texto para guardar la info del archivo
            /*
             * Llama al lector de archivo y mientras haya mas lineas en el archivo sigue
             * leyendo con "hasNext"
             */
            while (lectorDeArchivo.hasNext()) {
                /*
                 * Añade cada linea del archivo al contenido, mediante el "+=" y el
                 * lectorDeArchivo
                 */
                contenido += lectorDeArchivo.nextLine(); // nextLine lee una linea completa del archivo
            }
            // Cierra el archivo despues de usarlo, para no consumir recursos
            lectorDeArchivo.close();
            return contenido; // Retorna el conteido que se encuentra dentro del archivo de texto

            /*
             * Captura errores de entrada/salida (puede ser que no encontro el archivo, no
             * esta en la ubicacion, perimisos)
             */
        } catch (IOException e) {
            System.out.println("Error al leer el archivo. (Creacion del archivo)");
            //Muestra
            e.printStackTrace();
            return null;
        }
    }

    // Funciones para serializado

    public static void escribirArchivoSerializado(String nombreArchivo, Object contenido) {

        try {

            archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);

            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            fos = new FileOutputStream(archivo);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(contenido);

            oos.close();
            fos.close();

        } catch (IOException e) {
            System.out.println("Problemas al abrir el archivo serializado (Escritura)");
            e.printStackTrace();
        }
    }

    public static Object leerArchivoSerializado(String nombreArchivo) {

        Object contenido = null;

        try {

            archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);

            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            fis = new FileInputStream(archivo);
            ois = new ObjectInputStream(fis);

            contenido = ois.readObject();

            ois.close();
            fis.close();

        } catch (IOException e) {
            System.out.println("Error al leer el archivo serializado");
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            System.out.println("Error en los datos del archivo serializado");
            e.printStackTrace();
        }
        return contenido;
    }

}

