package ua.pp.mardes.chemconsumables;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.pp.mardes.chemconsumables.model.Consumable;
import ua.pp.mardes.chemconsumables.view.ConsumableEditDialogController;
import ua.pp.mardes.chemconsumables.view.ConsumablesController;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Consumables
     */
    private ObservableList<Consumable> consumableData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Витратні матеріали");
        initRootLayout();
        showConsumables();
    }

    /**
     * Constructor
     */
    public Main(){
        //add some sample data
        consumableData.add(new Consumable("Хлороформ"));
        consumableData.add(new Consumable("Аміак водний"));
    }

    /**
     * Returns the data as ObservableList
     * @return
     */
    public ObservableList<Consumable> getConsumableData(){
        return consumableData;
    }

    /**
     * Initialising parent layout
     */
    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Initializing child layout
     */
    public void showConsumables(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/consumablesLayout.fxml"));
            AnchorPane consumables = (AnchorPane) loader.load();
            //Placing child layout in the center of parent (BorderPane)
            rootLayout.setCenter(consumables);
            //Grant the controller access to MainApp
            ConsumablesController controller = loader.getController();
            controller.setMainApp(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Shows edit dialog
     * @param consumable
     * @return true if user pressed OK
     */
    public boolean showConsumablesEditDialog(Consumable consumable){
        try{
            //Load fxml resource
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/consumableEditDialog.fxml"));
            AnchorPane dialog = (AnchorPane) loader.load();
            //Create Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редагування записів");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            //Create Scene
            Scene dialogScene = new Scene(dialog);
            //Add Scene to Stage
            dialogStage.setScene(dialogScene);
            //Set consumable to controller
            ConsumableEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setConsumable(consumable);
            //Show dialog until user closes it
            dialogStage.showAndWait();
            return controller.isOkPressed();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
