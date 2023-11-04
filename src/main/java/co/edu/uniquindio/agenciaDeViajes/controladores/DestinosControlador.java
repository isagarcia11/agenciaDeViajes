package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.enums.Clima;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DestinosControlador implements Initializable, CambioIdiomaListener {
    public TableView<Destino> tableView;
    public TableColumn<Destino, String> columnNombre;
    public TableColumn<Destino, String> columnCiudad;
    public TableColumn<Destino, String> columnDescripcion;
    public TableColumn<Destino, String> columnClima;

    @FXML
    private Button btnAtras;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();

        columnNombre.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getNombre()));
        columnCiudad.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getCiudad()));
        columnDescripcion.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getDescripcion()));
        columnClima.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getClima().toString()));

        tableView.setItems(FXCollections.observableArrayList(agenciaDeViajes.getDestinos()));

    }
    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }

    private void actualizarTextos() {
        columnNombre.setText(propiedades.getResourceBundle().getString("TextoNombre"));
        columnCiudad.setText(propiedades.getResourceBundle().getString("TextoCiudad"));
        columnDescripcion.setText(propiedades.getResourceBundle().getString("TextoDescripcion"));
        columnClima.setText(propiedades.getResourceBundle().getString("TextoClima"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
    }

    public void regresarInicio(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }
}
