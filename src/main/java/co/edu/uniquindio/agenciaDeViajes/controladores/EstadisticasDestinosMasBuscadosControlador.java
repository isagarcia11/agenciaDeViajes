package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.*;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadisticasDestinosMasBuscadosControlador {

    @FXML
    private BarChart<String, Number> graficoDestinos;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    public void initialize() {
        // Obtener la lista de reservas
        List<BusquedasDestinos> busquedasDestinos = agenciaDeViajes.getBusquedasDestinos();

        // Contar la cantidad de veces que se reserva cada destino
        Map<String, Integer> contadorDestinos = new HashMap<>();
        for (BusquedasDestinos busquedasDestinos1 : busquedasDestinos) {
            contadorDestinos.put(busquedasDestinos1.getDestino().getNombre(), contadorDestinos.getOrDefault(busquedasDestinos1.getDestino().getNombre(), 0) + 1);
        }

        // Crear una serie de datos para el gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String destino : contadorDestinos.keySet()) {
            series.getData().add(new XYChart.Data<>(destino, contadorDestinos.get(destino)));
        }

        // Configurar el gráfico
        graficoDestinos.getData().add(series);
        graficoDestinos.setTitle("Destinos más Buscados");
        CategoryAxis xAxis = (CategoryAxis) graficoDestinos.getXAxis();
        xAxis.setLabel("Destino");
        NumberAxis yAxis = (NumberAxis) graficoDestinos.getYAxis();
        yAxis.setLabel("Cantidad de Destinos");
    }
}