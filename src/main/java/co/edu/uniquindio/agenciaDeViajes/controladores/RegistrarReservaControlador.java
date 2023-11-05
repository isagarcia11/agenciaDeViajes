package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.exceptions.*;
import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Cliente;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Reserva;
import co.edu.uniquindio.agenciaDeViajes.modelo.GuiaTuristico;
import co.edu.uniquindio.agenciaDeViajes.enums.Estado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RegistrarReservaControlador implements Initializable {

    @FXML
    private ComboBox<String> comboPaquetes;

    @FXML
    private ComboBox<String> comboGuias;

    @FXML
    private TextField txtCantidadPersonas;

    @FXML
    private DatePicker dateFechaViaje;

    @FXML
    private Button btnRegistrar, btnRegresar;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    private Cliente cliente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCliente(cliente);
        cargarPaquetes();
        cargarGuias();
    }


    public void setCliente(Cliente cliente) {
        this.cliente = agenciaDeViajes.getClienteAutenticado();
    }

    public void cargarPaquetes() {
        ObservableList<String> nombresPaquetes = FXCollections.observableArrayList();

        for (PaqueteTuristico paquete : agenciaDeViajes.getPaquetesTuristicos()) {
            nombresPaquetes.add(paquete.getNombre());
        }

        comboPaquetes.setItems(nombresPaquetes);
    }

    public void cargarGuias() {
        ObservableList<String> nombresGuias = FXCollections.observableArrayList();

        for (GuiaTuristico guia : agenciaDeViajes.getGuiasTuristicos()) {
            nombresGuias.add(guia.getNombre());
        }

        comboGuias.setItems(nombresGuias);
    }


    public void registrarReserva(ActionEvent actionEvent) {
        try {
            PaqueteTuristico paqueteSeleccionado = agenciaDeViajes.obtenerPaquete(comboPaquetes.getValue());
            GuiaTuristico guiaSeleccionado = agenciaDeViajes.obtenerGuiaTuristicoNombre(comboGuias.getValue());
            int cantidadPersonas = Integer.parseInt(txtCantidadPersonas.getText());
            LocalDate fechaSolicitud = LocalDate.now();
            LocalDate fechaViaje = dateFechaViaje.getValue();

            Reserva reserva = agenciaDeViajes.registrarReserva(
                    cliente,
                    cantidadPersonas,
                    paqueteSeleccionado,
                    guiaSeleccionado,
                    Estado.PENDIENTE,
                    fechaSolicitud,
                    fechaViaje
            );

            agenciaDeViajes.actualizarCupoPaquete(paqueteSeleccionado, cantidadPersonas);

            mostrarMensaje(Alert.AlertType.INFORMATION, "Se ha registrado correctamente la reserva.");

        } catch (AtributoVacioException | CupoMaximoExcedidoException | FechaInvalidaException |
                 AtributoNegativoException | ReservaDuplicadaException e) {
            mostrarMensaje(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    public void regresarInicio(ActionEvent event) {
        agenciaDeViajes.loadStage("/ventanas/inicioCliente.fxml", event);
    }

    public void mostrarMensaje(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }
}


