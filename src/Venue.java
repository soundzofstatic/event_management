import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Venue {

    private int id;
    private String name;
    private String streetAddress;
    private String locality;
    private String region;
    private String country;
    private String zipCode;
    private int capacity;
    private int[] typeOfSeating;

    public static final String DEFAULT_VENUE_FILE = "src/venues.csv";

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
            writeToFile();
        } catch (FileNotFoundException e1) {
            // Catch block
            System.out.println("File not Found");
        }


    }

    /**
     * Private method used to write to object fields to a venues.csv file
     *
     * @throws FileNotFoundException
     */
    private void writeToFile() throws FileNotFoundException
    {

        String record = name + ", " + streetAddress + ", " + locality + ", " + region + ", " + country + ", " + zipCode + ", " + capacity + ", " + Arrays.toString(typeOfSeating);

        // Instantiate a File output stream and set the pointer to the end of file in order to append
        // Creates file if it does not exist
        FileOutputStream file_output_stream = new FileOutputStream(new File(this.DEFAULT_VENUE_FILE), true);

        // Create a PrintStream object that will be used to output the file
        PrintStream output = new PrintStream(file_output_stream);

        // Print to file
        output.println(record);

    }

}
