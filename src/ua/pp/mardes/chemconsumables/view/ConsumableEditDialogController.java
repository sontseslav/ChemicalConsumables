package ua.pp.mardes.chemconsumables.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ua.pp.mardes.chemconsumables.model.Consumable;

import java.time.LocalDate;

/**
 * Created by coder on 10.01.17.
 */

public class ConsumableEditDialogController {

    private Stage dialogStage;
    private Consumable consumable;
    private boolean okPressed = false;

    @FXML
    private TextField consumNameField;
    @FXML
    private TextField purityClassField;
    @FXML
    private TextField consumISOField;
    @FXML
    private TextField qualityCertificateField;
    @FXML
    private DatePicker productionDateField = new DatePicker();
    //Useful time in month, Integer
    @FXML
    private TextField usefulTimeField;
    @FXML
    private DatePicker expirationTimeField = new DatePicker();
    @FXML
    private TextField consumQuantityField;
    @FXML
    private DatePicker spendDateField = new DatePicker();
    //must be modified on access
    //@FXML
    //private DatePicker lastChangeField = new DatePicker();

    /**
     * Controller initializer, calls automatically on fxml load
     */
    @FXML
    private void initialize(){}

    /**
     * Set dialog stage from outside
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    /**
     * Set selected consumable and get it fields
     * @param consumable
     */
    public void setConsumable(Consumable consumable){
        this.consumable = consumable;

        this.consumNameField.setText(consumable.getConsumName());
        this.purityClassField.setText(consumable.getPurityClass());
        this.consumISOField.setText(consumable.getConsumISO());
        this.qualityCertificateField.setText(consumable.getQualityPass());
        this.productionDateField.setValue(consumable.getProductionDate());
        //may be calculated automatically
        this.usefulTimeField.setText(Integer.toString(consumable.getUsefulTime()));

        this.expirationTimeField.setValue(consumable.getExpirationDate());
        this.consumQuantityField.setText(consumable.getConsumQuantity());
        this.spendDateField.setValue(consumable.getSpendDate());
        // not editable
        // this.lastChangeField.setValue(consumable.getLastChange());
    }

    /**
     *
     * @return true if user pressed OK button
     */
    public boolean isOkPressed(){
        return okPressed;
    }

    /**
     * Stores changes to Consumable instance on OK pressed
     */
    @FXML
    private void handleOkButton(){
        if (isFieldsValid()){
            consumable.setConsumName(consumNameField.getText());
            consumable.setPurityClass(purityClassField.getText());
            consumable.setConsumISO(consumISOField.getText());
            consumable.setQualityPass(qualityCertificateField.getText());
            consumable.setProductionDate(productionDateField.getValue());
            consumable.setUsefulTime(Integer.parseInt(usefulTimeField.getText()));
            consumable.setExpirationDate(expirationTimeField.getValue());
            consumable.setConsumQuantity(consumQuantityField.getText());
            consumable.setSpendDate(spendDateField.getValue());
            consumable.setLastChange(LocalDate.now());

            okPressed = true;

            dialogStage.close();
        }
    }

    /**
     * Discards changes and closes dialog
     */
    @FXML
    private void handleCancellButton(){
        dialogStage.close();
    }

    /**
     * Input fields validator
     * @return true if all fields values is correct
     */
    private boolean isFieldsValid(){
        String errMsg = "";
        if (consumNameField.getText() == null || consumNameField.getText().length() == 0 ||
                consumNameField.getText().matches("^\\s+")){
            errMsg += "Відсутня назва реактиву.\n";
        }
        if (purityClassField.getText() == null || purityClassField.getText().length() == 0 ||
                purityClassField.getText().matches("^\\s+")){
            errMsg += "Відсутній клас чистоти.\n";
        }
        /*if (consumISOField.getText() == null || consumISOField.getText().length() == 0 ||
                consumISOField.getText().matches("^\\s+")){
            errMsg += "\n";
        }*/
        if (qualityCertificateField.getText() == null || qualityCertificateField.getText().length() == 0 ||
                qualityCertificateField.getText().matches("^\\s+")){
            errMsg += "Відсутній паспорт якості.\n";
        }
        //need to be tested
        if (productionDateField.getValue() == null || productionDateField.getValue().toString().length() == 0 ||
                productionDateField.getValue().getYear() > LocalDate.now().getYear() ||
                productionDateField.getValue().getYear() < LocalDate.of(1990, 1, 1).getYear()){//critical year
            errMsg += "Неправильна дата виготовлення.\n";
        }
        // Deprecate if calculate it automatically
        if (usefulTimeField.getText() == null || usefulTimeField.getText().length() == 0){
            errMsg += "Відсутній термін зберігання.\n";
        }else{
            int period = 0;
            try{
                period = Integer.parseInt(usefulTimeField.getText());
            }catch (NumberFormatException e){
                errMsg += "Термін зберігання вказується цілим числом (кількість місяців).\n";
            }
            if (period <= 0){
                errMsg += "Термін зберігання має бути більшим від нуля.\n";
            }
        }

        if (expirationTimeField.getValue() == null || expirationTimeField.getValue().toString().length() == 0 ||
                expirationTimeField.getValue().getYear() < LocalDate.now().getYear()){
            errMsg += "Неправильна кінцева дата зберігання.\n";
        }
        if (consumQuantityField.getText() == null || consumQuantityField.getText().length() == 0 ||
                consumQuantityField.getText().matches("^\\s+")){
            errMsg += "Відсутня кількість реактиву.\n";
        }
        /*if (spendDateField == null || spendDateField.getValue().toString().length() == 0 ||
                spendDateField.getValue().getYear() < LocalDate.now().getYear()){
            errMsg += "\n";
        }*/
        if (errMsg.length() == 0){
            // no errors detected
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Помилка введення");
            alert.setHeaderText("Деякі поля заповнені некоректно:");
            alert.setContentText(errMsg);
            alert.showAndWait();
            return false;
        }
    }
}