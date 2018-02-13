package com.example.mac.uemr;

/**
 * Created by mac on 17/3/12.
 */

public class BeanClassForRecyclerView_EMR {
    //Integer image;
    String historyID;
    String diseaseName;
    String description;
    String medicineQuantity;
    String medicineName;
    String price;
    String date;

    Boolean type;
    //String price;

    public BeanClassForRecyclerView_EMR(String historyID, String diseaseName,
                                        String description, String medicineQuantity,
                                        String medicineName, String price, String date, Boolean type
                                        ) {
        //this.image = image;
        this.historyID = historyID;
        this.diseaseName = diseaseName;
        this.description = description;
        this.medicineQuantity = medicineQuantity;
        this.medicineName = medicineName;
        this.price = price;
        this.date = date;
        this.type=type;
        //this.price = price;
    }

    public String getHistoryID() {
        return historyID;
    }

    public void setHistoryID(String historyID) {
        this.historyID = historyID;
    }

    public String getDiseaseName() {return diseaseName; }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description= description;
    }

    public String getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(String medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }



}
