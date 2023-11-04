package co.edu.uniquindio.agenciaDeViajes.modelo;

import co.edu.uniquindio.agenciaDeViajes.enums.Clima;
import co.edu.uniquindio.agenciaDeViajes.enums.Idioma;
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
import java.time.LocalDate;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
@Getter
@Log
public class AgenciaDeViajes {

    private final ArrayList<Cliente> clientes;

    private ArrayList<Destino> destinos;

    private ArrayList<PaqueteTuristico> paquetesTuristicos;

    private ArrayList<GuiaTuristico> guiasTuristicos;

    private static AgenciaDeViajes agenciaDeViajes;

    private static final String RUTA_CLIENTES = "src/main/resources/persistencia/clientes.txt";

    private static final String RUTA_DESTINOS = "src/main/resources/persistencia/destinos.ser";

    private static final String RUTA_PAQUETES = "src/main/resources/persistencia/paquetes.ser";

    private static final String RUTA_GUIAS = "src/main/resources/persistencia/guias.ser";

    private static Cliente clienteAutenticado;

    private static PaqueteTuristico paqueteTuristico;

    private static GuiaTuristico guiaTuristico;

    private AgenciaDeViajes(){
        inicializarLogger();
        log.info("Se crea una nueva instancia de la Agencia de viajes");

        this.clientes = new ArrayList<>();
        leerClientes();

        this.destinos = new ArrayList<>();
        leerDestinos();
        for(Destino destino : destinos){
            System.out.println(destino);
        }

        this.paquetesTuristicos = new ArrayList<>();
        leerPaquetes();
        for(PaqueteTuristico paqueteTuristico : paquetesTuristicos){
            System.out.println(paqueteTuristico);
        }

        this.guiasTuristicos = new ArrayList<>();
        leerGuias();
        for(GuiaTuristico guiaTuristico : guiasTuristicos){
            System.out.println(guiaTuristico);
        }
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

    private ArrayList<String> obtenerListaDeImagenes(String imagen) throws AtributoVacioException {
        if (imagen == null || imagen.isBlank()) {
            throw new AtributoVacioException("La imagen es obligatoria");
        }

        String[] imagenesArray = imagen.split(","); // Divide la cadena en un arreglo de strings usando la coma como delimitador
        return new ArrayList<>(Arrays.asList(imagenesArray)); // Convierte el arreglo en un ArrayList
    }

    public Destino registrarDestino(String nombreDestino, String ciudad, String descripcion, String imagen, Clima clima) throws AtributoVacioException {
        if (nombreDestino == null || nombreDestino.isBlank()) {
            throw new AtributoVacioException("El nombre del destino es obligatorio");
        }

        if (ciudad == null || ciudad.isBlank()) {
            throw new AtributoVacioException("La ciudad es obligatoria");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción es obligatoria");
        }

        ArrayList<String> imagenesList = obtenerListaDeImagenes(imagen);

        Destino destino = Destino.builder()
                .nombre(nombreDestino)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .imagenes(imagenesList)
                .clima(clima)
                .build();

        destinos.add(destino);
        escribirDestino();
        log.info("Se ha registrado el nuevo destino: " + nombreDestino);

        return destino;
    }


    public PaqueteTuristico registrarPaquete(String nombre, ArrayList<Destino> destinos, String duracion, String serviciosAdicionales, float precio, int cupoMaximo, LocalDate fechaInicio, LocalDate fechaFin) throws AtributoVacioException, AtributoNegativoException, FechaInvalidaException{

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre del paquete es obligatorio");
        }

        if(duracion == null || duracion.isBlank()){
            throw new AtributoVacioException("La duracion del paquete es obligatoria");
        }

        if(serviciosAdicionales == null || serviciosAdicionales.isBlank()){
            throw new AtributoVacioException("Los servicios adicionales son obligatorios");
        }

        if(precio < 0){
            throw new AtributoNegativoException("El precio no puede ser negativo");
        }

        if(cupoMaximo < 0){
            throw new AtributoNegativoException("El cupo no puede ser negativo");
        }

        if(fechaInicio == null){
            throw new AtributoVacioException("Debe elegir una fecha de inicio del alquiler");
        }

        if(fechaFin == null){
            throw new AtributoVacioException("Debe elegir una fecha de fin del alquiler");
        }

        if(fechaInicio.isAfter(fechaFin)){
            log.severe("La fecha de inicio no puede ser después de la fecha final");
            throw new FechaInvalidaException("La fecha de inicio no puede ser después de la fecha final");
        }


        PaqueteTuristico paquete = PaqueteTuristico.builder()
                .nombre(nombre)
                .destinos(destinos)
                .duracion(duracion)
                .serviciosAdicionales(serviciosAdicionales)
                .precio(precio)
                .cupoMaximo(cupoMaximo)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .build();

        paquetesTuristicos.add(paquete);
        escribirPaquete();
        log.info("Se ha registrado el nuevo paquete: "+nombre);

        return paquete;
    }

