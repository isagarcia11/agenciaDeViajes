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
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class VentanaPaqueteIslas implements Initializable, CambioIdiomaListener {
    @FXML
    private Button btnAtras;

    @FXML
    private Label tituloPaquete,duracion, servAdicionales,precio, cupoMaximo, fechas;

    @FXML
    private TextField duracionPaquete, precioPaquete, cupoMaximoPaquete, servAdicionalesPaquete;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

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
        tituloPaquete.setText(propiedades.getResourceBundle().getString("TextoTituloPaquete2"));
        duracion.setText(propiedades.getResourceBundle().getString("TextoDuracion"));
        servAdicionales.setText(propiedades.getResourceBundle().getString("TextoServAdicionales"));
        precio.setText(propiedades.getResourceBundle().getString("TextoPrecio"));
        cupoMaximo.setText(propiedades.getResourceBundle().getString("TextoCupoMaximo"));
        fechas.setText(propiedades.getResourceBundle().getString("TextoFechas"));
        duracionPaquete.setText(propiedades.getResourceBundle().getString("TextoDuracionPaquete2"));
        servAdicionalesPaquete.setText(propiedades.getResourceBundle().getString("TextoServiciosPaquete2"));
        precioPaquete.setText(propiedades.getResourceBundle().getString("TextoPrecioPaquete2"));
        cupoMaximoPaquete.setText(propiedades.getResourceBundle().getString("TextoCupoMaximoPaquete2"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
    }

    public void atras(ActionEvent event) {
        Object evt = event.getSource();
        if (evt.equals(btnAtras)) {
            agenciaDeViajes.loadStage("/ventanas/inicio.fxml", event);
        }

    }
}


