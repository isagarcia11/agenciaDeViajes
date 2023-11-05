package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.Reserva;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
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

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar las columnas de la tabla
        columnNombrePaquete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaqueteTuristico().getNombre()));

        columnDestinos.setCellValueFactory(cellData -> {
            String destinos = cellData.getValue().getPaqueteTuristico().getDestinos().stream()
                    .map(Destino::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            return new javafx.beans.property.SimpleStringProperty(destinos);
        });

        columnFechaInicioPaquete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaqueteTuristico().getFechaInicio().toString()));
        columnFechaFinPaquete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaqueteTuristico().getFechaFin().toString()));

        columnCantidadPersonas.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidadDePersonas())));
        columnEstado.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEstado())));
        columnFechaSolicitud.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaDeSolicitud().toString()));
        columnFechaViaje.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaDeViaje().toString()));

        // Obtener el cliente autenticado
        Cliente cliente = agenciaDeViajes.getClienteAutenticado();

        // Filtrar las reservas del cliente
        List<Reserva> reservasCliente = agenciaDeViajes.getReservas().stream()
                .filter(reserva -> reserva.getCliente().equals(cliente))
                .collect(Collectors.toList());

        // Llenar la tabla con las reservas del cliente
        tablaReservas.setItems(FXCollections.observableArrayList(reservasCliente));
    }
}


