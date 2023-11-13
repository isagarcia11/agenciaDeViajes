package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Reserva;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadisticasControlador {

    @FXML
    private BarChart<String, Number> graficoDestinos;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    public void initialize() {
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
}

