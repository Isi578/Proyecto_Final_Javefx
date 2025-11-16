package gimnasiouq.viewcontroller;

import gimnasiouq.model.Entrenador;
import gimnasiouq.model.Gimnasio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import javax.swing.*;

public class AdminEntrenadorViewController {
        ObservableList<Entrenador> listaEntrenadores;
        Entrenador entrenadorSeleccionado;
        private Entrenador entrenador;

        @FXML
        private Button btnActualizar;

        @FXML
        private Button btnEliminar;

        @FXML
        private Button btnGuardar;

        @FXML
        private Button btnNuevo;

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
        void onActualizar(ActionEvent event) {
        }

        @FXML
        void onEliminar(ActionEvent event) {
        }

        @FXML
        void onGuardar(ActionEvent event) {
        }

        @FXML
        void onNuevo(ActionEvent event) {
        }


        private void nuevoEntrenador() {
            limpiarCampos();
            tableEntrenador.getSelectionModel().clearSelection();
            entrenadorSeleccionado = null;
        }

        private void guardarEntrenador() {
            String nombre = fieldNombre.getText();
            String identificacion = fieldIdentificacion.getText();
            String edad = fieldEdad.getText();
            String correo = fieldCorreo.getText();
            String cargo = fieldCargo.getText();

            if (nombre == null || nombre.isEmpty() ||
                    identificacion == null || identificacion.isEmpty() ||
                    edad == null || edad.isEmpty() ||
                    correo == null || correo.isEmpty() ||
                    cargo == null || cargo.isEmpty()) {
                mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
                return;
            }

            Entrenador entrenador = new Entrenador(nombre, identificacion, edad, correo, cargo);
            entrenador.setEdad(edad);
            entrenador.setCorreo(correo);
            entrenador.setCargo(cargo);

            if (Gimnasio.agregarEntrenador(entrenador)) {
                mostrarAlerta("Éxito", "Entrenador creado exitosamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
                tableEntrenador.refresh();
            } else {
                mostrarAlerta("Error", "No se pudo crear el entrenador. Verifique que no exista un entrenador con la misma identificación.", Alert.AlertType.ERROR);
            }
        }

        private void actualizarEntrenador() {
            if (entrenadorSeleccionado == null) {
                mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla", Alert.AlertType.ERROR);
                return;
            }

            String nombre = fieldNombre.getText();
            String identificacion = fieldIdentificacion.getText();
            String edad = fielEdad.getText();
            String correo = fieldCorreo.getText();
            String cargo = fieldCargo.getText();

            if (nombre == null || nombre.isEmpty() ||
                    identificacion == null || identificacion.isEmpty() ||
                    edad == null || edad.isEmpty() ||
                    correo == null || correo.isEmpty() ||
                    cargo == null || cargo.isEmpty()) {
                mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
                return;
            }

            Entrenador entrenadorActualizado = new Entrenador(nombre, identificacion, edad, correo, cargo);
            entrenadorActualizado.setEdad(edad);
            entrenadorActualizado.setCorreo(correo);
            entrenadorActualizado.setCargo(cargo);

            if (Gimnasio.actualizarEntrenador(entrenadorSeleccionado.getIdentificacion(), entrenadorActualizado)) {
                mostrarAlerta("Éxito", "Entrenador actualizado exitosamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
                tableEntrenador.refresh();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el entrenador", Alert.AlertType.ERROR);
            }
        }

        private void eliminarEntrenador() {
            if (entrenadorSeleccionado == null) {
                mostrarAlerta("Error", "Debe seleccionar un entrenador de la tabla", Alert.AlertType.ERROR);
                return;
            }

            if (Gimnasio.eliminarEntrenador(entrenadorSeleccionado.getIdentificacion())) {
                mostrarAlerta("Éxito", "Entrenador eliminado exitosamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
                tableEntrenador.refresh();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el entrenador", Alert.AlertType.ERROR);
            }
        }


        private final ObservableList<String> clasesObservables = FXCollections.observableArrayList();

        @FXML
        public void initialize() {
            gimnasiouq.model.Gimnasio = new Gimnasio();
            initView();
        }

        private void initView() {
            initDataBinding();
            listaEntrenadores = ModelFactory.getInstance().obtenerEntrenadorObservable();
            tableEntrenador.setItems(listaEntrenadores);
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
            tableEntrenador.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newSelection) -> {
                        entrenadorSeleccionado = newSelection;
                        mostrarInformacionEntrenador(entrenadorSeleccionado);
                    });
        }

        private void mostrarInformacionEntrenador(Entrenador entrenador) {
            if (entrenador != null) {
                fieldNombre.setText(entrenador.getNombre());
                fieldIdentificacion.setText(entrenador.getIdentificacion());
                fieldEdad.setText(entrenador.getEdad());
                fieldCorreo.setText(entrenador.getCorreo());
                fieldCargo.setText(entrenador.getCargo());
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
            fieldNombre.clear();
            fieldIdentificacion.clear();
            fieldEdad.clear();
            fieldCorreo.clear();
            fieldCargo.clear();
        }
    }


