package com.example.task_4_gui_final;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FuelQueueManager {
    public static Scanner scan = new Scanner(System.in);
    public static FuelQueue[] queues = new FuelQueue[5];
    public static Queue<Passenger> waitingList = new LinkedList<>();
    public static int fuelQty=6600;
    public static int qtyMissing=0;
    public static int totalQtyDemanded=0;
    public static Integer[] incomePerQueue = {0,0,0,0,0};


    public static void main(String[] args){
        Initialize();
        while(true){
            Scanner scan = new Scanner(System.in);
            System.out.println();
            System.out.println("                Fuel Queue Management System                ");
            System.out.println();
            System.out.println("Choose option: ");
            System.out.println("----------------------- Menu Option -----------------------");
            System.out.println();
            System.out.println("099 or VGI : View Graphical User Interface.");
            System.out.println("100 or VFQ : View all Fuel Queues.");
            System.out.println("101 or VEQ : View all Empty Queues. ");
            System.out.println("102 or ACQ : Add Customer to a Queues. ");
            System.out.println("103 or RCQ : Remove a Customer from a Queue. ");
            System.out.println("104 or PCQ : Remove a served customer. ");
            System.out.println("105 or VCS : View Customer Sorted in alphabetic order. ");
            System.out.println("106 or SPD : Store Program Data into file. ");
            System.out.println("107 or LPD : Load Program Data from file. ");
            System.out.println("108 or STK : View Remaining Fuel Stock. ");
            System.out.println("109 or AFS : Add Fuel Stock. ");
            System.out.println("110 or IFQ : View all Income of Fuel Queues. ");
            System.out.println("111 or FRR : Fill Random Rubbish. ");
            System.out.println("112 or VWL : View Waiting List. ");
            System.out.println("113 or FR2 : Fill Random Rubbish-2 (Use for Sorting).");
            System.out.println("114 or FR3 : Fill Random Rubbish (Use for Checking Add Method).");
            System.out.println("999 or EXT : Exit the Program. ");
            System.out.println();
            System.out.println("------------------------------------------------------------");
            System.out.println("Enter option chosen: ");
            String option = scan.next();

            switch (option) {
                case "099", "VGI" -> HelloApplication.main(args);
                case "100", "VFQ" -> viewAllQueue();
                case "101", "VEQ" -> viewAllEmpty();
                case "102", "ACQ" -> addCustomer();
                case "103", "RCQ" -> removeCustomerFromQueue();
                case "104", "PCQ" -> removeCustomerServed();
                case "105", "VCS" -> viewAlphabeticOrder();
                case "106", "SPD" -> storeData();
                case "107", "LPD" -> loadData();
                case "108", "STK" -> viewFuel();
                case "109", "AFS" -> addFuel();
                case "110", "IFQ" -> viewIncome();
                case "111", "FRR" -> fillRandomRubbish();
                case "112", "VWL" -> viewWaitingList();
                case "113", "FR2" -> fillRubbish2();
                case "114", "FR3" -> fillRubbish3();
                case "999", "EXT" -> System.exit(0);
            }
        }
    }
    public static void Initialize(){
        for (int i = 0; i< queues.length; i++){
            queues[i] = new FuelQueue();
        }
    }

        public static void viewAllQueue(){
            for (int i = 0; i< queues.length; i++){
                System.out.println("Fuel Queue 0"+i+" :");
            System.out.println();
            for (int c = 0; c< queues[i].slots.length; c++){
                Passenger slotsRef = queues[i].slots[c];
                System.out.println();
                System.out.println("Slot 0"+c+"                   : "+slotsRef.getCusFirstName()+" "+slotsRef.getCusLastName());
                System.out.println("Vehicle Number            : "+slotsRef.getCusVehicleNum());
                System.out.println("Fuel Quantity Requested   : "+slotsRef.getLitresRequested());
                System.out.println();
            }
            System.out.println();
        }
    }
    public static void viewAllEmpty(){
        for(int i=0;i<queues.length;i++){
            System.out.println();
            System.out.println("Fuel Queue: 0"+i);
            System.out.println();
            for (int x=0;x<queues[i].slots.length;x++){
                Passenger slotsRef=queues[i].slots[x];
                if(slotsRef.getCusFirstName().equals("Unknown")){
                    System.out.println("Slot 0"+x+" is empty");
                }
            }
        }

    }
    public static void addCustomer(){
        ArrayList<Integer> smallestQueueList = findSmallestQueue();
        boolean inWaitingList;
        int selectedQueue = 0;
        if(!(smallestQueueList.size() == 0)) {
            do {
                System.out.println();
                System.out.println("Available Queue: (Choose value in brackets)");
                System.out.println();
                for (Integer integer : smallestQueueList) {
                    System.out.println("Queue : 0" + integer + " (" + integer + ")");
                }
                System.out.println();
                boolean valLoop;
                //Data type validation.
                do {
                    System.out.println("Choose Queue: ");
                    String selectedQueueText = scan.next();
                    valLoop = isNumeric(selectedQueueText);
                    if (valLoop == false) {
                        selectedQueue = Integer.parseInt(selectedQueueText);
                    }
                } while (valLoop == true);

            } while (smallestQueueList.contains(selectedQueue) == false);
            inWaitingList=false;
        }else{
            System.out.println();
            System.out.println("Insufficient Space in Queues. Adding Customer to waiting list.");
            System.out.println();
            inWaitingList=true;
        }

        //Collecting Information;
        System.out.println();
        System.out.println("Customer Information: ");
        System.out.println("-----------------------------------------");
        System.out.println("Enter First Name: ");
        String tempFirstName = scan.next();
        System.out.println("Enter Second Name: ");
        String tempSecondName= scan.next();

        //Vehicle Number must be Unique
        boolean loop=true;
        String vehicleNum = null;
        do {
            if(loop == false){
                System.out.println();
                System.out.println("Vehicle Number: "+vehicleNum+" is already in Queue.");
                System.out.println();
            }
            System.out.println("Enter Vehicle Number: ");
            vehicleNum = scan.next();
            loop= isUnique(vehicleNum);
        }while(loop == false);

        // Validation to make sure, quantity requested can be supplied. (based on Qty demanded and not existing fuel quantity)
        int qtyRequested=0;
        do {
            int qtyAfterTotalDemand= fuelQty-totalQtyDemanded;
            if(qtyRequested>fuelQty-totalQtyDemanded || qtyRequested<0){
                System.out.println();
                System.out.println("Amount Requested Exceeds Quantity Available");
                System.out.println();
            }
            System.out.println();
            System.out.println("Fuel Quantity Available: "+fuelQty);
            System.out.println("Fuel Quantity Available after deducting total Quantity demanded: "+qtyAfterTotalDemand);
            System.out.println();
            System.out.println("Enter Quantity Requested: ");
            qtyRequested = scan.nextInt();
        }while(qtyRequested>fuelQty-totalQtyDemanded || qtyRequested<0);

        //Adding fuel demanded to total fuel demanded
        totalQtyDemanded+=qtyRequested;

        if(inWaitingList==false){
            for (int c = 0; c< queues[selectedQueue].getSlots().length; c++) {
                if(queues[selectedQueue].slots[c].getCusFirstName().equals("Unknown")) {
                    //Adding Data into Queue
                    Passenger slotsRef = queues[selectedQueue].slots[c];
                    slotsRef.setCusFirstName(tempFirstName);
                    slotsRef.setCusLastName(tempSecondName);
                    slotsRef.setCusVehicleNum(vehicleNum);
                    slotsRef.setLitresRequested(qtyRequested);

                    break;
                }
            }
            System.out.println();
            System.out.println("Customer Information Successfully added to Queue 0"+selectedQueue+".");
            System.out.println();
        }else if(inWaitingList == true){
            waitingList.add(new Passenger(tempFirstName,tempSecondName,vehicleNum,qtyRequested));
            System.out.println();
            System.out.println("Customer Information Successfully added to waiting list.");
            System.out.println();
        }
    }
    public static ArrayList<Integer> findSmallestQueue(){
        ArrayList<Integer> smallestQueueList = new ArrayList<>();
        int count=0;
        for(FuelQueue queueCount: queues){
            System.out.println("Number of Occupied Queue : "+queueCount.getNumNotVacant());
            count+=1;
        }

        int smallestNum = 6;
        for(int i = 0; i< queues.length; i++){
            if(queues[i].getNumNotVacant()<smallestNum){
                smallestQueueList.clear();
                smallestNum= queues[i].getNumNotVacant(); //getNumNotVacant is the number of occupied slots in queue.
                smallestQueueList.add(i);
            }else if(queues[i].getNumNotVacant()==smallestNum && queues[i].getNumNotVacant()!=6){
                smallestQueueList.add(i);
            }
        }
        return smallestQueueList;
    }
    public static boolean isNumeric(String valueEntered){
        boolean loop= true;
        try{
            Integer.parseInt(valueEntered);
            loop= false;
        }catch(NumberFormatException e) {
            System.out.println();
            System.out.println("Value is Invalid. Values has to be of type Integer.");
            System.out.println();
        }
        return loop;
    }
    public static boolean isUnique(String valueEntered){
        boolean loop =true;
        for (int i=0;i<queues.length;i++){
            for(int x=0;x<queues[i].slots.length;x++){
                Passenger slotsRef = queues[i].slots[x];
                if(valueEntered.equals(slotsRef.getCusVehicleNum())){
                    loop=false;
                }
            }
        }
        return loop;
    }
    public static void removeCustomerFromQueue(){
        System.out.println();
        System.out.println("Removing Customer from Queue");
        System.out.println();
        int queOption=0;
        do{
            if (queOption<0 || queOption>4){
                System.out.println("Invalid Queue Number.");
            }
            System.out.println("Choose the values in brackets. ");
            System.out.println();
            for(int i=0;i<queues.length;i++){
                System.out.println("Fuel Queue 0"+i+ ": ("+i+")");
            }
            System.out.println();
            System.out.println("Choose Which Fuel Queue: ");
            queOption = scan.nextInt();
        }while(queOption<0 || queOption>4);

        int slotOption=0;
        do{
            if (slotOption<0 || slotOption>5){
                System.out.println("Invalid Slot Number.");
            }
            System.out.println("Choose the value in brackets.");
            System.out.println();
            for (int x=0;x<queues[queOption].slots.length;x++){
                Passenger slotsRef= queues[queOption].slots[x];
                System.out.println("Slot 0"+x+" : "+slotsRef.getCusFirstName()+" "+slotsRef.getCusLastName()+" : ("+x+")");
            }
            System.out.println();
            System.out.println("Choose Slot: ");
            slotOption = scan.nextInt();
        }while(slotOption<0 || slotOption>5);

        Passenger slotsRefFinal = queues[queOption].slots[slotOption];
        String name = slotsRefFinal.getCusFirstName()+" "+slotsRefFinal.getCusLastName();

        //Deduct Qty demanded by the Customer removed
        totalQtyDemanded-=slotsRefFinal.getLitresRequested();

        //Remove Customer From Requested Slot from the requested Queue
        slotsRefFinal.setCusFirstName("Unknown");
        slotsRefFinal.setCusLastName("Unknown");
        slotsRefFinal.setCusVehicleNum("Unknown");
        slotsRefFinal.setLitresRequested(0);

        //Re-arrange Queue
        rearrangeArray(queOption,slotOption);

        System.out.println("Customer "+name+" has successfully been removed the queue. ");

        if(waitingList.isEmpty()==false){
            //Add Customer in Waiting list to Queue
            Passenger slotsRefFinal2 = queues[queOption].slots[5];
            slotsRefFinal2.setCusFirstName(waitingList.peek().getCusFirstName());
            slotsRefFinal2.setCusLastName(waitingList.peek().getCusLastName());
            slotsRefFinal2.setCusVehicleNum(waitingList.peek().getCusVehicleNum());
            slotsRefFinal2.setLitresRequested(waitingList.peek().getLitresRequested());

            //Removing Customer from waiting list.
            waitingList.remove();

            System.out.println();
            name = slotsRefFinal2.getCusFirstName()+" "+slotsRefFinal2.getCusLastName();
            System.out.println("Customer "+name+" removed from waiting list and added to Queue 0"+queOption+".");
        }
    }
    public static void rearrangeArray(int queNum, int slotNum){
        // 6 represents number of slots in a Queue
        int numOfCycle= 6-1;
        for(int i=slotNum;i<numOfCycle;i++){
            Passenger slotsRef1 = queues[queNum].slots[i];
            Passenger slotsRef2 = queues[queNum].slots[i+1];

            slotsRef1.setCusFirstName(slotsRef2.getCusFirstName());
            slotsRef1.setCusLastName(slotsRef2.getCusLastName());
            slotsRef1.setCusVehicleNum(slotsRef2.getCusVehicleNum());
            slotsRef1.setLitresRequested(slotsRef2.getLitresRequested());
        }
        queues[queNum].slots[5].setCusFirstName("Unknown");
        queues[queNum].slots[5].setCusLastName("Unknown");
        queues[queNum].slots[5].setCusVehicleNum("Unknown");
        queues[queNum].slots[5].setLitresRequested(0);
    }
    public static void removeCustomerServed(){
        System.out.println();
        int queOption=0;
        do{
            if (queOption<0 || queOption>4){
                System.out.println("Invalid Queue Number");
            }
            System.out.println("Choose the values in brackets. ");
            System.out.println();
            for(int i=0;i<queues.length;i++){
                System.out.println("Fuel Queue 0"+i+" : ("+i+")");
            }
            System.out.println();
            System.out.println("Choose which Fuel Queue: ");
            queOption = scan.nextInt();
        }while(queOption<0 || queOption>4);

        Passenger slotsRef = queues[queOption].slots[0];
        String name = slotsRef.getCusFirstName()+" "+slotsRef.getCusLastName();

        //Calculate and Add income to the Array
        incomePerQueue[queOption]+= slotsRef.getLitresRequested()*430;

        if(!slotsRef.getCusFirstName().equals("Unknown")){
            int qtyToRemove = slotsRef.getLitresRequested();
            //Remove Customer Served in Queue
            slotsRef.setCusFirstName("Unknown");
            slotsRef.setCusLastName("Unknown");
            slotsRef.setCusVehicleNum("Unknown");
            slotsRef.setLitresRequested(0);
            //Re-arrange Queue
            rearrangeArray(queOption,0);

            System.out.println("Customer " + name + " has been successfully served and removed from the queue.");

            //remove fuel quantity consumed
            fuelQty-=qtyToRemove;
            qtyMissing+=qtyToRemove;
            totalQtyDemanded-=qtyToRemove;

            //check fuel quantity consumed
            if(fuelQty <= 500){
                System.out.println();
                System.out.println("Warning: Fuel levels dangerously low. Fuel level at " + fuelQty + ". ");
                System.out.println();
            }

            if(waitingList.isEmpty()==false){
                //Add Customer in Waiting list to Queue
                Passenger slotsRefFinal = queues[queOption].slots[5];
                slotsRefFinal.setCusFirstName(waitingList.peek().getCusFirstName());
                slotsRefFinal.setCusLastName(waitingList.peek().getCusLastName());
                slotsRefFinal.setCusVehicleNum(waitingList.peek().getCusVehicleNum());
                slotsRefFinal.setLitresRequested(waitingList.peek().getLitresRequested());

                //Removing Customer from waiting list.
                waitingList.remove();

                System.out.println();
                name = slotsRefFinal.getCusFirstName()+" "+slotsRefFinal.getCusLastName();
                System.out.println("Customer "+name+" removed from waiting list and added to Queue 0"+queOption+".");
            }
        }else{
            System.out.println();
            System.out.println("No Customer in Queue");
            System.out.println();
        }
    }
    public static void viewFuel(){
        System.out.println();
        System.out.println("Fuel Levels at "+fuelQty+".");
        System.out.println();
        System.out.println("Fuel Quantity Missing: "+qtyMissing);
        System.out.println();
        System.out.println("Total Fuel Quantity Demanded: "+totalQtyDemanded);
        System.out.println();
    }
    public static void addFuel(){
        System.out.println();
        int additionalFuel=0;
        do{
            System.out.println("Fuel Information: ");
            System.out.println("----------------------------------------");
            System.out.println("Quantity Available: "+fuelQty);
            System.out.println("Addable Fuel Quantity: "+qtyMissing);
            System.out.println();
            System.out.println("Enter Quantity of Fuel Added: ");
            additionalFuel = scan.nextInt();
            if(additionalFuel <=qtyMissing){
                System.out.println();
                fuelQty+=additionalFuel;
                System.out.println("New Fuel Quantity: "+fuelQty+".");
                System.out.println();
            }else{
                System.out.println();
                System.out.println("Quantity Added: " + additionalFuel + " liters exceeds the addable quantity of " + qtyMissing + " litres.");
                System.out.println();
            }
        }while(additionalFuel>qtyMissing);
    }
    public static void viewIncome(){
        int totalIncome =0;
        System.out.println();
        for (int i=0;i<incomePerQueue.length;i++){
            System.out.println("Income Earned in Fuel Queue 0"+i+": Rs."+incomePerQueue[i]);
            totalIncome+=incomePerQueue[i];
        }
        System.out.println();
        System.out.println("Total Income Earned: Rs. "+totalIncome);
        System.out.println();
    }
    public static void fillRandomRubbish(){
        for (int i=0;i<queues.length;i++){
            for (int x=0;x<queues[i].slots.length;x++){
                Passenger slotsRef= queues[i].slots[x];

                slotsRef.setCusFirstName("Random First Name: "+i+" "+x);
                slotsRef.setCusLastName("Random Last Name: "+i+" "+x);
                slotsRef.setCusVehicleNum("Random Vehicle Number: "+i+" "+x);
                slotsRef.setLitresRequested(10);
                totalQtyDemanded+= slotsRef.getLitresRequested();
            }
        }
        System.out.println();
        System.out.println("Filled System with Random Rubbish.");
        System.out.println();
    }
    public static void viewWaitingList(){
        System.out.println();
        System.out.println("Waiting List Details");
        System.out.println("----------------------------------------------");
        System.out.println();
        if(waitingList.isEmpty()){
            System.out.println("No Customers in Waiting List.");
            System.out.println();
        }else {
            int i = 0;
            for (Passenger waitingPassenger : waitingList) {
                System.out.println("Waiting List Customer 0" + i + " : " + waitingPassenger.getCusFirstName() + " " + waitingPassenger.getCusLastName());
                i += 1;
            }
        }
    }
    public static void viewAlphabeticOrder(){
        //Duplicate Queue to a duplicate ArrayList.
        ArrayList<Passenger> duplicateQueue = new ArrayList<>();
        for (int i=0;i<queues.length;i++){
            for(int x=0;x<queues[i].slots.length;x++){
                Passenger slotsRef = queues[i].slots[x];
                //Prevent empty slots from being added to duplicate queue
                if(!slotsRef.getCusFirstName().equals("Unknown")){
                    duplicateQueue.add(new Passenger(slotsRef.getCusFirstName(), slotsRef.getCusLastName(), slotsRef.getCusVehicleNum(), slotsRef.getLitresRequested()));
                }
            }
        }
        System.out.println();
        System.out.println("Before Sort: ");
        System.out.println();
        for(Passenger passenger:duplicateQueue){
            System.out.println("First Name: "+passenger.getCusFirstName());
            System.out.println("Last Name: "+passenger.getCusLastName());
            System.out.println("Vehicle Number: "+passenger.getCusVehicleNum());
            System.out.println("Litres Requested: "+passenger.getLitresRequested());
            System.out.println();
        }
        //Sort duplicateQueue
        bubbleSort(duplicateQueue);

        System.out.println();
        System.out.println("After Sort: ");
        System.out.println();
        for(Passenger passenger:duplicateQueue){
            System.out.println("First Name: "+passenger.getCusFirstName());
            System.out.println("Last Name: "+passenger.getCusLastName());
            System.out.println("Vehicle Number: "+passenger.getCusVehicleNum());
            System.out.println("Litres Requested: "+passenger.getLitresRequested());
            System.out.println();
        }

        //Display Sorted Queue
        System.out.println();
        System.out.println("Queue Info In Alphabetical Order: ");
        System.out.println();

        for (int count=0;count<duplicateQueue.size();count++){
            Passenger passengerRef = duplicateQueue.get(count);
            for(int count1=0;count1<queues.length;count1++){
                for(int count2=0;count2<queues[count1].slots.length;count2++){
                    Passenger slotsRefFinal = queues[count1].slots[count2];
                    if(slotsRefFinal.getCusVehicleNum().equals(passengerRef.getCusVehicleNum())){
                        System.out.println("Fuel Queue 0"+count1+" : Slot 0"+count2+" : "+passengerRef.getCusFirstName()+" "+passengerRef.getCusLastName());
                    }
                }
            }
        }
        System.out.println();
    }
    public static void bubbleSort(ArrayList<Passenger> duplicateQueue){
        //BubbleSort
        int n=duplicateQueue.size();
        Passenger temp;
        for(int c=0;c<n;c++){
            for(int j=1;j<(n-c);j++){
                String name1 = duplicateQueue.get(j-1).getCusFirstName().toUpperCase()+" "+duplicateQueue.get(j-1).getCusLastName().toUpperCase();
                String name2 = duplicateQueue.get(j).getCusFirstName().toUpperCase()+" "+duplicateQueue.get(j).getCusLastName().toUpperCase();
                if(name1.compareTo(name2)>0){
                    //Swap Elements
                    temp = duplicateQueue.get(j-1);
                    duplicateQueue.set(j-1,duplicateQueue.get(j));
                    duplicateQueue.set(j,temp);
                }
            }
        }
    }
    public static void fillRubbish2(){
        Passenger slotsRef1 =queues[0].slots[0];
        Passenger slotsRef2 =queues[4].slots[0];

        slotsRef1.setCusFirstName("Xavier");
        slotsRef1.setCusLastName("Adam");
        slotsRef1.setCusVehicleNum("ZXCV");
        slotsRef1.setLitresRequested(10);

        slotsRef2.setCusFirstName("Adam");
        slotsRef2.setCusLastName("Abdullah");
        slotsRef2.setCusVehicleNum("DFGH");
        slotsRef2.setLitresRequested(10);
    }
    public static void storeData(){
        try{
            FileWriter myWriter = new FileWriter("D:\\Bsc (HONS) Computer Science\\1st Year\\20210059_w1914619__ Shiwarne Silva_ SD2 CW\\Task 2,3 - Class Version _ Waiting Queue\\src\\Storage");
            // Storing Fuel Queue info to File;
            for(int i=0;i<queues.length;i++){
                for(int x=0;x<queues[i].slots.length;x++){
                    Passenger slotsRef = queues[i].slots[x];
                    myWriter.write(slotsRef.getCusFirstName()+"\n");
                    myWriter.write(slotsRef.getCusLastName()+"\n");
                    myWriter.write(slotsRef.getCusVehicleNum()+"\n");
                    myWriter.write(slotsRef.getLitresRequested()+"\n");
                }
            }
            //Storing Waiting list to Fuel;
            myWriter.write(waitingList.size()+"\n");
            for(Passenger passenger : waitingList){
                myWriter.write(passenger.getCusFirstName()+"\n");
                myWriter.write(passenger.getCusLastName()+"\n");
                myWriter.write(passenger.getCusVehicleNum()+"\n");
                myWriter.write(passenger.getLitresRequested()+"\n");
            }
            //Storing Income Info to File;
            for(int c=0;c<incomePerQueue.length;c++){
                myWriter.write(incomePerQueue[c]+"\n");
            }

            //Storing Fuel Info to File;
            myWriter.write(fuelQty+"\n");
            myWriter.write(qtyMissing+"\n");
            myWriter.write(Integer.toString(totalQtyDemanded));

            //Closing File;
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }catch(IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void loadData(){
        try{
            File myObj = new File("D:\\Bsc (HONS) Computer Science\\1st Year\\20210059_w1914619__ Shiwarne Silva_ SD2 CW\\Task 2,3 - Class Version _ Waiting Queue\\src\\Storage");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()){
                //Loading Fuel Queue Info from File;
                for(int i=0;i<5;i++){
                    for(int x=0;x<6;x++){
                        Passenger slotsRef = queues[i].slots[x];
                        slotsRef.setCusFirstName(myReader.nextLine());
                        slotsRef.setCusLastName(myReader.nextLine());
                        slotsRef.setCusVehicleNum(myReader.nextLine());
                        slotsRef.setLitresRequested(Integer.parseInt(myReader.nextLine()));
                    }
                }
                //Clearing content in waiting list;
                waitingList.clear();

                //Loading waiting List Info from File;
                int waitingListSize = Integer.parseInt(myReader.nextLine());
                for(int c=0;c<waitingListSize;c++){
                    String tempFirstName = myReader.nextLine();
                    String tempLastName = myReader.nextLine();
                    String tempVehicleNum = myReader.nextLine();
                    int qtyRequested = Integer.parseInt(myReader.nextLine());
                    waitingList.add(new Passenger(tempFirstName,tempLastName,tempVehicleNum,qtyRequested));
                }
                //Loading Income Info From file;
                for(int x=0;x<5;x++){
                    incomePerQueue[x] = Integer.parseInt(myReader.nextLine());
                }

                //Loading Fuel info from file;
                fuelQty = Integer.parseInt(myReader.nextLine());
                qtyMissing = Integer.parseInt(myReader.nextLine());
                totalQtyDemanded = Integer.parseInt(myReader.nextLine());
                System.out.println("Successfully Loaded Data from File");
                System.out.println();
                viewAllQueue();
            }
            //Closing File;
            myReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void fillRubbish3(){
        for(int i=0; i<queues.length;i++){
            if(i!=2){
                queues[i].slots[0].setCusFirstName("Rubbish First Name 0"+i);
                queues[i].slots[0].setCusLastName("Rubbish Last Name 0"+i);
                queues[i].slots[0].setCusVehicleNum("Rubbish Vehicle Name 0"+i);
                queues[i].slots[0].setLitresRequested(100+1);
            }
        }
        System.out.println("Auto Filled Rubbish");
    }
}
