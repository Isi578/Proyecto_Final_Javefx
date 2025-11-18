package gimnasiouq.viewcontroller;

import gimnasiouq.factory.ModelFactory;
import gimnasiouq.model.*;
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

public class AdminUsuariosViewController implements Initializable {

    private Gimnasio gimnasio;
    private ObservableList<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gimnasio = ModelFactory.getInstance().getGimnasio();
        this.listaUsuarios = FXCollections.observableArrayList(gimnasio.getListaUsuarios());

        initDataBinding();
        tableUsuario.setItems(listaUsuarios);
        listenerSelection();

        comboBoxUsuarios.getItems().addAll("Externo", "Estudiante", "Trabajador");
        comboBoxMembresia.getItems().addAll("Basica", "Premium", "VIP");
        txtIdentificacion.setEditable(true); // Asegurarse de que sea editable al inicio
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdentificacion()));
        tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdad())));
        tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCelular()));
        tcMembresia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoMembresia()));
        tcUsuario.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof Estudiante) return new SimpleStringProperty("Estudiante");
            if (cellData.getValue() instanceof Trabajador) return new SimpleStringProperty("Trabajador");
            if (cellData.getValue() instanceof Externo) return new SimpleStringProperty("Externo");
            return new SimpleStringProperty("N/A");
        });
    }

    private void listenerSelection() {
        tableUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            usuarioSeleccionado = newSelection;
            mostrarInformacionUsuario(usuarioSeleccionado);
            // Ya no se deshabilita la edición de la identificación aquí
        });
    }

    @FXML
    void onNuevo() {
        limpiarCampos();
    }

    @FXML
    void onAgregar() {
        try {
            Usuario usuario = crearUsuarioDesdeFormulario();
            if (usuario == null) return;

            if (datosValidos(usuario)) {
                gimnasio.registrarUsuario(usuario);
                listaUsuarios.setAll(gimnasio.getListaUsuarios()); // Actualizar observable list
                tableUsuario.refresh(); // Refrescar tabla
                limpiarCampos();
                mostrarAlerta("Usuario Agregado", "El usuario se agregó correctamente.", Alert.AlertType.INFORMATION);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "La edad debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error al Agregar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onActualizar() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Seleccione un Usuario", "Debe seleccionar un usuario de la tabla para actualizarlo.", Alert.AlertType.WARNING);
            return;
        }

        try {
            String nombre = txtNombre.getText();
            // La identificación se toma del campo de texto, ya que es editable
            String identificacionActualizada = txtIdentificacion.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String celular = txtCelular.getText();
            String tipoMembresia = comboBoxMembresia.getValue();

            if (nombre.isEmpty() || identificacionActualizada.isEmpty()) {
                mostrarAlerta("Datos Incompletos", "El nombre y la identificación no pueden estar vacíos.", Alert.AlertType.ERROR);
                return;
            }

            // Si la identificación ha cambiado, se debe manejar como un nuevo usuario o una operación de cambio de ID
            // Por ahora, asumimos que si se actualiza, la ID no cambia o se maneja en el modelo.
            // Si la intención es permitir cambiar la ID, la lógica de `actualizarUsuario` en Gimnasio.java necesitaría ser más compleja.
            // Para este caso, se usará la ID original del usuario seleccionado para buscarlo en el modelo.
            // Si el usuario cambió la ID en el campo, esto podría llevar a un error si la nueva ID ya existe o si la original no se encuentra.
            // Para simplificar y mantener la funcionalidad de "actualizar", se seguirá usando la ID original del usuario seleccionado.
            // Si se desea cambiar la ID, se debería implementar una función específica para ello o un "eliminar y agregar".

            Usuario datosNuevos = new Usuario(nombre, usuarioSeleccionado.getIdentificacion(), edad, celular, tipoMembresia != null ? tipoMembresia : "Sin Membresia") {
            };

            gimnasio.actualizarUsuario(usuarioSeleccionado.getIdentificacion(), datosNuevos);

            listaUsuarios.setAll(gimnasio.getListaUsuarios()); // Actualizar observable list
            tableUsuario.refresh();
            limpiarCampos();
            mostrarAlerta("Usuario Actualizado", "El usuario se actualizó correctamente.", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "La edad debe ser un número válido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error al Actualizar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onEliminar() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Seleccione un Usuario", "Debe seleccionar un usuario de la tabla para eliminarlo.", Alert.AlertType.WARNING);
            return;
        }

        Optional<ButtonType> result = mostrarAlertaConfirmacion("Confirmar Eliminación",
                "¿Está seguro de que desea eliminar al usuario " + usuarioSeleccionado.getNombre() + "?");

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                gimnasio.eliminarUsuario(usuarioSeleccionado.getIdentificacion());
                listaUsuarios.setAll(gimnasio.getListaUsuarios()); // Actualizar observable list
                tableUsuario.refresh(); // Refrescar tabla
                limpiarCampos();
                mostrarAlerta("Usuario Eliminado", "El usuario se eliminó correctamente.", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Error al Eliminar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void limpiarCampos() {
        tableUsuario.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
        txtNombre.clear();
        txtIdentificacion.clear();
        txtEdad.clear();
        txtCelular.clear();
        comboBoxMembresia.setValue(null);
        comboBoxUsuarios.setValue(null);
        txtIdentificacion.setEditable(true); // Asegurarse de que sea editable al limpiar
    }

    private boolean datosValidos(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
                usuario.getIdentificacion() == null || usuario.getIdentificacion().isEmpty()) {
            mostrarAlerta("Datos Incompletos", "Por favor complete todos los campos obligatorios (Nombre, ID).", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private Usuario crearUsuarioDesdeFormulario() throws NumberFormatException {
        String tipoUsuario = comboBoxUsuarios.getValue();
        if (tipoUsuario == null) {
            mostrarAlerta("Tipo de Usuario no Seleccionado", "Debe seleccionar un tipo de usuario.", Alert.AlertType.ERROR);
            return null;
        }

        String nombre = txtNombre.getText();
        String id = txtIdentificacion.getText();
        int edad = txtEdad.getText().isEmpty() ? 0 : Integer.parseInt(txtEdad.getText());
        String telefono = txtCelular.getText();
        String tipoMembresia = comboBoxMembresia.getValue();
        if (tipoMembresia == null || tipoMembresia.isEmpty()) {
            tipoMembresia = "Sin Membresia";
        }

        switch (tipoUsuario) {
            case "Estudiante":
                return new Estudiante(nombre, id, edad, telefono, tipoMembresia, 0.1);
            case "Trabajador":
                return new Trabajador(nombre, id, edad, telefono, tipoMembresia);
            default: // Externo
                return new Externo(nombre, id, edad, telefono, tipoMembresia);
        }
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            txtNombre.setText(usuario.getNombre());
            txtIdentificacion.setText(usuario.getIdentificacion());
            txtEdad.setText(String.valueOf(usuario.getEdad()));
            txtCelular.setText(usuario.getCelular());
            comboBoxMembresia.setValue(usuario.getTipoMembresia());
            // Ya no se deshabilita la edición de la identificación aquí

            if (usuario instanceof Estudiante) {
                comboBoxUsuarios.setValue("Estudiante");
            } else if (usuario instanceof Trabajador) {
                comboBoxUsuarios.setValue("Trabajador");
            } else {
                comboBoxUsuarios.setValue("Externo");
            }
        } else {
            limpiarCampos();
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
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
