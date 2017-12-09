import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

/**
 * DESCRIPTION
 *
 * @author Daniel Paz
 * @author Scott Chaplinksi
 * @author Clarissa Dean
 * @version 1.0
 */
public class SeatingType {

    private String id;
    private String name;
    private int maxCapacityVIP;
    private int maxCapacityRegular;
    private int floors;
    private String shape;
    private ArrayList<Row> seatMap;

    private static final String DEFAULT_VENUE_FILE = "src/seating_types.csv";

    /**
     * Venue constructor that takes a Venue UUID string and name and implicitly reads data from venue file and set object fields
     *
     * @param id
     * @param name
     */
    public SeatingType(String id, String name){

        this.id = id;
        this.name = name;

        // run selectRecord(); to pull data from database
        try {
            selectRecord();

        } catch (FileNotFoundException e1) {   // Catch block

            System.out.println("File not Found");
        }

    }

    /**
     * SeatingType constructor used to accept client code definitions for seating type and implicitly writes data to a file
     *
     * @param name
     * @param maxCapacityVIP
     * @param maxCapacityRegular
     * @param floors
     * @param shape
     */
    public SeatingType(String name, int maxCapacityVIP, int maxCapacityRegular, int floors, String shape, int rows, int seats, int vipRowStart)
    {

        // Set all of the object fields
        this.name = name;
        this.maxCapacityVIP = maxCapacityVIP;
        this.maxCapacityRegular = maxCapacityRegular;
        this.floors = floors;
        this.shape = shape;
        this.seatMap = constructArrayListSeatmap(rows, seats, vipRowStart);

        // Write to File
        try {

            createRecord();

        } catch (FileNotFoundException e1) {

            // Catch block
            System.out.println("File not Found");

        }

    }

