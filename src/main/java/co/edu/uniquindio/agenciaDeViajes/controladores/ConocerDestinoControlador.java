package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConocerDestinoControlador implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnAnterior, btnSiguiente;

    @FXML
    private TextField txtNombreDestino;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtClima;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();

    private Destino destinoActual = agenciaDeViajes.getDestino();

    private ArrayList<String> rutasImagenes = new ArrayList<>();
    private int indiceImagenActual = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarDetalles();
    }

    public void mostrarDetalles() {
        txtNombreDestino.setText(destinoActual.getNombre());
        txtCiudad.setText(destinoActual.getCiudad());
        txtDescripcion.setText(destinoActual.getDescripcion());
        txtClima.setText(String.valueOf(destinoActual.getClima()));

        rutasImagenes = destinoActual.getImagenes();

        // Mostrar la primera imagen si hay al menos una
        if (!rutasImagenes.isEmpty()) {
            String primeraRutaImagen = rutasImagenes.get(0);
            Image imagen = new Image(primeraRutaImagen); // Cargar imagen desde la ruta
            imageView.setImage(imagen); // Mostrar la imagen en el ImageView
        }

        // Deshabilitar el botón "Anterior" si no hay imágenes anteriores
        btnAnterior.setDisable(indiceImagenActual == 0);

        // Deshabilitar el botón "Siguiente" si no hay imágenes siguientes
        btnSiguiente.setDisable(indiceImagenActual == rutasImagenes.size() - 1);
    }


    @FXML
    private void navegarAnterior() {
        if (indiceImagenActual > 0) {
            indiceImagenActual--;
            mostrarImagenActual();
        }
    }

    @FXML
    private void navegarSiguiente() {
        if (indiceImagenActual < rutasImagenes.size() - 1) {
            indiceImagenActual++;
            mostrarImagenActual();
        }
    }

    private void mostrarImagenActual() {
        String rutaImagen = rutasImagenes.get(indiceImagenActual);
        Image imagen = new Image(rutaImagen);
        imageView.setImage(imagen);

        // Actualizar estado de los botones de navegación
        btnAnterior.setDisable(indiceImagenActual == 0);
        btnSiguiente.setDisable(indiceImagenActual == rutasImagenes.size() - 1);

        System.out.println("Ruta de la imagen: " + rutaImagen);
    }
}


