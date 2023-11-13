package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaDeViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistrarClienteControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private TextField txtIdentificacion, txtNombre, txtCorreo, txtTelefono, txtDireccion;

    @FXML
    private Button btnGuardar, btnRegresar;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();

    }
    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }

    private void actualizarTextos() {
        txtNombre.setPromptText(propiedades.getResourceBundle().getString("TextoNombreCompleto"));
        txtIdentificacion.setPromptText(propiedades.getResourceBundle().getString("TextoIdentificacion"));
        txtCorreo.setPromptText(propiedades.getResourceBundle().getString("TextoCorreoElectronico"));
        txtDireccion.setPromptText(propiedades.getResourceBundle().getString("TextoDireccion"));
        txtTelefono.setPromptText(propiedades.getResourceBundle().getString("TextoTelefono"));
        btnGuardar.setText(propiedades.getResourceBundle().getString("TextoGuardar"));
        btnRegresar.setText(propiedades.getResourceBundle().getString("TextoRegresar"));

    }

    public void registrarCliente(ActionEvent actionEvent){
        ArrayList<Destino> destinos = new ArrayList<>();
        try{
            Cliente cliente = agenciaDeViajes.registrarCliente(
                    txtIdentificacion.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtTelefono.getText(),
                    txtDireccion.getText(),
                    destinos
            );

            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoCliente1")+cliente.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void regresarLogin(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnRegresar)){
            agenciaDeViajes.loadStage("/ventanas/login.fxml", event);
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

}
