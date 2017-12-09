import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Application used to reserve and purchase tickets for events
 *
 * @author Daniel Paz
 * @author Scott Chaplinksi
 * @author Clarissa Dean
 */
public class app{

    final int WINDOW_WIDTH = 800;
    final int WINDOW_HEIGHT = 600;
    private JFrame window = new JFrame("Ticketmaster2.0: Now with more tickets");
    private JPanel containerPanel = new JPanel();
    private CardLayout cards  = new CardLayout();
    private String focusedEventID = null;
    private Cart shoppingCart;

    /**
     * Main Method
     *
     * @param args
     */
    public static void main(String[] args)
    {

        new app();

    }

    /**
     * Constructor used to deploy App
     */
    public app()
    {

        // Reclaim Abandonned Tickets
        try {
            // Cleanup all abandonned Tickets
            Ticket.cleanupDeadTickets();
        } catch (IOException err) {
            System.out.print(err);
        }


        // Instantiate a new Cart object
        this.shoppingCart = new Cart();

        // Set the Layout for the window Frame
        containerPanel.setLayout(cards);

        // Add the Panel Pages to the containerPanel
        containerPanel.add(buildHomeWrappingPanel(),"1"); // Home Page
        containerPanel.add(buildEventDetailWrappingPanel(),"2"); // Default Event Details Page, Will never be used, can possibly delete
        containerPanel.add(buildCartWrappingPanel(),"3"); // Cart Overview Page
        containerPanel.add(buildCheckoutWrappingPanel(),"4"); // Checkout Overview Page

        // Set which Panel displays First on load
        cards.show(containerPanel, "1");

        // Configure the Frame
        window.add(containerPanel);

        // Set the size of the homeFrame
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // Specify on Close
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // todo - Pack does what?
        //window.pack();
        // Set Relative location for window on launch
        window.setLocationRelativeTo(null);

        // Show the window
        window.setVisible(true);

    }

    /*

        Home Card/Page/Panel

     */

    /**
     * Private method used to compile the Home Page
     *
     * @return
     */
    private JPanel buildHomeWrappingPanel()
    {

        // Create a Wrapper JPanel that will hold all of the inner contents
        JPanel wrapper = new JPanel();
        // Set wrapper LayoutManger to BorderLayout
        wrapper.setLayout(new BorderLayout());
        // Set wrapper Sizing
        wrapper.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        wrapper.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        // Set wrapper aesthetics
        wrapper.setBackground(Color.GREEN);

        // Add nested panels to wrapper
        wrapper.add(buildHomeSpacerPanel(), BorderLayout.NORTH);
        wrapper.add(buildHomeEventDetailPanel(), BorderLayout.CENTER);
        wrapper.add(buildHomeSpacerPanel(), BorderLayout.SOUTH);

        return wrapper;

    }

    /**
     * Private method used compile the Central component for the Home Page
     *
     * @return
     */
    private JPanel buildHomeEventDetailPanel() // Event List Panel + Ads/Social Media/Cart Panel
    {

        int panelWidth = 700;
        int panelHeight = 500;

        // Wrapping Panel
        JPanel homeDetailPanel = new JPanel();
        homeDetailPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        homeDetailPanel.add(buildHomeLeftPanel());
        homeDetailPanel.add(buildHomeRightPanel()); // Builds the Ads/Social Media/Cart Panel

        return homeDetailPanel;

    }

    /**
     * Private method returning the contents for left panel on the Home Page
     *
     * @return
     */
    private JPanel buildHomeLeftPanel() // Event List Panel
    {
        int panelWidth = 350;
        int panelHeight = 500;

        // Panel for holding a list of events
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Add a JLabel to panel
        JLabel label = new JLabel("Click on an event for more Information");
        label.setPreferredSize(new Dimension(panelWidth, 25));
        panel.add(label);

        // new JScrollPane
        JScrollPane eventListScrollPane = new JScrollPane();
        eventListScrollPane.setPreferredSize(new Dimension(panelWidth, (panelHeight - 25)));

        try {

            // new JList
            JList eventList = new JList(Event.eventsList().toArray());
            ListCellRenderer eventListRenderer = new eventListCellRenderer();
            eventList.setPreferredSize(new Dimension(panelWidth, (panelHeight - 25)));

            // Add Renderer to seatList
            eventList.setCellRenderer(eventListRenderer);

            // Add Event Listener
            eventList.addListSelectionListener(new eventListListener());

            // Add the VieportView to the JList
            eventListScrollPane.setViewportView(eventList);

            // Add to the Panel
            panel.add(eventListScrollPane);

        } catch (FileNotFoundException err){


        }

        return panel;

    }

