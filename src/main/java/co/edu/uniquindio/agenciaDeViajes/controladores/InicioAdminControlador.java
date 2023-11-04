package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioAdminControlador {

    @FXML
    private Button btnCrearDestino, btnActualizarDestino, btnCrearPaquete, btnActualizarPaquete, btnCrearGuiaTuristico, btnActualizarGuia;

    @FXML
    private Button btnClientes, btnDestinos, btnPaquetes, btnGuias;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    public void ingresarCrearDestino(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnCrearDestino)){
            agenciaDeViajes.loadStage("/ventanas/crearDestino.fxml", event);
        }
    }

    public void ingresarActualizarDestino(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnActualizarDestino)){
            agenciaDeViajes.loadStage("/ventanas/modificarDestino.fxml", event);
        }
    }

    public void ingresarCrearPaquete(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnCrearPaquete)){
            agenciaDeViajes.loadStage("/ventanas/crearPaquete.fxml", event);
        }
    }

    public void ingresarActualizarPaquete(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnActualizarPaquete)){
            agenciaDeViajes.loadStage("/ventanas/modificarPaquetesTuristicos.fxml", event);
        }
    }

    public void ingresarCrearGuia(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnCrearGuiaTuristico)){
            agenciaDeViajes.loadStage("/ventanas/crearGuiaTuristico.fxml", event);
        }
    }

    public void ingresarActualizarGuia(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnActualizarGuia)){
            agenciaDeViajes.loadStage("/ventanas/modificarGuia.fxml", event);
        }
    }

    public void ingresarReporteClientes(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnClientes)){
            agenciaDeViajes.loadStage("/ventanas/clientes.fxml", event);
        }
    }

    public void ingresarReporteDestinos(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnDestinos)){
            agenciaDeViajes.loadStage("/ventanas/destinos.fxml", event);
        }
    }

    public void ingresarReportePaquetes(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnPaquetes)){
            agenciaDeViajes.loadStage("/ventanas/paquetes.fxml", event);
        }
    }

    public void ingresarReporteGuias(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnGuias)){
            agenciaDeViajes.loadStage("/ventanas/guias.fxml", event);
        }
    }
}
