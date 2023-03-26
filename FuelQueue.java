package com.example.task_4_gui_final;

public class FuelQueue {
    Passenger[] slots = new Passenger[6];
    private int numOfNotEmptySlot;


    public FuelQueue(){
        for (int i = 0; i< this.getSlots().length; i++){
            this.getSlots()[i]= new Passenger("Unknown","Unknown","Unknown",0);
        }
        this.numOfNotEmptySlot =0;
    }

    //Find number of slots occupied.
    public int getNumNotVacant() {
        this.numOfNotEmptySlot=0;
        for(int i = 0; i< this.getSlots().length; i++){
            if(!this.slots[i].getCusFirstName().equals("Unknown")){
                this.numOfNotEmptySlot = this.numOfNotEmptySlot + 1;
            }
        }
        return numOfNotEmptySlot;
    }

    public Passenger[] getSlots() {
        return slots;
    }
}
