import java.util.ArrayList;

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

    public void addSeat(Seat seat)
    {

        this.seats.add(seat);

        // No update the seat count
        countSeats();

    }

    public ArrayList<Seat> getSeats()
    {

        return seats;

    }

    private void countSeats()
    {

        this.seatCount = seats.size();

    }

}
