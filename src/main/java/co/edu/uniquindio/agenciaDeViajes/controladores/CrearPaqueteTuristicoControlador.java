package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CrearPaqueteTuristicoControlador implements Initializable {

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
    private DatePicker dpFechaInicio;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private ComboBox<String> cbxDestinos;

    @FXML
    private Button btnAgregarDestino;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnAtras;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    public ArrayList<Destino> destinosEncontrados = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtNombrePaquete.setPromptText(propiedades.getResourceBundle().getString("TextoNombrePaquete"));
        txtDuracion.setPromptText(propiedades.getResourceBundle().getString("TextoDuracion"));
        txtCupoMaximo.setPromptText(propiedades.getResourceBundle().getString("TextoCupoMaximo"));
        txtPrecio.setPromptText(propiedades.getResourceBundle().getString("TextoPrecio"));
        txtServiciosAdicionales.setPromptText(propiedades.getResourceBundle().getString("TextoServiciosAdicionales"));
        btnAgregarDestino.setText(propiedades.getResourceBundle().getString("TextoAgregarDestino"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        btnGuardar.setText(propiedades.getResourceBundle().getString("TextoGuardar"));
        dpFechaInicio.setPromptText(propiedades.getResourceBundle().getString("TextoFechaInicio"));
        dpFechaFin.setPromptText(propiedades.getResourceBundle().getString("TextoFechaFin"));
        // Obtener la lista de destinos de la agencia de viajes
        ObservableList<String> nombresDestinos = FXCollections.observableArrayList();

        for (Destino destino : agenciaDeViajes.getDestinos()) {
            nombresDestinos.add(destino.getNombre());
        }

        cbxDestinos.setItems(nombresDestinos);
    }

    @FXML
    void regresarInicio(ActionEvent event) {
        // Implementa la lógica para regresar a la pantalla de inicio
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }

    @FXML
    void registrarPaqueteTuristico(ActionEvent event) {
        // Implementa la lógica para registrar el paquete turístico
        try{
            PaqueteTuristico paqueteTuristico = agenciaDeViajes.registrarPaquete(
                    txtNombrePaquete.getText(),
                    destinosEncontrados,
                    txtDuracion.getText(),
                    txtServiciosAdicionales.getText(),
                    Float.parseFloat(txtPrecio.getText()),
                    Integer.parseInt(txtCupoMaximo.getText()),
                    dpFechaInicio.getValue(),
                    dpFechaFin.getValue()
            );
            mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha creado el paquete "+txtNombrePaquete.getText());
        }catch (Exception e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    void buscarDestino(ActionEvent event) {
        String nombreSeleccionado = cbxDestinos.getValue(); // Obtiene el nombre seleccionado del ComboBox

        if (nombreSeleccionado != null) {
            for (Destino destino : agenciaDeViajes.getDestinos()) {
                if (destino.getNombre().equals(nombreSeleccionado)) {
                    if (!destinosEncontrados.contains(destino)) {
                        destinosEncontrados.add(destino);
                        mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha asignado el destino "+nombreSeleccionado+" al paquete "+txtNombrePaquete.getText());
                    } else {
                        mostrarMensaje(Alert.AlertType.ERROR, "El destino "+nombreSeleccionado+" ya hace parte del paquete");
                    }
                }
            }
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

}
