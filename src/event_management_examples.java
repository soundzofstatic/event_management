import java.util.ArrayList;
import java.util.Arrays;

public class event_management_examples {

    public static void main(String[] args){

        exampleCreateVenue();

        //exampleQueryVenue("SPECIFY_ID_HERE");

        exampleCreateSeatingType();

        //exampleQuerySeatingType("SPECIFY_ID_HERE");

    }

    public static void exampleCreateVenue()
    {
//        Seating Options
        int[] seatingTypes = {1, 2, 4};
        // New Venue
        Venue newVenue = new Venue("Some Venue", "123 Fake St.", "Fakeville", "Illinois", "USA", "60000-0000", 1000, seatingTypes);

//         Seating Options
        int[] seatingTypes1 = {1, 4};
        Venue newVenue1 = new Venue("Governor's State University", "1 University Pkwy", "University Park", "Illinois", "USA", "60484", 1000, seatingTypes1);

//         Seating Options
        int[] seatingTypes2 = {1, 2, 3, 4};
        Venue newVenue2 = new Venue("UIC Pavilion", "525 S. Racine Ave.", "Chicago", "Illinois", "USA", "60607", 1000, seatingTypes2);

//         Seating Options
        int[] seatingTypes3 = {1, 2, 4};
        Venue newVenue3 = new Venue("House of Blues", "329 N. Dearborn St.", "Chicago", "Illinois", "USA", "60654", 1000, seatingTypes3);

//         Seating Options
        int[] seatingTypes4 = {1, 2, 4};
        Venue newVenue4 = new Venue("Lyric Opera of Chicago", "20 N Upper Wacker Dr", "Chicago", "Illinois", "USA", "60606", 1000, seatingTypes4);

    }

    public static void exampleQueryVenue(String idToQuery, String nameToQuery)
    {

        //
        // Query by ID
        //
        Venue findVenue = new Venue(idToQuery, null);
        System.out.println(findVenue.getId());
        System.out.println(findVenue.getName());
        System.out.println(findVenue.getStreetAddress());
        System.out.println(findVenue.getLocality());
        System.out.println(findVenue.getRegion());
        System.out.println(findVenue.getCountry());
        System.out.println(findVenue.getZipCode());
        System.out.println(findVenue.getCapacity());
        System.out.println(Arrays.toString(findVenue.getTypeOfSeating()));
        System.out.println(findVenue.getAddress());
        System.out.println("Printing Objects State");
        System.out.println(findVenue);

        //
        // Query By Name
        //
        Venue findVenue2 = new Venue(null,nameToQuery);
        System.out.println(findVenue2.getId());
        System.out.println(findVenue2.getName());
        System.out.println(findVenue2.getStreetAddress());
        System.out.println(findVenue2.getLocality());
        System.out.println(findVenue2.getRegion());
        System.out.println(findVenue2.getCountry());
        System.out.println(findVenue2.getZipCode());
        System.out.println(findVenue2.getCapacity());
        System.out.println(Arrays.toString(findVenue2.getTypeOfSeating()));
        System.out.println(findVenue2.getAddress());
        System.out.println("Printing Objects State");
        System.out.println(findVenue2);

    }

    public static void exampleCreateSeatingType()
    {
        //
        // Create Seating Types
        //

        new SeatingType("Rectangular", 10, 990, 1, "rectangle", 3, 5, 3);
        new SeatingType("Circular", 10, 990, 1, "circle", 5, 5, 3);
        new SeatingType("Trapezoidal", 10, 990, 1, "trapezoid", 3, 4, 3);
        new SeatingType("Square", 10, 990, 1, "square", 5, 5, 3);
        new SeatingType("Example-5", 10, 990, 1, "rectangle", 5, 3, 3);
        new SeatingType("Example-6", 10, 990, 1, "square", 10, 10, 3);

    }

    public static void exampleQuerySeatingType(String idToQuery)
    {

        SeatingType findSeatingType = new SeatingType(idToQuery, null);
        System.out.println(findSeatingType); // Prints Objects State
        // testing Pulled Values
        System.out.println(findSeatingType.getId()); // Prints SeatingType ID
        System.out.println(findSeatingType.getName()); // Prints SeatingType Name
        System.out.println(findSeatingType.getMaxCapacityVIP()); // Prints SeatingType MaxCapacityVIP
        System.out.println(findSeatingType.getMaxCapacityRegular()); // Prints SeatingType MaxCapacityRegular
        System.out.println(findSeatingType.getFloors()); // Prints SeatingType Floors
        System.out.println(findSeatingType.getShape()); // Prints SeatingType Shape

        // Parse over ArrayList<Row> provided by getSeatMap
        ArrayList<Row> rows = findSeatingType.getSeatMap();

        // Iterate over each Row in seatLayout
        for(int i = 0; i < rows.size(); i++){

            // Get the Current row as an object of type Row
            Row currentRow = rows.get(i);

            // Get the Seats for the row as an ArrayList<Seat>
            ArrayList<Seat> seats = currentRow.getSeats();

            // Iterate over the seats (seat objects)
            for(int j = 0; j < seats.size(); j++){

                // Get the Current seat as an object of type Seat
                Seat currentSeat = seats.get(j);

                System.out.println(currentSeat);

            }

        }

    }

}
