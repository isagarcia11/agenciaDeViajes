package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Reserva;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadisticasPaquetesControlador {

    @FXML
    private BarChart<String, Number> graficoPaquetes;

    public AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    public void initialize() {
        // Obtener la lista de reservas
        List<Reserva> reservas = agenciaDeViajes.getReservas();

        // Contar la cantidad de veces que se reserva cada paquete
        Map<String, Integer> contadorPaquetes = new HashMap<>();
        for (Reserva reserva : reservas) {
            List<PaqueteTuristico> paqueteTuristicos = new ArrayList<>();
            PaqueteTuristico paquete = reserva.getPaqueteTuristico();
            paqueteTuristicos.add(paquete);
            for (PaqueteTuristico paqueteTuristico : paqueteTuristicos) {
                contadorPaquetes.put(paqueteTuristico.getNombre(), contadorPaquetes.getOrDefault(paqueteTuristico.getNombre(), 0) + 1);
            }
        }

        // Crear una serie de datos para el gráfico
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (String paquete : contadorPaquetes.keySet()) {
            series.getData().add(new XYChart.Data<>(paquete, contadorPaquetes.get(paquete)));
        }

        // Configurar el gráfico
        graficoPaquetes.getData().add(series);
        graficoPaquetes.setTitle("Paquetes más Reservados");
        CategoryAxis xAxis = (CategoryAxis) graficoPaquetes.getXAxis();
        xAxis.setLabel("Paquete");
        NumberAxis yAxis = (NumberAxis) graficoPaquetes.getYAxis();
        yAxis.setLabel("Cantidad de Paquetes");
    }
}