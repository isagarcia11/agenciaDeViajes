package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.net.URL;
import java.util.ResourceBundle;

public class ClientesControlador implements Initializable {
    public TableView<Cliente> tableView;
    public TableColumn<Cliente, String> columnIdentificacion;
    public TableColumn<Cliente, String> columnNombre;
    public TableColumn<Cliente, String> columnCorreo;
    public TableColumn<Cliente, String> columnTelefono;
    public TableColumn<Cliente, String> columnDireccion;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnIdentificacion.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getIdentificacion()) );
        columnNombre.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getNombre()) );
        columnCorreo.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getCorreo()) );
        columnTelefono.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getTelefono()) );
        columnDireccion.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getDireccion()) );

        tableView.setItems(FXCollections.observableArrayList(agenciaDeViajes.getClientes()));

    }
}
