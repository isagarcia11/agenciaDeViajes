package co.edu.uniquindio.agenciaDeViajes.utils;

import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class ArchivoUtils {

    /**
     * Permite leer un archivo desde una ruta específica mediante Scanner
     * @param ruta Ruta a leer
     * @return Lista de String por cada línea del archivo
     * @throws IOException
     */
    public static ArrayList<String> leerArchivoScanner(String ruta) throws IOException{

        ArrayList<String> lista = new ArrayList<>();
        Scanner sc = new Scanner(new File(ruta));

        while(sc.hasNextLine()) {
            lista.add(sc.nextLine());
        }

        sc.close();

        return lista;
    }

    /**
     * Escribe datos en un archivo de texo
     * @param ruta ruta Ruta donde se va a crear el archivo
     * @param lista Información a guardar en el archivo
     * @param concat True si se concatena los nuevos datos sin sobreescibir todo el archivo
     * @throws IOException
     */
    public static void escribirArchivoBufferedWriter(String ruta, List<String> lista, boolean concat) throws IOException{

        FileWriter fw = new FileWriter(ruta, concat);
        BufferedWriter bw = new BufferedWriter(fw);

        for (String string : lista) {
            bw.write(string);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    public static void actualizarArchivoCliente(String archivo, Cliente cliente) {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean identificacionEncontrada = false;

        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            String[] partes = linea.split(";");

            if (partes[0].equals(cliente.getIdentificacion())) {
                lineas.set(i, cliente.toFileFormat());
                identificacionEncontrada = true;
                break;
            }
        }

        if (!identificacionEncontrada) {
            System.out.println("La identificación no fue encontrada en el archivo.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("El archivo ha sido actualizado.");
    }

    public static void actualizarArchivoDestino(String archivo, Destino destino) {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean nombreDestinoEncontrado = false;

        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            String[] partes = linea.split(";");

            if (partes[0].equals(destino.getNombre())) {
                lineas.set(i, destino.toFileFormat());
                nombreDestinoEncontrado = true;
                break;
            }
        }

        if (!nombreDestinoEncontrado) {
            System.out.println("El nombre del destino no fue encontrado en el archivo.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("El archivo ha sido actualizado.");
    }

    public static void eliminarDestino(String archivo, String nombreDestino) {
        List<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean nombreDestinoEncontrado = false;

        Iterator<String> iter = lineas.iterator();
        while (iter.hasNext()) {
            String linea = iter.next();
            String[] partes = linea.split(";");

            if (partes[0].equals(nombreDestino)) {
                iter.remove(); // Eliminar la línea
                nombreDestinoEncontrado = true;
                break;
            }
        }

        if (!nombreDestinoEncontrado) {
            System.out.println("El nombre del destino no fue encontrado en el archivo.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("El destino ha sido eliminado del archivo.");
    }

    /**
     * Serializa un objeto en disco
     * @param ruta Ruta del archivo donde se va a serializar el objeto
     * @param objeto Objeto a serializar
     * @throws IOException
     */
    public static void serializarObjeto(String ruta, Object objeto) throws IOException{
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(ruta));
        os.writeObject(objeto);
        os.close();
    }

    /**
     * Deserializa un objeto que está guardado en disco
     * @param ruta Ruta del archivo a deserializar
     * @return Objeto deserializado
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Object deserializarObjeto(String ruta) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = is.readObject();
        is.close();

        return objeto;
    }

    /**
     * Serializa un objeto en un archivo en formato XML
     * @param ruta Ruta del archivo donde se va a serializar el objeto
     * @param objeto Objeto a serializar
     * @throws FileNotFoundException
     */

}