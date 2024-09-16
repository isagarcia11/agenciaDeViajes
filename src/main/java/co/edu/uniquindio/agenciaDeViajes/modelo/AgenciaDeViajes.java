package co.edu.uniquindio.agenciaDeViajes.modelo;

import co.edu.uniquindio.agenciaDeViajes.enums.Clima;
import co.edu.uniquindio.agenciaDeViajes.enums.Estado;
import co.edu.uniquindio.agenciaDeViajes.enums.Idioma;
import co.edu.uniquindio.agenciaDeViajes.exceptions.*;
import co.edu.uniquindio.agenciaDeViajes.utils.ArchivoUtils;
import com.twilio.Twilio;
import javafx.fxml.FXMLLoader;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Getter
@Log
public class AgenciaDeViajes {

    private ArrayList<Cliente> clientes;

    private ArrayList<Destino> destinos;

    private ArrayList<PaqueteTuristico> paquetesTuristicos;

    private ArrayList<GuiaTuristico> guiasTuristicos;

    private ArrayList<Reserva> reservas;

    private ArrayList<BusquedasDestinos> busquedasDestinos;

    private static AgenciaDeViajes agenciaDeViajes;

    private static final String RUTA_CLIENTES = "src/main/resources/persistencia/clientes.ser";

    private static final String RUTA_DESTINOS = "src/main/resources/persistencia/destinos.ser";

    private static final String RUTA_PAQUETES = "src/main/resources/persistencia/paquetes.ser";

    private static final String RUTA_GUIAS = "src/main/resources/persistencia/guias.ser";

    private static final String RUTA_RESERVAS = "src/main/resources/persistencia/reservas.ser";

    private static final String RUTA_BUSQUEDAS = "src/main/resources/persistencia/busquedasDestinos.ser";

    private static Cliente clienteAutenticado;

    private static PaqueteTuristico paqueteTuristico;

    private static GuiaTuristico guiaTuristico;

    private static Reserva reserva;

    private static Destino destino;

