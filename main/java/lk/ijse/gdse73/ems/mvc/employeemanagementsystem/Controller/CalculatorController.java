package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {

    public Button btnFirst;
    public Button btnSecond;
    public Button btnThird;
    public Button btnFive;
    public Button btnSix;
    public Button btnStar;
    public Button btnSeven;
    public Button btnEight;
    public Button btnNine;
    public Button btnZero;
    public Button btnClear;
    public Button btnEqual;
    public Button btnSum;
    public Button btnMinus;
    public Button btnDivide;
    public Button btnFour;
    @FXML
    private TextField txtdisplay;

    private String operator = "";
    private double num1 = 0;
    private boolean startNewNumber = true;


    public void handle1ButtonClick(ActionEvent actionEvent) {

        appendNumber("1");
    }
    public void handle2ButtonClick(ActionEvent actionEvent) {

        appendNumber("2");
    }
    public void handle3ButtonClick(ActionEvent actionEvent) {

        appendNumber("3");
    }
    public void handle4ButtonClick(ActionEvent actionEvent) {

        appendNumber("4");
    }
    public void handle5ButtonClick(ActionEvent actionEvent) {

        appendNumber("5");
    }
    public void handle6ButtonClick(ActionEvent actionEvent) {

        appendNumber("6");
    }
    public void handle7ButtonClick(ActionEvent actionEvent) {

        appendNumber("7");
    }
    public void handle8ButtonClick(ActionEvent actionEvent) {

        appendNumber("8");
    }
    public void handle9ButtonClick(ActionEvent actionEvent) {

        appendNumber("9");
    }
    public void handle0ButtonClick(ActionEvent actionEvent) {

        appendNumber("0");
    }
    public void handleSumOnAction(ActionEvent actionEvent) {

        handleOperator("+");
    }
    public void handleMinusButtonClick(ActionEvent actionEvent) {

        handleOperator("-");
    }
    public void handleStarButtonClick(ActionEvent actionEvent) {

        handleOperator("*");
    }
    public void handleDivideButtonClick(ActionEvent actionEvent) {

        handleOperator("/");
    }


    public void handleEqualOnAction(ActionEvent actionEvent) {
        try {
            double num2 = Double.parseDouble(txtdisplay.getText());
            double result = switch (operator) {
                case "+" -> num1 + num2;
                case "-" -> num1 - num2;
                case "*" -> num1 * num2;
                case "/" -> num2 != 0 ? num1 / num2 : 0;
                default -> num2;
            };
            txtdisplay.setText(String.valueOf(result));
            startNewNumber = true;
        } catch (NumberFormatException e) {
            txtdisplay.setText("Error");
        }
    }


    public void handleClearOnAction(ActionEvent actionEvent) {
        txtdisplay.clear();
        num1 = 0;
        operator = "";
        startNewNumber = true;
    }


    private void appendNumber(String num) {
        if (startNewNumber) {
            txtdisplay.setText(num);
            startNewNumber = false;
        } else {
            txtdisplay.setText(txtdisplay.getText() + num);
        }
    }

    private void handleOperator(String op) {
        try {
            num1 = Double.parseDouble(txtdisplay.getText());
            operator = op;
            startNewNumber = true;
        } catch (NumberFormatException e) {
            txtdisplay.setText("Error");
        }
    }


}
