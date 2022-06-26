
public class PassengerQueue 
{
    private Passenger [] queueArray = new Passenger[TrainStation.WAITING_ROOM_CAPACITY];
    private int first;
    private int last;
    private int maxQueueLen = 0;
    private int passengersN = 0;

    public PassengerQueue() {
        this.first = -1;
        this.last = -1;
    }
    
   public void add(Passenger next)
   {
       if ((this.last + 1) % this.queueArray.length == this.first) { // Queue is full
           System.out.println("Trying to add to a full queue");
           return;
       } else if (this.first == -1) { // Queue is empty
            this.first = 0;
            this.last = 0;
            this.queueArray[this.last] = next;
            passengersN++;
       } else {
           this.last = (this.last + 1) % this.queueArray.length;
           this.queueArray[this.last] = next;
           passengersN++;
       }
       this.maxQueueLen = Math.max(this.maxQueueLen, this.getPassengersN());
   }

   public Passenger remove()
   {
       Passenger passenger = null;

        if (this.first == -1 && this.last == -1) { // Queue is empty
            System.out.println("The queue is empty. Cannot remove a passenger");
        } else if (this.first == this.last) { // Only one element is present in the queue
            passenger = this.queueArray[this.first];
            this.first = -1;
            this.last = -1;
            passengersN--;
        } else { // There are some elements in the queue
            passenger = this.queueArray[this.first];
            this.first = (this.first + 1) % this.queueArray.length;
            passengersN--;
        }

        return passenger;
   }

   public Passenger getPassengerAtIndex(int i) {

        if (i > this.getPassengersN()) {
            System.out.println("Index out of bounds for passengers queue.");
            return null;
        }

        return this.queueArray[(this.first + i) % queueArray.length];
   }

   public int getPassengersN() {
        return this.passengersN;
   }

   public int getMaxQueueLen() {
        return this.maxQueueLen;
   }

   public void display()
   {
       //list elements from firsts to last in the queueArray
       for(int i = first; i < last; i++)
       {
           queueArray[i].display();
       }
   }
   
}
