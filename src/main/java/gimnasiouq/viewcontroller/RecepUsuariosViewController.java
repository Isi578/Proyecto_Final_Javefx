package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class RecepUsuariosViewController {

    private ModelFactory modelFactory;
    private ObservableList<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnNuevo;

    @FXML
    private TableView<Usuario> tableUsuario;

    @FXML
    private TableColumn<Usuario, String> tcUsuario;

    @FXML
    private TableColumn<Usuario, String> tcCelular;

    @FXML
    private TableColumn<Usuario, String> tcEdad;

    @FXML
    private TableColumn<Usuario, String> tcIdentificacion;

    @FXML
    private TableColumn<Usuario, String> tcMembresia;

    @FXML
    private TableColumn<Usuario, String> tcNombre;

    @FXML
    private TextField txtCelular;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtIdentificacion;

    @FXML
    private ComboBox<String> comboBoxMembresia;

    @FXML
    private ComboBox<String> comboBoxUsuarios;

    @FXML
    private TextField txtNombre;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarUsuario();
    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarUsuario();
    }

    @FXML
    void onEliminar(ActionEvent event) {
        eliminarUsuario();
    }

    @FXML
    void onNuevo(ActionEvent event) {
        nuevoUsuario();
    }

    @FXML
    void initialize() {
        initView();
        if (comboBoxMembresia != null) {
            comboBoxMembresia.getItems().addAll("Basica", "Premium", "VIP");
        }
        if (comboBoxUsuarios != null) {
            comboBoxUsuarios.getItems().addAll("Externo", "Estudiante", "TrabajadorUQ");
        }
    }

    private void initView() {
        this.modelFactory = ModelFactory.getInstance();
        initDataBinding();
        listaUsuarios = modelFactory.obtenerUsuariosObservable();
        tableUsuario.setItems(listaUsuarios);
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
            tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdad())));
        }
        if (tcCelular != null) {
            tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCelular()));
        }
        if (tcMembresia != null) {
            tcMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembresia()));
        }
        if (tcUsuario != null) {
            tcUsuario.setCellValueFactory(cellData -> {
                if (cellData.getValue() instanceof Estudiante) {
                    return new SimpleStringProperty("Estudiante");
                } else if (cellData.getValue() instanceof Trabajador) {
                    return new SimpleStringProperty("Trabajador UQ");
                } else {
                    return new SimpleStringProperty("Externo");
                }
            });
        }
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void nuevoUsuario() {
        limpiarCampos();
        usuarioSeleccionado = null;
        tableUsuario.getSelectionModel().clearSelection();
    }

    private void agregarUsuario() {
        try {
            Usuario usuario = crearUsuario();

            if (datosValidos(usuario)) {
                modelFactory.registrarUsuario(usuario);
                limpiarCampos();
                mostrarVentanaEmergente("Usuario agregado", "Éxito", "El usuario se agregó correctamente", Alert.AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            mostrarVentanaEmergente("Error de formato", "Error", "La edad debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarVentanaEmergente("Usuario no agregado", "Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void actualizarUsuario() {
        if (usuarioSeleccionado != null) {
            try {
                Usuario usuarioActualizado = crearUsuario();
                if (datosValidos(usuarioActualizado)) {
                    modelFactory.actualizarUsuario(usuarioSeleccionado.getIdentificacion(), usuarioActualizado);
                    tableUsuario.refresh();
                    limpiarCampos();
                    mostrarVentanaEmergente("Usuario actualizado", "Éxito", "El usuario se actualizó correctamente", Alert.AlertType.INFORMATION);
                }
            } catch (NumberFormatException e) {
                mostrarVentanaEmergente("Error de formato", "Error", "La edad debe ser un número válido.", Alert.AlertType.ERROR);
            } catch (Exception e) {
                mostrarVentanaEmergente("Usuario no actualizado", "Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            mostrarVentanaEmergente("Seleccione un usuario", "Advertencia", "Debe seleccionar un usuario de la tabla para actualizarlo", Alert.AlertType.WARNING);
        }
    }

    private void eliminarUsuario() {
        if (usuarioSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro?");
            confirmacion.setContentText("¿Desea eliminar al usuario " + usuarioSeleccionado.getNombre() + " con identificación " + usuarioSeleccionado.getIdentificacion() + "?");

            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        modelFactory.eliminarUsuario(usuarioSeleccionado.getIdentificacion());
                        limpiarCampos();
                        usuarioSeleccionado = null;
                        mostrarVentanaEmergente("Usuario eliminado", "Éxito", "El usuario se eliminó correctamente", Alert.AlertType.INFORMATION);
                    } catch (Exception e) {
                        mostrarVentanaEmergente("Usuario no eliminado", "Error", e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            mostrarVentanaEmergente("Seleccione un usuario", "Advertencia", "Debe seleccionar un usuario de la tabla para eliminarlo", Alert.AlertType.WARNING);
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtIdentificacion.clear();
        txtEdad.clear();
        txtCelular.clear();
        comboBoxMembresia.setValue(null);
        comboBoxUsuarios.setValue(null);
    }

    private boolean datosValidos(Usuario usuario) {
        String membresia = comboBoxMembresia.getValue();
        if (usuario == null ||
                usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
                usuario.getIdentificacion() == null || usuario.getIdentificacion().isEmpty() ||
                membresia == null || membresia.isEmpty()) {
            mostrarVentanaEmergente("Datos incompletos", "Error", "Por favor complete todos los campos obligatorios (Nombre, ID, Membresía).", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private Usuario crearUsuario() throws NumberFormatException {
        String tipoUsuario = comboBoxUsuarios.getValue();
        if (tipoUsuario == null) {
            mostrarVentanaEmergente("Tipo de usuario no seleccionado", "Error", "Debe seleccionar un tipo de usuario", Alert.AlertType.ERROR);
            return null;
        }

        String nombre = txtNombre.getText();
        String id = txtIdentificacion.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        String telefono = txtCelular.getText();
        String membresia = comboBoxMembresia.getValue();

        switch (tipoUsuario) {
            case "Estudiante":
                return new Estudiante(nombre, id, edad, telefono, membresia, 0.1); // Descuento de ejemplo
            case "TrabajadorUQ":
                return new Trabajador(nombre, id, edad, telefono, membresia, "Seguro de salud"); // Beneficios de ejemplo
            default:
                return new Externo(nombre, id, edad, telefono, membresia);
        }
    }

    private void mostrarInformacionUsuario(Usuario usuarioSeleccionado) {
        if (usuarioSeleccionado != null) {
            txtNombre.setText(usuarioSeleccionado.getNombre());
            txtIdentificacion.setText(usuarioSeleccionado.getIdentificacion());
            txtEdad.setText(String.valueOf(usuarioSeleccionado.getEdad()));
            txtCelular.setText(usuarioSeleccionado.getCelular());
            comboBoxMembresia.setValue(usuarioSeleccionado.getMembresia());

            if (usuarioSeleccionado instanceof Estudiante) {
                comboBoxUsuarios.setValue("Estudiante");
            } else if (usuarioSeleccionado instanceof Trabajador) {
                comboBoxUsuarios.setValue("TrabajadorUQ");
            } else {
                comboBoxUsuarios.setValue("Externo");
            }
        }
    }

    private void mostrarVentanaEmergente(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
