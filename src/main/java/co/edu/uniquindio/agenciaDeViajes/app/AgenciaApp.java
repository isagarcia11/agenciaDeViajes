package co.edu.uniquindio.agenciaDeViajes.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class AgenciaApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(AgenciaApp.class.getResource("/login.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Agencia De Viajes");
        stage.show();
}

    public static void main(String[] args) {
        launch(AgenciaApp.class, args);
    }
}

