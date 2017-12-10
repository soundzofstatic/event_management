import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.io.*;
import java.util.Date;


/**
 * DESCRIPTION
 *
 * @author Clarissa Dean
 * @author Daniel Paz
 * @author Scott Chaplinksi
 * @version 1.0
 */

public class Cart{

	private ArrayList<Ticket> tixList;
	private String id;
	private double subtotal = 0.0;
	private double taxRate = 0.07;
	private double tax = 0.0;
	private double total = 0.0;
	private int tixQuantity = 0;
	private Date date = new Date();

	private static final String DEFAULT_TRANSACTIONS_FILE =  System.getProperty("user.dir") + "/transactions.csv";

	/**
	 * Cart constructor that implicitly sets the Cart ID
	 *
	 */
	public Cart(){

		this.tixList = new ArrayList<Ticket>();
		UUID uuid = UUID.randomUUID();

		// Set the id for the object
		this.id = uuid.toString();

	}

	/**
	 * Public getter method that returns the valye of this.id
	 *
	 * @return
	 */
	public String getId() {

		return this.id;

	}

	/**
	 * Public Getter method that returns the value of this.tixList
	 *
	 * @return ArrayList
	 */
	public ArrayList<Ticket> getTixList(){

		return this.tixList;

	}


	/**
	 * Public Getter method that returns the value of this.subtotal
	 *
	 * @return
	 */
	public double getSubtotal(){
		return this.subtotal;
	}


	/**
	 * Public Getter method that returns the value of this.tax
	 *
	 * @return
	 */
	public double getTax(){
		return this.tax;
	}


	/**
	 * Public Getter method that returns the value of this.total
	 *
	 * @return
	 */
	public double getTotal(){
		return this.total;
	}


	/**
	 * Public Getter method that returns the value of this.tixQuantity
	 *
	 * @return
	 */
	public int getTixQuantity(){
		return this.tixQuantity;
	}


	/**
	 * Public Setter method that sets the value of this.tixList
	 *
	 * @param newTixList
	 */
	public void setTixList(ArrayList<Ticket> newTixList){

		this.tixList = newTixList;

	}


	/**
	 * Public method that adds a new ticket to this.tixList, recalculates the subtotal, tax, and total of the cart, and returns a boolean denoting whether the addition was successful or not.
	 *
	 * @param newTicket
	 * @return
	 */
	public boolean add(Ticket newTicket){
		boolean addSuccess = false;
		Scanner console = new Scanner(System.in);

		for(int i = 0; i < this.tixList.size(); i++){
			if (this.tixList.get(i).getId() == newTicket.getId()){
				return addSuccess;
			}
		}

		this.tixList.add(newTicket);
		calculateTotal(newTicket.getPrice(), true);
		this.tixQuantity++;
		addSuccess = true;
		return addSuccess;
	}


	/**
	 * Public method that removes a ticket by ID from this.tixList (if it exists within the list), recalculates the subtotal, tax, and total of the cart, and returns a boolean denoting whether the removal was successful or not.
	 *
	 * @param ticketId
	 * @return
	 */
	public boolean remove(String ticketId){

		Scanner console = new Scanner(System.in);
		boolean removeSuccess = false;

		if (ticketId == null){
			return removeSuccess;
		}

		for (int i = 0; i < tixList.size(); i++){
			if (this.tixList.get(i).getId().toLowerCase() == ticketId.toLowerCase()){
				calculateTotal(this.tixList.get(i).getPrice(), false);
				this.tixList.remove(i);
				this.tixQuantity--;
				removeSuccess = true;
				return removeSuccess;
			}
		}

		return removeSuccess;
	}


	/**
	 * @return String
	 */
	public String toString(){
		String cartString = "Tickets in Cart: \n";
		for (int i = 0; i < this.tixList.size(); i++){
			cartString += (this.tixList.get(i).getId() + "\n");
			this.subtotal += (this.tixList.get(i).getPrice());
		}
		cartString += "********************\n";
		cartString += "Subtotal: $" + String.format("%.2f", this.subtotal);
		cartString +=  "\n7% Tax: $" + String.format("%.2f", tax);
		cartString += "\nTotal: $" + String.format("%.2f", total);
		return cartString;
	}


	/**
	 * Public method that checks the cart out, adding the cart's data to the transactions.csv file and updating the status of the tickets to sold. Returns a boolean denoting if the checkout was successful or not.
	 *
	 * @return
	 */
	public boolean checkout(){
		boolean checkoutSuccess = false;

		try {
			createRecord();
		} catch (FileNotFoundException e1) {
			System.out.print("File not found. Checkout canceled.");
			return checkoutSuccess;
		}

		for (int i = 0; i < this.tixList.size(); i++){
			this.tixList.get(i).setPurchased(true);
		}

		checkoutSuccess = true;
		return checkoutSuccess;

	}


	/**
	 * Helper method that recalculates the subtotal, tax amount, and total price of the cart's contents, modifying their respective variables with the new amounts. Can both add to and subtract from the subtotal, based on a boolean.
	 *
	 * @param amountChanged
	 * @param isAddition
	 */
	private void calculateTotal(double amountChanged, boolean isAddition){

		if(isAddition){
			this.subtotal += amountChanged;
		}
		else{
			this.subtotal -= amountChanged;
		}

		this.tax = this.subtotal * this.taxRate;
		this.total = this.subtotal + this.tax;

	}


	/**
	 * Private method used to write object fields to a transactions.csv file.
	 *
	 * @throws FileNotFoundException
	 */
	private void createRecord() throws FileNotFoundException
	{
		String record = "\"" + this.id + "\",\"" + this.tixList.size() + "\",\"" + String.format("%.2f", this.subtotal) + "\",\"" + String.format("%.2f", (this.subtotal*.07)) + "\",\"" + String.format("%.2f", this.total) + "\",\"" + Utility.convertEpochToDatestamp(this.date.getTime()) + "\",\"" + this.date.getTime() + "\"";

		FileOutputStream file_output_stream = new FileOutputStream(new File(this.DEFAULT_TRANSACTIONS_FILE), true);

		if(!this.checkDuplicateRecord()) {

			PrintStream output = new PrintStream(file_output_stream);

			output.println(record);

		} else {

			// todo - Does file_output_stream need to be closed in order to conserve resources?

		}

	}


	/**
	 * Helper method used to check for duplicate records in our "database" file.
	 * Uses cart id as unique key
	 *
	 * @return
	 * @throws FileNotFoundException
	 */
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
