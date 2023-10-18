package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class InicioControlador {
    @FXML
    private Button iniciarSesion;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    public void iniciarSesiones(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(iniciarSesion)){
            agenciaDeViajes.loadStage("/escogerUsuario.fxml", event);
        }
    }

}