    private AgenciaDeViajes(){
        inicializarLogger();
        log.info("Se crea una nueva instancia de la Agencia de viajes");

        this.clientes = new ArrayList<>();
        leerClientes();
        for(Cliente cliente : clientes){
            System.out.println(cliente);
        }

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

        this.reservas = new ArrayList<>();
        leerReservas();
        for(Reserva reserva : reservas){
            System.out.println(reserva);
        }

        this.busquedasDestinos = new ArrayList<>();
        leerBusquedas();
        for(BusquedasDestinos busquedasDestinos1 : busquedasDestinos){
            System.out.println(busquedasDestinos1);
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

    public Cliente registrarCliente(String identificacion, String nombre, String correo, String telefono, String direccion, ArrayList<Destino> destinos) throws AtributoVacioException, InformacionRepetidaException {

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if(obtenerCliente(identificacion, 0) != null ){
            throw new InformacionRepetidaException("La cédula "+identificacion+" ya está registrada");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(correo == null || correo.isBlank()){
            throw new AtributoVacioException("El email es obligatorio");
        }

        if(obtenerCorreo(correo, 0) != null ){
            throw new InformacionRepetidaException("El correo "+correo+" ya está registrado");
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
                .busquedasDestinos(destinos)
                .build();

        clientes.add(cliente);
        escribirCliente();

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

    public Destino registrarDestino(String nombreDestino, String ciudad, String descripcion, String imagen, Clima clima, ArrayList<Integer> calificaciones, ArrayList<String> comentarios) throws AtributoVacioException, InformacionRepetidaException {

        if (nombreDestino == null || nombreDestino.isBlank()) {
            throw new AtributoVacioException("El nombre del destino es obligatorio");
        }

        if(obtenerDestino(nombreDestino, 0) != null ){
            throw new InformacionRepetidaException("El destino "+nombreDestino+" ya está registrado");
        }

        if (ciudad == null || ciudad.isBlank()) {
            throw new AtributoVacioException("La ciudad es obligatoria");
        }

        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción es obligatoria");
        }

        ArrayList<String> imagenesList = obtenerListaDeImagenes(imagen);

        /*
        if(obtenerImagen(imagenesList, imagen, 0) != null ){
            throw new InformacionRepetidaException("La imagen "+imagen+" ya está registrado");

        }
        */

        Destino destino = Destino.builder()
                .nombre(nombreDestino)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .imagenes(imagenesList)
                .clima(clima)
                .calificaciones(calificaciones)
                .comentarios(comentarios)
                .build();

        destinos.add(destino);
        escribirDestino();
        log.info("Se ha registrado el nuevo destino: " + nombreDestino);

        return destino;
    }


    public PaqueteTuristico registrarPaquete(String nombre, ArrayList<Destino> destinos, String duracion, String serviciosAdicionales, float precio, int cupoMaximo, LocalDate fechaInicio, LocalDate fechaFin) throws AtributoVacioException, AtributoNegativoException, FechaInvalidaException, InformacionRepetidaException{

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre del paquete es obligatorio");
        }

        if(obtenerPaqueteNombre(nombre, 0) != null ){
            throw new InformacionRepetidaException("El nombre "+nombre+" ya está registrado");
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

    public Cliente obtenerCliente(String identificacion, int index) {
        if (index >= clientes.size()) {
            return null;  // No se encontró el cliente
        }

        if (clientes.get(index).getIdentificacion().equals(identificacion)) {
            return clientes.get(index);  // Cliente encontrado
        }

        // Llamada recursiva para buscar en el siguiente elemento de la lista
        return obtenerCliente(identificacion, index + 1);
    }

    public Cliente obtenerCorreo(String correo, int index) {
        if (index >= clientes.size()) {
            return null;  // No se encontró el cliente
        }

        if (clientes.get(index).getCorreo().equals(correo)) {
            return clientes.get(index);  // Cliente encontrado
        }

        // Llamada recursiva para buscar en el siguiente elemento de la lista
        return obtenerCorreo(correo, index + 1);
    }

    public Cliente obtenerDestino(String nombreDestino, int index) {
        if (index >= destinos.size()) {
            return null;  // No se encontró el cliente
        }

        if (destinos.get(index).getNombre().equals(nombreDestino)) {
            return clientes.get(index);  // Cliente encontrado
        }

        // Llamada recursiva para buscar en el siguiente elemento de la lista
        return obtenerDestino(nombreDestino, index + 1);
    }

    /*
    public Cliente obtenerImagen(ArrayList<String> imagenesList, String imagen, int index) {
        if (index >= imagenesList.size()) {
            return null;  // No se encontró el cliente
        }

        if (imagenesList.get(index).equals(imagen)) {
            return clientes.get(index);  // Cliente encontrado
        }

        // Llamada recursiva para buscar en el siguiente elemento de la lista
        return obtenerImagen(imagenesList, imagen, index + 1);
    }*/

    public Cliente obtenerPaqueteNombre(String nombrePaquete, int index) {
        if (index >= paquetesTuristicos.size()) {
            return null;  // No se encontró el cliente
        }

        if (paquetesTuristicos.get(index).getNombre().equals(nombrePaquete)) {
            return clientes.get(index);  // Cliente encontrado
        }

        // Llamada recursiva para buscar en el siguiente elemento de la lista
        return obtenerPaqueteNombre(nombrePaquete, index + 1);
    }

    private void escribirCliente(){
        try{
            ArchivoUtils.serializarObjeto(RUTA_CLIENTES, clientes);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    private void leerClientes() {

        try {
            ArrayList<Cliente> lista = (ArrayList<Cliente>) ArchivoUtils.deserializarObjeto(RUTA_CLIENTES);
            if (lista != null) {
                this.clientes = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
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

    public Cliente verificarDatos(String nombre, String identificacion, int index) throws AtributoVacioException {
        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El usuario es obligatorio");
        }

        if (identificacion == null || identificacion.isBlank()) {
            throw new AtributoVacioException("La contraseña es obligatoria");
        }

        if (index >= clientes.size()) {
            return null;  // No se encontró el cliente
        }

        Cliente cliente = clientes.get(index);
        if (nombre.equals(cliente.getNombre()) && identificacion.equals(cliente.getIdentificacion())) {
            return cliente;  // Cliente encontrado
        }

        // Llamada recursiva para buscar en el siguiente elemento de la lista
        return verificarDatos(nombre, identificacion, index + 1);
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

    public GuiaTuristico getGuiaTuristico(){
        return guiaTuristico;
    }

    public void setGuiasTuristicos(GuiaTuristico guia){
        guiaTuristico = guia;
    }



    public void actualizarCliente(String identificacion, String nombre, String correo, String telefono, String direccion, ArrayList<Destino> destinos) throws AtributoVacioException, InformacionRepetidaException {

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La cédula es obligatoria");
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

        for (Cliente cliente : clientes) {
            if (identificacion.equals(cliente.getIdentificacion())) {
                cliente.setIdentificacion(identificacion);
                cliente.setNombre(nombre);
                cliente.setCorreo(correo);
                cliente.setTelefono(telefono);
                cliente.setDireccion(direccion);
                cliente.setBusquedasDestinos(destinos);

                ArchivoUtils.borrarArchivo(RUTA_CLIENTES);
                escribirCliente();

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

    public void actualizarDestino(String nombreDestino, String ciudad, String descripcion, String imagen, Clima clima, ArrayList<Integer> calificaciones, ArrayList<String> comentarios) throws AtributoVacioException, InformacionRepetidaException {

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

        /*
        if(obtenerImagen(imagenesList, imagen, 0) != null ){
            throw new InformacionRepetidaException("La imagen "+imagen+" ya está registrado");
        }
        */

        for(Destino destino : destinos){
            if(nombreDestino.equals(destino.getNombre())){
                destino.setNombre(nombreDestino);
                destino.setCiudad(ciudad);
                destino.setDescripcion(descripcion);
                destino.setImagenes(imagenesList);
                destino.setClima(clima);
                destino.setCalificaciones(calificaciones);
                destino.setComentarios(comentarios);

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

    public void actualizarPaquete(String nombre, ArrayList<Destino> destinos, String duracion, String serviciosAdicionales, float precio, int cupoMaximo, LocalDate fechaInicio, LocalDate fechaFin) throws AtributoVacioException, AtributoNegativoException, FechaInvalidaException {

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

    public GuiaTuristico registrarGuiaTuristico(String nombre, String identificacion, ArrayList<Idioma> idiomas, float experiencia, ArrayList<Integer> calificaciones, ArrayList<String> comentarios) throws AtributoVacioException, InformacionRepetidaException, AtributoNegativoException {

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La identificación es obligatoria");
        }

        if(obtenerId(identificacion, 0) != null ){
            throw new InformacionRepetidaException("La identificacion "+identificacion+" ya está registrada");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(idiomas == null || idiomas.isEmpty()){
            throw new AtributoVacioException("Debe seleccionar al menos un idioma");
        }

        if(experiencia < 0){
            throw new AtributoNegativoException("La experiencia no puede ser negativo");
        }

        // Aquí podrías realizar más validaciones según tus requisitos.

        GuiaTuristico guia = GuiaTuristico.builder()
                .nombre(nombre)
                .identificacion(identificacion)
                .idiomas(idiomas)
                .experiencia(experiencia)
                .calificaciones(calificaciones)
                .comentarios(comentarios)
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


    public GuiaTuristico obtenerGuiaTuristicoNombre(String nombre) {
        for (GuiaTuristico guiaTuristico : guiasTuristicos) {
            if (nombre.equals(guiaTuristico.getNombre())) {
                return guiaTuristico;
            }
        }
        return null;
    }

    public PaqueteTuristico obtenerPaquete(String nombre) {
        for (PaqueteTuristico paqueteTuristico : paquetesTuristicos) {
            if (nombre.equals(paqueteTuristico.getNombre())) {
                return paqueteTuristico;
            }
        }
        return null;
    }

    public Cliente obtenerId(String identificacion, int index) {
        if (index >= guiasTuristicos.size()) {
            return null;  // No se encontró el cliente
        }

        if (guiasTuristicos.get(index).getIdentificacion().equals(identificacion)) {
            return clientes.get(index);  // Cliente encontrado
        }

        // Llamada recursiva para buscar en el siguiente elemento de la lista
        return obtenerId(identificacion, index + 1);
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

    public void actualizarGuiaTuristico(String identificacion, String nombre, ArrayList<Idioma> idiomas, float experiencia, ArrayList<Integer> calificaciones, ArrayList<String> comentarios) throws AtributoVacioException, InformacionRepetidaException, AtributoNegativoException {

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La identificación es obligatoria");
        }


        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(idiomas == null || idiomas.isEmpty()){
            throw new AtributoVacioException("Debe seleccionar al menos un idioma");
        }

        if(experiencia < 0){
            throw new AtributoNegativoException("El cupo no puede ser negativo");
        }

        for(GuiaTuristico guia : guiasTuristicos){
            if(identificacion.equals(guia.getIdentificacion())){
                guia.setNombre(nombre);
                guia.setIdiomas(idiomas);
                guia.setExperiencia(experiencia);
                guia.setCalificaciones(calificaciones);
                guia.setComentarios(comentarios);

                ArchivoUtils.borrarArchivo(RUTA_GUIAS);
                escribirGuiaTuristico();

                log.info("Se ha actualizado al guía turístico con identificación: " + identificacion);
            }
        }
    }

    public Reserva registrarReserva(Cliente cliente, int cantidadDePersonas, PaqueteTuristico paqueteTuristico, GuiaTuristico guiaTuristico, Estado estado, LocalDate fechaDeSolicitud, LocalDate fechaDeViaje) throws CupoMaximoExcedidoException, FechaInvalidaException, AtributoVacioException, AtributoNegativoException, ReservaDuplicadaException{
        int cupoMaximo = paqueteTuristico.getCupoMaximo();

        for (Reserva reservaExistente : agenciaDeViajes.getReservas()) {
            if (reservaExistente.getCliente().equals(cliente) && reservaExistente.getPaqueteTuristico().equals(paqueteTuristico)) {
                throw new ReservaDuplicadaException("Ya tienes una reserva para este paquete.");
            }
        }

        if (cliente == null) {
            throw new AtributoVacioException("El cliente es obligatorio.");
        }

        if (cantidadDePersonas <= 0) {
            throw new AtributoNegativoException("La cantidad de personas debe ser mayor que cero.");
        }

        if (guiaTuristico == null) {
            throw new AtributoVacioException("El guía turístico es obligatorio.");
        }

        if (estado == null) {
            throw new AtributoVacioException("El estado es obligatorio.");
        }

        if (fechaDeSolicitud == null) {
            throw new AtributoVacioException("La fecha de solicitud es obligatoria.");
        }

        if (fechaDeViaje == null) {
            throw new AtributoVacioException("La fecha de viaje es obligatoria.");
        }

        if (cantidadDePersonas > cupoMaximo) {
            throw new CupoMaximoExcedidoException("La cantidad de personas excede el cupo máximo del paquete turístico.");
        }

        if (fechaDeSolicitud.isAfter(fechaDeViaje)) {
            throw new FechaInvalidaException("La fecha de solicitud no puede ser posterior a la fecha del viaje.");
        }

        if (fechaDeSolicitud.equals(paqueteTuristico.getFechaInicio())) {
            throw new FechaInvalidaException("La fecha de solicitud no puede ser el mismo día que la fecha de inicio del paquete.");
        }

        if (fechaDeSolicitud.isAfter(paqueteTuristico.getFechaFin())) {
            throw new FechaInvalidaException("La fecha de solicitud no puede estar después de la fecha de fin del paquete.");
        }

        if (fechaDeSolicitud.isAfter(paqueteTuristico.getFechaInicio()) && fechaDeSolicitud.isBefore(paqueteTuristico.getFechaFin())) {
            throw new FechaInvalidaException("La fecha de solicitud no puede estar entre la fecha de inicio y fin del paquete.");
        }

        if (!fechaDeViaje.equals(paqueteTuristico.getFechaInicio())) {
            throw new FechaInvalidaException("La fecha de viaje debe ser el mismo día que la fecha de inicio del paquete.");
        }

        if (fechaDeViaje.isBefore(paqueteTuristico.getFechaInicio())) {
            throw new FechaInvalidaException("La fecha de viaje no puede estar antes de la fecha de inicio del paquete.");
        }

        if (fechaDeViaje.isAfter(paqueteTuristico.getFechaFin())) {
            throw new FechaInvalidaException("La fecha de viaje no puede estar después de la fecha de fin del paquete.");
        }

        if (fechaDeViaje.isAfter(paqueteTuristico.getFechaInicio()) && fechaDeViaje.isBefore(paqueteTuristico.getFechaFin())) {
            throw new FechaInvalidaException("La fecha de viaje no puede estar entre la fecha de inicio y fin del paquete.");
        }

        Reserva reserva = Reserva.builder()
                .Cliente(cliente)
                .cantidadDePersonas(cantidadDePersonas)
                .paqueteTuristico(paqueteTuristico)
                .guiaTuristico(guiaTuristico)
                .estado(estado)
                .fechaDeSolicitud(fechaDeSolicitud)
                .fechaDeViaje(fechaDeViaje)
                .build();

        reservas.add(reserva);
        escribirReserva();
        log.info("Se ha registrado un nueva reserva para el usuario con la identificación: "+cliente.getIdentificacion());

        return reserva;
    }

    private void escribirReserva() {
        try{
            ArchivoUtils.serializarObjeto(RUTA_RESERVAS, reservas);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    private void leerReservas() {

        try {
            ArrayList<Reserva> lista = (ArrayList<Reserva>) ArchivoUtils.deserializarObjeto(RUTA_RESERVAS);
            if (lista != null) {
                this.reservas = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }
    }

    public void actualizarReserva(){
        ArchivoUtils.borrarArchivo(RUTA_RESERVAS);
        escribirReserva();
    }

    public void actualizarCupoPaquete(PaqueteTuristico paquete, int cantidadReserva) throws AtributoVacioException, AtributoNegativoException, FechaInvalidaException, InformacionRepetidaException {
        int nuevoCupo = paquete.getCupoMaximo() - cantidadReserva;
        paquete.setCupoMaximo(nuevoCupo);
        actualizarPaquete(paquete.getNombre(), paquete.getDestinos(), paquete.getDuracion(), paquete.getServiciosAdicionales(), paquete.getPrecio(), nuevoCupo, paquete.getFechaInicio(), paquete.getFechaFin());
    }

    public ArrayList<Reserva> obtenerReservasDeCliente() {
        ArrayList<Reserva> reservasCliente = new ArrayList<>();

        for (Reserva reserva : reservas) {
            if (reserva.getCliente().equals(clienteAutenticado)) {
                reservasCliente.add(reserva);
            }

        }

        return reservasCliente;
    }

    public String generarMensajeReserva(Reserva reserva) {
        String mensaje = "¡Hola " + reserva.getCliente().getNombre() + "!\n\n" +
                "Te confirmamos que tu reserva ha sido registrada con éxito.\n" +
                "Aquí están los detalles de tu reserva:\n\n" +
                "Nombre del Cliente: " + reserva.getCliente().getNombre() + "\n" +
                "Cantidad de Personas: " + reserva.getCantidadDePersonas() + "\n" +
                "Paquete Turístico: " + reserva.getPaqueteTuristico().getNombre() + "\n" +
                "Guía Turístico: " + reserva.getGuiaTuristico().getNombre() + "\n" +
                "Estado de la Reserva: " + reserva.getEstado() + "\n" +
                "Fecha de Solicitud: " + reserva.getFechaDeSolicitud() + "\n" +
                "Fecha de Viaje: " + reserva.getFechaDeViaje() + "\n\n" +
                "¡Esperamos que tengas un increíble viaje!\n" +
                "Gracias por elegir nuestra agencia de viajes.";

        return mensaje;
    }


    public void enviarCorreoReserva(Reserva reserva, String mensaje) {
        try {
            // Configurar propiedades del servidor de correo
            Properties props = ConfiguracionCorreo.obtenerPropiedadesCorreo();

            // Crear una sesión de correo
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
                        }
                    });

            // Crear el mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("mail.user")));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(clienteAutenticado.getCorreo()));
            message.setSubject("Detalles de tu reserva");

            // Establecer el contenido del mensaje
            message.setText(mensaje);

            // Enviar el correo electrónico
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace(); // Manejo de errores al enviar el correo
        }
    }

    public boolean fechaFinPaquetePasada(PaqueteTuristico paquete) {
        LocalDate fechaActual = LocalDate.now();
        return fechaActual.isAfter(paquete.getFechaFin());
    }

    public Reserva getReserva() {
        return reserva;
    }
    public void setReserva(Reserva reserva1){
        reserva = reserva1;
    }

    public BusquedasDestinos registrarBusqueda(BusquedasDestinos busquedaDestino){
        busquedasDestinos.add(busquedaDestino);
        escribirBusqueda();
        return busquedaDestino;
    }

    private void escribirBusqueda() {
        try{
            ArchivoUtils.serializarObjeto(RUTA_BUSQUEDAS, busquedasDestinos);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }

    private void leerBusquedas() {

        try {
            ArrayList<BusquedasDestinos> lista = (ArrayList<BusquedasDestinos>) ArchivoUtils.deserializarObjeto(RUTA_BUSQUEDAS);
            if (lista != null) {
                this.busquedasDestinos = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }
    }

    public ArrayList<PaqueteTuristico> buscarPaquetesPorDestino() {
        ArrayList<PaqueteTuristico> paquetesCoincidentes = new ArrayList<>();

        for (PaqueteTuristico paquete : paquetesTuristicos) {
            ArrayList<Destino> destinosPaquete = paquete.getDestinos();

            for (Destino destino : clienteAutenticado.getBusquedasDestinos()) {
                if (destinosPaquete.contains(destino)) {
                    paquetesCoincidentes.add(paquete);
                    break; // Puede que solo quieras agregar el paquete una vez
                }
            }
        }

        for (PaqueteTuristico paqueteTuristico1 : paquetesCoincidentes){
            System.out.println(paqueteTuristico1);
        }

        return paquetesCoincidentes;
    }

    public boolean reservaFinalizada(Reserva reserva) {
        LocalDate fechaActual = LocalDate.now();
        return reserva.getPaqueteTuristico().getFechaFin().isBefore(fechaActual);
    }

    public void enviarMensajeSMS() {
        Twilio.init(EnviarSMS.ACCOUNT_SID, EnviarSMS.AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                        new com.twilio.type.PhoneNumber("+573007590341"),
                        new com.twilio.type.PhoneNumber("+16573320921"),
                        "El cliente: " + clienteAutenticado.getNombre() + " ha realizo una reserva")
                .create();

        System.out.println(message.getSid());
    }

    public Destino getDestino() {
        return destino;
    }
    public void setDestino(Destino destino1){
        destino = destino1;
    }

    private boolean esPrimeraReserva() {
        for (Reserva reservaExistente : reservas) {
            if (reservaExistente.getCliente().equals(clienteAutenticado)) {
                return false;
            }
        }
        return true;
    }

    public void procesarReserva(Reserva reserva) {
        if (!esPrimeraReserva()) {

            double precioConDescuento = reserva.getPaqueteTuristico().getPrecio() * 0.8;

            String mensajeFactura = "¡Hola " + reserva.getCliente().getNombre() + "!\n\n" +
                    "Te informamos que tu reserva ha sido confirmada con éxito.\n" +
                    "Aquí están los detalles de tu reserva:\n\n" +
                    "Por ser la primera reserva con nuestra agencia de viajes te obsequiamos un excelente bono de descuento: " + "\n" +
                    "Nombre del Cliente: " + reserva.getCliente().getNombre() + "\n" +
                    "Cantidad de Personas: " + reserva.getCantidadDePersonas() + "\n" +
                    "Paquete Turístico: " + reserva.getPaqueteTuristico().getNombre() + "\n" +
                    "Precio Original del Paquete: " + reserva.getPaqueteTuristico().getPrecio() + "\n" +
                    "Descuento del 20%: -" + (reserva.getPaqueteTuristico().getPrecio() - precioConDescuento) + "\n" +
                    "Precio con Descuento: " + precioConDescuento + "\n" +
                    "Guía Turístico: " + reserva.getGuiaTuristico().getNombre() + "\n" +
                    "Estado de la Reserva: " + reserva.getEstado() + "\n" +
                    "Fecha de Solicitud: " + reserva.getFechaDeSolicitud() + "\n" +
                    "Fecha de Viaje: " + reserva.getFechaDeViaje() + "\n\n" +
                    "¡Esperamos que tengas un increíble viaje!\n" +
                    "Gracias por elegir nuestra agencia de viajes.";

            enviarCorreoReserva(reserva, mensajeFactura);
        } else {
            String mensaje = "¡Hola " + reserva.getCliente().getNombre() + "!\n\n" +
                    "Te informamos que tu reserva ha sido confirmada con éxito.\n" +
                    "Aquí están los detalles de tu reserva:\n\n" +
                    "Nombre del Cliente: " + reserva.getCliente().getNombre() + "\n" +
                    "Cantidad de Personas: " + reserva.getCantidadDePersonas() + "\n" +
                    "Paquete Turístico: " + reserva.getPaqueteTuristico().getNombre() + "\n" +
                    "Precio del Paquete: " + reserva.getPaqueteTuristico().getPrecio() + "\n" +
                    "Guía Turístico: " + reserva.getGuiaTuristico().getNombre() + "\n" +
                    "Estado de la Reserva: " + reserva.getEstado() + "\n" +
                    "Fecha de Solicitud: " + reserva.getFechaDeSolicitud() + "\n" +
                    "Fecha de Viaje: " + reserva.getFechaDeViaje() + "\n\n" +
                    "¡Esperamos que tengas un increíble viaje!\n" +
                    "Gracias por elegir nuestra agencia de viajes.";
            enviarCorreoReserva(reserva, mensaje);
        }
    }
}
