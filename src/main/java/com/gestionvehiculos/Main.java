package com.gestionvehiculos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.gestionvehiculos.controller.ControladorPrincipal;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 * Extiende Application para aprovechar el ciclo de vida de JavaFX (start, stop).
 * Se encarga de cargar la vista inicial (VistaPrincipal.fxml) y conectarla con su controlador.
 */
public class Main extends Application {

    /**
     * Metodo de entrada de JavaFX (llamado automáticamente tras `launch()`).
     * Configura la primera ventana y prepara la navegación entre escenas.
     *
     * @param stage El contenedor principal de la aplicación (ventana raíz).
     * @throws IOException Si no se encuentra VistaPrincipal.fxml.
     */
    @Override
    public void start(Stage stage) throws IOException {

        try {
            //Carga el archivo FXML de la vista principal
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/VistaPrincipal.fxml"));

            //Crea la escena con el contenido de FXML y dimensiones fijas
            Scene scene = new Scene(fxmlLoader.load(), 460, 460);
            stage.setTitle("Gestión de vehículos");
            stage.setScene(scene);
            stage.show();

            // Conecta el controlador con la ventana para permitir navegación
            ControladorPrincipal controladorPrincipal = fxmlLoader.getController();
            controladorPrincipal.setStage(stage);
        } catch(IOException e) {
            System.out.println("Error al cargar VistaPrincipal.fxml" + e.getMessage());
        }
    }

    /**
     * Metodo main estándar para lanzar una aplicación JavaFX.
     * Delega en launch() para iniciar el ciclo de vida.
     */
    public static void main(String[] args) {
        launch();
    }
}