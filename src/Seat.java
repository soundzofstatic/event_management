import java.util.UUID;
/**
 * DESCRIPTION
 *
 * @author Daniel Paz
 * @version 1.0
 */
public class Seat {

    private String id;

    /**
     * Constructor used to create a default seat
     */
    public Seat()
    {

        // Generate a UUID ID
        UUID uuid = UUID.randomUUID();

        // Set the id for the object
        this.id = uuid.toString();

    }

    /**
     * Constructor used to create a seat with specified settings
     * @param id
     */
    public Seat(String id)
    {

        // Set the id for the object
        this.id = id;

    }

    public String toString() {

        return "SeatID: " + this.id + "\n";

    }

    /*
        Getter/Setters
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*
        Public Methods
     */

    public String[] getSeatArray(){

        return new String[]{id};

    }

}


