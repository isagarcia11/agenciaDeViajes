package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ModificarPaquetesTuristicosControlador implements Initializable {

    @FXML
    private TextField txtNombrePaquete;

    @FXML
    private TextField txtDuracion;

    @FXML
    private TextField txtServiciosAdicionales;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCupoMaximo;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private ComboBox<String> cbxDestinos;

    @FXML
    private ComboBox<String> cbxDestinosPaquete;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnAgregarDestino;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnEliminarDestino;

    @FXML
    private Label nombrePaquete, duracion, serviciosAdicionales, precio, cupoMaximo, fechaInicio, fechaFin, destinosAsignados;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    private PaqueteTuristico paqueteTuristico;

    private ArrayList<Destino> destinos;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombrePaquete.setText(propiedades.getResourceBundle().getString("TextoNombrePaquete"));
        duracion.setText(propiedades.getResourceBundle().getString("TextoDuracion"));
        serviciosAdicionales.setText(propiedades.getResourceBundle().getString("TextoServiciosAdicionales"));
        precio.setText(propiedades.getResourceBundle().getString("TextoPrecio"));
        cupoMaximo.setText(propiedades.getResourceBundle().getString("TextoCupoMaximo"));
        fechaInicio.setText(propiedades.getResourceBundle().getString("TextoFechaInicio"));
        fechaFin.setText(propiedades.getResourceBundle().getString("TextoFechaFin"));
        destinosAsignados.setText(propiedades.getResourceBundle().getString("TextoDestinosAsignados"));
        btnBuscar.setText(propiedades.getResourceBundle().getString("TextoBuscar"));
        btnEliminarDestino.setText(propiedades.getResourceBundle().getString("TextoEliminarDestino"));
        btnAgregarDestino.setText(propiedades.getResourceBundle().getString("TextoAgregarDestino"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        btnActualizar.setText(propiedades.getResourceBundle().getString("TextoActualizar"));
        btnEliminar.setText(propiedades.getResourceBundle().getString("TextoEliminarPaquete"));
        ObservableList<String> nombresDestinos = FXCollections.observableArrayList();

        for (Destino destino : agenciaDeViajes.getDestinos()) {
            nombresDestinos.add(destino.getNombre());
        }

        cbxDestinos.setItems(nombresDestinos);
    }
    public void setPaqueteTuristico() {
        this.paqueteTuristico = agenciaDeViajes.obtenerPaquete(txtNombrePaquete.getText());
        this.destinos = paqueteTuristico.getDestinos();
        llenarComboBox();
        actualizarCampos(); // Llamamos a este método para llenar los campos inicialmente

    }

    public void llenarComboBox(){
        ObservableList<String> nombresDestinosPaquete = FXCollections.observableArrayList();

        for (Destino destino : destinos) {
            nombresDestinosPaquete.add(destino.getNombre());
        }

        cbxDestinosPaquete.setItems(nombresDestinosPaquete);
    }

    private void actualizarCampos() {
        if (paqueteTuristico != null) {
            txtNombrePaquete.setText(paqueteTuristico.getNombre());
            txtDuracion.setText(paqueteTuristico.getDuracion());
            txtServiciosAdicionales.setText(paqueteTuristico.getServiciosAdicionales());
            txtPrecio.setText(String.valueOf(paqueteTuristico.getPrecio()));
            txtCupoMaximo.setText(String.valueOf(paqueteTuristico.getCupoMaximo()));
            dpFechaInicio.setValue(paqueteTuristico.getFechaInicio());
            dpFechaFin.setValue(paqueteTuristico.getFechaFin());

        } else {
            mostrarMensaje(Alert.AlertType.INFORMATION, "El paquete turístico no está registrado");
        }
    }

    @FXML
    void regresarInicio(ActionEvent event) {
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }

    @FXML
    void actualizarPaqueteTuristico(ActionEvent event) {

        try {
            agenciaDeViajes.actualizarPaquete(
                    txtNombrePaquete.getText(),
                    destinos,
                    txtDuracion.getText(),
                    txtServiciosAdicionales.getText(),
                    Float.parseFloat(txtPrecio.getText()),
                    Integer.parseInt(txtCupoMaximo.getText()),
                    dpFechaInicio.getValue(),
                    dpFechaFin.getValue()
            );
            mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha actualizado correctamente el paquete turístico: " + paqueteTuristico.getNombre());
        } catch (Exception e) {
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    void eliminarDestino(ActionEvent event){
        String nombreSeleccionado = cbxDestinosPaquete.getValue();

        if(nombreSeleccionado != null){
            Iterator<Destino> iter = destinos.iterator();
            while (iter.hasNext()) {
                Destino destino = iter.next();
                if(destino.getNombre().equals(nombreSeleccionado)){
                    iter.remove(); // Usamos el iterador para eliminar el destino de la lista
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha eliminado el destino");
                }
            }

            // Después de eliminar el destino, actualizamos el ComboBox de destinos
            llenarComboBox();
        }
    }


    @FXML
    void buscarDestino(ActionEvent event) {
        String nombreSeleccionado = cbxDestinos.getValue(); // Obtiene el nombre seleccionado del ComboBox

        if (nombreSeleccionado != null) {
            boolean destinoEncontrado = false;
            for (Destino destino : destinos) {
                if (destino.getNombre().equals(nombreSeleccionado)) {
                    destinoEncontrado = true;
                    break;
                }
            }

            if (!destinoEncontrado) {
                // Si el destino no se encuentra en la lista, lo agregamos
                Destino nuevoDestino = agenciaDeViajes.obtenerDestino(nombreSeleccionado);
                if (nuevoDestino != null) {
                    destinos.add(nuevoDestino);
                    llenarComboBox();
                    mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha asignado el destino " + nombreSeleccionado + " al paquete");
                } else {
                    mostrarMensaje(Alert.AlertType.ERROR, "El destino no se encontró en la lista de destinos disponibles");
                }
            } else {
                mostrarMensaje(Alert.AlertType.ERROR, "El destino " + nombreSeleccionado + " ya hace parte del paquete");
            }
        }
    }

    @FXML
    void eliminarPaqueteTuristico(ActionEvent event) {
        // Implementa la lógica para eliminar un paquete turistico
        agenciaDeViajes.eliminarPaquete(txtNombrePaquete.getText());
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}