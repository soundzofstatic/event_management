import java.io.*;
import java.util.UUID;
/**
 * DESCRIPTION
 *
 * @author Scott Chaplinksi
 * @author Daniel Paz
 * @version 1.0
 */
public class Ticket {

    private String id;
    private String eventID;
    private String seatID;
    private double price;
    private String tier;
    private boolean purchased;

    private static final String DEFAULT_TICKET_FILE = "src/tickets.csv";

    /**
     * Ticket constructor used to accept client code definitions for a venue and implicitly writes data to a file
     *
     * @param seatID
     * @param price
     * @param tier
     */
    public Ticket(String eventID, String seatID, double price, String tier) {


        // Set all of the object fields
        this.id = UUID.randomUUID().toString();
        this.eventID = eventID;
        this.seatID = seatID;
        this.price = price;
        this.tier = tier;
        this.purchased = false;

        // todo - Validate that eventID is a valid event ID

        // todo - Validate that seatID is a valid seat ID for an Event

        // Write to File
        try {
            createRecord();
        } catch (FileNotFoundException e1) {
            // Catch block
            System.out.println("File not Found");
        }

    }

    /**
     * Constructor used to search for a ticket and return it's values
     *
     * @param ticketID
     */
    public Ticket(String ticketID) {

        // todo -

    }

    /**
     * @return String
     */
    public String toString() {

        String state = "Ticket '" + this.id + "', for event '" + this.eventID + "', corresponds to Seat '" + this.seatID + "' and was purchased at '" + String.format("%.2f", this.price) + "'. The ticket is of tier '" + this.tier + "' and ";

        if(!this.purchased) {

            state += "IS NOT ";

        } else {

            state += "IS ";

        }

        state += "paid for.";

        return state;

    }

    /**
     * Public Getter method that returns value of this.id
     * @return String
     */
    public String getId()
    {

        return this.id;

    }

    /**
     * Public Getter method that returns value of this.eventID
     *
     * @return String
     */
    public String getEventID() {
        return this.eventID;

    }

    /**
     * Public Getter method that returns value of this.seatID
     *
     * @return String
     */
    public String getSeatID() {
        return this.seatID;

    }

    /**
     * Public Getter method that returns value of this.price
     *
     * @return float
     */

    public double getPrice() {

        return this.price;

    }

    /**
     * Public Getter method that returns value of this.tier
     *
     * @return int
     */

    public String getTier() {

        return this.tier;

    }

    /**
     * Public getter method that returns the value for this.purchased
     *
     * @return
     */
    public boolean getPurchased()
    {

        return this.purchased;

    }

    /**
     * Public setter method that sets the value for this.purchased
     *
     * @param purchased
     */
    public void setPurchased(boolean purchased)
    {

        this.purchased = purchased;

    }

    /**
     * Private method used to write object fields to a venues.csv file.
     *
     * @throws FileNotFoundException
     */
    private void createRecord() throws FileNotFoundException
    {
        // Generate a UUID ID
        UUID uuid = UUID.randomUUID();

        // Set the id for the object
        this.id = uuid.toString();

        // Create the String that represents a record
        String record = "\"" + id + "\",\"" + this.eventID + "\",\"" + this.seatID + "\",\"" + this.price + "\",\"" + this.tier + "\", \"" + this.purchased + "\"";

        // Instantiate a File output stream and set the pointer to the end of file in order to append
        // Creates file if it does not exist
        FileOutputStream file_output_stream = new FileOutputStream(new File(this.DEFAULT_TICKET_FILE), true);

        // In order to avoid duplicate records, use id and name as keys and check if they exist in database
//        if(!this.checkDuplicateRecord()) {

            // Create a PrintStream object that will be used to output the file
            PrintStream output = new PrintStream(file_output_stream);

            // Print to file
            output.println(record);

//        } else {
//
//            // todo - Does file_output_stream need to be closed in order to conserve resources?
//
//        }

    }

}