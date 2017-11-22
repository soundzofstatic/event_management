import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Event {

    private String id;
    private String title;
    private String venueID;
    private String seatID;
//  private String eventType;
    private int vipAvailability;
    private int regTixAvailability;
    private Venue venue;
    private SeatingType seatingType;
    private String artistName;
    private String datetime;
    private int timestamp;
    private boolean active;

    private static final String DEFAULT_EVENT_FILE = "src/events.csv";

    /**
     * Event constructor that takes a Event UUID string and name and implicitly reads data from event file and set object fields
     *
     * @param id
     * @param title
     */
    public Event(String id, String title) {

        this.id = id;
        this.title = title;

        // run selectRecord(); to pull data from database
        try {
            selectRecord();

        } catch (FileNotFoundException e1) {   // Catch block

            System.out.println("File not Found");
        }

    }

    /**
     * Venue constructor used to accept client code definitions for a venue and implicitly writes data to a file
     *
     * @param eventType
     * @param venue
     * @param artistName
     * @param datetime
     * @param ticketAvailability
     * @param capacity
     */

    public Event(String eventType, String venue, String artistName, String datetime, int ticketAvailability, int capacity) {

        // Set all of the object fields
        //this.eventType = eventType;
        this.venue = null;
        this.seatingType = null;
        this.artistName = artistName;
        this.datetime = datetime;
        //this.ticketAvailability = ticketAvailability;
        //this.capacity = capacity;

        // Write to File
        try {
            createRecord();
        } catch (FileNotFoundException e1) {
            // Catch block
            System.out.println("File not Found");
        }
    }

    /**
     * @return String
     */
    public String toString() {

        // todo - define
        return "Todo - DEFINE how this should output";

    }

    public int getTimestamp() {

        return this.timestamp;

    }

    public Boolean getActive() {

        return this.active;

    }

    /**
     * Public Getter method that returns value of this.venue
     *
     * @return String
     */
    public String getVenue() {
        Venue eventVenue = new Venue(this.venueID, null);
        return eventVenue.toString();

    }

    /**
     * Public Getter method that returns value of this.title
     *
     * @return String
     */
    public String getTitle() {
        return this.title;

    }

    /**
     * Public Getter method that returns value of this.venue
     *
     * @return String
     */
    public String getSeatID() {
        SeatingType venueSeating = new SeatingType(this.seatID, null);
        return venueSeating.toString();

    }

    /**
     * Public Getter method that returns value of this.artistName
     *
     * @return String
     */
    public String getArtistName() {

        return this.artistName;

    }

    /**
     * Public Getter method that returns value of this.datetime
     *
     * @return String
     */
    public String getDatetime() {

        return this.datetime;

    }

    /**
     * Public Getter method that returns value of this.ticketAvailability
     *
     * @return String
     */
    public int getRegTixAvailability() {

        return this.regTixAvailability;

    }

    /**
     * Public Getter method that returns value of this.ticketAvailability
     *
     * @return String
     */
    public int getVipTicketAvailability() {

        return this.vipAvailability;

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
        String record = "\"" + id;
        //String record = "\"" + id + "\",\"" + name + "\",\"" + streetAddress + "\",\"" + locality + "\",\"" + region + "\",\"" + country + "\",\"" + zipCode + "\",\"" + capacity + "\",\"" + Arrays.toString(typeOfSeating) + "\"";

        // Instantiate a File output stream and set the pointer to the end of file in order to append
        // Creates file if it does not exist
        FileOutputStream file_output_stream = new FileOutputStream(new File(this.DEFAULT_EVENT_FILE), true);

        // In order to avoid duplicate records, use id and name as keys and check if they exist in database
        if(!this.checkDuplicateRecord()) {

            // Create a PrintStream object that will be used to output the file
            PrintStream output = new PrintStream(file_output_stream);

            // Print to file
            output.println(record);

        } else {

            // todo - Does file_output_stream need to be closed in order to conserve resources?

        }

    }

    /**
     * Private method used to select a record from the venues.csv file and write the value to the object fields
     *
     * @throws FileNotFoundException
     */
    private void selectRecord() throws FileNotFoundException
    {

        // Get input from the File
        Scanner fileInput = new Scanner(new File(this.DEFAULT_EVENT_FILE));

        while(fileInput.hasNextLine()){

            // read record line
            String record = fileInput.nextLine();

            // Before exploding, replace problematic characters
            // todo - consider making the next two lines into it's own helper method
            String cleanRecord1 = record.replace("\",", "|");
            String cleanRecord2 = cleanRecord1.replace("\"", "");

            // Explode the Record into individual elements
            String[] recordArray = cleanRecord2.split("\\|");

            // Skip records that don't have matching id
            if(this.id != null && !recordArray[0].toLowerCase().equals(this.id.toLowerCase())) {

                continue;

            }

            // Skip records that don't have matching name
            if(this.title != null && !recordArray[1].toLowerCase().equals(this.title.toLowerCase())) {

                continue;

            }

            System.out.println(Arrays.toString(recordArray));

            // Iterate over elements and map to object fields
            for(int i = 0; i < recordArray.length; i++){
                switch(i){
                    case 0:
                        this.id = recordArray[i];
                        break;
                    case 1:
                        this.title = recordArray[i];
                        break;
                    case 2:
                        this.venueID = recordArray[i];
                        break;
                    case 3:
                        this.seatID = recordArray[i];
                        break;
                    case 4:
                        this.vipAvailability = Integer.parseInt(recordArray[i]);
                        break;
                    case 5:
                        this.regTixAvailability = Integer.parseInt(recordArray[i]);
                        break;
                    case 6:
                        this.artistName = recordArray[i];
                        break;
                    case 7:
                        this.datetime = recordArray[i];
                        break;
                    case 8:
                        this.timestamp = Integer.parseInt(recordArray[i]);
                        break;
                }
            }

            // Don't continue on, break while loop
            break;
        }

    }

    /**
     * Helper method used to check for duplicate records in our "database" file.
     * Uses id and name as unique keys
     *
     * @return boolean
     * @throws FileNotFoundException
     */
    private boolean checkDuplicateRecord() throws FileNotFoundException
    {

        // Get input from the File
        Scanner fileInput = new Scanner(new File(this.DEFAULT_EVENT_FILE));

        while(fileInput.hasNextLine()){

            // read record line
            String record = fileInput.nextLine();

            // Explode the Record into individual elements
            String[] recordArray = record.split(",");

            // Iterate over the first two elements of the array (expected to be id and name, respectively)
            for(int i=0; i < 2; i++){

                if(i == 0){// if id

                    if(recordArray[i].toLowerCase().substring(1, (recordArray[i].length() - 1)).equals(this.id.toLowerCase())){

                        System.out.println("Duplicate Venue ID.");

                        return true;

                    }

                }

                if(i == 1){//if name

                    if(recordArray[i].toLowerCase().substring(1, (recordArray[i].length() - 1)).equals(this.title.toLowerCase())){

                        System.out.println("Duplicate Venue Name.");

                        return true;

                    }

                }

            }

        }

        return false;

    }
    
}