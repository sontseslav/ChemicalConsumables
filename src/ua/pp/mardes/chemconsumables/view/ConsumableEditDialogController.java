package ua.pp.mardes.chemconsumables.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.pp.mardes.chemconsumables.db.DbController;
import ua.pp.mardes.chemconsumables.model.Consumable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by coder on 10.01.17.
 */

public class ConsumableEditDialogController {

    @FXML
    private TextField consumNameField;
    @FXML
    private ComboBox<String> purityClassField;
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

    private ObservableList<String> purityClassFieldData =
            FXCollections.observableArrayList();
    private Stage dialogStage;
    private Consumable consumable;
    private DbController dbControllerInstance = null;
    private boolean okPressed = false;

    /**
     * Controller initializer, calls automatically on fxml load
     */
    @FXML
    private void initialize(){
        purityClassFieldData.add("ос.ч.");
        purityClassFieldData.add("х.ч.");
        purityClassFieldData.add("ч.д.а.");
        purityClassFieldData.add("ч.");
        purityClassFieldData.add("оч.");
        purityClassFieldData.add("техн.");
        purityClassFieldData.add("сирий");
        purityClassFieldData.add("фарм.");
        purityClassField.setItems(purityClassFieldData);
    }

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
        this.purityClassField.setValue(consumable.getPurityClass());
        this.consumISOField.setText(consumable.getConsumISO());
        this.qualityCertificateField.setText(consumable.getQualityPass());
        this.productionDateField.setValue(consumable.getProductionDate());
        //may be calculated automatically
        this.usefulTimeField.setText(Integer.toString(consumable.getUsefulTime()));

        this.expirationTimeField.setValue(consumable.getExpirationDate());
        this.consumQuantityField.setText(consumable.getConsumQuantity());
        this.spendDateField.setValue(consumable.getSpendDate());
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
            consumable.setPurityClass(purityClassField.getValue());
            consumable.setConsumISO(consumISOField.getText());
            consumable.setQualityPass(qualityCertificateField.getText());
            consumable.setProductionDate(productionDateField.getValue());
            consumable.setUsefulTime(Integer.parseInt(usefulTimeField.getText()));
            consumable.setExpirationDate(expirationTimeField.getValue());
            consumable.setConsumQuantity(consumQuantityField.getText());
            consumable.setSpendDate(spendDateField.getValue());
            consumable.setLastChange(LocalDate.now());

            prepareStoreConsumable(consumable);

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
     * Clear expiration date on useful time changes
     */
    @FXML
    private void handleUsefulTimeAction(){
        expirationTimeField.setValue(null);
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
        //may be empty
        /*if (purityClassField.getText() == null || purityClassField.getText().length() == 0 ||
                purityClassField.getText().matches("^\\s+")){
            errMsg += "Відсутній клас чистоти.\n";
        }*/
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

        if (usefulTimeField.getText() != null || usefulTimeField.getText().length() != 0){
            try{
                Integer.parseInt(usefulTimeField.getText());
            }catch (NumberFormatException e){
                errMsg += "Термін зберігання вказується цілим числом (кількість місяців).\n";
            }
        }

        if ((expirationTimeField.getValue() == null || expirationTimeField.getValue().toString().length() == 0) &&
                (usefulTimeField.getText() == null || usefulTimeField.getText().length() == 0 ||
                        Integer.parseInt(usefulTimeField.getText())==0)){
            //no exp date and no useful time
            errMsg += "Кінцева дата зберігання не може бути розрахована.\n";
        }else if ((usefulTimeField.getText() != null || usefulTimeField.getText().length() != 0) &&
                Integer.parseInt(usefulTimeField.getText()) > 0 &&
                (expirationTimeField.getValue() == null || expirationTimeField.getValue().toString().length() == 0)){
            //if usef. time presented and exp date - not
            expirationTimeField.setValue(productionDateField.getValue().plusMonths(
                    Integer.parseInt(usefulTimeField.getText())
            ));
        }else if ((expirationTimeField.getValue() != null || expirationTimeField.getValue().toString().length() != 0) &&
                (usefulTimeField.getText() == null || usefulTimeField.getText().length() == 0 ||
                        Integer.parseInt(usefulTimeField.getText())==0)) {
            //not presented useful time it calculated based on prod abd exp dates
            //can be safely converted to int
            usefulTimeField.setText(Integer.toString(
                    (int) Math.abs(ChronoUnit.MONTHS.between(
                            expirationTimeField.getValue(), productionDateField.getValue())
                    )));
        }else if (expirationTimeField.getValue().getYear() < LocalDate.now().getYear()){
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
            expirationTimeField.setValue(null);
            usefulTimeField.setText(Integer.toString(0));
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.initModality(Modality.APPLICATION_MODAL);
            //alert.setHeight(250);
            alert.setTitle("Помилка введення");
            alert.setHeaderText("Деякі поля заповнені некоректно:");
            alert.setContentText(errMsg+"\n");
            alert.showAndWait();
            return false;
        }
    }

    private void prepareStoreConsumable(Consumable consumable){
        dbControllerInstance = new DbController();
        boolean isStoredOk = dbControllerInstance.storeConsumable(consumable);
        if (isStoredOk){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(dialogStage);
            alert.setTitle("Вдале збереження");
            alert.setHeaderText(null);
            alert.setContentText("Дані успішно введені в базу даних.");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Помилка збереження");
            alert.setHeaderText(null);
            alert.setContentText("Помилка внесення до бази даних.");
            alert.showAndWait();
        }
    }
}