package ua.pp.mardes.chemconsumables.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ua.pp.mardes.chemconsumables.Main;
import ua.pp.mardes.chemconsumables.db.DbController;
import ua.pp.mardes.chemconsumables.model.Consumable;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Consumables view controller
 * Created by coder on 04.01.17.
 * @author Stanislav Khvalinsky
 */
public class ConsumablesController {
    @FXML
    private TableView<Consumable> consumableTable;

    @FXML
    private TableColumn<Consumable, Long> numberColumn;
    @FXML
    private TableColumn<Consumable, String> consumNameColumn;
    @FXML
    private TableColumn<Consumable, String> purityClassColumn;
    @FXML
    private TableColumn<Consumable, String> consumISOColumn;
    @FXML
    private TableColumn<Consumable, String> qualityCertificateColumn;
    @FXML
    private TableColumn<Consumable, LocalDate> productionDateColumn;
    @FXML
    private TableColumn<Consumable, Integer> usefulTimeColumn;
    @FXML
    private TableColumn<Consumable, LocalDate> expirationDateColumn;
    @FXML
    private TableColumn<Consumable, String> consumQuantityColumn;
    @FXML
    private TableColumn<Consumable, LocalDate> spendDateColumn;
    @FXML
    private TableColumn<Consumable, LocalDate> lastChangeColumn;

    //Reference to Main App
    private Main mainApp;

    /**
     * Constructor
     */
    public ConsumablesController(){}

    /**
     * Initializes controller class. This method is automatically called after loading fxml file
     */
    @FXML
    private void initialize(){
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty().asObject());
        consumNameColumn.setCellValueFactory(cellData -> cellData.getValue().consumNameProperty());
        purityClassColumn.setCellValueFactory(cellData -> cellData.getValue().purityClassProperty());
        consumISOColumn.setCellValueFactory(cellData -> cellData.getValue().consumISOProperty());
        qualityCertificateColumn.setCellValueFactory(cellData -> cellData.getValue().qualityPassProperty());
        productionDateColumn.setCellValueFactory(cellData -> cellData.getValue().productionDateProperty());
        usefulTimeColumn.setCellValueFactory(cellData -> cellData.getValue().usefulTimeProperty().asObject());
        expirationDateColumn.setCellValueFactory(cellData -> cellData.getValue().expirationDateProperty());
        consumQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().consumQuantityProperty());
        spendDateColumn.setCellValueFactory(cellData -> cellData.getValue().spendDateProperty());
        lastChangeColumn.setCellValueFactory(cellData -> cellData.getValue().lastChangeProperty());
    }

    /**
     * Delete consumable helper
     */
    @FXML
    private void helperDeleteConsumable(){
        int selectedIndex = consumableTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Видалення запису");
            alert.setHeaderText(null);
            alert.setContentText("Ви впевнені, що хочете видалити запис?");
            //Get user's response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Consumable consumable = consumableTable.getSelectionModel().getSelectedItem();
                if (prepareDeleteConsumable(consumable)) {
                    consumableTable.getItems().remove(selectedIndex);
                }
            }else{
                alert.close();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Видалення запису");
            alert.setHeaderText("Запис не обрано");
            alert.setContentText("Оберіть запис для видалення!");
            alert.showAndWait();
        }
    }

    /**
     * Add consumable helper
     */
    @FXML
    private void helperAddConsumable(){
        Consumable newConsumable = new Consumable();
        boolean isOkPressed = mainApp.showConsumablesEditDialog(newConsumable);
        if (isOkPressed){
            consumableTable.getItems().removeAll(consumableTable.getItems());
            //by commenting the piece of code below, we prevent double-show of last added element
            mainApp.getConsumableData()/*.add(newConsumable)*/;
        }
    }

    /**
     * Edit consumable helper
     */
    @FXML
    private void helperEditConsumable(){
        Consumable selectedConsumable = consumableTable.getSelectionModel().getSelectedItem();
        if (selectedConsumable != null){
            boolean isOkPressed = mainApp.showConsumablesEditDialog(selectedConsumable);
        }else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Редагування запису");
            alert.setHeaderText("Запис не обрано");
            alert.setContentText("Оберіть запис для редагування");
            alert.showAndWait();
        }
    }

    /**
     * Delete item
     * @param consumable to remove
     * @return true on success
     */
    private boolean prepareDeleteConsumable(Consumable consumable){
        DbController dbControllerInstance = new DbController();
        boolean isDeletedOk = dbControllerInstance.deleteConsumable(consumable);
        if (isDeletedOk){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Вдале видалення");
            alert.setHeaderText(null);
            alert.setContentText("Дані успішно видалені з бази даних.");
            alert.showAndWait();
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Помилка видалення");
            alert.setHeaderText(null);
            alert.setContentText("Помилка видалення з бази даних.");
            alert.showAndWait();
            return false;
        }
    }


    /**
     * Called by Main to set reference on itself
     * @param mainApp
     */
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        //Add observable list data to the table
        consumableTable.setItems(mainApp.getConsumableData());
    }
}
