import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * File used to generate default CSV files for project
 *
 * @author Daniel Paz
 * @author Scott Chaplinksi
 * @author Clarissa Dean
 */
public class event_management_examples {

    private ArrayList<String> VenueIDs = new ArrayList<>();
    private ArrayList<String> SeatingTypeIDs = new ArrayList<>();

    public static void main(String[] args){


        new event_management_examples();

    }

    /**
     * Constructor
     */
    public event_management_examples()
    {

        generateExampleFiles();

    }

    /**
     * Void method used to generate Example Files
     */
    public void generateExampleFiles()
    {
        String[] artists = new String[]{"Johnny Depp", "Lady Gaga", "Oprah Winfrey", "Simon Cowell", "Will Smith", "Robert Downey", "Sandra Bullock", "Ellen DeGeneres"};

        String[] dates = new String[]{"12/31/2017 23:30:00", "03/15/2018 16:30:00", "06/13/2018 18:00:00", "09/04/2018 15:45:00", "10/31/2018 16:00:00", "03/13/2019 21:30:00", "04/14/2018 12:15:00", "07/11/2019 11:00:00"};

        // Create Ticket File
        try {

            Ticket.createTicketFile();

        } catch (IOException err){

            System.out.println(err);

        }

        exampleCreateVenue();

        exampleCreateSeatingType();

        for(int i=0; i < 10; i++){

            Random r = new Random();
            int venueIDtoUse = r.nextInt((VenueIDs.size()-1));
            int seatingIDtoUse = r.nextInt((SeatingTypeIDs.size()-1));
            int artistToUse = r.nextInt((artists.length - 1));
            int datesToUse = r.nextInt((dates.length-1));

            new Event("Example Event-" + i, VenueIDs.get(venueIDtoUse), SeatingTypeIDs.get(seatingIDtoUse), 77.77, r.nextInt(100), r.nextInt(900), artists[artistToUse], dates[datesToUse]);

        }

    }

    /**
     * Void method used to create Example Venues
     */
    public void exampleCreateVenue()
    {
        // Seating Options
        int[] seatingTypes = {1, 2, 4};
        // New Venue
        Venue newVenue = new Venue("Some Venue", "123 Fake St.", "Fakeville", "Illinois", "USA", "60000-0000", 1000, seatingTypes);
        this.VenueIDs.add(newVenue.getId());


        // Seating Options
        int[] seatingTypes1 = {1, 4};
        Venue newVenue1 = new Venue("Governor's State University", "1 University Pkwy", "University Park", "Illinois", "USA", "60484", 1000, seatingTypes1);
        this.VenueIDs.add(newVenue1.getId());

        // Seating Options
        int[] seatingTypes2 = {1, 2, 3, 4};
        Venue newVenue2 = new Venue("UIC Pavilion", "525 S. Racine Ave.", "Chicago", "Illinois", "USA", "60607", 1000, seatingTypes2);
        this.VenueIDs.add(newVenue2.getId());

        // Seating Options
        int[] seatingTypes3 = {1, 2, 4};
        Venue newVenue3 = new Venue("House of Blues", "329 N. Dearborn St.", "Chicago", "Illinois", "USA", "60654", 1000, seatingTypes3);
        this.VenueIDs.add(newVenue3.getId());

        // Seating Options
        int[] seatingTypes4 = {1, 2, 4};
        Venue newVenue4 = new Venue("Lyric Opera of Chicago", "20 N Upper Wacker Dr", "Chicago", "Illinois", "USA", "60606", 1000, seatingTypes4);
        this.VenueIDs.add(newVenue4.getId());

    }

    /**
     * Void metho used to create Example Seating Types
     */
    public void exampleCreateSeatingType()
    {
        //
        // Create Seating Types
        //

        SeatingType seatingType1 = new SeatingType("Rectangular", 10, 990, 1, "rectangle", 3, 5, 3);
        this.SeatingTypeIDs.add(seatingType1.getId());

        SeatingType seatingType2 = new SeatingType("Circular", 10, 990, 1, "circle", 5, 5, 3);
        this.SeatingTypeIDs.add(seatingType2.getId());

        SeatingType seatingType3 = new SeatingType("Trapezoidal", 10, 990, 1, "trapezoid", 12, 5, 3);
        this.SeatingTypeIDs.add(seatingType3.getId());

        SeatingType seatingType4 = new SeatingType("Square", 10, 990, 1, "square", 5, 5, 3);
        this.SeatingTypeIDs.add(seatingType4.getId());

        SeatingType seatingType5 = new SeatingType("Example-5", 10, 990, 1, "rectangle", 5, 3, 3);
        this.SeatingTypeIDs.add(seatingType5.getId());

        SeatingType seatingType6 = new SeatingType("Example-6", 10, 990, 1, "square", 10, 10, 3);
        this.SeatingTypeIDs.add(seatingType6.getId());

    }

}
