package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DetallesPaqueteControlador implements Initializable {

    @FXML
    private TextField txtNombrePaquete;

    @FXML
    private TextField txtDuracion;

    @FXML
    private TextField txtServiciosAdicionales;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCupoMaximo;

    @FXML
    private ComboBox<String> cbxDestinos;

    @FXML
    private TextField txtFechaInicio;

    @FXML
    private TextField txtFechaFin;

    @FXML
    private Button btnAnterior;

    @FXML
    private Button btnSiguiente;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField txtNombreDestino;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtClima;

    @FXML
    private Button btnAtras;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private PaqueteTuristico paqueteTuristico;

    private ArrayList<String> rutasImagenes = new ArrayList<>();
    private int indiceImagenActual = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPaqueteTuristico(paqueteTuristico);
        ArrayList<Destino> destinos = paqueteTuristico.getDestinos();
        ObservableList<String> nombresDestinos = FXCollections.observableArrayList();

        for (Destino destino : destinos) {
            nombresDestinos.add(destino.getNombre());
        }

        cbxDestinos.setItems(nombresDestinos);
    }
    public void setPaqueteTuristico(PaqueteTuristico paqueteTuristico) {
        this.paqueteTuristico = agenciaDeViajes.getPaqueteTuristico();
        actualizarCampos(); // Llamamos a este método para llenar los campos inicialmente
    }

    private void actualizarCampos() {
        ArrayList<Destino> destinos = paqueteTuristico.getDestinos();
        String nombresDestinos = destinos.stream().map(Destino :: getNombre).collect(Collectors.joining(", "));
        if (paqueteTuristico != null) {
            txtNombrePaquete.setText(paqueteTuristico.getNombre());
            txtDuracion.setText(paqueteTuristico.getDuracion());
            txtServiciosAdicionales.setText(paqueteTuristico.getServiciosAdicionales());
            txtPrecio.setText(String.valueOf(paqueteTuristico.getPrecio()));
            txtCupoMaximo.setText(String.valueOf(paqueteTuristico.getCupoMaximo()));
            txtFechaInicio.setText(String.valueOf(paqueteTuristico.getFechaInicio()));
            txtFechaFin.setText(String.valueOf(paqueteTuristico.getFechaFin()));

        }
    }

    @FXML
    private void verDestino(){
        Destino destino = agenciaDeViajes.obtenerDestino(cbxDestinos.getValue());
        txtNombreDestino.setText(destino.getNombre());
        txtCiudad.setText(destino.getCiudad());
        txtDescripcion.setText(destino.getDescripcion());
        txtClima.setText(String.valueOf(destino.getClima()));

        rutasImagenes = destino.getImagenes();

        // Mostrar la primera imagen si hay al menos una
        if (!rutasImagenes.isEmpty()) {
            String primeraRutaImagen = rutasImagenes.get(0);
            Image imagen = new Image(primeraRutaImagen); // Cargar imagen desde la ruta
            imageView.setImage(imagen); // Mostrar la imagen en el ImageView
        }

        // Deshabilitar el botón "Anterior" si no hay imágenes anteriores
        btnAnterior.setDisable(indiceImagenActual == 0);

        // Deshabilitar el botón "Siguiente" si no hay imágenes siguientes
        btnSiguiente.setDisable(indiceImagenActual == rutasImagenes.size() - 1);
    }

    @FXML
    private void navegarAnterior() {
        if (indiceImagenActual > 0) {
            indiceImagenActual--;
            mostrarImagenActual();
        }
    }

    @FXML
    private void navegarSiguiente() {
        if (indiceImagenActual < rutasImagenes.size() - 1) {
            indiceImagenActual++;
            mostrarImagenActual();
        }
    }

    private void mostrarImagenActual() {
        String rutaImagen = rutasImagenes.get(indiceImagenActual);
        Image imagen = new Image(rutaImagen);
        imageView.setImage(imagen);

        // Actualizar estado de los botones de navegación
        btnAnterior.setDisable(indiceImagenActual == 0);
        btnSiguiente.setDisable(indiceImagenActual == rutasImagenes.size() - 1);

        System.out.println("Ruta de la imagen: " + rutaImagen);
    }

    public void volverSeleccionarPaquete(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/seleccionarPaquete.fxml", event);
        }
    }
}