    /**
     *
     * @return String
     */
    public String toString() {

        // todo - define
        return "Todo - DEFINE how this should output";

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
     * Public Getter method that returns value of this.name
     * @return String
     */
    public String getName()
    {

        return this.name;

    }

    /**
     * Public Getter method that returns value of this.maxCapacityVIP
     * @return int
     */
    public int getMaxCapacityVIP()
    {

        return this.maxCapacityVIP;

    }

    /**
     * Public Getter method that returns value of this.maxCapacityRegular
     * @return int
     */
    public int getMaxCapacityRegular()
    {

        return this.maxCapacityRegular;

    }

    /**
     * Public Getter method that returns value of this.floors (int)
     * @return int
     */
    public int getFloors()
    {

        return this.floors;

    }

    /**
     * Public Getter method that returns value of this.shape (String)
     * @return String
     */
    public String getShape()
    {

        return this.shape;

    }

    /**
     * Public Getter method that returns the vlaue of this.SeatMap (ArrayList of Row Objects)
     * @return
     */
    public ArrayList<Row> getSeatMap()
    {

        return this.seatMap;

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
        String record = "\"" + id + "\",\"" + name + "\",\"" + maxCapacityVIP + "\",\"" + maxCapacityRegular + "\",\"" + floors + "\",\"" + shape + "\",\"" + arrayListSeatmapToString(seatMap) + "\"";

        // Instantiate a File output stream and set the pointer to the end of file in order to append
        // Creates file if it does not exist
        FileOutputStream file_output_stream = new FileOutputStream(new File(this.DEFAULT_VENUE_FILE), true);

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
        Scanner fileInput = new Scanner(new File(this.DEFAULT_VENUE_FILE));

        while(fileInput.hasNextLine()){

            // Read record line and explode it via ","
            String[] recordArray = Utility.explode(fileInput.nextLine(), ",");

            // Skip records that don't have matching id
            if(this.id != null && !recordArray[0].toLowerCase().equals(this.id.toLowerCase())) {

                continue;

            }

            // Skip records that don't have matching name
            if(this.name != null && !recordArray[1].toLowerCase().equals(this.name.toLowerCase())) {

                continue;

            }

            // Iterate over elements and map to object fields
            for(int i = 0; i < recordArray.length; i++){
                switch(i){
                    case 0:
                        this.id = recordArray[i];
                        break;
                    case 1:
                        this.name = recordArray[i];
                        break;
                    case 2:
                        this.maxCapacityVIP = Integer.parseInt(recordArray[i]);
                        break;
                    case 3:
                        this.maxCapacityRegular = Integer.parseInt(recordArray[i]);
                        break;
                    case 4:
                        this.floors = Integer.parseInt(recordArray[i]);
                        break;
                    case 5:
                        this.shape = recordArray[i];
                        break;
                    case 6:
                        this.seatMap = convertStringtoArrayList(recordArray[i]);
                        break;
                }
            }

            // Don't continue on, break while loop
            break;
        }

    }

    /**
     * Private method used to convert a string representation of a multidimensional array to an ArrayList of Row objects
     *
     * @param stringArray
     * @return
     */
    private ArrayList<Row> convertStringtoArrayList(String stringArray)
    {
        // Integer augmentor used to keep track at what level in the multidim array we are in
        int groupLevel = -1;

        // String used to hold inner values of a seat array
        String values = "";

        // Array storing Row objects
        ArrayList<Row> seatLayout = new ArrayList<>();

        for(int i = 0; i < stringArray.length();i++){

            if(stringArray.charAt(i) != '[' && stringArray.charAt(i) != ']' && stringArray.charAt(i) != ','){

                values += stringArray.charAt(i);

            } else {

                // An array has started
                if(stringArray.charAt(i) == '['){

                    // Increment since we are within another (nested) group
                    groupLevel++;

                    // Reset the values variable
                    values = "";

                    // Open (append) a new Row to seatLayout
                    if(groupLevel == 1){

                        // Create a new Row object and append it to the seatLayout
                        seatLayout.add(new Row());

                    }

                } else if(stringArray.charAt(i) == ']'){

                    // Check if the values up to this point only equal blank (in the case of between nested arrays ie. [], [])
                    if(!values.equals("")) {

                        // Split properties for Seat which are separated by \s at this point
                        // todo - Note: if a single seat attribute is multi word, this will bug out
                        String[] seatProperties = values.split(" ");

                        // Get the last element of seatLayout, which is a Row Object
                        Row currentRow = seatLayout.get((seatLayout.size() - 1));

                        // Create a new Seat and set it to the last row object in the seatLayout
                        currentRow.addSeat(new Seat(seatProperties[0], Integer.parseInt(seatProperties[1]), Integer.parseInt(seatProperties[2]), Integer.parseInt(seatProperties[3])));

                    }

                    // now Reset values variable since it has already been logged to the row
                    values = "";

                    // Decrement to step out of this group
                    groupLevel--;

                }

            }

        }

        return seatLayout;

    }

    /**
     * Private method that is used to iterate over an ArrayList of Row objects and convert them to arrays
     * in order to flatten the arrays out using .toString so we can write a full String representation of the
     * array to file
     *
     * @param seatLayout
     * @return
     */
    private String arrayListSeatmapToString(ArrayList<Row> seatLayout)
    {

        // Initialize a general ArrayList to be used to keep Rows in String Array format
        ArrayList dynamicRowArray = new ArrayList();

        // Iterate over each Row in seatLayout
        for(int i = 0; i < seatLayout.size(); i++){

            // Initialize general ArrayList to be used to keep Seats in String Array format
            ArrayList dynamicSeatArray = new ArrayList();

            // Get the Current row as an object of type Row
            Row currentRow = seatLayout.get(i);

            // Get the Seats for the row as an ArrayList<Seat>
            ArrayList<Seat> seats = currentRow.getSeats();

            // Iterate over the seats (seat objects)
            for(int j = 0; j < seats.size(); j++){

                // Get the Current seat as an object of type Seat
                Seat currentSeat = seats.get(j);

                // Add the currentSeat's to the general dynamicSeatArray as String[]
                dynamicSeatArray.add(new String[]{currentSeat.getId(), Integer.toString(currentSeat.getRowNumber()), Integer.toString(currentSeat.getSeatNumber()), Integer.toString(currentSeat.getFloor())});

            }

            // Add the dynamicSeatArray String[] to the array, but use the String Representation of the array
            dynamicRowArray.add(Arrays.deepToString(dynamicSeatArray.toArray()));

        }

        // Return
        return Arrays.deepToString(dynamicRowArray.toArray());

    }

    /**
     * Private method used to construct an ArrayList of Row object based on row and seat specifications
     *
     * @param rows
     * @param seats
     * @param vipRowStart
     * @return
     */
    private ArrayList<Row> constructArrayListSeatmap(int rows, int seats, int vipRowStart)
    {

        // Array storing Row objects
        ArrayList<Row> seatLayout = new ArrayList<>();

        // Iterate over Rows
        for(int i = 0; i < rows; i++){

            // Create a new Row object and append it to the seatLayout
            seatLayout.add(new Row());

            // Iterate over Seats
            for(int j=0; j < seats; j++){

                int localFloor = 1;

                if(i >= 10) { // if the row count is greater than 10, then we are on the second floor

                    localFloor = 2;

                }

                // Create a new Seat and set it to the last row object in the seatLayout
                seatLayout.get((seatLayout.size() - 1)).addSeat(new Seat(i, j, localFloor));

            }

        }

        return seatLayout;

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
        Scanner fileInput = new Scanner(new File(this.DEFAULT_VENUE_FILE));

        while(fileInput.hasNextLine()){

            // read record line
            String record = fileInput.nextLine();

            // Explode the Record into individual elements
            String[] recordArray = record.split(",");

            // Iterate over the first two elements of the array (expected to be id and name, respectively)
            for(int i=0; i < 2; i++){

                if(i == 0){// if id

                    if(recordArray[i].toLowerCase().substring(1, (recordArray[i].length() - 1)).equals(this.id.toLowerCase())){

                        System.out.println("Duplicate SeatingType ID.");

                        return true;

                    }

                }

                if(i == 1){//if name

                    if(recordArray[i].toLowerCase().substring(1, (recordArray[i].length() - 1)).equals(this.name.toLowerCase())){

                        System.out.println("Duplicate SeatingType Name.");

                        return true;

                    }

                }

            }

        }

        return false;

    }

}