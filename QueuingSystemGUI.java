import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueuingSystemGUI extends JFrame {
    // Declaring components of the GUI
    private JPanel mainPanel;
    private JLabel title;
    private JPanel contentPanel;
    private JPanel leftPanel;
    private JPanel rigthPanel;
    private JLabel leftTitle;
    private JRadioButton custRB;
    private JRadioButton minRB;
    private JTextField userInput;
    private JButton simulateButton;
    private JTable table;

    // Declaring components of the analytics panel
    private JPanel AnalysisPanel;
    private JLabel wtLabel;
    private JLabel pwLabel;
    private JLabel ptsLabel;
    private JLabel asrLabel;
    private JLabel atbaLabel;
    private JLabel awtqLabel;
    private JLabel atsLabel;

    private JLabel wtValue ;
    private JLabel pwValue ;
    private JLabel ptsValue ;
    private JLabel astValue ;
    private JLabel atbaValue ;
    private JLabel awtqvalue ;
    private JLabel atsValue ;

    // Defining headers for the table
    String [] headers = {"Customer No.", "Interarrival Time", "Arrival Time", "Service Time",
            "Service Begins", "Waiting Time", "Service Ends", "Time Spent", "Idle Time"};

    // Initializing the results array
    Integer[][] results = new Integer[1][1];

    // Initializing variables to keep track of the maximum time and customer values
    int maxTime = 0;
    int maxCustomer = 0;

    // Constructor for the QueuingSystemGUI class
    public QueuingSystemGUI(){
        // Calling the constructor of the JFrame superclass and setting properties of the GUI
        super("midterm-act2");
        setContentPane(mainPanel);
        setResizable(false);
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Initializing the table with the headers and results array
        table.setModel(new DefaultTableModel(
                results,
                headers
        ));
        table.setEnabled(false);
        // Adding an ActionListener to the simulateButton
        simulateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resetting the maxTime and maxCustomer variables
                maxTime = 0;
                maxCustomer = 0;
                // Checking if the "Customers" radio button is selected
                if(custRB.isSelected()){
                    maxCustomer = Integer.parseInt(userInput.getText());
                }
                // Checking if the "Minutes" radio button is selected
                else if(minRB.isSelected()){
                    maxTime = Integer.parseInt(userInput.getText());
                }
                // Creating a new QueuingSystem object and calling the startQueue method to get the results
                QueuingSystem qss = new QueuingSystem();

                results = qss.startQueue(maxCustomer, maxTime);

                // Updating the table with the new results
                table.setModel(new DefaultTableModel(
                        results,
                        headers
                ));
                // Updating the analytics panel with the new values
                wtValue.setText(String.valueOf(Math.round(qss.averageWaitingTime*1000.0)/1000.0) + " minutes");
                pwValue.setText(String.valueOf(Math.round(qss.probabilityOfWaitingPercentage*1000.0)/1000.0) + "%");
                ptsValue.setText(String.valueOf(Math.round(qss.proportionOfIdleTime*1000.0)/1000.0) + "%");
                astValue.setText(String.valueOf(Math.round(qss.averageServiceTime*1000.0)/1000.0)+ " minutes");
                atbaValue.setText(String.valueOf(Math.round(qss.averageTimeBetweenArrivals*1000.0)/1000.0)+ " minutes");
                awtqvalue.setText(String.valueOf(Math.round(qss.averageWaitingTimeInQueue*1000.0)/1000.0)+ " minutes");
                atsValue.setText(String.valueOf(Math.round(qss.averageTimeInSystem*1000.0)/1000.0)+ " minutes");
            }


        });
    }
    // Start the queuing simulation
    public static void main(String[] args) {
        QueuingSystemGUI qs = new QueuingSystemGUI();
    }


}
