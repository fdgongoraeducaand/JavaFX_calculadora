package com.example.javafx_calculadoracompleja;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculadoraControlador {
    // Inyección reflexiva del control anotado con @FXML
    @FXML
    private TextField txtPantalla;

    private double primerOperando = 0.0;
    private String operadorActual = "";
    private boolean inicioNuevoNumero = true;
    @FXML
    private Button porcentaje;
    @FXML
    private Button btnLimpiar;

    /**
     * Invocado automáticamente por FXMLLoader tras completar la inyección.
     * IMPORTANTE: No realizar inicializaciones visuales en el constructor [10].
     */
    @FXML
    private void initialize() {
        txtPantalla.setText("0");
        // Creamos el evento en tiempo de ejecución

        btnLimpiar.setOnAction(event -> {
            txtPantalla.setText("0");
            primerOperando = 0.0;
            operadorActual = "";
            inicioNuevoNumero = true;
        });
    }

    @FXML
    private void handleDigit(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String digito = btn.getText();

        if (inicioNuevoNumero) {
            txtPantalla.setText(digito);
            inicioNuevoNumero = false;
        } else {
            if (txtPantalla.getText().equals("0")) {
                txtPantalla.setText(digito);
            } else {
                txtPantalla.setText(txtPantalla.getText() + digito);
            }
        }
    }

    @FXML
    private void handlePunto(ActionEvent event) {
        if (inicioNuevoNumero) {
            txtPantalla.setText("0.");
            inicioNuevoNumero = false;
        } else if (!txtPantalla.getText().contains(".")) {
            txtPantalla.setText(txtPantalla.getText() + ".");
        }
    }

    @FXML
    private void handleOperador(ActionEvent event) {
        try {
            Button btn = (Button) event.getSource();
            primerOperando = Double.parseDouble(txtPantalla.getText());
            operadorActual = btn.getText();
            inicioNuevoNumero = true;
        } catch (NumberFormatException e) {
            mostrarAlertaError("Entrada Numérica Inválida", "No se pudo interpretar el número ingresado.");
        }
    }

    @FXML
    private void handleIgual(ActionEvent event) {
        if (operadorActual.isEmpty() || inicioNuevoNumero) {
            return;
        }

        try {
            double segundoOperando = Double.parseDouble(txtPantalla.getText());
            double resultado = 0.0;

            switch (operadorActual) {
                case "+":
                    resultado = primerOperando + segundoOperando;
                    break;
                case "-":
                    resultado = primerOperando - segundoOperando;
                    break;
                case "×":
                    resultado = primerOperando * segundoOperando;
                    break;
                case "÷":
                    if (segundoOperando == 0.0) {
                        mostrarAlertaError("Error Aritmético", "No es posible dividir entre cero.");

                        return;
                    }
                    resultado = primerOperando / segundoOperando;
                    break;
            }

            // Formateo del resultado
            if (resultado == (long) resultado) {
                txtPantalla.setText(String.format("%d", (long) resultado));
            } else {
                txtPantalla.setText(String.valueOf(resultado));
            }

            operadorActual = "";
            inicioNuevoNumero = true;

        } catch (NumberFormatException e) {
            mostrarAlertaError("Error de Cálculo", "Ocurrió un error al procesar la operación.");

        }
    }

    @FXML
    private void handleCambioSigno(ActionEvent event) {
        try {
            double valor = Double.parseDouble(txtPantalla.getText());
            if (valor != 0) {
                valor = valor * -1;
                if (valor == (long) valor) {
                    txtPantalla.setText(String.format("%d", (long) valor));
                } else {
                    txtPantalla.setText(String.valueOf(valor));
                }
            }
        } catch (NumberFormatException e) {
            txtPantalla.setText("0");
        }
    }

    @FXML
    private void handlePorcentaje(ActionEvent event) {
        try {
            double valor = Double.parseDouble(txtPantalla.getText());
            txtPantalla.setText(String.valueOf(valor / 100.0));
            inicioNuevoNumero = true;
        } catch (NumberFormatException e) {
            txtPantalla.setText("0");
        }
    }



    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
