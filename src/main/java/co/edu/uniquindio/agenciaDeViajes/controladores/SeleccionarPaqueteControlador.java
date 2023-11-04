package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SeleccionarPaqueteControlador implements Initializable {

    @FXML
    private TextField filtroNombre;

    @FXML
    private TextField filtroDestino;

    @FXML
    private TextField filtroDuracion;

    @FXML
    private TextField filtroServiciosAdicionales;

    @FXML
    private TextField filtroPrecio;

    @FXML
    private TextField filtroCupoMaximo;

    @FXML
    private TextField filtroFechaInicio;

    @FXML
    private TextField filtroFechaFin;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnNombre;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnDestinos;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnDuracion;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnServiciosAdicionales;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnPrecio;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnCupoMaximo;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnFechaInicio;

    @FXML
    private TableColumn<PaqueteTuristico, String> columnFechaFin;

    @FXML
    private TableView<PaqueteTuristico> tablaPaquetes;

    @FXML
    private Button btnDetallesPaquete;

    private ObservableList<PaqueteTuristico> paquetesList = FXCollections.observableArrayList();

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    private PaqueteTuristico paqueteSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnNombre.setText(propiedades.getResourceBundle().getString("TextoNombre"));
        columnDuracion.setText(propiedades.getResourceBundle().getString("TextoDuracion"));
        columnDestinos.setText(propiedades.getResourceBundle().getString("TextoDestinos"));
        columnCupoMaximo.setText(propiedades.getResourceBundle().getString("TextoCupoMaximo"));
        columnPrecio.setText(propiedades.getResourceBundle().getString("TextoPrecio"));
        columnFechaInicio.setText(propiedades.getResourceBundle().getString("TextoFechaInicio"));
        columnFechaFin.setText(propiedades.getResourceBundle().getString("TextoFechaFin"));
        columnServiciosAdicionales.setText(propiedades.getResourceBundle().getString("TextoServAdicionales"));
        btnDetallesPaquete.setText(propiedades.getResourceBundle().getString("TextoDetallesPaquete"));
        filtroNombre.setPromptText(propiedades.getResourceBundle().getString("TextoNombre"));
        filtroDestino.setPromptText(propiedades.getResourceBundle().getString("TextoDestinos"));
        filtroCupoMaximo.setPromptText(propiedades.getResourceBundle().getString("TextoCupoMaximo"));
        filtroDuracion.setPromptText(propiedades.getResourceBundle().getString("TextoDuracion"));
        filtroPrecio.setPromptText(propiedades.getResourceBundle().getString("TextoPrecio"));
        filtroServiciosAdicionales.setPromptText(propiedades.getResourceBundle().getString("TextoServAdicionales"));
        filtroFechaInicio.setPromptText(propiedades.getResourceBundle().getString("TextoFechaInicio"));
        filtroFechaFin.setPromptText(propiedades.getResourceBundle().getString("TextoFechaFin"));

        cargarPaquetes();

        columnNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        columnDestinos.setCellValueFactory(cellData -> {
            String destinos = cellData.getValue().getDestinos().stream()
                    .map(Destino::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");
            return new SimpleStringProperty(destinos);
        });
        columnDuracion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDuracion()));
        columnServiciosAdicionales.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiciosAdicionales()));
        columnPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        columnCupoMaximo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCupoMaximo())));
        columnFechaInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicio().toString()));
        columnFechaFin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFin().toString()));

        tablaPaquetes.setItems(FXCollections.observableArrayList(agenciaDeViajes.getPaquetesTuristicos()));
    }


    public void cargarPaquetes() {
        ArrayList<PaqueteTuristico> paquetes = agenciaDeViajes.getPaquetesTuristicos();
        paquetesList.setAll(paquetes);
        tablaPaquetes.setItems(paquetesList);
    }

    @FXML
    private void filtrar() {
        String nombreFiltro = filtroNombre.getText().toLowerCase();
        String destinoFiltro = filtroDestino.getText().toLowerCase();
        String duracionFiltro = filtroDuracion.getText().toLowerCase();
        String serviciosAdicionalesFiltro = filtroServiciosAdicionales.getText().toLowerCase();
        String precioFiltro = filtroPrecio.getText().toLowerCase();
        String cupoMaximoFiltro = filtroCupoMaximo.getText().toLowerCase();
        String fechaInicioFiltro = filtroFechaInicio.getText().toLowerCase();
        String fechaFinFiltro = filtroFechaFin.getText().toLowerCase();

        ObservableList<PaqueteTuristico> paquetesFiltrados = FXCollections.observableArrayList();

        for (PaqueteTuristico paquete : paquetesList) {
            boolean cumpleFiltroNombre = paquete.getNombre().toLowerCase().contains(nombreFiltro);
            boolean cumpleFiltroDestino = paquete.getDestinos().stream().anyMatch(destino -> destino.getNombre().toLowerCase().contains(destinoFiltro));
            boolean cumpleFiltroDuracion = paquete.getDuracion().toLowerCase().contains(duracionFiltro);
            boolean cumpleFiltroServiciosAdicionales = paquete.getServiciosAdicionales().toLowerCase().contains(serviciosAdicionalesFiltro);
            boolean cumpleFiltroPrecio = String.valueOf(paquete.getPrecio()).toLowerCase().contains(precioFiltro);
            boolean cumpleFiltroCupoMaximo = String.valueOf(paquete.getCupoMaximo()).toLowerCase().contains(cupoMaximoFiltro);
            boolean cumpleFiltroFechaInicio = paquete.getFechaInicio().toString().toLowerCase().contains(fechaInicioFiltro);
            boolean cumpleFiltroFechaFin = paquete.getFechaFin().toString().toLowerCase().contains(fechaFinFiltro);

            if (cumpleFiltroNombre && cumpleFiltroDestino && cumpleFiltroDuracion && cumpleFiltroServiciosAdicionales &&
                    cumpleFiltroPrecio && cumpleFiltroCupoMaximo && cumpleFiltroFechaInicio && cumpleFiltroFechaFin) {
                paquetesFiltrados.add(paquete);
            }
        }

        tablaPaquetes.setItems(paquetesFiltrados);
    }

    @FXML
    private void mostrarDetalle(ActionEvent event) {
        Object evt = event.getSource();
        paqueteSeleccionado = tablaPaquetes.getSelectionModel().getSelectedItem();
        agenciaDeViajes.setPaquetesTuristicos(paqueteSeleccionado);
        if(evt.equals(btnDetallesPaquete)){
            agenciaDeViajes.loadStage("/ventanas/detallesPaquete.fxml", event);
        }
    }


}
