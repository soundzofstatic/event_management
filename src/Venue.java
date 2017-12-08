import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

/**
 * DESCRIPTION
 *
 * @author Daniel Paz
 * @version 1.0
 */
public class Venue {

    private String id;
    private String name;
    private String streetAddress;
    private String locality;
    private String region;
    private String country;
    private String zipCode;
    private int capacity;
    private int[] typeOfSeating;

    private static final String DEFAULT_VENUE_FILE = "src/venues.csv";

    /**
     * Venue constructor that takes a Venue UUID string and name and implicitly reads data from venue file and set object fields
     *
     * @param id
     * @param name
     */
    public Venue(String id, String name){

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
     * Venue constructor used to accept client code definitions for a venue and implicitly writes data to a file
     *
     * @param name
     * @param streetAddress
     * @param locality
     * @param region
     * @param country
     * @param zipCode
     * @param capacity
     * @param typeOfSeating
     */
    public Venue(String name, String streetAddress, String locality, String region, String country, String zipCode, int capacity, int[] typeOfSeating)
    {

        // Set all of the object fields
        this.name = name;
        this.streetAddress = streetAddress;
        this.locality = locality;
        this.region = region;
        this.country = country;
        this.zipCode = zipCode;
        this.capacity = capacity;
        this.typeOfSeating = typeOfSeating;

        // Write to File
        try {
            createRecord();
        } catch (FileNotFoundException e1) {
            // Catch block
            System.out.println("File not Found");
        }

    }

    /**
     * Formatted as a USPS valid address
     *
     * @return String
     */
    public String toString() {

        String address =  this.streetAddress + "\n";
        address += this.locality + ", " + this.region + " " + this.zipCode + "\n";
        address += this.country + "\n";

        return address;

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
     * Public Getter method that returns value of this.streetAddress
     * @return String
     */
    public String getStreetAddress()
    {

        return this.streetAddress;

    }

    /**
     * Public Getter method that returns value of this.locality
     * @return String
     */
    public String getLocality()
    {

        return this.locality;

    }

    /**
     * Public Getter method that returns value of this.region
     * @return String
     */
    public String getRegion()
    {

        return this.region;

    }

    /**
     * Public Getter method that returns value of this.country
     * @return String
     */
    public String getCountry()
    {

        return this.country;

    }

    /**
     * Public Getter method that returns value of this.zipCode
     * @return String
     */
    public String getZipCode()
    {

        return this.zipCode;

    }

    /**
     * Public Getter method that returns value of this.capacity
     * @return int
     */
    public int getCapacity()
    {

        return this.capacity;

    }

    /**
     * Public Getter method that returns value of this.typeOfSeating
     * @return int[]
     */
    public int[] getTypeOfSeating()
    {

        return this.typeOfSeating;

    }

    /**
     * Public method that returns a full address
     *
     * @return String
     */
    public String getAddress()
    {

        String address = "";

        if(this.streetAddress != null){

            address += " " + this.streetAddress;

        }

        if(this.locality != null){

            address += " " + this.locality;

        }

        if(this.region != null){

            address += ", " + this.region;

        }

        if(this.country != null){

            address += " " + this.country;

        }

        if(this.zipCode != null){

            address += " " + this.zipCode;

        }

        return address;

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
        String record = "\"" + id + "\",\"" + name + "\",\"" + streetAddress + "\",\"" + locality + "\",\"" + region + "\",\"" + country + "\",\"" + zipCode + "\",\"" + capacity + "\",\"" + Arrays.toString(typeOfSeating) + "\"";

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
                        this.streetAddress = recordArray[i];
                        break;
                    case 3:
                        this.locality = recordArray[i];
                        break;
                    case 4:
                        this.region = recordArray[i];
                        break;
                    case 5:
                        this.country = recordArray[i];
                        break;
                    case 6:
                        this.zipCode = recordArray[i];
                        break;
                    case 7:
                        this.capacity = Integer.parseInt(recordArray[i]);
                        break;
                    case 8:
                        int[] seatMap = {1, 2, 3};
                        this.typeOfSeating = seatMap;
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

                        System.out.println("Duplicate Venue ID.");

                        return true;

                    }

                }

                if(i == 1){//if name

                    if(recordArray[i].toLowerCase().substring(1, (recordArray[i].length() - 1)).equals(this.name.toLowerCase())){

                        System.out.println("Duplicate Venue Name.");

                        return true;

                    }

                }

            }

        }

        return false;

    }

}
