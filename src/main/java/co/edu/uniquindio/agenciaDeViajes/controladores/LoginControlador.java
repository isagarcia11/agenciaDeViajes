package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.exceptions.AtributoVacioException;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginControlador {

    @FXML
    private TextField usuarioCliente;

    @FXML
    private PasswordField contrasenaCliente;

    @FXML
    private Button btnIniciar;

    @FXML
    private Button btnRegistrarse;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    public void iniciarSesion(ActionEvent event) {
        Object evt = event.getSource();
        try {
            Cliente cliente = agenciaDeViajes.verificarDatos(usuarioCliente.getText(), contrasenaCliente.getText());
            if(evt.equals(btnIniciar)){
                if(usuarioCliente.getText().equals("Alejandro Arango") && contrasenaCliente.getText().equals("1104804234")){
                    agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
                } else{
                    if(cliente != null){
                        agenciaDeViajes.setClienteAutenticado(cliente);
                        agenciaDeViajes.loadStage("/ventanas/inicioCliente.fxml", event);
                        mostrarMensaje(Alert.AlertType.INFORMATION, "El usuario "+usuarioCliente.getText()+" ha ingresado correctamente");
                    } else {
                        mostrarMensaje(Alert.AlertType.ERROR, "El usuario o contraseña son incorrectos");
                    }
                }
            }
        } catch (AtributoVacioException e){
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void registrarUsuario(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnRegistrarse)){
            agenciaDeViajes.loadStage("/ventanas/registrarCliente.fxml", event);
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}