    private void escribirPaquete(){
        try{
            ArchivoUtils.serializarObjeto(RUTA_PAQUETES, paquetesTuristicos);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    private void leerPaquetes() {

        try {
            ArrayList<PaqueteTuristico> lista = (ArrayList<PaqueteTuristico>) ArchivoUtils.deserializarObjeto(RUTA_PAQUETES);
            if (lista != null) {
                this.paquetesTuristicos = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }
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

    private void escribirDestino(){
        try{
            ArchivoUtils.serializarObjeto(RUTA_DESTINOS, destinos);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    private void leerDestinos() {

        try {
            ArrayList<Destino> lista = (ArrayList<Destino>) ArchivoUtils.deserializarObjeto(RUTA_DESTINOS);
            if (lista != null) {
                this.destinos = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }
    }

    public Cliente verificarDatos(String nombre, String identificacion) throws AtributoVacioException{

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El usuario es obligatorio");
        }

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La contraseña es obligatoria");
        }

        for (Cliente cliente : clientes) {
            if (nombre.equals(cliente.getNombre()) && identificacion.equals(cliente.getIdentificacion())) {
                return cliente;
            }
        }
        return null;
    }

    public Cliente getClienteAutenticado() {
        return clienteAutenticado;
    }
    public void setClienteAutenticado(Cliente cliente){
        clienteAutenticado = cliente;
    }

    public PaqueteTuristico getPaqueteTuristico() {
        return paqueteTuristico;
    }
    public void setPaquetesTuristicos(PaqueteTuristico paquete){
        paqueteTuristico = paquete;
    }

    public GuiaTuristico guiaTuristico(){
        return guiaTuristico;
    }

    public void setGuiasTuristicos(GuiaTuristico guia){
        guiaTuristico = guia;
    }



    public void actualizarCliente(String identificacion, String nombre, String correo, String telefono, String direccion) throws AtributoVacioException, InformacionRepetidaException {

        if (identificacion == null || identificacion.isBlank()) {
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if (correo == null || correo.isBlank()) {
            throw new AtributoVacioException("El email es obligatorio");
        }

        if (telefono == null || telefono.isBlank()) {
            throw new AtributoVacioException("El telefono es obliagatorio");
        }

        if (direccion == null || direccion.isBlank()) {
            throw new AtributoVacioException("La direccion es obligatoria");
        }

        for (Cliente cliente : clientes) {
            if (identificacion.equals(cliente.getIdentificacion())) {
                cliente.setIdentificacion(identificacion);
                cliente.setNombre(nombre);
                cliente.setCorreo(correo);
                cliente.setTelefono(telefono);
                cliente.setDireccion(direccion);

                ArchivoUtils.actualizarArchivoCliente(RUTA_CLIENTES, cliente);

                log.info("Se ha actualizado al cliente con la cedula: " + identificacion);

            }
        }
    }

    public Destino obtenerDestino(String nombreDestino){
        for (Destino destino : destinos) {
            if (nombreDestino.equals(destino.getNombre())) {
                return destino;
            }
        }
        return null;
    }

    public void eliminarDestino(String nombreDestino){

        destinos.removeIf(objeto -> objeto.getNombre().equals(nombreDestino));
        ArchivoUtils.borrarArchivo(RUTA_DESTINOS);
        escribirDestino();
        log.info("Se ha borrado el destino: "+nombreDestino);
        // Eliminar el destino de los paquetes turísticos
        agenciaDeViajes.eliminarDestinoDePaquetes(nombreDestino);
    }

    public void eliminarGuiaTuristico(String identificacion) {
        guiasTuristicos.removeIf(guia -> guia.getIdentificacion().equals(identificacion));
        ArchivoUtils.borrarArchivo(RUTA_GUIAS);
        escribirGuiaTuristico();
        log.info("Se ha borrado el guía turístico con identificación: " + identificacion);
    }


    public void eliminarDestinoDePaquetes(String nombreDestino) {
        for (PaqueteTuristico paquete : paquetesTuristicos) {
            ArrayList<Destino> destinosPaquete = paquete.getDestinos();
            destinosPaquete.removeIf(destino -> destino.getNombre().equals(nombreDestino));
        }
    }

    public void actualizarDestino(String nombreDestino, String ciudad, String descripcion, String imagen, Clima clima) throws AtributoVacioException {

        if(nombreDestino == null || nombreDestino.isBlank()){
            throw new AtributoVacioException("El nombre del destino es obligatorio");
        }

        if(ciudad == null || ciudad.isBlank()){
            throw new AtributoVacioException("La ciudad es obligatoria");
        }

        if(descripcion == null || descripcion.isBlank()){
            throw new AtributoVacioException("La descripción es obligatoria");
        }

        ArrayList<String> imagenesList = obtenerListaDeImagenes(imagen);

        for(Destino destino : destinos){
            if(nombreDestino.equals(destino.getNombre())){
                destino.setNombre(nombreDestino);
                destino.setCiudad(ciudad);
                destino.setDescripcion(descripcion);
                destino.setImagenes(imagenesList);
                destino.setClima(clima);

                ArchivoUtils.borrarArchivo(RUTA_DESTINOS);
                escribirDestino();

                log.info("Se ha actualizado al destino con nombre: " + nombreDestino);

            }
        }
    }

    public void eliminarPaquete(String nombrePaquete){

        paquetesTuristicos.removeIf(objeto -> objeto.getNombre().equals(nombrePaquete));
        ArchivoUtils.borrarArchivo(RUTA_PAQUETES);
        escribirPaquete();
        log.info("Se ha eliminado el paquete: "+nombrePaquete);
    }

    public void actualizarPaquete(String nombre, ArrayList<Destino> destinos, String duracion, String serviciosAdicionales, float precio, int cupoMaximo, LocalDate fechaInicio, LocalDate fechaFin) throws AtributoVacioException, AtributoNegativoException, FechaInvalidaException{

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre del paquete es obligatorio");
        }

        if(duracion == null || duracion.isBlank()){
            throw new AtributoVacioException("La duracion del paquete es obligatoria");
        }

        if(serviciosAdicionales == null || serviciosAdicionales.isBlank()){
            throw new AtributoVacioException("Los servicios adicionales son obligatorios");
        }

        if(precio < 0){
            throw new AtributoNegativoException("El precio no puede ser negativo");
        }

        if(cupoMaximo < 0){
            throw new AtributoNegativoException("El cupo no puede ser negativo");
        }

        if(fechaInicio == null){
            throw new AtributoVacioException("Debe elegir una fecha de inicio del alquiler");
        }

        if(fechaFin == null){
            throw new AtributoVacioException("Debe elegir una fecha de fin del alquiler");
        }

        if(fechaInicio.isAfter(fechaFin)){
            log.severe("La fecha de inicio no puede ser después de la fecha final");
            throw new FechaInvalidaException("La fecha de inicio no puede ser después de la fecha final");
        }


        for(PaqueteTuristico paqueteTuristico : paquetesTuristicos) {
            if (nombre.equals(paqueteTuristico.getNombre())) {
                paqueteTuristico.setNombre(nombre);
                paqueteTuristico.setDestinos(destinos);
                paqueteTuristico.setDuracion(duracion);
                paqueteTuristico.setServiciosAdicionales(serviciosAdicionales);
                paqueteTuristico.setPrecio(precio);
                paqueteTuristico.setCupoMaximo(cupoMaximo);
                paqueteTuristico.setFechaInicio(fechaInicio);
                paqueteTuristico.setFechaFin(fechaFin);

                ArchivoUtils.borrarArchivo(RUTA_PAQUETES);
                escribirPaquete();
                log.info("Se ha actualizado al paquete con nombre: " + nombre);
            }
        }
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PaqueteTuristico obtenerPaquete(String nombrePaquete){
        for (PaqueteTuristico paqueteTuristico : paquetesTuristicos) {
            if (nombrePaquete.equals(paqueteTuristico.getNombre())) {
                return paqueteTuristico;
            }
        }
        return null;
    }

    public ArrayList<String> obtenerRutasDeImagenes(Destino destino) {
        ArrayList<Destino> destinos = new ArrayList<>();
        destinos.add(destino);
        ArrayList<String> rutas = new ArrayList<>();

        for (Destino destino1 : destinos) {
            ArrayList<String> rutasDestino = destino1.getImagenes();
            rutas.addAll(rutasDestino);

            // Imprimir rutasDestino
            for (String ruta : rutasDestino) {
                System.out.println(ruta);
            }
        }
        return rutas;
    }

    public GuiaTuristico registrarGuiaTuristico(String nombre, String identificacion, ArrayList<Idioma> idiomas, float experiencia) throws AtributoVacioException, InformacionRepetidaException {

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La identificación es obligatoria");
        }

        if(obtenerGuiaTuristico(identificacion) != null ){
            throw new InformacionRepetidaException("La identificación "+identificacion+" ya está registrada");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(idiomas == null || idiomas.isEmpty()){
            throw new AtributoVacioException("Debe seleccionar al menos un idioma");
        }

        // Aquí podrías realizar más validaciones según tus requisitos.

        GuiaTuristico guia = GuiaTuristico.builder()
                .nombre(nombre)
                .identificacion(identificacion)
                .idiomas(idiomas)
                .experiencia(experiencia)
                .build();

        guiasTuristicos.add(guia);

        escribirGuiaTuristico();

        log.info("Se ha registrado un nuevo guía turístico con la identificación: "+identificacion);

        return guia;
    }

    public GuiaTuristico obtenerGuiaTuristico(String identificacion) {
        for (GuiaTuristico guiaTuristico : guiasTuristicos) {
            if (identificacion.equals(guiaTuristico.getIdentificacion())) {
                return guiaTuristico;
            }
        }
        return null;
    }

    private void escribirGuiaTuristico() {
        try{
            ArchivoUtils.serializarObjeto(RUTA_GUIAS, guiasTuristicos);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    private void leerGuias() {

        try {
            ArrayList<GuiaTuristico> lista = (ArrayList<GuiaTuristico>) ArchivoUtils.deserializarObjeto(RUTA_GUIAS);
            if (lista != null) {
                this.guiasTuristicos = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }
    }

    public void actualizarGuiaTuristico(String identificacion, String nombre, ArrayList<Idioma> idiomas, float experiencia) throws AtributoVacioException {
        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La identificación es obligatoria");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(idiomas == null || idiomas.isEmpty()){
            throw new AtributoVacioException("Debe seleccionar al menos un idioma");
        }

        // Buscar el guía turístico por identificación
        for(GuiaTuristico guia : guiasTuristicos){
            if(identificacion.equals(guia.getIdentificacion())){
                guia.setNombre(nombre);
                guia.setIdiomas(idiomas);
                guia.setExperiencia(experiencia);

                ArchivoUtils.borrarArchivo(RUTA_GUIAS);
                escribirGuiaTuristico();

                log.info("Se ha actualizado al guía turístico con identificación: " + identificacion);
            }
        }
    }


}
