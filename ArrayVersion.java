import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ArrayVersion {
    public static String[][] queue = new String[3][6];
    public static Scanner scan = new Scanner(System.in);
    public static int fuelQty=6600;
    public static int qtyMissing=0;

    public static void main(String[] args){
        initialize();
        while(true){
            Scanner scan = new Scanner(System.in);
            System.out.println();
            System.out.println("                Fuel Queue Management System                ");
            System.out.println();
            System.out.print("Choose your option...");
            System.out.println();
            System.out.println("----------------------- Menu Option -----------------------");
            System.out.println();
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
            System.out.println("999 or EXT : Exit the Program. ");
            System.out.println();
            System.out.println("------------------------------------------------------------");
            System.out.print("Enter option chosen : ");
            String option = scan.next();

            //Reference Link for using Switch Cases - https://www.w3schools.com/java/java_switch.asp
            switch (option) {
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
                case "999", "EXT" -> System.exit(0);
            }
        }
    }
    public static void initialize(){
        for(int i=0;i<3;i++){
            for (int x=0;x<6;x++){
                queue[i][x]="empty";
            }
        }
    }
    public static void viewAllQueue(){
        for(int i=0;i<3;i++){
            int count= i+1;
            System.out.println("Fuel Queue 0"+count+": ");
            System.out.println();
            for (int x=0;x<6;x++){
                System.out.println("Slot 0"+x+": "+queue[i][x]);
            }
            System.out.println();
        }
    }
    public static void viewAllEmpty(){
        boolean[] notEmpty = {false,false,false};
        boolean [] result = {true,true,true};
        for (int i=0;i<3;i++){
            if(queue[i][0].equals("empty")){
                int iCount= i+1;
                System.out.println();
                System.out.println("Queue : 0"+iCount);
                System.out.println();
                for (int x=0;x<6;x++) {
                    int xCount = x + 1;
                    System.out.println("Queue: 0" + iCount + " : Slot 0" + xCount + ": " + queue[i][x]);
                }
            }else{
                notEmpty[i] = true;
                if (Arrays.equals(notEmpty,result)){
                    System.out.println();
                    System.out.println("No Queues Are Empty.");
                    System.out.println();
                }
            }
        }
    }
    public static void addCustomer(){
        boolean[] queueIsFree= {false,false,false};
        //Check available queue
        for(int c=0;c<3;c++){
            for (int x=0;x<6;x++){
                if (queue[c][x].equals("empty")) {
                    queueIsFree[c] = true;
                    break;
                }
            }
        }
        System.out.println();
        System.out.println("Available Queue : ");
        System.out.println();
        displayAvailableQueue(queueIsFree);

        int queueNumber=0;
        do {
            boolean loop=true;
            while (loop==true) {
                System.out.print("Enter Queue Number : ");
                String queueNumberText = scan.next();
                loop=isNumeric(queueNumberText);
                if(loop==false){
                    queueNumber=Integer.parseInt(queueNumberText);
                }
                if(queueNumber>2 || queueNumber<0){
                    loop=true;
                    System.out.println();
                    System.out.println("Queue Number is Invalid.");
                    System.out.println();
                    displayAvailableQueue(queueIsFree);
                }
            }
            if(queueIsFree[queueNumber]!=true){
                System.out.println();
                System.out.println("Queue Selected is Full, Choose available Queue:");
                System.out.println();
                displayAvailableQueue(queueIsFree);
            }
        }while(queueIsFree[queueNumber]!=true);

        System.out.print("Enter Customer Name : ");
        String cusName = scan.next();

        for(int i=0;i<6;i++){
            if(queue[queueNumber][i].equals("empty")){
                queue[queueNumber][i] = cusName;
                break;
            }
        }
    }
    public static boolean isNumeric(String valueEntered){
        boolean loop= true;
        try{
            Integer.parseInt(valueEntered);
            loop= false;
        }catch(NumberFormatException e) {
            System.out.println();
            System.out.print("Value is Invalid. Values must be (0,1,2) :");
            System.out.println();
        }
        return loop;
    }
    public static void displayAvailableQueue(boolean[] isAvailable){
        for (int i =0; i<3;i++) {
            if (isAvailable[i] == true) {
                int count= i+1;
                System.out.println("Queue 0" + count + " is available : ("+i+")");
            }
        }
        System.out.println();
    }
    public static void removeCustomerFromQueue(){  //Reference link for, how to remove an element form an array - https://www.geeksforgeeks.org/remove-an-element-at-specific-index-from-an-array-in-java/
        System.out.println();
        int queOption=0;
        do {
            if(queOption<0 || queOption>2){
                System.out.println("Invalid Queue Number!!!");
            }
            System.out.println("Choose the values in brackets. ");
            for (int i = 0; i < 3; i++) {
                int iCount = i + 1;
                System.out.println();
                System.out.println("Queue 0" + iCount + " : (" + i + ")");
            }
            System.out.println();
            System.out.print("Choose Which Fuel Queue : ");
            queOption = scan.nextInt();
        }while(queOption<0 || queOption>2);

        int slotOption = 0;
        do{
            if (slotOption<0 || slotOption>5){
                System.out.println("Invalid Slot Number.");
            }
            System.out.print("Choose the values in brackets :  ");
            for(int x=0;x<6;x++){
                int xCount = x+1;
                System.out.println();
                System.out.println("Slot 0" + xCount + " : "+queue[queOption][x]+": (" + x + ")");
            }
            System.out.println();
            System.out.print("Choose Slot : ");
            slotOption = scan.nextInt();
        }while(slotOption<0 || slotOption>5);

        String name=queue[queOption][slotOption];
        //Remove Customer From Requested Slot from the requested Queue
        queue[queOption][slotOption]="empty";
        rearrangeArray(queOption,slotOption);

        System.out.println("Customer "+name+" has successfully been removed from the queue. ");
    }
    public static void rearrangeArray(int queueNum , int slotNum){
        int numOfCycles= 6-slotNum-1;
        for (int i=slotNum;i<numOfCycles;i++){
            queue[queueNum][i]= queue[queueNum][i+1];
        }
        queue[queueNum][5]="empty";
    }
    public static void removeCustomerServed(){
        System.out.println();
        int queOption=0;
        do {
            if(queOption<0 || queOption>2){
                System.out.println("Invalid Queue Number.");
            }
            System.out.print("Choose the values in brackets :  ");
            for (int i = 0; i < 3; i++) {
                int iCount = i + 1;
                System.out.println();
                System.out.println("Queue 0" + iCount + " : (" + i + ")");
            }
            System.out.println();
            System.out.print("Choose Which Fuel Queue : ");
            queOption = scan.nextInt();
        }while(queOption<0 || queOption>2);

        String name = queue[queOption][0];
        if (!name.equals("empty")) {
            //Remove Customer Served From Queue
            queue[queOption][0] = "empty";
            rearrangeArray(queOption, 0);

            System.out.println("Customer " + name + " has been successfully served and removed from the queue.");

            //Remove fuel quantity consumed
            fuelQty -= 10;
            qtyMissing+=10;
            //Check fuel Quantity
            if (fuelQty <= 500) {
                System.out.println();
                System.out.println("Warning: Fuel levels dangerously low. Fuel level at " + fuelQty + ". ");
                System.out.println();
            }
        }else{
            System.out.println();
            System.out.println("No Customers in the Queue.");
            System.out.println();
        }
    }
    public static void viewAlphabeticOrder() {
        //Duplicate the array into 1 ArrayList.
        ArrayList<String> duplicateQueue = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int iCount = i+1;
            for (int x = 0; x < 6; x++) {
                int xCount = i+1;
                duplicateQueue.add(queue[i][x]);
            }
        }
        System.out.println("Duplicate Queue before removing empty slots: " + duplicateQueue);
        //remove empty slots
        System.out.println("Size of duplicate Queue: "+duplicateQueue.size());
        duplicateQueue.removeAll(Collections.singleton("empty"));

        System.out.println("Duplicate Queue after removing empty slots: " + duplicateQueue);
        bubbleSort(duplicateQueue);
        System.out.println("Duplicate Queue after ordered: "+ duplicateQueue);

        System.out.println();
        System.out.println("Queue Info In Alphabetical Order: ");
        System.out.println();
        for (int count=0;count<duplicateQueue.size();count++){
            for (int count1=0;count1<3;count1++){
                for(int count2=0;count2<6;count2++){
                    if(queue[count1][count2].equals(duplicateQueue.get(count))){
                        int tempCount1 = count1+1;
                        int tempCount2 = count2+1;
                        System.out.println("Queue : 0"+tempCount1+" : Slot : 0"+tempCount2+" : "+duplicateQueue.get(count));
                    }
                }
            }
        }
    }
    public static void bubbleSort(ArrayList<String> duplicateQueue){ //Loop used for bubble sort the sorted customer Array
        //Reference link for bubble sort - https://www.geeksforgeeks.org/bubble-sort/
        //BubbleSort
        for (int x=0;x<3;x++){
            int n=duplicateQueue.size();
            String temp;
            for(int c=0;c<n;c++){
                for(int j=1;j<(n-c);j++){
                    if(duplicateQueue.get(j-1).compareTo(duplicateQueue.get(j)) > 0){
                        //swap elements
                        temp = duplicateQueue.get(j-1);
                        duplicateQueue.set(j-1,duplicateQueue.get(j));
                        duplicateQueue.set(j,temp);
                    }
                }
            }
        }
    }
    public static void storeData(){ //Reference link for Storing data for a file - https://stackoverflow.com/questions/22411958/how-to-save-a-file-in-java
        try {
            FileWriter myWriter = new FileWriter("E:\\20210059_w1914619__ Shiwarne Silva_ SD2 CW\\Task 1 - Array Version\\src\\Data");
            for(int i=0;i<3;i++){
                for(int c=0; c<6;c++){
                    myWriter.write(queue[i][c]+"\n");
                }
            }
            myWriter.write(Integer.toString(fuelQty));
            myWriter.close();
            System.out.println("Successfully wrote to the file....");
        } catch (IOException e) {  //Catch Block to catch the error occurred when saving data to the file.
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public static void loadData(){  //Reference link for loading data from a file - https://www.w3schools.com/java/java_files_read.asp
        try {
            File myObj = new File("E:\\20210059_w1914619__ Shiwarne Silva_ SD2 CW\\Task 1 - Array Version\\src\\Data");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                for(int i=0;i<3;i++){
                    for(int c=0; c<6;c++){
                        queue[i][c] = myReader.nextLine();
                    }
                }
                fuelQty = Integer.parseInt(myReader.nextLine());
                System.out.println("Successfully Loaded Data from File...");
            }
            myReader.close();
        } catch (FileNotFoundException e) { //Catch Block to catch the error occurred when loading the file.
            System.out.println("An error occurred!!");
            e.printStackTrace();
        }
    }
    public static void viewFuel(){
        System.out.println();
        System.out.println("Fuel Levels at "+fuelQty+".");
        System.out.println();
        System.out.println("Fuel Quantity Missing : "+qtyMissing);
        System.out.println();
    }
    public static void addFuel(){
        //Validate Fuel Qty entered.
        System.out.println();
        int additionalFuel=0;
        do {
            System.out.println("Enter Quantity of Fuel Added: ");
            additionalFuel = scan.nextInt();
            if (additionalFuel <= qtyMissing) {
                fuelQty += additionalFuel;
                System.out.println("New fuel Quantity: " + fuelQty + ".");
                System.out.println();
            } else {
                System.out.println();
                System.out.println("Quantity Added " + additionalFuel + " liters exceeds the addable quantity of " + qtyMissing + " litres.");
                System.out.println();
            }
        }while(additionalFuel > qtyMissing);
    }
}