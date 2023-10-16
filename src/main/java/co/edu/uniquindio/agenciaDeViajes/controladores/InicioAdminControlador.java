package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioAdminControlador {

    @FXML
    private Button btnClientes;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    public void ingresarReporteClientes(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnClientes)){
            agenciaDeViajes.loadStage("/clientes.fxml", event);
        }
    }
}
