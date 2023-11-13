package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.GuiaTuristico;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EstadisticasGuiaTuristicoControlador implements Initializable {

    @FXML
    private BarChart<String, Number> graficoGuias;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Obtener la lista de guías turísticos
        List<GuiaTuristico> guias = AgenciaDeViajes.getInstance().getGuiasTuristicos();

        // Calcular el promedio de calificaciones por guía
        Map<String, Double> promedioCalificaciones = new HashMap<>();
        for (GuiaTuristico guia : guias) {
            ArrayList<Integer> calificaciones = guia.getCalificaciones();
            if (!calificaciones.isEmpty()) {
                double promedio = calificaciones.stream().mapToInt(Integer::intValue).average().orElse(0.0);
                promedioCalificaciones.put(guia.getNombre(), promedio);
            }
        }

        // Crear una serie de datos para el gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String guia : promedioCalificaciones.keySet()) {
            series.getData().add(new XYChart.Data<>(guia, promedioCalificaciones.get(guia)));
        }

        // Configurar el gráfico
        graficoGuias.getData().add(series);
        graficoGuias.setTitle("Guías Turísticos Mejor Puntuados");
        CategoryAxis xAxis = (CategoryAxis) graficoGuias.getXAxis();
        xAxis.setLabel("Guía Turístico");
        NumberAxis yAxis = (NumberAxis) graficoGuias.getYAxis();
        yAxis.setLabel("Promedio de Calificaciones");
    }
}

