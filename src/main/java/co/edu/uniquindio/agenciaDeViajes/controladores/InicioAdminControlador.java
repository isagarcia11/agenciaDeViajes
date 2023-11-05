package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioAdminControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private Button btnCrearDestino, btnActualizarDestino, btnCrearPaquete, btnActualizarPaquete, btnCrearGuiaTuristico;

    @FXML
    private Button btnActualizarGuia;

    @FXML
    private Button btnClientes, btnDestinos, btnPaquetes, btnGuias;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();
    }
    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }

    private void actualizarTextos() {
        btnCrearDestino.setText(propiedades.getResourceBundle().getString("TextoCrearDestino"));
        btnActualizarDestino.setText(propiedades.getResourceBundle().getString("TextoActualizarDestino"));
        btnCrearPaquete.setText(propiedades.getResourceBundle().getString("TextoCrearPaquete"));
        btnActualizarPaquete.setText(propiedades.getResourceBundle().getString("TextoActualizarPaquete"));
        btnCrearGuiaTuristico.setText(propiedades.getResourceBundle().getString("TextoCrearGuia"));
        btnActualizarGuia.setText(propiedades.getResourceBundle().getString("TextoActualizarGuia1"));
        btnClientes.setText(propiedades.getResourceBundle().getString("TextoReporteClientes"));
        btnDestinos.setText(propiedades.getResourceBundle().getString("TextoReporteDestinos"));
        btnPaquetes.setText(propiedades.getResourceBundle().getString("TextoReportePaquetes"));
        btnGuias.setText(propiedades.getResourceBundle().getString("TextoReporteGuias"));

    }

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
