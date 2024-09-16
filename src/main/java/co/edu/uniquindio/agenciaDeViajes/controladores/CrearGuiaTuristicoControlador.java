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

public class CrearGuiaTuristicoControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private TextField txtNombre, txtIdentificacion, txtExperiencia;

    @FXML
    private Label nombre, identificacion, experiencia, idioma;

    @FXML
    private ComboBox<Idioma> comboIdiomas;

    @FXML
    private Button btnAsignarIdioma, btnGuardar, btnRegresar;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();
    private ArrayList<Idioma> idiomas = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();
        // Llenar el ComboBox con los idiomas del enum Idioma
        comboIdiomas.setItems(FXCollections.observableArrayList(Arrays.asList(Idioma.values())));

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
        idioma.setText(propiedades.getResourceBundle().getString("TextoIdioma"));
        btnAsignarIdioma.setText(propiedades.getResourceBundle().getString("TextoAsignarIdioma"));
        btnGuardar.setText(propiedades.getResourceBundle().getString("TextoGuardar"));
        btnRegresar.setText(propiedades.getResourceBundle().getString("TextoRegresar"));

    }

    public void asignarIdioma(ActionEvent actionEvent){
        Idioma idiomaSeleccionado = comboIdiomas.getValue();

        // Verificar si el idioma ya ha sido agregado
        if (!idiomas.contains(idiomaSeleccionado)) {
            idiomas.add(idiomaSeleccionado);
            comboIdiomas.setValue(null); // Limpia la selección del ComboBox
        } else {
            mostrarMensaje(Alert.AlertType.WARNING, "Este idioma ya ha sido asignado al guía.");
        }
    }

    public void registrarGuiaTuristico(ActionEvent actionEvent){

        ArrayList<Integer> calificaiones = new ArrayList<>();
        ArrayList<String> comentarios = new ArrayList<>();

        try{
            GuiaTuristico guia = agenciaDeViajes.registrarGuiaTuristico(
                    txtNombre.getText(),
                    txtIdentificacion.getText(),
                    idiomas,
                    Float.parseFloat(txtExperiencia.getText()),
                    calificaiones,
                    comentarios
            );

            mostrarMensaje(Alert.AlertType.INFORMATION, propiedades.getResourceBundle().getString("TextoCrearGuia1")+guia.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException | AtributoNegativoException e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        } catch (NumberFormatException e){
            mostrarMensaje(Alert.AlertType.ERROR, propiedades.getResourceBundle().getString("TextoCrearGuia2"));
        }
    }

    public void regresarInicio(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnRegresar)){
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
