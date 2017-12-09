import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
/**
 * DESCRIPTION
 *
 * @author Scott Chaplinksi
 * @author Daniel Paz
 * @author Clarissa Dean
 * @version 1.0
 */
public class Ticket {

    private String id;
    private String cartID;
    private String eventID;
    private String seatID;
    private double price;
    private String tier;
    private String status;
    private boolean purchased;

    private static final String DEFAULT_TICKET_FILE = "src/tickets.csv";

    /**
     * Ticket constructor used to accept client code definitions for a venue and implicitly writes data to a file
     *
     * @param eventID
     * @param cartID
     * @param seatID
     * @param price
     * @param tier
     */
    public Ticket(String eventID, String cartID, String seatID, double price, String tier) {


        try {
            // Check that the SeatID in question has not already been sold or reserved by someone else
            if(countTicketOccurence(seatID) == 0) {

                // Set all of the object fields
                this.id = UUID.randomUUID().toString();
                this.cartID = cartID;
                this.eventID = eventID;
                this.seatID = seatID;
                this.price = price;
                this.tier = tier;
                this.status = "pending";
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

            } else {

                // todo - THROW ERROR, cannot add to basket because ticket is already taken
                System.out.println("Cannot add ticket. Ticket for Seat " + seatID + " is either sold or reserved.");

            }
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

        try{
            this.id = ticketID;
            selectRecord();
        } catch (FileNotFoundException err) {
            System.out.println(err);
            // todo - TICKET NOT FOUND
        }

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
     * Public getter method that returns value of this.cartID
     *
     * @return
     */
    public String getCartID()
    {

        return this.cartID;

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
     * Public getter method that returns the String status of a ticket
     * @return
     */
    public String getStatus()
    {

        return this.status;
    }

    /**
     * Public setter method that sets the String value of this.cartID
     *
     * @param cartID
     */
    public void setCartID(String cartID)
    {

        this.cartID = cartID;

    }

    /**
     * Public setter method that sets the value for this.purchased
     *
     * @param purchased
     */
    public void setPurchased(boolean purchased)
    {

        this.purchased = purchased;

        // Update this ticket within the ticket file and mark it as sold
        if(purchased){

            this.status = "sold";

        } else {

            this.status = "pending";

        }

        // Update the ticket within the ticket.csv file
        try {

            updateRecord();

        } catch (FileNotFoundException err){

            System.out.println(err);

        } catch (IOException err){

            System.out.println(err);
        }


    }

    /**
     * Private method used to update a ticket record within the DEFAULT_TICKET_FILE
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void updateRecord() throws IOException
    {

        Scanner fileInput = new Scanner(new File(this.DEFAULT_TICKET_FILE));

        String fileContents = "";

        while(fileInput.hasNextLine()){

            String line = fileInput.nextLine();

            // Read record line and explode it via ","
            String[] recordArray = Utility.explode(line, ",");

            // Skip records that don't have matching id
            if(this.id != null && recordArray[0].toLowerCase().equals(this.id.toLowerCase())) {

                // Replace index 1 with the appropriate updates made
                recordArray[1] = this.status; // Status

            }

            fileContents += Utility.implode(recordArray, ",") + "\n";

        }

        fileInput.close();

        // Reopen File, this time overwrite mode
        FileOutputStream file_output_stream = new FileOutputStream(new File(this.DEFAULT_TICKET_FILE), false);

        // Create a PrintStream object that will be used to output the file
        PrintStream output = new PrintStream(file_output_stream);

        // Write to the file
        output.print(fileContents);

        // Close the file output stream
        file_output_stream.close();

    }

    /**
     * Private method used to write object fields to a venues.csv file.
     *
     * @throws FileNotFoundException
     */
    private void createRecord() throws FileNotFoundException
    {

        // Create the String that represents a record
        String record = "\"" + this.id + "\",\"" + this.status + "\",\"" + this.cartID + "\",\"" + this.eventID + "\",\"" + this.seatID + "\",\"" + this.price + "\",\"" + this.tier + "\",\"" + this.purchased + "\"";

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

    /**
     * Private method used to select a record from the venues.csv file and write the value to the object fields
     *
     * @throws FileNotFoundException
     */
    private void selectRecord() throws FileNotFoundException
    {

        // Get input from the File
        Scanner fileInput = new Scanner(new File(this.DEFAULT_TICKET_FILE));

        while(fileInput.hasNextLine()){

            // Read record line and explode it via ","
            String[] recordArray = Utility.explode(fileInput.nextLine(), ",");

            // Skip records that don't have matching id
            if(this.id != null && !recordArray[0].toLowerCase().equals(this.id.toLowerCase())) {

                continue;

            }

            // Iterate over elements and map to object fields
            for(int i = 0; i < recordArray.length; i++){
                switch(i){
                    case 0:
                        this.id = recordArray[i];
                        break;
                    case 1:
                        this.status = recordArray[i];
                        break;
                    case 2:
                        this.cartID = recordArray[i];
                        break;
                    case 3:
                        this.eventID = recordArray[i];
                        break;
                    case 4:
                        this.seatID = recordArray[i];
                        break;
                    case 5:
                        this.price = Double.parseDouble(recordArray[i]);
                        break;
                    case 6:
                        this.tier = recordArray[i];
                        break;
                    case 7:
                        this.purchased = Boolean.parseBoolean(recordArray[i]);
                        break;
                }
            }

            // Don't continue on, break while loop
            break;
        }

    }

    /**
     * Private method used to count the number of occurences of a seatID within the DEFAULT_TICKET_FILE file
     *
     * @param seatID
     * @return
     * @throws FileNotFoundException
     */
    private int countTicketOccurence(String seatID) throws FileNotFoundException
    {
        int count = 0;

        // Get input from the File
        Scanner fileInput = new Scanner(new File(this.DEFAULT_TICKET_FILE));

        while(fileInput.hasNextLine()){

            // Read record line and explode it via ","
            String[] recordArray = Utility.explode(fileInput.nextLine(), ",");

            // Skip records that don't have matching id
            if(seatID != null && recordArray[0].toLowerCase().equals(seatID.toLowerCase())) {

                count++;
                continue;

            }
        }

        return count;
    }

    /**
     * Public static method used to iterate through the DEFAULT_TICKET_FILE
     * and look for tickets sold for a specified eventID
     *
     * @param eventID
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<String> seatSold(String eventID) throws FileNotFoundException
    {

        ArrayList<String> seatSold = new ArrayList<>();

        // Get input from the File
        Scanner fileInput = new Scanner(new File(DEFAULT_TICKET_FILE));

        while(fileInput.hasNextLine()){

            // Read record line and explode it via ","
            String[] recordArray = Utility.explode(fileInput.nextLine(), ",");

            // Skip records that don't have matching id
            if(eventID != null && recordArray[3].toLowerCase().equals(eventID.toLowerCase())) {

                // Push the ID of the seat that is sold for eventID
                seatSold.add(recordArray[4]);
                continue;

            }
        }

        return seatSold;

    }

    /**
     * Static public method used to cleanup tickets that were abandonned in pending status in the DEFAULT_TICKET_FILE
     *
     * @throws IOException
     */
    public static void cleanupDeadTickets() throws IOException
    {

        Scanner fileInput = new Scanner(new File(DEFAULT_TICKET_FILE));

        String fileContents = "";

        while(fileInput.hasNextLine()){

            String line = fileInput.nextLine();

            // Read record line and explode it via ","
            String[] recordArray = Utility.explode(line, ",");

            // Only keep records that are marked sold
            if(recordArray[1].toLowerCase().equals("sold")) {

                fileContents += Utility.implode(recordArray, ",") + "\n";

            }

        }

        fileInput.close();

        // Reopen File, this time overwrite mode
        FileOutputStream file_output_stream = new FileOutputStream(new File(DEFAULT_TICKET_FILE), false);

        // Create a PrintStream object that will be used to output the file
        PrintStream output = new PrintStream(file_output_stream);

        // Write to the file
        output.print(fileContents);

        // Close the file output stream
        file_output_stream.close();

    }

    /**
     * Static public method used to delete a ticket by ID in the DEFAULT_TICKET_FILE
     *
     * @throws IOException
     */
    public static void removeTicketFromFile(String ticketID) throws IOException
    {

        Scanner fileInput = new Scanner(new File(DEFAULT_TICKET_FILE));

        String fileContents = "";

        while(fileInput.hasNextLine()){

            String line = fileInput.nextLine();

            // Read record line and explode it via ","
            String[] recordArray = Utility.explode(line, ",");

            // Only keep records that are marked sold
            if(!recordArray[0].equals(ticketID)) {

                fileContents += Utility.implode(recordArray, ",") + "\n";

            }

        }

        fileInput.close();

        // Reopen File, this time overwrite mode
        FileOutputStream file_output_stream = new FileOutputStream(new File(DEFAULT_TICKET_FILE), false);

        // Create a PrintStream object that will be used to output the file
        PrintStream output = new PrintStream(file_output_stream);

        // Write to the file
        output.print(fileContents);

        // Close the file output stream
        file_output_stream.close();

    }

}