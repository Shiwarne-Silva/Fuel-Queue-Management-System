package com.example.task_4_gui_final;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextArea fuel_queue_show;
    @FXML
    private TextArea waiting_queue_show;
    @FXML
    private TextField search_field;
    @FXML
    protected void view_all_queues(){
        String ViewQueueDetails = "";
        for (int i = 0; i< FuelQueueManager.queues.length; i++){
            ViewQueueDetails += "Fuel Queue 0"+i+" :\n";
            ViewQueueDetails += "\n";

            for (int c = 0; c< FuelQueueManager.queues[i].slots.length; c++){
                Passenger slotsRef = FuelQueueManager.queues[i].slots[c];
                ViewQueueDetails += "\n";
                ViewQueueDetails += "Slot 0"+c+"                   : "+slotsRef.getCusFirstName()+" "+slotsRef.getCusLastName();
                ViewQueueDetails += "\nVehicle Number            : "+slotsRef.getCusVehicleNum();
                ViewQueueDetails += "\nFuel Quantity Requested   : "+slotsRef.getLitresRequested();
                ViewQueueDetails += "\n";
                ViewQueueDetails += "\n";
            }
            ViewQueueDetails += "\n";
        fuel_queue_show.setText(ViewQueueDetails);
        }
    }

    @FXML
    protected void  Search_Cus(){
        String ViewQueueDetails = "";
        boolean Cus_Available = false;
        fuel_queue_show.setText("");
        String Name = search_field.getText();
        for (int i = 0; i< FuelQueueManager.queues.length; i++){
            for (int c = 0; c< FuelQueueManager.queues[i].slots.length; c++){
                if (FuelQueueManager.queues[i].slots[c].getCusFirstName().equalsIgnoreCase(Name)){
                    ViewQueueDetails += "Fuel Queue 0"+i+" :\n";
                    ViewQueueDetails += "\n";
                    Passenger slotsRef = FuelQueueManager.queues[i].slots[c];
                    ViewQueueDetails += "\n";
                    ViewQueueDetails += "Slot 0"+c+"                   : "+slotsRef.getCusFirstName()+" "+slotsRef.getCusLastName();
                    ViewQueueDetails += "\nVehicle Number            : "+slotsRef.getCusVehicleNum();
                    ViewQueueDetails += "\nFuel Quantity Requested   : "+slotsRef.getLitresRequested();
                    ViewQueueDetails += "\n";
                    Cus_Available = true;
                }
            }
        }
        if (!Cus_Available){
            ViewQueueDetails += "Customer Not Found.....";
        }
        fuel_queue_show.setText(ViewQueueDetails);
    }


}