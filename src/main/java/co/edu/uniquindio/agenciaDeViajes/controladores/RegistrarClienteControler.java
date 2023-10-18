package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaDeViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrarClienteControler {

    @FXML
    private Label lblIdentificacion, lblNombre, lblCorreo, lblTelefono, lblDireccion;

    @FXML
    private TextField txtIdentificacion, txtNombre, txtCorreo, txtTelefono, txtDireccion;

    @FXML
    private Button btnGuardar, btnRegresar;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    public void registrarCliente(ActionEvent actionEvent){
        try{
            Cliente cliente = agenciaDeViajes.registrarCliente(
                    txtIdentificacion.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtTelefono.getText(),
                    txtDireccion.getText()
            );

            mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha registrado correctamente el cliente: "+cliente.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void regresarLogin(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnRegresar)){
            agenciaDeViajes.loadStage("/login.fxml", event);
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

}
