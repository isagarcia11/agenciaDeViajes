package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.enums.Clima;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class DestinosControlador implements Initializable {
    public TableView<Destino> tableView;
    public TableColumn<Destino, String> columnNombre;
    public TableColumn<Destino, String> columnCiudad;
    public TableColumn<Destino, String> columnDescripcion;
    public TableColumn<Destino, String> columnClima;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnNombre.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getNombre()));
        columnCiudad.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getCiudad()));
        columnDescripcion.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getDescripcion()));
        columnClima.setCellValueFactory( cellData -> new SimpleStringProperty(cellData.getValue().getClima().toString()));

        tableView.setItems(FXCollections.observableArrayList(agenciaDeViajes.getDestinos()));

    }
}
