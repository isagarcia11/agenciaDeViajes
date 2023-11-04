package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioClienteControlador implements Initializable {

    @FXML
    private Button btnModificarDatos;

    @FXML
    private Button btnPaquetes;

    @FXML
    private Button btnSalir;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnPaquetes.setText(propiedades.getResourceBundle().getString("TextoPaquetes"));
        btnModificarDatos.setText(propiedades.getResourceBundle().getString("TextoModificarDatos"));
        btnSalir.setText(propiedades.getResourceBundle().getString("TextoSalir"));

    }
    public void modificarDatos(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnModificarDatos)){
            agenciaDeViajes.loadStage("/ventanas/modificarCliente.fxml", event);
        }
    }

    public void irAPaquetes(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnPaquetes)){
            agenciaDeViajes.loadStage("/ventanas/seleccionarPaquete.fxml", event);
        }
    }

    public void volverLogin(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnSalir)){
            agenciaDeViajes.loadStage("/ventanas/login.fxml", event);
        }
    }
}
