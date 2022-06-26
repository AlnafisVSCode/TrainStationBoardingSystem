
import java.io.*;
import java.util.*;

import java.io.File;

public class TrainStation 
{
    
    static int WAITING_ROOM_CAPACITY = 30;
    
    //implement fields here:
    private static ArrayList<Passenger>  waitingRoom = new ArrayList<>();
    private static PassengerQueue trainQueue = new PassengerQueue();
    private static HashMap<String, Integer> waitingTimes = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
    {
        // Menu
        //Repeat the options unil q
        //options a, v, d ,s, l, r
        System.out.println("+------------------------------------+");
        System.out.println("|           Welcome to our           |");
        System.out.println("|     Train Booking Application      |");
        System.out.println("+------------------------------------+");
        
        
        boolean exit = false;
        while (!exit) {
            TrainStation.displayMenu();
            String choice = in.next().toUpperCase();
            
            switch (choice) {
                case "A":
                    loadDataToWaitingRoom();
                    addPassengerToTrainQueue(1);
                    break;
                case "V":
                    loadDataToWaitingRoom();
                    viewTrainQueue();
                    break;
             
                case "D":
                    loadDataToWaitingRoom();
                    removePassengerFromQueue();
                    break;
                    
                case "L":
                    loadDataToTrainQueue();
                    viewTrainQueue();
                    break;
                    
                case "S":
                    storeTrainQueuedata();
                    break;
                case "LW":
                    loadDataToWaitingRoom();
                    break;
                    
                case "R":
                    runSimulation();
                    break;
                case "E":
                    exit = true;
                    break;
               default: System.out.println("");
                    System.out.println("Invalid character entered."
                            + " Please press one of the options below !");
                    System.out.println("");
             }
         }   
     }
 }
  private static void displayMenu()
  {         System.out.println("");
            System.out.println("Press A: Add Passenger To Queue");
            System.out.println("Press V: View Train Queue");
            System.out.println("Press D: Delete Passenger from Queue");
            System.out.println("Press S: Store Train Queue Data in to file");
            System.out.println("Press L: Load data from file into train Queue");
            System.out.println("Press R: Run Simulation and produce report");
            System.out.println("");
            System.out.println("Press E: To Exit");
  }   
  
  private static void addPassengerToTrainQueue(int nPassengers)
  {
      if (waitingRoom.size() == 0) {
          System.out.println("Waiting room is empty. Cannot add passengers from the waiting room to the train queue");
          return;
      }

      int numberOfPassengers = Math.min(nPassengers, waitingRoom.size());

      for (int i = 0; i < numberOfPassengers; i++) {
          Passenger passenger = waitingRoom.remove(0);
          System.out.println("Passenger " + passenger.getName()+ " added to the train queue");
          trainQueue.add(passenger);
      }
  }
    
  private static void viewTrainQueue()
  {
      trainQueue.display();
  }
  
  private static void removePassengerFromQueue()
  {
      Passenger removedPassenger = trainQueue.remove();
      System.out.println("Passenger "+removedPassenger.getName()+" removed from the queue");
  }
  
  private static void storeTrainQueuedata()
  {
      File file = new File ("stats.txt");
      try {
          System.out.println("Successfully Stored Queue data");
          if (!file.createNewFile()) {
              file.delete();
              file.createNewFile();
          }
          FileWriter fileWriter = new FileWriter(file);
          BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
          bufferedWriter.write("Longest waited: " + longestWaitedTime()+"\n");
          bufferedWriter.write("Shortest waited: " + shortestWaitedTime()+"\n");
          bufferedWriter.write("Average waited: " + getAverageWaitTime()+"\n");
          bufferedWriter.write("Max queue length: " + maxLengthOfQueue()+"\n");
          
          bufferedWriter.close();
         
         
      } catch (IOException e) {
          System.out.println("Could not create file");
      }
             
  }

  private static void loadDataToTrainQueue()
  {
      File file = new File ("passengers.dat");
      try {
          Scanner passengersReader = new Scanner (file);
          while(passengersReader.hasNextLine()){
              String line = passengersReader.nextLine();
              Scanner passengerReader = new Scanner(line);
              String name = passengerReader.next();
              String surname = passengerReader.next();
              Passenger passenger = new Passenger(name, surname);
              trainQueue.add(passenger);
          } 
      } catch (FileNotFoundException ex) {
          System.out.println("File has not been found.");
      }
      System.out.println("Successfully loaded the data");
      System.out.println(trainQueue);
  }
  
  private static void loadDataToWaitingRoom()
  {
      File file = new File ("passengers.dat");
        try {
            Scanner passengersReader = new Scanner (file);
            while(passengersReader.hasNextLine()){
                String line = passengersReader.nextLine();
                Scanner passengerReader = new Scanner(line);
                String name = passengerReader.next();
                String surname = passengerReader.next();
                Passenger passenger = new Passenger(name, surname);
                waitingRoom.add(passenger);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File has not been found.");
        }
  }

  private static int getAverageWaitTime() {
    int nOfPassengers = 0;
    int totalWaitTime = 0;

    // Creates an iterator
    Iterator iterator = waitingTimes.entrySet().iterator();
    // While there is new stuff to read from the iterator
    while(iterator.hasNext()) {
        // Get an entry from the map
        // Entry is an object containing a key and a value
          Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iterator.next();
          nOfPassengers++;
          totalWaitTime += entry.getValue();
    } return totalWaitTime / nOfPassengers;
  }

  private static int longestWaitedTime() {
      int longestWaitTime = 0;
      Iterator iterator = waitingTimes.entrySet().iterator();
      while(iterator.hasNext()) {
          Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iterator.next();
          longestWaitTime = Math.max(longestWaitTime, entry.getValue());
      } return longestWaitTime;
  }

  private static int shortestWaitedTime() {
      int shortestWaitTime = 10000;
      Iterator iterator = waitingTimes.entrySet().iterator();
      while(iterator.hasNext()) {
          Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iterator.next();
          shortestWaitTime = Math.min(shortestWaitTime, entry.getValue());
      } return shortestWaitTime;
  }

  private static int maxLengthOfQueue() {
    return trainQueue.getMaxQueueLen();
  }

  private static void runSimulation()
  {
      Random random = new Random();
      loadDataToWaitingRoom();
      System.out.println(waitingRoom.size());
      do {
          if (waitingRoom.size() != 0) {
              addPassengerToTrainQueue(random.nextInt(6)); // Add random amount of passengers to trainqueue
          }
          int processingDelay = random.nextInt(6 * 3);
          for (int i = 0; i < trainQueue.getPassengersN(); i++) {
              Passenger passenger = trainQueue.getPassengerAtIndex(i);
              // Increment processing delay for passenger
              passenger.setSecondInQueue(passenger.getsecondsinqueue() + processingDelay);
              waitingTimes.put(passenger.getName(), passenger.getsecondsinqueue());
          }
          removePassengerFromQueue();
      } while(trainQueue.getPassengersN() != 0);
      storeTrainQueuedata();
      System.out.println("max Queue Length: " + maxLengthOfQueue ());
      System.out.println("Minimum Waited Time: " + shortestWaitedTime());
      System.out.println("Maximum Waited Time: " + longestWaitedTime());
      System.out.println("Average Waited Time: " + getAverageWaitTime());
     
  }

}
