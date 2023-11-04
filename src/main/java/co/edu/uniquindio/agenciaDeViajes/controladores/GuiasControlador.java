package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.GuiaTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
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

public class GuiasControlador implements Initializable {
    public TableView<GuiaTuristico> tableView;
    public TableColumn<GuiaTuristico, String> columnNombre;
    public TableColumn<GuiaTuristico, String> columnIdentificacion;
    public TableColumn<GuiaTuristico, String> columnIdiomas;
    public TableColumn<GuiaTuristico, String> columnExperiencia;

    @FXML
    private Button btnAtras;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnNombre.setText(propiedades.getResourceBundle().getString("TextoNombre"));
        columnIdentificacion.setText(propiedades.getResourceBundle().getString("TextoIdentificacion"));
        columnIdiomas.setText(propiedades.getResourceBundle().getString("TextoIdiomas"));
        columnExperiencia.setText(propiedades.getResourceBundle().getString("TextoExperiencia"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        columnIdiomas.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdiomas().toString()));
        columnExperiencia.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getExperiencia())));

        tableView.setItems(FXCollections.observableArrayList(agenciaDeViajes.getGuiasTuristicos()));
    }

    public void volverAlInicio(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }
}