    /**
     * Private method returning the contents for right panel on the Home Page
     *
     * @return
     */
    private JPanel buildHomeRightPanel() // Ads/Social Media/Cart Panel
    {
        int panelWidth = 350;
        int panelHeight = 500;

        // Panel for holding Ads and cart
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Add the CartButton JButton
        panel.add(buildAdPanel());
        panel.add(buildMiniCartViewPanel());

        return panel;

    }

    /**
     * Private method returning a JPanel that for Ads
     *
     * @return
     */
    private JPanel buildAdPanel()
    {

        int panelWidth = 350;
        int panelHeight = 200;

        //new JPanel, dimensions, and color
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        panel.setBackground(Color.BLUE);

        return panel;

    }

    /**
     * Private method that returns a Jpanel containing a overview of the Cart
     *
     * @return
     */
    private JPanel buildMiniCartViewPanel()
    {

        int panelWidth = 350;
        int panelHeight = 300;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        //panel.setBackground(Color.DARK_GRAY);

        // Build a Panel to hold details about the current Cart
        // Create Textarea with Event details
        String totalTextAreaString =  "Ticket QTY: " +  this.shoppingCart.getTixQuantity() + "\n\n";
        totalTextAreaString +=  "Subtotal: $" +  String.format("%.2f", this.shoppingCart.getSubtotal()) + "\n";
        totalTextAreaString +=  "Tax: $" +  String.format("%.2f", this.shoppingCart.getTax()) + "\n";
        totalTextAreaString +=  "Total: $" +  String.format("%.2f", this.shoppingCart.getTotal()) + "\n";

        // Compile the JTextarea
        JTextArea totalTextArea = new JTextArea(totalTextAreaString);
        totalTextArea.setPreferredSize(new Dimension(panelWidth, 200));
        totalTextArea.setMinimumSize(new Dimension(panelWidth, 200));
        totalTextArea.setLineWrap(true);
        totalTextArea.setWrapStyleWord(true);
        totalTextArea.setOpaque(false);
        totalTextArea.setEditable(false);

        // Build a Panel to hold 2 buttons
        JPanel twoButtons = new JPanel();
        twoButtons.setPreferredSize(new Dimension(panelWidth, 100));
        twoButtons.setMinimumSize(new Dimension(panelWidth, 100));

        // Add the Buttons
        twoButtons.add(buildViewCartButton());
        twoButtons.add(buildCheckoutButton());

        //Add totalTextArea, twoButtons to panel
        panel.add(totalTextArea);
        panel.add(twoButtons);

        return panel;
    }


    /**
     * Private method used as a blank spacer used on the Home Page
     *
     * @return
     */
    private JPanel buildHomeSpacerPanel()
    {

        int panelWidth = 800;
        int panelHeight = 50;

        // Wrapping Panel
        JPanel homeSpacerlPanel = new JPanel();
        homeSpacerlPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        return homeSpacerlPanel;

    }

    /*

        EventDetails Card/Page/Panel

     */

    /**
     * Private method used to compile an Event Detail Page
     *
     * @return
     */
    private JPanel buildEventDetailWrappingPanel()
    {

        // Create a Wrapper JPanel that will hold all of the inner contents
        JPanel wrapper = new JPanel();

        // Set wrapper LayoutManger to BorderLayout
        wrapper.setLayout(new BorderLayout());

        // Set wrapper Sizing
        wrapper.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        wrapper.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        // Set wrapper aesthetics
        wrapper.setBackground(Color.YELLOW);

        // Check if focusedEventID has been defined or if it is still null
        if(focusedEventID != null) {

            // Get the data for the Event
            Event localEvent = new Event(focusedEventID, null);

            // Add nested panels to wrapper
            wrapper.add(buildEventDetailPanel(localEvent), BorderLayout.NORTH); // Event/Venue Details Panel
            wrapper.add(buildEventSeatsDetailPanel(localEvent), BorderLayout.CENTER); // Available Seats Panel
            wrapper.add(buildStandardButtonPanel(), BorderLayout.SOUTH); // Buttons Panel

        } else {

            System.out.println("EventID is not defined");

        }

        return wrapper;

    }

