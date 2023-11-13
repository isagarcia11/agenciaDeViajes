package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.enums.Estado;
import co.edu.uniquindio.agenciaDeViajes.modelo.*;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class VentanaReservasClienteControlador  implements Initializable {

    @FXML
    private TableView<Reserva> tablaReservas;

    @FXML
    private TableColumn<Reserva, String> columnNombrePaquete;

    @FXML
    private TableColumn<Reserva, String> columnDestinos;

    @FXML
    private TableColumn<Reserva, String> columnFechaInicioPaquete;

    @FXML
    private TableColumn<Reserva, String> columnFechaFinPaquete;

    @FXML
    private TableColumn<Reserva, String> columnCantidadPersonas;

    @FXML
    private TableColumn<Reserva, String> columnEstado;

    @FXML
    private TableColumn<Reserva, String> columnFechaSolicitud;

    @FXML
    private TableColumn<Reserva, String> columnFechaViaje;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnCancelarReserva;

    @FXML
    private Button btnConfirmarReserva;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    private Cliente cliente = agenciaDeViajes.getClienteAutenticado();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<Reserva> reservasCliente = new ArrayList<>();

        for (Reserva reserva : agenciaDeViajes.getReservas()) {
            if (reserva.getCliente().equals(cliente)) {
                reservasCliente.add(reserva);
            }

        }
        // Configurar las columnas de la tabla
        columnNombrePaquete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaqueteTuristico().getNombre()));

        columnDestinos.setCellValueFactory(cellData -> {
            String destinos = cellData.getValue().getPaqueteTuristico().getDestinos().stream()
                    .map(Destino::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            return new SimpleStringProperty(destinos);
        });

        columnFechaInicioPaquete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaqueteTuristico().getFechaInicio().toString()));
        columnFechaFinPaquete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaqueteTuristico().getFechaFin().toString()));

        columnCantidadPersonas.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidadDePersonas())));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEstado())));
        columnFechaSolicitud.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaDeSolicitud().toString()));
        columnFechaViaje.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaDeViaje().toString()));

        tablaReservas.setItems(FXCollections.observableArrayList(reservasCliente));
    }

    @FXML
    private void cancelarReserva(ActionEvent event) {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada != null) {
            if (reservaSeleccionada.getEstado() == Estado.CONFIRMADA) {
                mostrarMensaje(Alert.AlertType.WARNING, "No se puede cancelar una reserva confirmada.");
            } else {
                // Cambiar el estado de la reserva a CANCELADA
                reservaSeleccionada.setEstado(Estado.CANCELADA);

                agenciaDeViajes.actualizarReserva();

                // Actualizar la tabla
                tablaReservas.refresh();

                mostrarMensaje(Alert.AlertType.INFORMATION, "La reserva ha sido cancelada.");
            }
        } else {
            mostrarMensaje(Alert.AlertType.WARNING, "Por favor, selecciona una reserva para cancelar.");
        }
    }


    @FXML
    private void confirmarReserva(ActionEvent event) {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        if (reservaSeleccionada != null) {
            // Cambiar el estado de la reserva a CONFIRMADA
            reservaSeleccionada.setEstado(Estado.CONFIRMADA);

            agenciaDeViajes.actualizarReserva();

            // Actualizar la tabla
            tablaReservas.refresh();

            mostrarMensaje(Alert.AlertType.INFORMATION, "La reserva ha sido confirmada.");
        } else {
            mostrarMensaje(Alert.AlertType.WARNING, "Por favor, selecciona una reserva para confirmar.");
        }
    }

    @FXML
    private void calificarDestinos(ActionEvent event) {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        agenciaDeViajes.setReserva(reservaSeleccionada);

        if (reservaSeleccionada != null) {
            agenciaDeViajes.loadStage("/ventanas/calificarDestino.fxml", event);
        } else {
            mostrarMensaje(Alert.AlertType.WARNING, "Por favor selecciona una reserva.");
        }
    }

    @FXML
    private void calificarGuia(ActionEvent event) {
        Reserva reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();

        agenciaDeViajes.setReserva(reservaSeleccionada);

        if (reservaSeleccionada != null) {
            if(agenciaDeViajes.reservaFinalizada(reservaSeleccionada)){
                agenciaDeViajes.loadStage("/ventanas/calificarGuia.fxml", event);
            } else {
                mostrarMensaje(Alert.AlertType.WARNING, "La reserva no ha finalizado");
            }

        } else {
            mostrarMensaje(Alert.AlertType.WARNING, "Por favor selecciona una reserva.");
        }
    }


    @FXML
    public void volverInicio(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioCliente.fxml", event);
        }
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje){
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

}


