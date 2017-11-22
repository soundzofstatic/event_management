import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class SeatingType {

    private String id;
    private String name;
    private int maxCapacityVIP;
    private int maxCapacityRegular;
    private int floors;
    private String shape;
    private String[][][] seatMap;

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
     * @param seatMap
     */
    public SeatingType(String name, int maxCapacityVIP, int maxCapacityRegular, int floors, String shape, String[][][] seatMap)
    {

        // Set all of the object fields
        this.name = name;
        this.maxCapacityVIP = maxCapacityVIP;
        this.maxCapacityRegular = maxCapacityRegular;
        this.floors = floors;
        this.shape = shape;
        this.seatMap = seatMap;

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
     * Public Getter method that returns value of this.floors
     * @return int
     */
    public int getFloors()
    {

        return this.floors;

    }

    /**
     * Public Getter method that returns value of this.shape
     * @return String
     */
    public String getShape()
    {

        return this.shape;

    }

    /**
     * Public Getter method that returns value of this.seatMap
     * @return int[]
     */
    public String[][][] getSeatMap()
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
        String record = "\"" + id + "\",\"" + name + "\",\"" + maxCapacityVIP + "\",\"" + maxCapacityRegular + "\",\"" + floors + "\",\"" + shape + "\",\"" + Arrays.deepToString(seatMap) + "\"";

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
                        //String[][][] seatMap = {{{"unsold", "regular"}, {"unsold", "regular"}}, {{"unsold", "regular"}, {"unsold", "regular"}}};
                        this.seatMap =  convertStringtoArray(recordArray[i]);//seatMap;//recordArray[i]; // todo - need algorithm to convert string representation of array (or JSON array), potentially multi-dim array back into an JAVA array
                        break;
                }
            }

            // Don't continue on, break while loop
            break;
        }

    }

    private String[][][] convertStringtoArray(String stringArray)
    {

        String[][][] sampleStringArray = new String[][][] {{{"unsold", "regular"}, {"unsold", "regular"}}, {{"unsold", "regular"}, {"unsold", "regular"}}};

        // todo - Overridden during testing
        stringArray = Arrays.deepToString(sampleStringArray);

        String arrayStringStructure = "";

        ArrayList dimensions = new ArrayList();
        ArrayList valuesArray = new ArrayList();

        int openBracket = 0;

        int lastArrayStart = -1;

        String values = "";


        for(int i = 0; i < stringArray.length();i++){

            if(stringArray.charAt(i) != '[' && stringArray.charAt(i) != ']' && stringArray.charAt(i) != ','){

                //openBracket += 1;
                values += stringArray.charAt(i);

            } else {

                arrayStringStructure += stringArray.charAt(i);

                // An array has started
                if(stringArray.charAt(i) == '['){

                    lastArrayStart++;

                    // Reset the values variable
                    values = "";

                    if(dimensions.contains(lastArrayStart)){

                        //System.out.println("Already in ArrayList: " + lastArrayStart);

                    } else {

                        //System.out.println("Not in ArrayList: " + lastArrayStart);
                        dimensions.add(lastArrayStart, lastArrayStart);

                    }

                    System.out.println("Opened at: " + lastArrayStart);

                } else if(stringArray.charAt(i) == ']'){

                    // Report the value up to date
                    System.out.println(values);

                    System.out.println("Closed at: " + lastArrayStart);

                    if(!values.equals("")) {
                        int indexToUse = (valuesArray.size() - 1);
                        if (indexToUse < 0){
                            indexToUse = 0;
                        }

                        String[] splitString = values.split(" ");

                        // Array
                        ArrayList arr = new ArrayList();
                        for(int j =0; j < splitString.length; j++){

                            arr.add(splitString[j]);

                        }
                        valuesArray.add(arr);
                    }

                    // now Reset values since a new element is starting
                    values = "";

                    lastArrayStart--;

                } /*else if(stringArray.charAt(i) == ','){

                    // Report the values variable in its current state
                    System.out.println(values);

                    System.out.println("Continues at: " + lastArrayStart);

                    if(!values.equals("")) {

                        int indexToUse = (valuesArray.size() - 1);
                        if (indexToUse < 0){
                            indexToUse = 0;
                        }
                        valuesArray.add(indexToUse, values);
                    }

                    // now Reset values since a new element is starting
                    values = "";

                }*/

            }

        }

        System.out.println("Open Brackets: " + openBracket);
        System.out.println("Array String Structure: " + arrayStringStructure);
        System.out.println("Array String Value: " + values);
        System.out.println("Dimensions in Structure: " + dimensions);
        System.out.println("Values in Structure: " + valuesArray);



        return sampleStringArray;

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