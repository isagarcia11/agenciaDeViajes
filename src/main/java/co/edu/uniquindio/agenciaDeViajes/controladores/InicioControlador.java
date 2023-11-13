package co.edu.uniquindio.agenciaDeViajes.controladores;

import co.edu.uniquindio.agenciaDeViajes.modelo.AgenciaDeViajes;
import co.edu.uniquindio.agenciaDeViajes.modelo.Destino;
import co.edu.uniquindio.agenciaDeViajes.modelo.PaqueteTuristico;
import co.edu.uniquindio.agenciaDeViajes.modelo.Propiedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class InicioControlador implements Initializable {

    @FXML
    private Button iniciarSesion, cambiarIdioma, btnSantorini,btnRoma,btnParis, btnBali;

    @FXML
    private Label banner, banner3;

    @FXML
    private Button btnPaquete1, btnPaquete2;

    @FXML
    private ImageView imagen;

    @FXML
    private Button nombreDestino;

    @FXML
    private Button botonSiguiente;

    @FXML
    private Button botonAtras;

    @FXML
    private List<PaqueteTuristico> listaPaquetes;

    @FXML
    private Button nombrePaquete;

    @FXML
    private ImageView imagenDestino;

    @FXML
    private Button btnAnteriorPaquete, btnSiguientePaquete;

    private final AgenciaDeViajes agenciaDeViajes = AgenciaDeViajes.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    private List<Destino> destinos = agenciaDeViajes.getDestinos();
    private int indiceActual = 0;
    private int indicePaqueteActual = 0;

    private boolean esIngles = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarTextos();
        btnSiguientePaquete.setOnAction(event -> cambiarPaquete(1));
        btnAnteriorPaquete.setOnAction(event -> cambiarPaquete(-1));

        mostrarPaquete(agenciaDeViajes.getPaquetesTuristicos().get(indicePaqueteActual));
        mostrarDestino();
    }

    @FXML
    private void cambiarIdioma(ActionEvent event) {
        // Cambia el idioma
        if (esIngles) {
            Propiedades.getInstance().setLocale(new Locale("es", "ES"));
        } else {
            Propiedades.getInstance().setLocale(Locale.ENGLISH);
        }

        // Invierte el valor de esIngles para la próxima vez
        esIngles = !esIngles;

        // Actualiza la interfaz de usuario
        actualizarTextos();
    }

    @FXML
    private void siguienteDestino() {
        if (indiceActual < destinos.size() - 1) {
            indiceActual++;
            mostrarDestino();
        }
    }

    @FXML
    private void destinoAnterior() {
        if (indiceActual > 0) {
            indiceActual--;
            mostrarDestino();
        }
    }

    private void mostrarDestino() {
        Destino destinoActual = destinos.get(indiceActual);
        nombreDestino.setText(destinoActual.getNombre());

        String urlImagen = destinoActual.getImagenes().get(0);
        Image imagen = new Image(urlImagen);
        this.imagen.setImage(imagen);
    }

    private void mostrarPaquete(PaqueteTuristico paqueteTuristico) {
        if (paqueteTuristico != null) {
            nombrePaquete.setText(paqueteTuristico.getNombre());

            if (!paqueteTuristico.getDestinos().isEmpty()) {
                String primeraRutaImagen = paqueteTuristico.getDestinos().get(0).getImagenes().get(0);
                Image img = new Image(primeraRutaImagen);
                imagenDestino.setImage(img);
            } else {
                imagenDestino.setImage(null);
            }
        } else {
            nombrePaquete.setText("");
            imagenDestino.setImage(null);
        }
    }

    private void cambiarPaquete(int cambio) {
        int nuevoIndice = indicePaqueteActual + cambio;

        if (nuevoIndice >= 0 && nuevoIndice < agenciaDeViajes.getPaquetesTuristicos().size()) {
            indicePaqueteActual = nuevoIndice;
            mostrarPaquete(agenciaDeViajes.getPaquetesTuristicos().get(indicePaqueteActual));
        }
    }

    @FXML
    public void conocerDestino(ActionEvent event){
        agenciaDeViajes.setDestino(destinos.get(indiceActual));

        Object evt = event.getSource();

        if(evt.equals(nombreDestino)){
            agenciaDeViajes.loadStage("/ventanas/conocerDestino.fxml", event);
        }
    }

    @FXML
    public void conocerPaquete(ActionEvent event){
        agenciaDeViajes.setPaquetesTuristicos(listaPaquetes.get(indicePaqueteActual));

        Object evt = event.getSource();

        if(evt.equals(nombrePaquete)){
            agenciaDeViajes.loadStage("/ventanas/conocerPaquete.fxml", event);
        }
    }

    private void actualizarTextos() {
        iniciarSesion.setText(propiedades.getResourceBundle().getString("TextoIniciarSesion"));
        cambiarIdioma.setText(propiedades.getResourceBundle().getString("TextoCambiarIdioma"));
        banner.setText(propiedades.getResourceBundle().getString("TextoBanner"));
        banner3.setText(propiedades.getResourceBundle().getString("TextoBanner3"));
        btnBali.setText(propiedades.getResourceBundle().getString("TextoBali"));
        btnParis.setText(propiedades.getResourceBundle().getString("TextoParis"));
        btnRoma.setText(propiedades.getResourceBundle().getString("TextoRoma"));
        btnSantorini.setText(propiedades.getResourceBundle().getString("TextoSantorini"));
        btnPaquete1.setText(propiedades.getResourceBundle().getString("TextoPaquete1"));
        btnPaquete2.setText(propiedades.getResourceBundle().getString("TextoPaquete2"));
    }


    public void iniciarSesion(ActionEvent event){
        agenciaDeViajes.setDestino(agenciaDeViajes.getPaquetesTuristicos().get(indicePaqueteActual).getDestinos().get(indiceActual));

        Object evt = event.getSource();

        if(evt.equals(iniciarSesion)){
            agenciaDeViajes.loadStage("/ventanas/login.fxml", event);
        }
    }

    public void irSantorini(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnSantorini)){
            agenciaDeViajes.loadStage("/ventanas/ventanaSantorini.fxml", event);
        }

    }
    public void irParis(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnParis)){
            agenciaDeViajes.loadStage("/ventanas/ventanaParis.fxml", event);
        }

    }
    public void irBali(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnBali)){
            agenciaDeViajes.loadStage("/ventanas/ventanaBali.fxml", event);
        }

    }
    public void irRoma(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnRoma)){
            agenciaDeViajes.loadStage("/ventanas/ventanaRoma.fxml", event);
        }

    }
    public void siguienteVentana(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnPaquete1)){
            agenciaDeViajes.loadStage("/ventanas/ventanaPaqueteEuropa.fxml", event);
        }

    }
    public void siguienteVentana2(ActionEvent event){
        Object evt = event.getSource();
        if(evt.equals(btnPaquete2)){
            agenciaDeViajes.loadStage("/ventanas/ventanaPaqueteIslas.fxml", event);
        }

    }

}
