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
    private Button iniciarSesion, cambiarIdioma, btnSantorini,btnRoma,btnParis, btnBali;

    @FXML
    private Label banner, banner3;

    @FXML
    private Button btnPaquete1, btnPaquete2;

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
        banner3.setText(propiedades.getResourceBundle().getString("TextoBanner3"));
        btnBali.setText(propiedades.getResourceBundle().getString("TextoBali"));
        btnParis.setText(propiedades.getResourceBundle().getString("TextoParis"));
        btnRoma.setText(propiedades.getResourceBundle().getString("TextoRoma"));
        btnSantorini.setText(propiedades.getResourceBundle().getString("TextoSantorini"));
        btnPaquete1.setText(propiedades.getResourceBundle().getString("TextoPaquete1"));
        btnPaquete2.setText(propiedades.getResourceBundle().getString("TextoPaquete2"));
    }


    public void iniciarSesion(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(iniciarSesion)){
            agenciaDeViajes.loadStage("/ventanas/login.fxml", event);
        }
    }

    public void irSantorini(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnSantorini)){
            agenciaDeViajes.loadStage("/ventanas/ventanaSantorini.fxml", event);
        }

    }
    public void irParis(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnParis)){
            agenciaDeViajes.loadStage("/ventanas/ventanaParis.fxml", event);
        }

    }
    public void irBali(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnBali)){
            agenciaDeViajes.loadStage("/ventanas/ventanaBali.fxml", event);
        }

    }
    public void irRoma(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnRoma)){
            agenciaDeViajes.loadStage("/ventanas/ventanaRoma.fxml", event);
        }

    }
    public void siguienteVentana(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnPaquete1)){
            agenciaDeViajes.loadStage("/ventanas/ventanaPaqueteEuropa.fxml", event);
        }

    }
    public void siguienteVentana2(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnPaquete2)){
            agenciaDeViajes.loadStage("/ventanas/ventanaPaqueteIslas.fxml", event);
        }

    }

}
