package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Entrenador;
import gimnasiouq.model.Gimnasio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminEntrenadorViewController implements Initializable {

    private Gimnasio gimnasio;
    private ObservableList<Entrenador> listaEntrenadores;
    private Entrenador entrenadorSeleccionado;

    @FXML
    private TableView<Entrenador> tableEntrenador;
    @FXML
    private TableColumn<Entrenador, String> tcCargo;
    @FXML
    private TableColumn<Entrenador, String> tcCorreo;
    @FXML
    private TableColumn<Entrenador, String> tcEdad;
    @FXML
    private TableColumn<Entrenador, String> tcIdentificacion;
    @FXML
    private TableColumn<Entrenador, String> tcNombre;
    @FXML
    private TextField txtCargo;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaEntrenadores = FXCollections.observableArrayList(gimnasio.getListaEntrenador());

        initDataBinding();
        tableEntrenador.setItems(listaEntrenadores);
        listenerSelection();
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdad())));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        tcCargo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCargo()));
    }

    private void listenerSelection() {
        tableEntrenador.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            entrenadorSeleccionado = newSelection;
            mostrarInformacionEntrenador(entrenadorSeleccionado);
        });
    }

    @FXML
    void onNuevo() {
        limpiarCampos();
        entrenadorSeleccionado = null;
        tableEntrenador.getSelectionModel().clearSelection();
        txtIdentificacion.setEditable(true);
    }

    @FXML
    void onGuardar() {
        try {
            Entrenador entrenador = buildEntrenadorFromFields();
            if (entrenador == null) return; // Error de validación

            gimnasio.registrarEntrenador(entrenador);
            listaEntrenadores.add(entrenador); // Actualizar la lista observable

            limpiarCampos();
            mostrarAlerta("Éxito", "Entrenador creado exitosamente.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "La edad debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error al Guardar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar() {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Entrenador entrenadorActualizado = buildEntrenadorFromFields();
            if (entrenadorActualizado == null) return;

            gimnasio.actualizarEntrenador(entrenadorSeleccionado.getId(), entrenadorActualizado);
            tableEntrenador.refresh(); // Refrescar para mostrar los cambios
            limpiarCampos();
            mostrarAlerta("Éxito", "Entrenador actualizado exitosamente.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "La edad debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error al Actualizar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar() {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        Optional<ButtonType> result = mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar al entrenador " + entrenadorSeleccionado.getNombre() + "?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                gimnasio.eliminarEntrenador(entrenadorSeleccionado.getId());
                listaEntrenadores.remove(entrenadorSeleccionado); // Actualizar la lista observable
                limpiarCampos();
                mostrarAlerta("Éxito", "Entrenador eliminado exitosamente.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Error al Eliminar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private Entrenador buildEntrenadorFromFields() throws NumberFormatException {
        String nombre = txtNombre.getText();
        String identificacion = txtIdentificacion.getText();
        String edadStr = txtEdad.getText();
        String correo = txtCorreo.getText();
        String cargo = txtCargo.getText();

        if (nombre.isEmpty() || identificacion.isEmpty() || edadStr.isEmpty() || correo.isEmpty() || cargo.isEmpty()) {
            mostrarAlerta("Error", "Complete todos los campos.", Alert.AlertType.ERROR);
            return null;
        }

        int edad = Integer.parseInt(edadStr); // Lanza NumberFormatException si no es válido
        return new Entrenador(nombre, identificacion, edad, correo, cargo);
    }

    private void mostrarInformacionEntrenador(Entrenador entrenador) {
        if (entrenador != null) {
            txtNombre.setText(entrenador.getNombre());
            txtIdentificacion.setText(entrenador.getId());
            txtEdad.setText(String.valueOf(entrenador.getEdad()));
            txtCorreo.setText(entrenador.getCorreo());
            txtCargo.setText(entrenador.getCargo());
            txtIdentificacion.setEditable(false);
        } else {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtIdentificacion.clear();
        txtEdad.clear();
        txtCorreo.clear();
        txtCargo.clear();
        txtIdentificacion.setEditable(true);
        entrenadorSeleccionado = null;
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private Optional<ButtonType> mostrarAlertaConfirmacion(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        return alert.showAndWait();
    }
}