    /**
     * Private method used to compile the Event and Venue details (NORTH region) contents for an Event Details Page
     *
     * @param focusedEvent
     * @return
     */
    private JPanel buildEventDetailPanel(Event focusedEvent)
    {

        int panelWidth = 700;
        int panelHeight = 150;

        // Create Textarea with Event details
        String eventDetailsTextAreaString =  "Event:\n\n";
        eventDetailsTextAreaString +=  focusedEvent.getTitle() + "\n";
        eventDetailsTextAreaString +=  focusedEvent.getArtistName() + "\n";
        eventDetailsTextAreaString +=  focusedEvent.getDatetime() + "\n";

        // Compile the JTextarea
        JTextArea eventDetailsTextArea = new JTextArea(eventDetailsTextAreaString);
        eventDetailsTextArea.setPreferredSize(new Dimension((panelWidth/2), panelHeight));
        eventDetailsTextArea.setMaximumSize(new Dimension((panelWidth/2), panelHeight));
        eventDetailsTextArea.setLineWrap(true);
        eventDetailsTextArea.setWrapStyleWord(true);
        eventDetailsTextArea.setOpaque(false);
        eventDetailsTextArea.setEditable(false);

        // Create Textarea with Venue Details
        String venueDetailsTextAreaString =  "Location:\n\n";
        venueDetailsTextAreaString +=  focusedEvent.getVenue().getName() + "\n";
        venueDetailsTextAreaString +=  focusedEvent.getVenue().toString();

        // Compile the JTextarea
        JTextArea venueDetailsTextArea = new JTextArea(venueDetailsTextAreaString);
        venueDetailsTextArea.setPreferredSize(new Dimension((panelWidth/2), panelHeight));
        venueDetailsTextArea.setMaximumSize(new Dimension((panelWidth/2), panelHeight));
        venueDetailsTextArea.setLineWrap(true);
        venueDetailsTextArea.setWrapStyleWord(true);
        venueDetailsTextArea.setOpaque(false);
        venueDetailsTextArea.setEditable(false);

        //new JPanel
        JPanel eventDetailPanel = new JPanel();
        eventDetailPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Add eventDetailsTextArea, venueDetailsTextArea to eventDetailPanel
        eventDetailPanel.add(eventDetailsTextArea);
        eventDetailPanel.add(venueDetailsTextArea);

        return eventDetailPanel;

    }

    /**
     * Private method used to compile the Seat list (CENTER region) contents for an Event Details Page
     *
     * @param focusedEvent
     * @return
     */
    private JPanel buildEventSeatsDetailPanel(Event focusedEvent)
    {

        int panelWidth = 700;
        int panelHeight = 350;

        // Preceding Label
        JLabel seatInstruction = new JLabel("Click to add Seats to Cart");
        seatInstruction.setPreferredSize(new Dimension(panelWidth, 25));

        // new Panel what will Hold a list of Seats
        JPanel seatPanelList = new JPanel();
        seatPanelList.setPreferredSize(new Dimension(panelWidth, (panelHeight - 25)));
        seatPanelList.setBackground(Color.ORANGE);

        // new JScrollPane
        JScrollPane seatListScrollPane = new JScrollPane();
        seatListScrollPane.setPreferredSize(new Dimension(panelWidth, (panelHeight - 25)));

        // Refresh the seatsSold ArrayList
        focusedEvent.seatAvailabilityData();

        System.out.println(focusedEvent.getSeatsSold().toString());

        // new JList
        JList seatList = new JList(focusedEvent.getSeatsAvailable().toArray());
        ListCellRenderer seatRenderer = new focusedSeatCellRenderer();
        seatList.setPreferredSize(new Dimension(panelWidth, (panelHeight - 25)));

        // Add Renderer to seatList
        seatList.setCellRenderer(seatRenderer);

        // Add Event Listener
        seatList.addListSelectionListener(new seatListListener());

        // Add the VieportView to the JList
        seatListScrollPane.setViewportView(seatList);

        // Add to the Panel
        seatPanelList.add(seatListScrollPane);


        // New Panel that holds all of the contents before this
        JPanel eventDetailstSeatPanel = new JPanel();
        eventDetailstSeatPanel.add(seatInstruction);
        eventDetailstSeatPanel.add(seatPanelList);
        //eventSeatDetailPanel.add(venueDetailsTextArea);

        return eventDetailstSeatPanel;

    }

