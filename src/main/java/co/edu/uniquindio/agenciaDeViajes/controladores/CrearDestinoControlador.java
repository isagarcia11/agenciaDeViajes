package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.enums.Clima;
import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaDeViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
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

public class CrearDestinoControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private TextField txtNombreDestino, txtCiudad, txtDescripcion, txtImagen;

    @FXML
    private ComboBox<Clima> cbxClima;

    @FXML
    private Button btnGuardar, btnAtras;

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
        cbxClima.setItems(FXCollections.observableArrayList( List.of(Clima.values() ) ) );
        txtNombreDestino.setPromptText(propiedades.getResourceBundle().getString("TextoNombreDestino"));
        txtCiudad.setPromptText(propiedades.getResourceBundle().getString("TextoCiudad"));
        txtDescripcion.setPromptText(propiedades.getResourceBundle().getString("TextoDescripcion"));
        txtImagen.setPromptText(propiedades.getResourceBundle().getString("TextoImagen"));
        btnGuardar.setText(propiedades.getResourceBundle().getString("TextoGuardar"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));

    }
    public void registrarDestino(ActionEvent actionEvent){

        ArrayList<Integer> calificaiones = new ArrayList<>();
        ArrayList<String> comentarios = new ArrayList<>();

        try{
            Destino destino = agenciaDeViajes.registrarDestino(
                    txtNombreDestino.getText(),
                    txtCiudad.getText(),
                    txtDescripcion.getText(),
                    txtImagen.getText(),
                    cbxClima.getValue(),
                    calificaiones,
                    comentarios
            );

            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoCrearDestino1")
                    +destino.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void regresarInicio(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}
