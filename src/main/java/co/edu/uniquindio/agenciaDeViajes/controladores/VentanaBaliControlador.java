package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class VentanaBaliControlador implements Initializable, CambioIdiomaListener {
    @FXML
    private Label nombre, ciudad, clima, descripcion, tituloBali;

    @FXML
    private TextField bali, provincia, tropical;

   @FXML
   private TextArea descripcionBali;

    @FXML
    private Button btnAtras;



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
        nombre.setText(propiedades.getResourceBundle().getString("TextoNombre"));
        ciudad.setText(propiedades.getResourceBundle().getString("TextoCiudad"));
        clima.setText(propiedades.getResourceBundle().getString("TextoClima"));
        descripcion.setText(propiedades.getResourceBundle().getString("TextoDescripcion"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        bali.setText(propiedades.getResourceBundle().getString("TextoBali"));
        provincia.setText(propiedades.getResourceBundle().getString("TextoProvincia"));
        tropical.setText(propiedades.getResourceBundle().getString("TextoTropical"));
        descripcionBali.setText(propiedades.getResourceBundle().getString("TextoDescripcionBali"));
        tituloBali.setText(propiedades.getResourceBundle().getString("TextoBali"));

    }
    public void atras(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicio.fxml", event);
        }
    }
}
