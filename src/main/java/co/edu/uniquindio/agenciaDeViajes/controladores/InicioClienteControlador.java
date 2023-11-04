package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioClienteControlador {

    @FXML
    private Button btnModificarDatos;

    @FXML
    private Button btnPaquetes;

    @FXML
    private Button btnSalir;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
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
