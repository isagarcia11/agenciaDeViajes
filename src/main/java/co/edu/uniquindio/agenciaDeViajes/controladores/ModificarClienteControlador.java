package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaDeViajes.exceptions.InformacionRepetidaException;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ModificarClienteControlador implements Initializable {

    @FXML
    private TextField txtIdentificacion, txtNombre, txtCorreo, txtTelefono, txtDireccion;

    @FXML
    private Button btnGuardar, btnRegresar;
    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCliente(cliente);
    }
    public void setCliente(Cliente cliente) {
        this.cliente = agenciaDeViajes.getClienteAutenticado();
        actualizarCampos(); // Llamamos a este método para llenar los campos inicialmente
    }

    private void actualizarCampos() {
        if (cliente != null) {
            txtIdentificacion.setText(cliente.getIdentificacion());
            txtNombre.setText(cliente.getNombre());
            txtCorreo.setText(cliente.getCorreo());
            txtTelefono.setText(cliente.getTelefono());
            txtDireccion.setText(cliente.getDireccion());
        }
    }

    public void actualizarCliente(ActionEvent actionEvent){
        try{
            agenciaDeViajes.actualizarCliente(
                    txtIdentificacion.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtTelefono.getText(),
                    txtDireccion.getText()
            );

            mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha actualizado correctamente el cliente: "+cliente.getNombre());
        } catch (AtributoVacioException | InformacionRepetidaException e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void regresarInicio(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnRegresar)){
            agenciaDeViajes.loadStage("/ventanas/inicio.fxml", event);
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

}
