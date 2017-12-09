import java.util.UUID;
/**
 * DESCRIPTION
 *
 * @author Daniel Paz
 * @author Scott Chaplinksi
 * @author Clarissa Dean
 * @version 1.0
 */
public class Seat {

    private String id;
    private int rowNumber;
    private int seatNumber;
    private int floor;

    /**
     * Constructor used to create a default seat
     */
    public Seat(int rowNumber, int seatNumber, int floor)
    {

        // Generate a UUID ID
        UUID uuid = UUID.randomUUID();

        // Set the id for the object
        this.id = uuid.toString();
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.floor = floor;

    }

    /**
     * Constructor used to create a seat with specified settings
     * @param id
     */
    public Seat(String id, int rowNumber, int seatNumber, int floor)
    {

        // Set the id for the object
        this.id = id;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.floor = floor;

    }

    /**
     * Prints the Object's state
     *
     * @return
     */
    public String toString() {

        return "SeatID: " + this.id + "\n";

    }

    /*
        Getter/Setters
     */

    /**
     * Public getter method used to return this.id
     *
     * @return
     */
    public String getId() {

        return this.id;
    }

    /**
     * Public getter method used to return this.rowNumber
     *
     * @return
     */
    public int getRowNumber() {

        return this.rowNumber;

    }

    /**
     * Public getter method used to return this.seatNumber
     *
     * @return
     */
    public int getSeatNumber() {

        return this.seatNumber;

    }

    /**
     * Public getter method used to return this.floor
     *
     * @return
     */
    public int getFloor() {

        return this.floor;

    }

    /**
     * Public setter method used to set this.id
     *
     * @param id
     */
    public void setId(String id) {

        this.id = id;

    }


}


