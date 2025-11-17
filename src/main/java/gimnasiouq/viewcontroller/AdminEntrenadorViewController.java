package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.Entrenador;
import gimnasiouq.model.Gimnasio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminEntrenadorViewController {
    private ModelFactory modelFactory;
    private Gimnasio gimnasio;
    ObservableList<Entrenador> listaEntrenadores;
    Entrenador entrenadorSeleccionado;

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

    @FXML
    void onActualizar() {
        actualizarEntrenador();
    }

    @FXML
    void onEliminar() {
        eliminarEntrenador();
    }

    @FXML
    void onGuardar() {
        guardarEntrenador();
    }

    @FXML
    void onNuevo() {
        nuevoEntrenador();
    }

    private void nuevoEntrenador() {
        limpiarCampos();
        if (tableEntrenador != null) {
            tableEntrenador.getSelectionModel().clearSelection();
        }
        entrenadorSeleccionado = null;
    }

    private void guardarEntrenador() {
        Entrenador entrenador = buildEntrenadorFromFields();
        if (entrenador == null) return;

        if (modelFactory.agregarEntrenador(entrenador)) {
            mostrarAlerta("Éxito", "Entrenador creado exitosamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
            if (tableEntrenador != null) {
                tableEntrenador.refresh();
            }
        } else {
            mostrarAlerta("Error", "No se pudo crear el entrenador. Verifique que no exista un entrenador con la misma identificación.", Alert.AlertType.ERROR);
        }
    }

    private void actualizarEntrenador() {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla", Alert.AlertType.ERROR);
            return;
        }

        Entrenador entrenadorActualizado = buildEntrenadorFromFields();
        if (entrenadorActualizado == null) return;

        try {
            if (modelFactory.actualizarEntrenador(entrenadorSeleccionado.getIdentificacion(), entrenadorActualizado) != null) {
                mostrarAlerta("Éxito", "Entrenador actualizado exitosamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
                if (tableEntrenador != null) {
                    tableEntrenador.refresh();
                }
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el entrenador", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private Entrenador buildEntrenadorFromFields() {
        String nombre = txtNombre.getText();
        String identificacion = txtIdentificacion.getText();
        String edad = txtEdad.getText();
        String correo = txtCorreo.getText();
        String cargo = txtCargo.getText();

        if (nombre == null || nombre.isEmpty() ||
                identificacion == null || identificacion.isEmpty() ||
                edad == null || edad.isEmpty() ||
                correo == null || correo.isEmpty() ||
                cargo == null || cargo.isEmpty()) {
            mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
            return null;
        }
        return new Entrenador(nombre, identificacion, edad, correo, cargo);
    }

    private void eliminarEntrenador() {
        if (entrenadorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla", Alert.AlertType.ERROR);
            return;
        }

        try {
            if (modelFactory.eliminarEntrenador(entrenadorSeleccionado.getIdentificacion())) {
                mostrarAlerta("Éxito", "Entrenador eliminado exitosamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
                if (tableEntrenador != null) {
                    tableEntrenador.refresh();
                }
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el entrenador", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void initialize() {
        assert tableEntrenador != null : "fx:id=\"tableEntrenador\" was not injected: check your FXML file.";
        assert tcCargo != null : "fx:id=\"tcCargo\" was not injected: check your FXML file.";
        assert tcCorreo != null : "fx:id=\"tcCorreo\" was not injected: check your FXML file.";
        assert tcEdad != null : "fx:id=\"tcEdad\" was not injected: check your FXML file.";
        assert tcIdentificacion != null : "fx:id=\"tcIdentificacion\" was not injected: check your FXML file.";
        assert tcNombre != null : "fx:id=\"tcNombre\" was not injected: check your FXML file.";
        assert txtCargo != null : "fx:id=\"txtCargo\" was not injected: check your FXML file.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file.";
        assert txtEdad != null : "fx:id=\"txtEdad\" was not injected: check your FXML file.";
        assert txtIdentificacion != null : "fx:id=\"txtIdentificacion\" was not injected: check your FXML file.";
        assert txtNombre != null : "fx:id=\"txtNombre\" was not injected: check your FXML file.";

        this.modelFactory = ModelFactory.getInstance();
        this.gimnasio = modelFactory.getGimnasio();
        initView();
    }

    private void initView() {
        initDataBinding();
        listaEntrenadores = modelFactory.obtenerEntrenadorObservable();
        if (tableEntrenador != null) {
            tableEntrenador.setItems(listaEntrenadores);
        }
        listenerSelection();
    }

    private void initDataBinding() {
        if (tcNombre != null) {
            tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        }
        if (tcIdentificacion != null) {
            tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        }
        if (tcEdad != null) {
            tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdad()));
        }
        if (tcCorreo != null) {
            tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        }
        if (tcCargo != null) {
            tcCargo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCargo()));
        }
    }

    private void listenerSelection() {
        if (tableEntrenador != null) {
            tableEntrenador.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newSelection) -> {
                        entrenadorSeleccionado = newSelection;
                        mostrarInformacionEntrenador(entrenadorSeleccionado);
                    });
        }
    }

    private void mostrarInformacionEntrenador(Entrenador entrenador) {
        if (entrenador != null) {
            txtNombre.setText(entrenador.getNombre());
            txtIdentificacion.setText(entrenador.getIdentificacion());
            txtEdad.setText(entrenador.getEdad());
            txtCorreo.setText(entrenador.getCorreo());
            txtCargo.setText(entrenador.getCargo());
        } else {
            limpiarCampos();
        }
    }

    private void mostrarAlerta(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtIdentificacion.clear();
        txtEdad.clear();
        txtCorreo.clear();
        txtCargo.clear();
    }
}
