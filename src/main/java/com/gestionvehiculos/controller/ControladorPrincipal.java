package com.gestionvehiculos.controller;

import com.gestionvehiculos.model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controlador de la ventana principal de la aplicación.
 * Gestiona la entrada de datos del usuario, validación, creación de reservas y navegación hacia la lista de reservas.
 * Implementa Initializable para configurar componentes al cargar la vista.
 */
public class ControladorPrincipal implements Initializable {

    // Referencias a los componentes de la interfaz (inyectados automáticamente por FXMLLoader)
    @FXML private Label labelFechaReserva;
    @FXML private Spinner<Integer> spinnerHorasUso;
    @FXML private Label labelHorasUso;
    @FXML private TextArea areaInfo;
    @FXML private RadioButton cargaLenta;
    @FXML private ComboBox<String> comboBoxVehiculo;
    @FXML private Button btnVerReservas;
    @FXML private Label labelTipoCarga;
    @FXML private TextField txtFieldNombre;
    @FXML private Label labelVehiculo;
    @FXML private Label labelNombre;
    @FXML private RadioButton cargaRapida;
    @FXML private Button btnReservar;
    @FXML private DatePicker datePicker;

    // Datos compartidos
    private Stage stage;
    private ObservableList<Reserva> reservas = FXCollections.observableArrayList();;

    // Constante: lista de vehículos disponibles
    private final String[] VEHICULOS = {
            "Tesla Model 3", "Nissan Leaf", "Renault Zoe", "BMW i4", "Hyundai Kona Electric"
    };

    // Grupo para asegurar selección mutuamente excluyente entre radio buttons
    @FXML private ToggleGroup grupoCarga;

    /**
     * Metodo llamado automáticamente tras cargar el FXML.
     * Configura los componentes con valores iniciales (lista de vehículos, rango de horas, grupo de radio buttons).
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Carga la lista de vehículos en el ComboBox
        comboBoxVehiculo.setItems(FXCollections.observableArrayList(VEHICULOS));
        comboBoxVehiculo.setValue(null);

        // Configura el Spinner para horas de uso (entre 1 y 24 horas)
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1);
        spinnerHorasUso.setValueFactory(valueFactory);

        // Agrupa los radio buttons para que solo uno pueda estar seleccionado a la vez
        grupoCarga = new ToggleGroup();
        cargaLenta.setToggleGroup(grupoCarga);
        cargaRapida.setToggleGroup(grupoCarga);
    }

    /**
     * Inicializa la lista de reservas compartida.
     * Se llama desde ControladorLista al volver a esta ventana, preservando los datos.
     */
    public void initAttributtes(ObservableList<Reserva> reservas) {
        this.reservas = reservas;
    }

    /**
     * Establece la referencia a la ventana principal (Stage).
     * Necesario para cambiar escenas (navegación).
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Registra una nueva reserva tras validar todos los campos obligatorios.
     * Calcula el precio automáticamente según tipo de carga y horas.
     * Muestra el resumen en el área de texto y añade la reserva a la lista compartida.
     *
     * @param event Evento de clic en el botón "Registrar reserva".
     */
    @FXML
    private void reservar(ActionEvent event) {
        try {
            // Recoge los valores de los controles
            String cliente = this.txtFieldNombre.getText();
            String vehiculo = this.comboBoxVehiculo.getValue();
            LocalDate fecha = this.datePicker.getValue();
            int horasUso = spinnerHorasUso.getValue();
            String tipoCarga = cargaLenta.isSelected() ? "Carga lenta" : "Carga rápida";

            // Validaciones básicas (campos obligatorios y rangos)
            if (cliente.isEmpty()){
                mostrarAlertaError("Error: Nombre requerido.","Introduce el nombre del cliente.");
                return;
            }
            if (vehiculo == null){
                mostrarAlertaError("Error: Vehículo requerido.","Selecciona un vehículo.");
                return;
            }
            if (fecha == null){
                mostrarAlertaError("Error: Fecha requerida.","Selecciona una fecha.");
                return;
            }
            if (horasUso < 1 || horasUso > 24){
                mostrarAlertaError("Error. Horas inválidas","Selecciona entre 1 y 24 horas.");
                return;
            }

            // Crea la reserva (el cálculo del precio se hace dentro del constructor de Reserva)
            Reserva r = new Reserva(cliente, vehiculo, fecha, horasUso, tipoCarga);
            reservas.add(r);

            // Muestra resumen en el área de texto
            String informacion = (
                    "Reserva registrada correctamente\n" +
                    "Cliente: " + r.getCliente() +
                    "\nVehiculo: " + r.getVehiculo() +
                    "\nFecha de reserva: " + r.getFecha() +
                    "\nHoras de duración: " + r.getHorasUso() +
                    "\nTipo de carga: " + r.getTipoCarga() +
                    "\nPrecio total: " + r.getPrecioTotal() + "€"
                    );
            areaInfo.setText(informacion);

        } catch (Exception e) {
            mostrarAlertaError("Error","Formato incorrecto o dato inválido");
        }
    }

    /**
     * Navega a la ventana de lista de reservas.
     * Pasa la lista actual de reservas al controlador de la nueva vista.
     *
     * @param event Evento de clic en el botón "Ver reservas".
     */
    @FXML
    private void verReservas(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/VistaLista.fxml"));
            Parent root = fxmlLoader.load();

            ControladorLista controladorLista = fxmlLoader.getController();
            controladorLista.setReservas(reservas);
            controladorLista.setStage(stage);
            stage.setScene(new Scene(root, 860, 570));
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Metodo auxiliar para evitar repetición de código en validaciones
    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
