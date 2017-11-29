import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;
/**
 * DESCRIPTION
 *
 * @author Scott Chaplinksi
 * @version 1.0
 */
public class Ticket {

    private float price;
    private int tier;
    private int quantity;

    private static final String DEFAULT_TICKET_FILE = "src/tickets.csv";

    /**
     * Venue constructor used to accept client code definitions for a venue and implicitly writes data to a file
     *
     * @param price
     * @param tier
     * @param quantity
     */

    public Ticket(float price, int tier, int quantity) {

        // Set all of the object fields
        this.price = price;
        this.tier = tier;
        this.quantity = quantity;
    }

    /**
     * @return String
     */
    public String toString() {

        // todo - define
        return "Todo - DEFINE how this should output";

    }

    /**
     * Public Getter method that returns value of this.price
     *
     * @return float
     */

    public float getPrice() {

        return this.price;

    }

    /**
     * Public Getter method that returns value of this.tier
     *
     * @return int
     */

    public int getTier() {

        return this.tier;

    }

    /**
     * Public Getter method that returns value of this.quantity
     *
     * @return int
     */
    public int getQuantity() {
        return this.quantity;

    }

}