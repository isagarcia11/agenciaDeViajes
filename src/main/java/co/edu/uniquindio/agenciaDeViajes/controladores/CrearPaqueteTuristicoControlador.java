package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CrearPaqueteTuristicoControlador implements Initializable, CambioIdiomaListener {

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
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();

        // Obtener la lista de destinos de la agencia de viajes
        ObservableList<String> nombresDestinos = FXCollections.observableArrayList();

        for (Destino destino : agenciaDeViajes.getDestinos()) {
            nombresDestinos.add(destino.getNombre());
        }

        cbxDestinos.setItems(nombresDestinos);
    }
    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }

    private void actualizarTextos() {
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
            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoPaqueteTuristico")+txtNombrePaquete.getText());
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
                        mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoPaqueteTuristico1")+nombreSeleccionado+propiedades.getResourceBundle().getString("TextoPaqueteTuristico2")+txtNombrePaquete.getText());
                    } else {
                        mostrarMensaje(Alert.AlertType.ERROR, propiedades.getResourceBundle().getString("TextoPaqueteTuristico3")+nombreSeleccionado+propiedades.getResourceBundle().getString("TextoPaqueteTuristico4"));
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
