import java.io.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Scanner;
import java.util.UUID;
import java.util.Date;

/**
 * DESCRIPTION
 *
 * @author Scott Chaplinksi
 * @author Daniel Paz
 * @version 1.0
 */
public class Event {

    private String id;
    private String title;
    private String venueID;
    private Venue venue;
    private String seatingTypeID;
    private SeatingType seatingType;
//  private String eventType;
    private int vipAvailability;
    private int regTixAvailability;
    private String artistName;
    private String datetime;
    private long timestamp;
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
     * Event Constructor used to accept client code definitions for an event and implicitly writes data to a file
     *
     * @param title
     * @param venueID
     * @param seatingTypeID
     * @param vipAvailability
     * @param regTixAvailability
     * @param artistName
     * @param datetime
     */
    public Event(String title, String venueID, String seatingTypeID, int vipAvailability, int regTixAvailability, String artistName, String datetime) {

        // Set all of the object fields
        this.title = title;
        this.venueID = venueID;
        this.seatingTypeID = seatingTypeID;
        this.vipAvailability = vipAvailability;
        this.regTixAvailability = regTixAvailability;
        this.artistName = artistName;
        this.datetime = datetime;
        try {

            this.timestamp = convertDatestampToEpoch(datetime);

        } catch (Exception err){

            this.timestamp = 0;

        }


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

    public Long getTimestamp() {

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
    public String getVenueID() {
        return this.venueID;

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
    public String getSeatingTypeID() {
        return this.seatingTypeID;

    }

    /**
     * Public Getter method that returns value of this.venue
     *
     * @return String
     */
    public String getSeatingType() {
        SeatingType venueSeating = new SeatingType(this.seatingTypeID, null);
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
        String record = "\"" + id + "\",\"" + this.title + "\",\"" + this.venueID + "\",\"" + this.seatingTypeID + "\",\"" + this.vipAvailability + "\",\"" + this.regTixAvailability + "\",\"" + this.artistName + "\",\"" + this.datetime + "\",\"" + this.timestamp + "\"";

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
                        this.seatingTypeID = recordArray[i];
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
                        this.timestamp = Long.parseLong(recordArray[i]);
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
            for(int i=0; i < 1; i++){

                if(i == 0){// if id

                    if(recordArray[i].toLowerCase().substring(1, (recordArray[i].length() - 1)).equals(this.id.toLowerCase())){

                        System.out.println("Duplicate Event ID.");

                        return true;

                    }

                }
            }

        }

        return false;

    }

    private Long convertDatestampToEpoch(String datetime) throws Exception
    {

        // Per https://stackoverflow.com/questions/6687433/convert-a-date-format-in-epoch

        // Note: Dattime is expected in the following format
        // String datetime = "12/15/2017 23:11:52";

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = df.parse(datetime);
        return  date.getTime();

    }

    private String convertEpochToDatestamp(Long epoch)
    {
        // Per https://stackoverflow.com/questions/7740972/convert-epoch-time-to-date

        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return format.format(date);

    }
    
}