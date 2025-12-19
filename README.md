# gestion-vehiculos-javafx

## Descripción
Proyecto académico en Java que implementa una aplicación de escritorio desarrollada en Java con JavaFX, diseñada para simular un sistema básico de reserva de vehículos eléctricos en un entorno de taller o flota compartida. El proyecto implementa una arquitectura orientada a objetos limpia, con separación clara entre modelo (Reserva), vista (archivos FXML) y controlador (clases Java), siguiendo los principios del patrón MVC ligero.

La interfaz permite al usuario:

- Registrar nuevas reservas introduciendo nombre del cliente, vehículo, fecha, duración (1–24 h) y tipo de carga (lenta/rápida).
- Visualizar un listado completo de las reservas activas en formato tabular legible.
- Eliminar reservas de forma segura mediante búsqueda case-insensitive por nombre de cliente.
- Navegar entre ventanas sin pérdida de estado, gracias al uso de una lista compartida (ObservableList<Reserva>), lo que demuestra manejo eficiente de la vida útil de los datos en aplicaciones con múltiples escenas.

El modelo de negocio incorpora una lógica de cálculo de precios integrada:
- Carga lenta: 10 €/hora
- Carga rápida: 15 €/hora
El precio total se computa automáticamente al crear la reserva y se muestra como retroalimentación inmediata al usuario.

---

## Estructura principal
```
gestion-vehiculos-javafx/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── gestionvehiculos/
│       │           ├── Main.java
│       │           ├── model/
│       │           │   └── Reserva.java
│       │           └── controller/
│       │               ├── ControladorPrincipal.java
│       │               └── ControladorLista.java
│       └── resources/
│           ├── VistaPrincipal.fxml
│           └── VistaLista.fxml
└── LICENSE
```

---

## Tecnologías
- Java 24
- JavaFX 21.0.6 (`javafx-controls`, `javafx-fxml`)
- Maven
- Bibliotecas adicionales: ControlsFX, FormsFX, ValidatorFX

---

## Cómo descargar y ejecutar el proyecto
### Clona el repositorio
```bash
git clone https://github.com/carlxs115/gestion-vehiculos-javafx.git
```
### Entra al directorio
```bash
cd gestion-vehiculos-javafx
```
### Compila y ejecuta
```bash
mvn javafx:run
```

---

## Licencia
Este proyecto está bajo la licencia MIT. Con intención de fines educativos.
```text
Copyright © 2025 Señor Riera
```
