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
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdad()));
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
    void onNuevo(ActionEvent event) {
        limpiarCampos();
        entrenadorSeleccionado = null;
        tableEntrenador.getSelectionModel().clearSelection();
        txtIdentificacion.setEditable(true);
    }

    @FXML
    void onGuardar(ActionEvent event) {
        try {
            Entrenador entrenador = buildEntrenadorFromFields();
            if (entrenador == null) return;

            gimnasio.agregarEntrenador(entrenador);
            listaEntrenadores.add(entrenador);

            limpiarCampos();
            mostrarAlerta("Éxito", "Entrenador creado exitosamente.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error al Guardar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar(ActionEvent event) {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        try {
            Entrenador entrenadorActualizado = buildEntrenadorFromFields();
            if (entrenadorActualizado == null) return;

            gimnasio.actualizarEntrenador(entrenadorSeleccionado.getIdentificacion(), entrenadorActualizado);
            tableEntrenador.refresh();
            limpiarCampos();
            mostrarAlerta("Éxito", "Entrenador actualizado exitosamente.", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            mostrarAlerta("Error al Actualizar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar(ActionEvent event) {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla.", Alert.AlertType.ERROR);
            return;
        }

        Optional<ButtonType> result = mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar al entrenador " + entrenadorSeleccionado.getNombre() + "?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                gimnasio.eliminarEntrenador(entrenadorSeleccionado.getIdentificacion());
                listaEntrenadores.remove(entrenadorSeleccionado);
                limpiarCampos();
                mostrarAlerta("Éxito", "Entrenador eliminado exitosamente.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Error al Eliminar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private Entrenador buildEntrenadorFromFields() {
        String nombre = txtNombre.getText();
        String identificacion = txtIdentificacion.getText();
        String edad = txtEdad.getText();
        String correo = txtCorreo.getText();
        String cargo = txtCargo.getText();

        if (nombre.isEmpty() || identificacion.isEmpty() || edad.isEmpty() || correo.isEmpty() || cargo.isEmpty()) {
            mostrarAlerta("Error", "Complete todos los campos.", Alert.AlertType.ERROR);
            return null;
        }

        return new Entrenador(nombre, identificacion, edad, correo, cargo);
    }

    private void mostrarInformacionEntrenador(Entrenador entrenador) {
        if (entrenador != null) {
            txtNombre.setText(entrenador.getNombre());
            txtIdentificacion.setText(entrenador.getIdentificacion());
            txtEdad.setText(entrenador.getEdad());
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
