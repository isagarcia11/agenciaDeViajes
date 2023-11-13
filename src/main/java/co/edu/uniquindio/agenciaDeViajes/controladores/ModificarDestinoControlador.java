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
import java.util.List;
import java.util.ResourceBundle;

public class ModificarDestinoControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private TextField txtNombreDestino, txtCiudad, txtDescripcion, txtImagen;

    @FXML
    private ComboBox<Clima> cbxClima;

    @FXML
    private Button btnActualizar, btnAtras, btnBuscar, btnEliminar;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    private Destino destino;

    private final Propiedades propiedades = Propiedades.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();
        cbxClima.setItems(FXCollections.observableArrayList( List.of(Clima.values() ) ) );


    }

    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }

    private void actualizarTextos() {
        txtNombreDestino.setPromptText(propiedades.getResourceBundle().getString("TextoNombreDestino"));
        btnBuscar.setText(propiedades.getResourceBundle().getString("TextoBuscar"));
        txtCiudad.setPromptText(propiedades.getResourceBundle().getString("TextoCiudad"));
        txtDescripcion.setPromptText(propiedades.getResourceBundle().getString("TextoDescripcion"));
        txtImagen.setPromptText(propiedades.getResourceBundle().getString("TextoImagen"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        btnActualizar.setText(propiedades.getResourceBundle().getString("TextoActualizar"));
        btnEliminar.setText(propiedades.getResourceBundle().getString("TextoEliminar"));
    }

    public void setDestino() {
        this.destino = agenciaDeViajes.obtenerDestino(txtNombreDestino.getText());
        actualizarCampos(); // Llamamos a este método para llenar los campos inicialmente
    }

    private void actualizarCampos() {
        if (destino != null) {
            txtNombreDestino.setText(destino.getNombre());
            txtCiudad.setText(destino.getCiudad());
            txtDescripcion.setText(destino.getDescripcion());
            txtImagen.setText(String.join("," , destino.getImagenes()));
            cbxClima.getSelectionModel().select(destino.getClima());
        } else {
            mostrarMensaje(Alert.AlertType.INFORMATION, "El destino no esta registrado");
        }
    }

    public void actualizarDestino(ActionEvent actionEvent){

        try{
            agenciaDeViajes.actualizarDestino(
                    txtNombreDestino.getText(),
                    txtCiudad.getText(),
                    txtDescripcion.getText(),
                    txtImagen.getText(),
                    cbxClima.getValue(),
                    destino.getCalificaciones(),
                    destino.getComentarios()
            );

            mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha actualizado correctamente el destino: "+destino.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void eliminarDestino(ActionEvent actionEvent){
        agenciaDeViajes.eliminarDestino(txtNombreDestino.getText());
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
