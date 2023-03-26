package com.example.task_4_gui_final;

public class Passenger {
    private String cusFirstName;
    private String cusLastName;
    private String cusVehicleNum;
    private int litresRequested;

    public Passenger(String cusFirstName, String cusLastName, String cusVehicleNum, int litresRequested){
        this.setCusFirstName(cusFirstName);
        this.setCusLastName(cusLastName);
        this.setCusVehicleNum(cusVehicleNum);
        this.setLitresRequested(litresRequested);
    }

    public String getCusFirstName() {
        return cusFirstName;
    }

    public void setCusFirstName(String cusFirstName) {
        this.cusFirstName = cusFirstName;
    }

    public String getCusLastName() {
        return cusLastName;
    }

    public void setCusLastName(String cusLastName) {
        this.cusLastName = cusLastName;
    }

    public String getCusVehicleNum() {
        return cusVehicleNum;
    }

    public void setCusVehicleNum(String cusVehicleNum) {
        this.cusVehicleNum = cusVehicleNum;
    }

    public int getLitresRequested() {
        return litresRequested;
    }

    public void setLitresRequested(int litresRequested) {
        this.litresRequested = litresRequested;
    }
}
