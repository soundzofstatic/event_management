import java.util.ArrayList;

/**
 * DESCRIPTION
 *
 * @author Daniel Paz
 * @author Scott Chaplinksi
 * @author Clarissa Dean
 * @version 1.0
 */
public class Row {

    int seatCount;
    ArrayList<Seat> seats = new ArrayList<Seat>();


    public String toString() {

        // todo - define how this should output
        String output = "This row contains " + seatCount + " seats. They are:\n";

        // Iterate over each seat and print it out
        for(int i = 0; i < seats.size(); i++)
        {
            output += seats.get(i).toString();

        }

        return output;

    }

    /**
     * Public void method used to set a Seat within seats
     *
     * @param seat
     */
    public void addSeat(Seat seat)
    {

        this.seats.add(seat);

        // No update the seat count
        countSeats();

    }

    /**
     * Public method used to return a ArrayList of Seat
     *
     * @return
     */
    public ArrayList<Seat> getSeats()
    {

        return seats;

    }

    /**
     * Public void method used to return number of Seat in seats
     *
     */
    private void countSeats()
    {

        this.seatCount = seats.size();

    }

}
