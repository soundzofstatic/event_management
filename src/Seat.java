import java.util.UUID;
public class Seat {

    private String id;
    private String sold;
    private String tier;

    /**
     * Constructor used to create a default seat
     */
    public Seat()
    {

        // Generate a UUID ID
        UUID uuid = UUID.randomUUID();

        // Set the id for the object
        this.id = uuid.toString();

        this.sold = "unsold";

        this.tier = "regular";

    }

    /**
     *  Constructor used to create a seat with specified settings
     * @param sold
     * @param tier
     */
    public Seat(String id, String sold, String tier)
    {

        // Set the id for the object
        this.id = id;

        this.sold = sold;

        this.tier = tier;

    }

    public String toString() {

        return "SeatID: " + this.id + ", Purchased: " + this.sold + ", Tier: " + this.tier + "\n";

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

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    /*
        Public Methods
     */

    public String[] getSeatArray(){

        return new String[]{id, sold, tier};

    }

}


