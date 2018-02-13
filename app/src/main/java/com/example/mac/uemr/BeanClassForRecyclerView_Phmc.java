package com.example.mac.uemr;

/**
 * Created by matthew on 17/3/1.
 */

public class BeanClassForRecyclerView_Phmc {
    Integer image;
    String medicineID;
    String medicineName;
    Integer inventory;
    Double medicinePrice;
    String function;

    public BeanClassForRecyclerView_Phmc(Integer image, String medicineID, String medicineName, Integer inventory, Double medicinePrice, String function) {
        this.image = image;
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.inventory = inventory;
        this.medicinePrice = medicinePrice;
        this.function=function;
    }

    public Integer getImage() { return image; }

    public void setImage(Integer image) { this.image = image; }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getMedcineName() {
        return medicineName;
    }

    public void setMedcineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }


    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Double getMedicinePrice() {
        return medicinePrice;
    }

    public void setMedicinePrice(Double medicinePrice) {
        this.medicinePrice = medicinePrice;
    }


}
