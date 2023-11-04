package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;


public class InicioControlador implements Initializable {

    @FXML
    private Button iniciarSesion, cambiarIdioma;

    @FXML
    private Label banner;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    private boolean esIngles = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        actualizarTextos();
    }

    @FXML
    private void cambiarIdioma(ActionEvent event) {
        // Cambia el idioma
        if (esIngles) {
            Propiedades.getInstance().setLocale(new Locale("es", "ES"));
        } else {
            Propiedades.getInstance().setLocale(Locale.ENGLISH);
        }

        // Invierte el valor de esIngles para la próxima vez
        esIngles = !esIngles;

        // Actualiza la interfaz de usuario
        actualizarTextos();
    }

    private void actualizarTextos() {
        iniciarSesion.setText(propiedades.getResourceBundle().getString("TextoIniciarSesion"));
        cambiarIdioma.setText(propiedades.getResourceBundle().getString("TextoCambiarIdioma"));
        banner.setText(propiedades.getResourceBundle().getString("TextoBanner"));
    }


    public void iniciarSesion(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(iniciarSesion)){
            agenciaDeViajes.loadStage("/ventanas/login.fxml", event);
        }
    }

}
