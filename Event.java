import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Venue {

    private String id;
    private String eventType;
    private String venue;
    private String artistName;
    private String datetime;
    private int ticketAvailability;
    private int capacity;

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
     * @param eventType
     * @param venue
     * @param artistName
     * @param datetime
     * @param ticketAvailability
     * @param capacity
     */

    public Venue(String eventType, String venue, String artistName, String datetime, int ticketAvailability, int capacity)
    {

        // Set all of the object fields
        this.eventType = eventType;
        this.venue = venue;
        this.artistName = artistName;
        this.datetime = datetime;
        this.ticketAvailability = ticketAvailability;
        this.capacity = capacity;

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
     * Public Getter method that returns value of this.eventType
     * @return String
     */
    public String getEventType()
    {

        return this.eventType;

    }

    /**
     * Public Getter method that returns value of this.venue
     * @return String
     */
    public String getVenue()
    {

        return this.venue;

    }

    /**
     * Public Getter method that returns value of this.artistName
     * @return String
     */
    public String getArtistName()
    {

        return this.artistName;

    }

    /**
     * Public Getter method that returns value of this.datetime
     * @return String
     */
    public String getDatetime()
    {

        return this.datetime;

    }

    /**
     * Public Getter method that returns value of this.ticketAvailability
     * @return String
     */
    public String getTicketAvailability()
    {

        return this.ticketAvailability;

    }

    /**
     * Public Getter method that returns value of this.capacity
     * @return String
     */
    public String getCapacity()
    {

        return this.capacity;

    }