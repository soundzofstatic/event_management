import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.io.*;
import java.util.Date;


/**
 * DESCRIPTION
 *
 * @author Clarissa Dean
 * @version 1.0
 */
 
 /*
 NEEDED: - Checkout method needs to write to a transactions.csv (see createRecord() method Event, Venue or SeatingType) file that keeps track of an order’s:
			- transactionID/cartID
			- # of tickets ordered
			- Subtotal of order
			- Tax for order (if needed)
			- date of transaction (you can use the static method added into the Utility class)
			- epoch of transaction (you can use the static method added into the Utility class)
 */

public class Cart{

	private ArrayList<Ticket> tixList;
	private String id;
	private double subtotal = 0.0;
	private Date date = new Date();
	
	private static final String DEFAULT_TRANSACTIONS_FILE = "src/transactions.csv";

	public Cart(){

		this.tixList = new ArrayList<Ticket>();
		UUID uuid = UUID.randomUUID();

        // Set the id for the object
        this.id = uuid.toString();

	}

	public ArrayList<Ticket> getTixList(){

		return this.tixList;

	}

	public void setTixList(ArrayList<Ticket> newTixList){

		this.tixList = newTixList;

	}

	public Boolean add(Ticket newTicket){
		Boolean addSuccess = false;
		Scanner console = new Scanner(System.in);
		
		while (int i = 0; i < tixList.length(); i++){
			if (tixList.get(i).getId() == newTicket.getId()){
			return addSuccess;
			}
		}
		
		this.tixList.add(newTicket);
		this.subtotal += newTicket.getPrice();
		addSuccess = true;
		return addSuccess;
	}

	public Boolean remove(int ticketIndex){

		Scanner console = new Scanner(System.in);
		Boolean removeSuccess = false;

		if (ticketIndex < 0 || ticketIndex >= tixList.length()){
			return removeSuccess;
		}

		this.subtotal -= tixList.get(ticketIndex).getPrice();
		tixList.remove(ticketIndex);
		removeSuccess = true;
		return removeSuccess;
	}


	public String toString(){
		String cartString = "Tickets in Cart: \n";
		double tax;
		double total;
		for (int i = 0; i < tixList.length(); i++){
			cartString += (tixList.get(i).getId() + "\n");
			this.subtotal += (tixList.get(i).getPrice());
		}
		tax = this.subtotal * .07;
		total = this.subtotal + tax;
		cartString += ((20 * "-") + "\nSubtotal: $" + String.format("%.2f", this.subtotal)) +
				("\n7% Tax: $" + String.format("%.2f", tax)) +
				("\nTotal: $" + String.format("%.2f", total));
		return cartString;
	}


	public Boolean checkout(){
		Boolean checkoutSuccess = false;
		
		try {
			createRecord();
		} catch (FileNotFoundException e1) {
			System.out.print("File not found. Checkout canceled.");
			return checkoutSuccess;
		}
		
		for (int i = 0; i < tixList.length(); i++){
			tixlist.get(i).setPurchased(true);
		}
		
		checkoutSuccess = true;
		return checkoutSuccess;
		
	}
	
	private void createRecord() throws FileNotFoundException
    {
        String record = "\"" + this.id + "\",\"" + this.tixList.length + "\",\"" + String.format("%.2f", this.subtotal)) + "\",\"" + String.format("%.2f", (this.subtotal*.07)) + "\",\"" + convertEpochToDatestamp(this.date.getTime()) + "\",\"" + this.date.getTime() + "\"";

        FileOutputStream file_output_stream = new FileOutputStream(new File(this.DEFAULT_TRANSACTIONS_FILE), true);

        if(!this.checkDuplicateRecord()) {

            PrintStream output = new PrintStream(file_output_stream);

            output.println(record);

        } else {

            // todo - Does file_output_stream need to be closed in order to conserve resources?

        }

    }
	
	private boolean checkDuplicateRecord() throws FileNotFoundException
    {

        // Get input from the File
        Scanner fileInput = new Scanner(new File(this.DEFAULT_TRANSACTIONS_FILE));

        while(fileInput.hasNextLine()){

            // read record line
            String record = fileInput.nextLine();

            // Explode the Record into individual elements
            String[] recordArray = record.split(",");

            // Iterate over the first element of the array (expected to be id)
            for(int i=0; i < 1; i++){

                if(i == 0){// if id

                    if(recordArray[i].toLowerCase().substring(1, (recordArray[i].length() - 1)).equals(this.id.toLowerCase())){

                        System.out.println("Duplicate Transaction ID.");

                        return true;

                    }

                }
            }

        }

        return false;

    }
}
