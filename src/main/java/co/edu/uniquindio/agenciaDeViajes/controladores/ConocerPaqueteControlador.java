package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.enums.Clima;
import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaDeViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConocerPaqueteControlador implements Initializable, CambioIdiomaListener {
    @FXML
    private TextField txtNombreDestino, txtCiudad, txtDescripcion, txtClima, txtCupo, txtFechas, txtDestinos;

    @FXML
    private Button btnAtras, btnAnterior, btnSiguiente;

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
        txtNombreDestino.setPromptText(propiedades.getResourceBundle().getString("TextoNombrePaquete"));
        txtCiudad.setPromptText(propiedades.getResourceBundle().getString("TextoDuracion"));
        txtDescripcion.setPromptText(propiedades.getResourceBundle().getString("TextoServiciosAdicionales"));
        txtClima.setPromptText(propiedades.getResourceBundle().getString("TextoPrecio"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        txtCupo.setPromptText(propiedades.getResourceBundle().getString("TextoCupoMaximo"));
        txtFechas.setPromptText(Propiedades.getInstance().getResourceBundle().getString("TextoFechas"));
        btnAnterior.setText(propiedades.getResourceBundle().getString("TextoAnterior"));
        btnSiguiente.setText(propiedades.getResourceBundle().getString("TextoSiguiente"));
        txtDestinos.setPromptText(propiedades.getResourceBundle().getString("TextoDestinos"));

    }


    public void atras(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicio.fxml", event);
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}