    /*

        Cart Card/Page/Panel

     */

    private JPanel buildCartWrappingPanel()
    {

        // Create a Wrapper JPanel that will hold all of the inner contents
        JPanel wrapper = new JPanel();
        // Set wrapper LayoutManger to BorderLayout
        wrapper.setLayout(new BorderLayout());
        // Set wrapper Sizing
        wrapper.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        wrapper.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        // Set wrapper aesthetics
        wrapper.setBackground(Color.GREEN);

        // Add nested panels to wrapper
        wrapper.add(buildHomeSpacerPanel(), BorderLayout.NORTH);
        wrapper.add(buildCartDetailPanel(), BorderLayout.CENTER);
        wrapper.add(buildHomeSpacerPanel(), BorderLayout.SOUTH);

        return wrapper;

    }

    private JPanel buildCartDetailPanel()
    {
        int panelWidth = 700;
        int panelHeight = 500;

        //Panel for holding a list of events
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        panel.setMinimumSize(new Dimension(panelWidth, panelHeight));

        //Create JLabel for Cart Label
        JLabel cartWord = new JLabel("Cart - Click on Ticket list item to remove from your Cart");
        cartWord.setPreferredSize(new Dimension(panelWidth, 25));
        cartWord.setMinimumSize(new Dimension(panelWidth, 25));

        //new JScrollPane
        JScrollPane cartListScrollPane = new JScrollPane();
        cartListScrollPane.setPreferredSize(new Dimension(panelWidth, (panelHeight-50)));
        cartListScrollPane.setMinimumSize(new Dimension(panelWidth, (panelHeight-50)));

        //new JList
        JList cartList = new JList(shoppingCart.getTixList().toArray());
        ListCellRenderer cartListRenderer = new cartListCellRenderer();
        cartList.setPreferredSize(new Dimension(panelWidth, (panelHeight-25)));
        cartList.setMinimumSize(new Dimension(panelWidth, (panelHeight-25)));

        //set defined renderer to cart
        cartList.setCellRenderer(cartListRenderer);

        //add event listener
        cartList.addListSelectionListener(new cartListListener());

        //add to the viewport
        cartListScrollPane.setViewportView(cartList);

        //new JPanel
        JPanel quantityPanel = new JPanel();
        quantityPanel.setPreferredSize(new Dimension(panelWidth, 50));
        quantityPanel.setMinimumSize(new Dimension(panelWidth, 50));

        JLabel quantityLabel = new JLabel("Ticket QTY: " + shoppingCart.getTixQuantity());
        quantityLabel.setPreferredSize(new Dimension(((panelWidth /2) - 5), 25));
        quantityLabel.setMinimumSize(new Dimension(((panelWidth /2) - 5), 25));

        // Create Textarea with Event details
        String totalTextAreaString =  "Subtotal: $" +  String.format("%.2f", shoppingCart.getSubtotal()) + "\n";
        totalTextAreaString +=  "Tax: $" +  String.format("%.2f", shoppingCart.getTax()) + "\n";
        totalTextAreaString +=  "Total: $" +  String.format("%.2f", shoppingCart.getTotal()) + "\n";

        // Compile the JTextarea
        JTextArea totalTextArea = new JTextArea(totalTextAreaString);
        totalTextArea.setPreferredSize(new Dimension(((panelWidth /2) - 5), 50));
        totalTextArea.setMinimumSize(new Dimension(((panelWidth /2) - 5), 50));
        totalTextArea.setLineWrap(true);
        totalTextArea.setWrapStyleWord(true);
        totalTextArea.setOpaque(false);
        totalTextArea.setEditable(false);

        //Add quantityLabel, totalTextArea to quantityPanel
        quantityPanel.add(quantityLabel);
        quantityPanel.add(totalTextArea);

        //Add cartWord, cartListScrollPane, quantityPanel, buildStandardButtonPanel, to panel
        panel.add(cartWord);
        panel.add(cartListScrollPane);
        panel.add(quantityPanel);
        panel.add(buildStandardButtonPanel());

        return panel;

    }

