package com.gestionvehiculos.controller;

import com.gestionvehiculos.model.Reserva;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Controlador de la ventana de listado de reservas.
 * Permite visualizar todas las reservas registradas, eliminar una por nombre de cliente
 * y navegar de vuelta a la ventana principal sin perder los datos.
 */
public class ControladorLista {

    // Datos compartidos
    private ObservableList<Reserva> reservas;
    private Stage stage;

    // Componentes de la interfaz
    @FXML private TextArea listaReservas;
    @FXML private Button btnEliminar;
    @FXML private Label tituloLista;

    /**
     * Establece la lista de reservas compartida y actualiza inmediatamente el área de texto.
     */
    public void setReservas(ObservableList<Reserva> reservas){
        this.reservas = reservas;
        actualizarLista();
    }

    /**
     * Establece la referencia a la ventana actual (Stage).
     * Necesario para cambiar escenas al volver.
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Reconstruye el contenido del TextArea con todas las reservas en formato legible.
     * Si no hay reservas, muestra un mensaje informativo.
     */
    private void actualizarLista(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Reserva r : reservas){
            stringBuilder.append("Cliente: ").append(r.getCliente())
                    .append(" | Vehiculo: ").append(r.getVehiculo())
                    .append(" | Fecha de reserva: ").append(r.getFecha())
                    .append(" | Horas de duración: ").append(r.getHorasUso())
                    .append(" | Tipo de carga: ").append(r.getTipoCarga())
                    .append(" | Precio total: ").append(r.getPrecioTotal()).append("€\n");
            stringBuilder.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
        listaReservas.setText(!stringBuilder.isEmpty() ? stringBuilder.toString() : "No hay reservas registradas actualmente.");
    }

    /**
     * Elimina una reserva buscando por nombre de cliente (ignora mayúsculas/minúsculas).
     * Muestra diálogo de entrada, busca, elimina si existe y actualiza la lista.
     *
     * @param event Evento de clic en el botón "Eliminar reserva".
     */
    @FXML
    private void eliminarReserva(ActionEvent event){

        // Diálogo para introducir nombre
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Eliminar reserva");
        dialog.setHeaderText("Introduce el nombre del cliente.");
        dialog.setContentText("Nombre: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String clienteBuscado = result.get().trim();

            if (clienteBuscado.isEmpty()){
                mostrarAlertaInfo("Campo vacío","Debes introducir el nombre del cliente.");
                return;
            }

            Reserva eliminar = null;
            for (Reserva r : reservas){
                if (r.getCliente().equalsIgnoreCase(clienteBuscado)){
                    eliminar = r;
                    break;
                }
            }

            if (eliminar != null){
                reservas.remove(eliminar);
                actualizarLista();
                mostrarAlertaInfo("Reserva eliminada","Reserva de " + eliminar.getCliente() + " eliminada.");
            } else {
                reservas.remove(eliminar);
                actualizarLista();
                mostrarAlertaInfo("Reserva no encontrada","No hay ninguna reserva para el cliente: " + clienteBuscado + ".");
            }
        }
    }

    /**
     * Navega de vuelta a la ventana principal.
     * Pasa la lista actual de reservas para conservar los cambios (eliminaciones).
     *
     * @param event Evento de clic en el botón "Volver".
     */
    @FXML
    private void volver(ActionEvent event){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/VistaPrincipal.fxml"));
            Parent root = fxmlLoader.load();

            ControladorPrincipal controladorPrincipal = fxmlLoader.getController();
            controladorPrincipal.setStage(stage);
            controladorPrincipal.initAttributtes(reservas); // Esto mantiene las reservas tras volver

            stage.setScene(new Scene(root, 460, 460));
            stage.setTitle("Gestión de vehículos");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlertaError("Error","No se pudo volver a la ventana principal.");
        }
    }

    // Métodos auxiliares para alertas (reutilizables)
    private void mostrarAlertaInfo(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}