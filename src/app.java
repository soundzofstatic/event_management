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
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Application used to reserve and purchase tickets for events
 *
 * @author Daniel Paz
 */
public class app{

    final int WINDOW_WIDTH = 800;
    final int WINDOW_HEIGHT = 600;
    private JFrame window = new JFrame("Ticketmaster2.0: Now with more tickets");
    private JPanel containerPanel = new JPanel();
    private JPanel pageCheckoutPanel = new JPanel(); // todo - delete after build method is created for this
    private CardLayout cards  = new CardLayout();
    private String focusedEventID = null;

    //Cart shoppingCart;
    private ArrayList<Ticket> fauxShoppingCart = new ArrayList<Ticket>();

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

        // Set the Layout for the window Frame
        containerPanel.setLayout(cards);

        // Add the Panel Pages to the containerPanel
        containerPanel.add(buildHomeWrappingPanel(),"1"); // Home Page
        containerPanel.add(buildEventDetailWrappingPanel(),"2"); // Default Event Details Page, Will never be used, can possibly delete
        containerPanel.add(buildCartWrappingPanel(),"3"); // Cart Overview Page
        containerPanel.add(pageCheckoutPanel,"4"); // Checkout Overview Page

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
        panel.setBackground(Color.ORANGE);

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
        panel.setBackground(Color.BLUE);

        JButton cartButton = new JButton("See Cart");
        cartButton.addActionListener(new cartViewListener());

        panel.add(cartButton);

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
            wrapper.add(buildEventDetailButtonPanel(), BorderLayout.SOUTH); // Buttons Panel

        } else {

            // todo - call on an error screen

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

        //
        JPanel eventDetailPanel = new JPanel();
        eventDetailPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Add it to this local panel
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

    /**
     * Private method used to compile the Buttons (SOUTH region) contents for an Event Details Page
     *
     * @return
     */
    private JPanel buildEventDetailButtonPanel()
    {

        int panelWidth = 700;
        int panelHeight = 100;

        // Go Home Button
        JButton goHomeBTN = new JButton("Go Home");
        goHomeBTN.addActionListener(new goHomeListener());

        // Checkout Button
        JButton checkoutBTN = new JButton("Checkout");
        // todo - need listener for checkoutBTN

        // New Panel that holds all of the contents before this
        JPanel eventDetailsButtonPanel = new JPanel();
        eventDetailsButtonPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        eventDetailsButtonPanel.setMinimumSize(new Dimension(panelWidth, panelHeight));

        // Add the Buttons
        eventDetailsButtonPanel.add(goHomeBTN);
        eventDetailsButtonPanel.add(checkoutBTN);

        return eventDetailsButtonPanel;

    }


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

    private JPanel buildCartDetailPanel() {
        int panelWidth = 700;
        int panelHeight = 500;

        //Panel for holding a list of events
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        panel.setMinimumSize(new Dimension(panelWidth, panelHeight));
        panel.setBackground(Color.BLUE);

        //Create JLabel for Cart Label
        JLabel cartWord = new JLabel("Cart");
        cartWord.setPreferredSize(new Dimension(panelWidth, 25));
        cartWord.setMinimumSize(new Dimension(panelWidth, 25));

        //new JScrollPane
        JScrollPane cartListScrollPane = new JScrollPane();
        cartListScrollPane.setPreferredSize(new Dimension(panelWidth, (panelHeight-25)));
        cartListScrollPane.setMinimumSize(new Dimension(panelWidth, (panelHeight-25)));


        ArrayList<String> cart = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            cart.add("dummyString");
        }


        //new JList
        JList cartList = new JList(cart.toArray());
        ListCellRenderer cartListRenderer = new cartListCellRenderer();
        cartList.setPreferredSize(new Dimension(panelWidth, (panelHeight-25)));
        cartList.setMinimumSize(new Dimension(panelWidth, (panelHeight-25)));

        //set defined renderer to cart
        cartList.setCellRenderer(cartListRenderer);

        //add event listener
        cartList.addListSelectionListener(new cartListListener());

        //add to the viewport
        cartListScrollPane.setViewportView(cartList);

        panel.add(cartListScrollPane);

        return panel;

    }
    /*

        Listeners

     */

    /**
     * ListCellRender for focusedSeatCellRenderer, which allows the use of objects within the list,
     * but displays a human readable list item
     */
    private class focusedSeatCellRenderer implements ListCellRenderer {

        // todo - Detemerine if this FocustBorder need to stay
        protected Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);

        protected TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder(), "title");

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

        // todo - Detemerine if this FocustBorder need to stay
        protected Border noFocusBorder = new EmptyBorder(15, 1, 1, 1);

        protected TitledBorder focusBorder = new TitledBorder(LineBorder.createGrayLineBorder(), "title");

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

    private class cartListCellRenderer implements ListCellRenderer{

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){


            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, "test", index, isSelected, cellHasFocus);

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

                // todo - remove these lines as they were to used to DEBUG
                System.out.println(vipPackage);
                if (vipPackage == 1){

                    System.out.println("CLICKED YES");

                } else if (vipPackage == 0){

                    System.out.println("CLICKED NO");

                }

                if(vipPackage != -1) { // Check to see if the user pressed the ESCAPE key
                    // Get an instance of focusedEventID Event for the Pricing Fields
                    Event localEvent = new Event(focusedEventID, null);

                    // Now that we know the Ticket Type, we can compile a Ticket and add it to the Cart
                    fauxShoppingCart.add(new Ticket(focusedEventID, selected.getId(), vipPackage == 1 ? localEvent.getVipTicketPrice() : localEvent.getBaseTicketPrice(), vipPackage == 1 ? "vip" : "regular"));

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

            // Check if the User is changing selection, i think todo - clarify what isAdjusting looks out for
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


    private class cartListListener implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e){

            if (!e.getValueIsAdjusting()){

                JPanel panel = new JPanel();
                panel.add(new JLabel("Would you like to remove this item from your cart?")); //TODO personalize this dialogue

                Object[] panelButtons = {"No", "Yes"};

                int removeCartItem = JOptionPane.showOptionDialog(
                        null,
                        panel,
                        "Remove ticket", //TODO personalie this dialogue
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        panelButtons,
                        null
                );



            }

        }
    }




    /**
     * ActionListener for buttons invoking the goHomeListener
     */
    private class goHomeListener implements ActionListener {

        public void actionPerformed(ActionEvent e)
        {

            // todo - remove all this commented code
            //JButton source = (JButton) e.getSource();
            //String selected = source.getText();

            //JOptionPane.showMessageDialog(null, source.getText() + " - " + source.getActionCommand());

            // Repaint the Frame
            //loadHomeFrame();

            // Switch Cards back to Home
            cards.show(containerPanel, "1");

        }

    }

    private class cartViewListener implements ActionListener {

        public void actionPerformed(ActionEvent e)
        {

            // todo - remove all this commented code
            //JButton source = (JButton) e.getSource();
            //String selected = source.getText();

            //JOptionPane.showMessageDialog(null, source.getText() + " - " + source.getActionCommand());

            // Repaint the Frame
            //loadHomeFrame();

            // Switch Cards back to Home
            cards.show(containerPanel, "3");

        }

    }

}
