package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
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

public class ClientesControlador implements Initializable {
    public TableView<Cliente> tableView;
    public TableColumn<Cliente, String> columnIdentificacion;
    public TableColumn<Cliente, String> columnNombre;
    public TableColumn<Cliente, String> columnCorreo;
    public TableColumn<Cliente, String> columnTelefono;
    public TableColumn<Cliente, String> columnDireccion;

    @FXML
    private Button btnAtras;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnIdentificacion.setText(propiedades.getResourceBundle().getString("TextoIdentificacion"));
        columnNombre.setText(propiedades.getResourceBundle().getString("TextoNombre"));
        columnCorreo.setText(propiedades.getResourceBundle().getString("TextoCorreo"));
        columnTelefono.setText(propiedades.getResourceBundle().getString("TextoTelefono"));
        columnDireccion.setText(propiedades.getResourceBundle().getString("TextoDireccion"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        columnIdentificacion.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getIdentificacion()) );
        columnNombre.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getNombre()) );
        columnCorreo.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getCorreo()) );
        columnTelefono.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getTelefono()) );
        columnDireccion.setCellValueFactory( cellData -> new SimpleStringProperty( cellData.getValue().getDireccion()) );

        tableView.setItems(FXCollections.observableArrayList(agenciaDeViajes.getClientes()));

    }

    public void regresarInicio(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }
}
