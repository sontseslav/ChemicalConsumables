package ua.pp.mardes.chemconsumables.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

/**
 * Model class for a Consumable
 * Created by coder on 03.01.17.
 * @author Stanislav Khvalinsky
 */

public class Consumable {
    //not for production
    private static int counter = 0;
    private final IntegerProperty number;
    private final StringProperty consumName;
    private final StringProperty purityClass;
    private final StringProperty consumISO;
    private final StringProperty qualityPass;
    private final ObjectProperty<LocalDate> productionDate;
    private final IntegerProperty usefulTime; //in month
    private final ObjectProperty<LocalDate> expirationDate;
    private final StringProperty consumQuantity;
    private final ObjectProperty<LocalDate> spendDate;
    private final ObjectProperty<LocalDate> lastChange;

    /**
     * for production number should be added while reading DB
     * counter
     */
    {
        counter++;
    }

    /**
     * Default constructor
     */
    public Consumable(){
        this(null,null,null,null,null,0,null,null,null,null);
    }

    /**
     * Regular constructor
     *
     * @param consumName
     * @param purityClass
     * @param consumISO
     * @param qualityPass
     * @param productionDate
     * @param usefulTime
     * @param expirationDate
     * @param consumQuantity
     * @param spendDate
     * @param lastChange;
     */
    public Consumable(String consumName, String purityClass, String consumISO,
                      String qualityPass, LocalDate productionDate,
                      int usefulTime, LocalDate expirationDate, String consumQuantity,
                      LocalDate spendDate, LocalDate lastChange){
        //not for production
        this.number = new SimpleIntegerProperty(counter);
        this.consumName = new SimpleStringProperty(consumName);
        this.purityClass = new SimpleStringProperty(purityClass);
        this.consumISO = new SimpleStringProperty(consumISO);
        this.qualityPass = new SimpleStringProperty(qualityPass);
        this.productionDate = new SimpleObjectProperty<LocalDate>(productionDate);
        this.usefulTime = new SimpleIntegerProperty(usefulTime);
        this.expirationDate = new SimpleObjectProperty<LocalDate>(expirationDate);
        this.consumQuantity = new SimpleStringProperty(consumQuantity);
        this.spendDate = new SimpleObjectProperty<LocalDate>(spendDate);
        this.lastChange = new SimpleObjectProperty<LocalDate>(lastChange);
    }

    /**
     * Testing constructor
     * @param consumName
     */
    public Consumable(String consumName){
        this.number = new SimpleIntegerProperty(counter);
        this.consumName = new SimpleStringProperty(consumName);
        this.purityClass = new SimpleStringProperty("ч.д.а.");
        this.consumISO = new SimpleStringProperty("ГОСТ 3760");
        this.qualityPass = new SimpleStringProperty("№ 78 від 16.06.2016");
        this.productionDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2016, 6, 12));
        this.usefulTime = new SimpleIntegerProperty(12);
        this.expirationDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2017, 6, 12));
        this.consumQuantity = new SimpleStringProperty("1,5 кг");
        this.spendDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
        this.lastChange = new SimpleObjectProperty<LocalDate>(LocalDate.now().minusDays(15));
    }

    public int getNumber(){
        return number.get();
    }

    public IntegerProperty numberProperty(){
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getConsumName() {
        return consumName.get();
    }

    public StringProperty consumNameProperty() {
        return consumName;
    }

    public void setConsumName(String consumName) {
        this.consumName.set(consumName);
    }

    public String getPurityClass() {
        return purityClass.get();
    }

    public StringProperty purityClassProperty() {
        return purityClass;
    }

    public void setPurityClass(String purityClass) {
        this.purityClass.set(purityClass);
    }

    public String getConsumISO() {
        return consumISO.get();
    }

    public StringProperty consumISOProperty() {
        return consumISO;
    }

    public void setConsumISO(String consumISO) {
        this.consumISO.set(consumISO);
    }

    public String getQualityPass() {
        return qualityPass.get();
    }

    public StringProperty qualityPassProperty() {
        return qualityPass;
    }

    public void setQualityPass(String qualityPass) {
        this.qualityPass.set(qualityPass);
    }

    public LocalDate getProductionDate() {
        return productionDate.get();
    }

    public ObjectProperty<LocalDate> productionDateProperty() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate.set(productionDate);
    }

    public int getUsefulTime() {
        return usefulTime.get();
    }

    public IntegerProperty usefulTimeProperty() {
        return usefulTime;
    }

    public void setUsefulTime(int usefulTime) {
        this.usefulTime.set(usefulTime);
    }

    public LocalDate getExpirationDate() {
        return expirationDate.get();
    }

    public ObjectProperty<LocalDate> expirationDateProperty() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate.set(expirationDate);
    }

    public String getConsumQuantity() {
        return consumQuantity.get();
    }

    public StringProperty consumQuantityProperty() {
        return consumQuantity;
    }

    public void setConsumQuantity(String consumQuantity) {
        this.consumQuantity.set(consumQuantity);
    }

    public LocalDate getSpendDate() {
        return spendDate.get();
    }

    public ObjectProperty<LocalDate> spendDateProperty() {
        return spendDate;
    }

    public void setSpendDate(LocalDate spendDate) {
        this.spendDate.set(spendDate);
    }

    public LocalDate getLastChange() {
        return lastChange.get();
    }

    public ObjectProperty<LocalDate> lastChangeProperty() {
        return lastChange;
    }

    public void setLastChange(LocalDate lastChange) {
        this.lastChange.set(lastChange);
    }

    public static int getCounter() {
        return counter;
    }
}
