import java.util.ArrayList;
import java.util.Scanner;


/**
 * DESCRIPTION
 *
 * @author Clarissa Dean
 * @version 1.0
 */
 
 /*
 NEEDED: setRegTixAvailability() function in Event Class - lines 60, 114
		 setVipTicketAvailability() function in Event Class - lines 80, 118
		 location of Ticket Prices for an event (hard coded those in for now) - lines 59, 79
		 getEvent() function in Ticket class, which returns Event object - lines 100, 114, 118
		 A tester who's halfway decent at Java and isn't nursing the worst cold of the season!!! 
 */

public class Cart{
	private ArrayList<Ticket> tixList;
	
    public Cart(){
        tixList = new ArrayList<Ticket>();
	}
        
    public ArrayList<Ticket> getTixList(){
        return tixList;
	}

    public void setTixList(ArrayList<Ticket> newTixList){
        this.tixList = newTixList;
	}

    public Boolean add(Ticket newTicket, Event ticketedEvent){
		Boolean addSuccess = False;
		Scanner console = new Scanner(System.in);
		int input = 2;
		int quantity = 0;
		
		System.out.println("Would you like to buy Regular or VIP tickets for " + ticketedEvent.getTitle() + "? (type 1 for Regular tickets, or 2 for VIP.");
		while (input != 1 && input != 2){
			input = console.nextInt();
			if (input != 1 && input != 2){
				System.out.println("Invalid entry. Please type 1 for Regular tickets, or 2 for VIP tickets.");
			}
		}
		
		if (input == 1){
			
			if ticketedEvent.getRegTixAvailability() == 0{
				System.out.println("There are no regular tickets left for " + ticketedEvent.getTitle() + ". Please try adding a ticket to the cart again.")
				return addSuccess;
			}
			
			do{System.out.print("How many Regular tickets would you like to buy? There are only " + ticketedEvent.getRegTixAvailability() + " regular tickets left for " + ticketedEvent.getTitle() + ".");
			int quantity = console.nextInt();
			}while(quantity <= ticketedEvent.getRegTixAvailability() && quantity > 0);
			
			for (x=0; x < quantity; x++){
				tixList.add(new Ticket(ticketedEvent.getEventID(), ticketedEvent.getSeatID(), 2.00, "Regular"));
				ticketedEvent.setRegTixAvailability((ticketedEvent.getRegTixAvailability()) - 1);
			}
			System.out.println(quantity + " Regular tickets added to cart.");     
			
			addSuccess = True;
			return addSuccess;
		}
		else{
			
			if ticketedEvent.getVipTicketAvailability() == 0{
				System.out.println("There are no VIP tickets left for " + ticketedEvent.getTitle() + ". Please try adding a ticket to the cart again.")
				return addSuccess;
			}
			
			do{System.out.print("How many VIP tickets would you like to buy? There are only " + ticketedEvent.getVipTicketAvailability() + " VIP tickets left for " + ticketedEvent.getTitle() + ".");
			quantity = console.nextInt();
			}while(quantity <= ticketedEvent.getVipTicketAvailability() && quantity > 0);
			
			for (x=0; x < quantity; x++){
				tixList.add(new Ticket(ticketedEvent.getEventID(), ticketedEvent.getSeatID(), 4.00, "VIP"));
				ticketedEvent.setVipTicketAvailability((ticketedEvent.getVipTicketAvailability()) - 1);
			}
			System.out.println(quantity + " VIP tickets added to cart.");     
			
			addSuccess = True;
			return addSuccess;
		}
	}

    public Boolean remove(int ticketIndex){
		Scanner console = new Scanner(System.in);
		Boolean removeSuccess = False;
		
		if (ticketIndex < 0 || ticketIndex >= tixList.length()){
			System.out.println("The selected ticket does not exist in the cart. Please try again.");
			return removeSuccess;
		}
		
		Ticket ticketRemoved = tixList.get(ticketIndex);
		System.out.print("How many " + ticketRemoved.getTier() + " tickets for " ticketRemoved.getEvent().getTitle() + " would you like to remove?: ");
        int quantity = console.nextInt();
		System.out.println("");
		while(quantity <= 0 || quantity > ticketRemoved.getQuantity()){
			System.out.println("Invalid number. Please enter a number between 0 and " + ticketRemoved.getQuantity() + ": ");
			quantity = console.nextInt();
			System.out.println("");
		}
		
		if (quantity == ticketRemoved.getQuantity()){
			tixList.remove(ticketIndex);
		}
		else{
			ticketRemoved.setQuantity(ticketRemoved.getQuantity() - quantity);
			if ticketRemoved.getTier().equals("Regular"){
				ticketRemoved.getEvent().setRegTixAvailability((ticketRemoved.getEvent().getRegTixAvailability) + quantity);
			}
			else{
				ticketRemoved.getEvent().setVipTicketAvailability((ticketRemoved.getEvent().getVipTicketAvailability) + quantity);
			}
			
			System.out.println (ticketRemoved.getQuantity()) + " " + ticketRemoved.getTier() + " tickets for " + ticketRemoved.getEvent() + " removed from cart.");
		}
		removeSuccess = True;
		return removeSuccess;
		}
		
                
    public String toString(){
		double subtotal = 0.0;
		double tax;
		double total;
        String cartString = "Tickets in Cart: \n"
        for (int i = 0; i < tixList.length(); i++){
            cartString += (tixList.get(i).getQuantity() + " " + tixList.get(i).getTier() + " tickets for " + tixList.get(i).getEvent() + "\n");
			subtotal += (tixList.get(i).getPrice() * tixList.get(i).getQuantity());
		}
		tax = subtotal * .07;
		total = subtotal + tax;
        cartString += ((20 * "-") + "\nSubtotal: $" + String.format("%.2f", subtotal)) + 
					  ("\n7% Tax: $" + String.format("%.2f", tax)) + 
					  ("\nTotal: $" + String.format("%.2f", total));
        return cartString;
	}
		

    public Boolean checkout(){
		Boolean checkoutSuccess = False;
		Scanner console = new Scanner(System.in);
		System.out.println(this.toString());
		
		System.out.print("Would you like to complete your checkout? (enter \"Y\" for yes or \"N\" for no.): ");
        String checkoutConfirm = console.next().toUpper();
		System.out.println("");
		
        while (checkoutConfirm != "Y" && checkoutConfirm != "N"):\{
            print("Please enter \"Y\" for yes, or \"N\" for no.")
            checkoutConfirm = console.next().toUpper();
		}
        if checkoutConfirm.equals("Y"){
            System.out.println("Order confirmed. Thank you for shopping with us!");
            checkoutSuccess = True;
			return checkoutSuccess;
		}
        else{
            System.out.println("Order canceled. Checkout again to confirm your order.");
            return checkoutSuccess;
		}
	}
            
}