package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoNegativoException;
import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaDeViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.GuiaTuristico;
import co.edu.uniquindio.agenciaDeViajes.enums.Idioma;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ModificarGuiaControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private TextField txtNombre, txtIdentificacion, txtExperiencia;

    @FXML
    private Label nombre, identificacion, experiencia, idiomasGuia, idiomasDisponibles;

    @FXML
    private ComboBox<Idioma> comboIdiomasGuia, comboIdiomasDisponibles;

    @FXML
    private Button btnAtras, btnEliminar, btnActualizarGuia, btnEliminarIdioma, btnAgregarIdioma, btnBuscar;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();
    private GuiaTuristico guiaTuristico;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();

        comboIdiomasDisponibles.setItems(FXCollections.observableArrayList(Arrays.asList(Idioma.values())));

    }
    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }

    private void actualizarTextos() {
        nombre.setText(propiedades.getResourceBundle().getString("TextoNombre"));
        identificacion.setText(propiedades.getResourceBundle().getString("TextoIdentificacion"));
        experiencia.setText(propiedades.getResourceBundle().getString("TextoExperiencia"));
        idiomasGuia.setText(propiedades.getResourceBundle().getString("TextoIdiomasGuia"));
        idiomasDisponibles.setText(propiedades.getResourceBundle().getString("TextoIdiomasDisponibles"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        btnEliminar.setText(propiedades.getResourceBundle().getString("TextoEliminar"));
        btnActualizarGuia.setText(propiedades.getResourceBundle().getString("TextoActualizarGuia1"));
        btnEliminarIdioma.setText(propiedades.getResourceBundle().getString("TextoEliminarIdioma"));
        btnAgregarIdioma.setText(propiedades.getResourceBundle().getString("TextoAgregarIdioma"));
        btnBuscar.setText(propiedades.getResourceBundle().getString("TextoBuscar"));

    }

    public void setGuiaTuristico() {
        this.guiaTuristico = agenciaDeViajes.obtenerGuiaTuristico(txtIdentificacion.getText());
        actualizarCampos();

        // Llenar ComboBox de idiomas del guía
        comboIdiomasGuia.setItems(FXCollections.observableArrayList(guiaTuristico.getIdiomas()));
    }


    private void actualizarCampos() {
        if (guiaTuristico != null) {
            txtNombre.setText(guiaTuristico.getNombre());
            txtIdentificacion.setText(guiaTuristico.getIdentificacion());
            txtExperiencia.setText(String.valueOf(guiaTuristico.getExperiencia()));
        } else {
            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoGuia3"));
        }
    }

    @FXML
    void agregarIdioma(ActionEvent event) {
        Idioma idiomaSeleccionado = comboIdiomasDisponibles.getValue();
        if (idiomaSeleccionado != null) {
            // Verificar si el idioma ya ha sido agregado
            if (!guiaTuristico.getIdiomas().contains(idiomaSeleccionado)) {
                guiaTuristico.getIdiomas().add(idiomaSeleccionado);
                comboIdiomasDisponibles.setValue(null); // Limpia la selección del ComboBox
                comboIdiomasGuia.setItems(FXCollections.observableArrayList(guiaTuristico.getIdiomas()));
            } else {
                mostrarMensaje(Alert.AlertType.WARNING, propiedades.getResourceBundle().getString("TextoGuia4"));
            }
        }
    }

    @FXML
    void eliminarGuiaTuristico(ActionEvent event){
        if(txtIdentificacion.getText() != null){
            agenciaDeViajes.eliminarGuiaTuristico(txtIdentificacion.getText());
        } else {
            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoGuia5"));
        }

    }

    @FXML
    void eliminarIdioma(ActionEvent event) {
        Idioma idiomaSeleccionado = comboIdiomasGuia.getValue();
        if (idiomaSeleccionado != null) {
            guiaTuristico.getIdiomas().remove(idiomaSeleccionado);

            // Actualizar ComboBox de idiomas del guía
            comboIdiomasGuia.setItems(FXCollections.observableArrayList(guiaTuristico.getIdiomas()));

            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoGuia6") + idiomaSeleccionado);
        }
    }

    @FXML
    void actualizarGuia(ActionEvent event) {
        try {
            agenciaDeViajes.actualizarGuiaTuristico(
                    guiaTuristico.getIdentificacion(),
                    txtNombre.getText(),
                    guiaTuristico.getIdiomas(),
                    Float.parseFloat(txtExperiencia.getText()),
                    guiaTuristico.getCalificaciones(),
                    guiaTuristico.getComentarios()
            );

            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoGuia7") + guiaTuristico.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException | AtributoNegativoException e) {
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void regresarInicio(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }

    private void mostrarMensaje(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}
