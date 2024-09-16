package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.*;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaEvent;
import co.edu.uniquindio.agenciaDeViajes.utils.CambioIdiomaListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadisticasDestinosMasReservadosControlador implements Initializable, CambioIdiomaListener{
    @FXML
    private BarChart<String, Number> graficoDestinos;

    @FXML
    private Button btnAtras;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();
        // Obtener la lista de reservas
        List<Reserva> reservas = agenciaDeViajes.getReservas();

        // Contar la cantidad de veces que se reserva cada destino
        Map<String, Integer> contadorDestinos = new HashMap<>();
        for (Reserva reserva : reservas) {
            PaqueteTuristico paquete = reserva.getPaqueteTuristico();
            for (Destino destino : paquete.getDestinos()) {
                contadorDestinos.put(destino.getNombre(), contadorDestinos.getOrDefault(destino.getNombre(), 0) + 1);
            }
        }

        // Crear una serie de datos para el gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String destino : contadorDestinos.keySet()) {
            series.getData().add(new XYChart.Data<>(destino, contadorDestinos.get(destino)));
        }

        // Configurar el gráfico
        graficoDestinos.getData().add(series);
        graficoDestinos.setTitle("Destinos más Reservados");
        CategoryAxis xAxis = (CategoryAxis) graficoDestinos.getXAxis();
        xAxis.setLabel("Destino");
        NumberAxis yAxis = (NumberAxis) graficoDestinos.getYAxis();
        yAxis.setLabel("Cantidad de Reservas");
    }

    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }

    private void actualizarTextos() {
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));

    }
    public void atras(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnAtras)){
            agenciaDeViajes.loadStage("/ventanas/inicioAdmin.fxml", event);
        }
    }
}