    /*

        Checkout Card/Page/Panel

     */

    private JPanel buildCheckoutWrappingPanel()
    {

        // Create a Wrapper JPanel that will hold all of the inner contents
        JPanel wrapper = new JPanel();
        // Set wrapper LayoutManger to BorderLayout
        wrapper.setLayout(new BorderLayout());
        // Set wrapper Sizing
        wrapper.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        wrapper.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        // Set wrapper aesthetics
        wrapper.setBackground(Color.GREEN);

        // Add nested panels to wrapper
        wrapper.add(buildHomeSpacerPanel(), BorderLayout.NORTH);
        wrapper.add(buildCheckoutDetailPanel(), BorderLayout.CENTER);
        wrapper.add(buildHomeSpacerPanel(), BorderLayout.SOUTH);

        return wrapper;

    }

    private JPanel buildCheckoutDetailPanel()
    {
        int panelWidth = 700;
        int panelHeight = 500;

        //Panel for holding a list of events
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        panel.setMinimumSize(new Dimension(panelWidth, panelHeight));

        //Create JLabel for Cart Label
        JLabel cartWord = new JLabel("Checkout");
        cartWord.setPreferredSize(new Dimension(panelWidth, 25));
        cartWord.setMinimumSize(new Dimension(panelWidth, 25));

        //new JScrollPane
        JScrollPane cartListScrollPane = new JScrollPane();
        cartListScrollPane.setPreferredSize(new Dimension(panelWidth, (panelHeight-50)));
        cartListScrollPane.setMinimumSize(new Dimension(panelWidth, (panelHeight-50)));


        JList cartList = new JList(shoppingCart.getTixList().toArray());
        ListCellRenderer cartListRenderer = new cartListCellRenderer();
        cartList.setPreferredSize(new Dimension(panelWidth, (panelHeight-25)));
        cartList.setMinimumSize(new Dimension(panelWidth, (panelHeight-25)));

        //set defined renderer to cart
        cartList.setCellRenderer(cartListRenderer);

        //add event listener
        cartList.addListSelectionListener(new cartListListener());

        //add to the viewport
        cartListScrollPane.setViewportView(cartList);

        //new JPanel
        JPanel quantityPanel = new JPanel();
        quantityPanel.setPreferredSize(new Dimension(panelWidth, 50));
        quantityPanel.setMinimumSize(new Dimension(panelWidth, 50));

        JLabel quantityLabel = new JLabel("Ticket QTY: " + shoppingCart.getTixQuantity());
        quantityLabel.setPreferredSize(new Dimension(((panelWidth /2) - 5), 25));
        quantityLabel.setMinimumSize(new Dimension(((panelWidth /2) - 5), 25));

        // Create Textarea with Event details
        String totalTextAreaString =  "Subtotal: $" +  String.format("%.2f", shoppingCart.getSubtotal()) + "\n";
        totalTextAreaString +=  "Tax: $" +  String.format("%.2f", shoppingCart.getTax()) + "\n";
        totalTextAreaString +=  "Total: $" +  String.format("%.2f", shoppingCart.getTotal()) + "\n";

        // Compile the JTextarea
        JTextArea totalTextArea = new JTextArea(totalTextAreaString);
        totalTextArea.setPreferredSize(new Dimension(((panelWidth /2) - 5), 50));
        totalTextArea.setMinimumSize(new Dimension(((panelWidth /2) - 5), 50));
        totalTextArea.setLineWrap(true);
        totalTextArea.setWrapStyleWord(true);
        totalTextArea.setOpaque(false);
        totalTextArea.setEditable(false);

        //new JLabel
        JLabel thanksLabel = new JLabel("Thank you for using Ticketmaster 2.0: Optimized");
        thanksLabel.setPreferredSize(new Dimension((panelWidth), 25));
        thanksLabel.setMinimumSize(new Dimension((panelWidth), 25));

        //new JPanel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(panelWidth, 100));
        buttonPanel.setMinimumSize(new Dimension(panelWidth, 100));

        //Add buildCloseButton to buttonPanel
        buttonPanel.add(buildCloseButton());

        //Add quantityLabel, totalTextArea, thanksLabel to quantityPanel
        quantityPanel.add(quantityLabel);
        quantityPanel.add(totalTextArea);
        quantityPanel.add(thanksLabel);

        //Add cartWord, cartListScrollPane, quantityPanel, buttonPanel to panel
        panel.add(cartWord);
        panel.add(cartListScrollPane);
        panel.add(quantityPanel);
        panel.add(buttonPanel);


        return panel;

    }

