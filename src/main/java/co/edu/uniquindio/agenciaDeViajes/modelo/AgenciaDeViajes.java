package co.edu.uniquindio.agenciaDeViajes.modelo;

import co.edu.uniquindio.agenciaDeViajes.exceptions.*;
import co.edu.uniquindio.agenciaDeViajes.utils.ArchivoUtils;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

@Getter
@Log
public class AgenciaDeViajes {

    private final ArrayList<Cliente> clientes;

    private static AgenciaDeViajes agenciaDeViajes;

    private static final String RUTA_CLIENTES = "src/main/resources/persistencia/clientes.txt";

    private AgenciaDeViajes(){
        inicializarLogger();
        log.info("Se crea una nueva instancia de la Agencia de viajes");

        this.clientes = new ArrayList<>();
        leerClientes();
    }

    private void inicializarLogger(){
        try{
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter(new SimpleFormatter());
            log.addHandler(fh);
        }catch (IOException e){
          log.severe(e.getMessage());
        }
    }

    public static AgenciaDeViajes getInstance() {
        if(agenciaDeViajes == null){
            agenciaDeViajes = new AgenciaDeViajes();
        }

        return agenciaDeViajes;
    }

    public Cliente registrarCliente(String identificacion, String nombre, String correo, String telefono, String direccion) throws AtributoVacioException, InformacionRepetidaException {

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if(obtenerCliente(identificacion) != null ){
            throw new InformacionRepetidaException("La cédula "+identificacion+" ya está registrada");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(correo == null || correo.isBlank()){
            throw new AtributoVacioException("El email es obligatorio");
        }

        if(telefono == null || telefono.isBlank()){
            throw new AtributoVacioException("El telefono es obliagatorio");
        }

        if(direccion == null || direccion.isBlank()){
            throw new AtributoVacioException("La direccion es obligatoria");
        }

        Cliente cliente = Cliente.builder()
                .identificacion(identificacion)
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .direccion(direccion)
                .build();

        clientes.add(cliente);
        escribirCliente(cliente);

        log.info("Se ha registrado un nuevo cliente con la cedula: "+identificacion);

        return cliente;
    }

    public Cliente obtenerCliente(String identificacion){
        return clientes.stream().filter(c -> c.getIdentificacion().equals(identificacion)).findFirst().orElse(null);
    }

    private void escribirCliente(Cliente cliente){
        try {
            String linea = cliente.getIdentificacion()+";"+cliente.getNombre()+";"+cliente.getCorreo()+";"+cliente.getTelefono()+";"+cliente.getDireccion();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_CLIENTES, List.of(linea), true);
        }catch (IOException e){
            log.severe(e.getMessage());
        }
    }

    private void leerClientes() {

        try{

            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTA_CLIENTES);

            for(String linea : lineas){

                String[] valores = linea.split(";");
                this.clientes.add( Cliente.builder()
                        .identificacion(valores[0])
                        .nombre(valores[1])
                        .correo(valores[2])
                        .telefono(valores[3])
                        .direccion(valores[4])
                        .build());
            }

        }catch (IOException e){
            log.severe(e.getMessage());
        }

    }

    public boolean verificarDatos(String nombre, String identificacion) throws AtributoVacioException{

        boolean state = false;

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El usuario es obligatorio");
        }

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La contraseña es obligatoria");
        }

        for (Cliente cliente : clientes) {
            if (nombre.equals(cliente.getNombre()) && identificacion.equals(cliente.getIdentificacion())) {
                state = true;
                break;
            }
        }
        return state;
    }

    public void loadStage(String url, Event event){

        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();

            Parent root = FXMLLoader.load(Objects.requireNonNull(AgenciaDeViajes.class.getResource(url)));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Agencia De Viajes");
            newStage.show();

        } catch (Exception ignored) {

        }
    }
}