    /*

        General Panels

     */

    /**
     * Private method used to compile the Buttons (SOUTH region) contents for an Event Details Page
     *
     * @return
     */
    private JPanel buildStandardButtonPanel()
    {

        int panelWidth = 700;
        int panelHeight = 100;

        // New Panel that holds all of the contents before this
        JPanel eventDetailsButtonPanel = new JPanel();
        eventDetailsButtonPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        eventDetailsButtonPanel.setMinimumSize(new Dimension(panelWidth, panelHeight));

        // Add the Buttons
        eventDetailsButtonPanel.add(buildViewGoHomeButton()); // Go Home Button
        eventDetailsButtonPanel.add(buildViewCartButton()); // View Cart button
        eventDetailsButtonPanel.add(buildCheckoutButton()); // Checkout Button

        return eventDetailsButtonPanel;

    }

    /*

        Buttons

     */

    /**
     * Private method that returns a JButton for a view cart button
     * @return
     */
    private JButton buildViewCartButton()
    {

        //View Cart Button
        JButton cartButton = new JButton("View Cart");
        cartButton.addActionListener(new cartViewListener());

        return cartButton;

    }

    /**
     * Private method that returns a JButton for a go home button
     * @return
     */
    private JButton buildViewGoHomeButton()
    {
        // Go Home Button
        JButton goHomeBTN = new JButton("Go Home");
        goHomeBTN.addActionListener(new goHomeListener());

        return goHomeBTN;

    }

    /**
     * Private method that returns a JButton for a checkout button
     * @return
     */
    private JButton buildCheckoutButton()
    {
        // Go Home Button
        JButton checkoutBTN = new JButton("Checkout");
        checkoutBTN.addActionListener(new checkoutListener());

        return checkoutBTN;

    }

    /**
     * Private method that returns a JButton for a close program button
     * @return
     */
    private JButton buildCloseButton()
    {
        // Go Home Button
        JButton closeBTN = new JButton("Close");
        closeBTN.addActionListener(new closeProgramListener());

        return closeBTN;

    }

    /*

        Listeners

     */

    /**
     * ListCellRender for focusedSeatCellRenderer, which allows the use of objects within the list,
     * but displays a human readable list item
     */
    private class focusedSeatCellRenderer implements ListCellRenderer {

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){

            // Get the Object value and cast it as a Seat
            Seat thisSeat = (Seat) value;

            // Label for the Renderer
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, "Row " + (thisSeat.getRowNumber() + 1) + ", Seat " + (thisSeat.getSeatNumber() + 1) + " (ID: " + thisSeat.getId() + ")", index, isSelected, cellHasFocus);
            //renderer.setBorder(cellHasFocus ? focusBorder : noFocusBorder);
            return renderer;
        }

    }

    /**
     * ListCellRender for eventListCellRenderer, which allows the use of objects within the list,
     * but displays a human readable list item
     */
    private class eventListCellRenderer implements ListCellRenderer {

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){

            // Get the Object value and cast it as a Seat
            Event thisEvent = (Event) value;

            // Label for the Renderer
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list,  thisEvent.getTitle() + " - " + thisEvent.getDatetime() + " (ID: " + thisEvent.getId() + ")", index, isSelected, cellHasFocus);

            //renderer.setBorder(cellHasFocus ? focusBorder : noFocusBorder);
            return renderer;
        }

    }

    /**
     * ListCellRender cartListCellRenderer, which allows the use of objects within the list,
     * but displays a human readable list item
     */
    private class cartListCellRenderer implements ListCellRenderer{

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){

            // Get the Object value and cast it as a Seat
            Ticket thisTicket = (Ticket) value;

            // Get the Event details for this Ticket
            Event thisTicketEvent = new Event(thisTicket.getEventID(), null);

            Seat thisSeat = null;

            // Get the Seat Details for this Ticket
            for(Seat seat : thisTicketEvent.getSeats()){

                if(thisSeat != null){

                    break;

                }

                if(seat.getId().equals(thisTicket.getSeatID())){

                    thisSeat = seat;
                    continue;

                }

            }

            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, "Ticket: " + thisTicket.getId() + " (" +  thisTicket.getTier() + ") - " + thisTicketEvent.getTitle() + " - Row " + (thisSeat.getRowNumber() + 1) + ", Seat " + (thisSeat.getSeatNumber() + 1), index, isSelected, cellHasFocus);

            return renderer;
        }
    }

    /**
     * ListSelectionListener for lists invoking the seatListListener
     */
    private class seatListListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e)
        {
            if (!e.getValueIsAdjusting()){
                JList source = (JList) e.getSource();
                Seat selected = (Seat) source.getSelectedValue();

                // Create a new Panel for the Dialog
                JPanel panel = new JPanel();
                panel.add(new JLabel("Would you like to add the VIP Package to your ticket?"));

                // The Options for the Option dialog
                Object[] panelButtons = {"No", "Yes"};

                //JOptionPane.showMessageDialog(null, selected);
                int vipPackage = JOptionPane.showOptionDialog(
                        null,
                        panel,
                        "VIP Package - Row: " + (selected.getRowNumber() + 1) + " Seat: " + (selected.getSeatNumber() + 1) ,
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        panelButtons,
                        null
                );

                if(vipPackage != -1) { // Check to see if the user pressed the ESCAPE key
                    // Get an instance of focusedEventID Event for the Pricing Fields
                    Event localEvent = new Event(focusedEventID, null);

                    // Now that we know the Ticket Type, we can compile a Ticket and add it to the Cart
                    shoppingCart.add(new Ticket(focusedEventID, shoppingCart.getId(), selected.getId(), vipPackage == 1 ? localEvent.getVipTicketPrice() : localEvent.getBaseTicketPrice(), vipPackage == 1 ? "vip" : "regular"));

                    // Gives Feedback to the user that the ticket was added to the cart
                    JOptionPane.showMessageDialog(null, "Successfully added ticket for Row: " + (selected.getRowNumber() + 1) + " Seat: " + (selected.getSeatNumber() + 1));

                }

                // Refresh the page
                // Add a new card to the containerPanel for the focusedEvent, use the focusedEvent value as the constraint
                containerPanel.add(buildEventDetailWrappingPanel(), focusedEventID);

                // Now show the card that you just added, an event detail card for focusedEventID
                cards.show(containerPanel, focusedEventID);

            }

        }

    }

    /**
     * ListSelectionListener for lists invoking the eventListListener
     */
    private class eventListListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e)
        {

            // Check if the User is changing selection
            if (!e.getValueIsAdjusting()){

                // Get the source from e, cast it as a JList
                JList source = (JList) e.getSource();

                // Get the source and cast it as an Event (since we know we are working objects)
                Event selected = (Event) source.getSelectedValue();

                // Set the focusedEventID property to the selected.getID value
                focusedEventID = selected.getId();

                // Add a new card to the containerPanel for the focusedEvent, use the focusedEvent value as the constraint
                containerPanel.add(buildEventDetailWrappingPanel(), focusedEventID);

                // Now show the card that you just added, an event detail card for focusedEventID
                cards.show(containerPanel, focusedEventID);
            }

        }

    }


    /**
     * ListSelectionListener for lists invoking the cartListListener
     */
    private class cartListListener implements ListSelectionListener{

        public void valueChanged(ListSelectionEvent e){

            if (!e.getValueIsAdjusting()){

                //new JPanel
                JPanel panel = new JPanel();
                panel.add(new JLabel("Would you like to remove this item from your cart?"));

                //Instantiate panelButtons
                Object[] panelButtons = {"No", "Yes"};

                //Instantiate removeCartItem
                int removeCartItem = JOptionPane.showOptionDialog(
                        null,
                        panel,
                        "Remove ticket",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        panelButtons,
                        null
                );

                if(removeCartItem == 1) {

                    JList source = (JList) e.getSource();
                    Ticket selectedTicket = (Ticket) source.getSelectedValue();

                    // Remove Ticket from Cart
                    shoppingCart.remove(selectedTicket.getId());

                    try {
                        // Remove Ticket from File
                        Ticket.removeTicketFromFile(selectedTicket.getId());
                    } catch (IOException err){

                        System.out.println(err);

                    }

                    // Add the card, effectively replacing it
                    containerPanel.add(buildHomeWrappingPanel(), "1");

                    // Switch Cards back to Home
                    cards.show(containerPanel, "1");


                } else if(removeCartItem < 0) {

                    // Do Nothing

                } else {

                    // Do Nothing

                }

            }

        }
    }

    /**
     * ActionListener for buttons invoking the goHomeListener
     */
    private class goHomeListener implements ActionListener {

        public void actionPerformed(ActionEvent e)
        {

            // Add the card, effectively replacing it
            containerPanel.add(buildHomeWrappingPanel(), "1");

            // Switch Cards back to Home
            cards.show(containerPanel, "1");

        }

    }

    /**
     * ActionListener for View Cart Button implementing cartViewListener
     */
    private class cartViewListener implements ActionListener {

        public void actionPerformed(ActionEvent e)
        {

            // Add the card, effectively replacing it
            containerPanel.add(buildCartDetailPanel(), "3");

            // Switch Cards back to Home
            cards.show(containerPanel, "3");

        }

    }

    /**
     * ActionLIstener for Checkout Button implementing checkoutListener
     */
    private class checkoutListener implements ActionListener {

        public void actionPerformed(ActionEvent e)
        {

            // Notify the User that Checkingout will finalize their order
            // Create a new Panel for the Dialog
            /*JPanel panel = new JPanel();
            panel.add(new JLabel("Clicking on Checkout will finalize your Ticket Order. You will not be able to add any more tickets. Would you like to continue or Keep shopping?"));

            // The Options for the Option dialog
            Object[] panelButtons = {"Keep Shopping", "Continue to Checkout"};

            //JOptionPane.showMessageDialog(null, selected);
            int checkout = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Checkout" ,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    panelButtons,
                    null
            );*/

            //if(checkout == 1) {


                if (shoppingCart.getTixQuantity() > 0) {

                    // Gives Feedback to the user that the ticket was added to the cart
                    JOptionPane.showMessageDialog(null, "Success! You have been successfully checked-out. Save the details of your order for your records. Thank you!");

                    // Checkout the Cart into a file
                    shoppingCart.checkout();

                } else {

                    // Gives Feedback to the user that the ticket was added to the cart
                    JOptionPane.showMessageDialog(null, "Your cart was empty. Nothing To Checkout.");

                }

                try {
                    // Cleanup all abandonned Tickets
                    Ticket.cleanupDeadTickets();
                } catch (IOException err) {
                    System.out.print(err);
                }

                // Add the card, effectively replacing it
                containerPanel.add(buildCheckoutDetailPanel(), "4");

                // Switch Cards back to Home
                cards.show(containerPanel, "4");


            //} else if( checkout < 1) {
                // User hit escape or No button
                //Do Nothing

            //}

        }

    }

    /**
     * ActionListener for buttons invoking the closeProgramListener
     */
    private class closeProgramListener implements ActionListener{
        public void actionPerformed(ActionEvent e){

            window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));

        }
    }

}
