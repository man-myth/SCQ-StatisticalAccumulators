import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManufacturingSystemGUI extends JFrame {
    // Declaring components of the GUI
    private JPanel mainPanel;
    private JLabel title;
    private JPanel contentPanel;
    private JPanel rigthPanel;
    private JButton simulateButton;
    private JTable table;

    // Declaring components of the analytics panel
    private JPanel AnalysisPanel;
    private JLabel nppLabel;
    private JLabel twtLabel;
    private JLabel nptLabel;
    private JLabel ltsLabel;
    private JLabel ltiLabel;
    private JLabel auqLabel;
    private JLabel hlqLabel;

    private JLabel nppValue;
    private JLabel twtValue;
    private JLabel nptValue;
    private JLabel ltsValue;
    private JLabel ltValue;
    private JLabel auqvalue;
    private JLabel htqValue;
    private JPanel inputPanel;
    private JTextField input;
    private JLabel ausLabel;
    private JLabel ausValue;

    // Defining headers for the table
    String [] headers = {"Entity No.", "Time", "Event type", "Q(t)",
            "B(t)", "In Queue", "In service", "P", "N", "ΣWQ", "WQ*", "ΣTQ", "TS*"};

    // Initializing the results array
    Integer[][] results = new Integer[1][1];

    // Initializing variables to keep track of the maximum time and customer values
    int maxTime = 0;

    // Constructor for the QueuingSystemGUI class
    public ManufacturingSystemGUI(){
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
                // Getting User input
                maxTime = Integer.parseInt(input.getText());

                // Creating a new ManufacturingSystem object
                ManufacturingSystem simulation = new ManufacturingSystem();
                ArrayList<> result= new ArrayList<>();
                for(double time = simulation.time; time < maxTime; time = simulation.time){

                }
                // Updating the table with the new results
                table.setModel(new DefaultTableModel(
                        results,
                        headers
                ));
                // Updating the analytics panel with the new values
                nppValue.setText(String.valueOf(Math.round(qss.averageWaitingTime*1000.0)/1000.0) + " minutes");
                twtValue.setText();
                nptValue.setText(String.valueOf(Math.round(qss.proportionOfIdleTime*1000.0)/1000.0) + "%");
                ltsValue.setText(String.valueOf(Math.round(qss.averageServiceTime*1000.0)/1000.0)+ " minutes");
                ltValue.setText(String.valueOf(Math.round(qss.averageTimeBetweenArrivals*1000.0)/1000.0)+ " minutes");
                auqvalue.setText(String.valueOf(Math.round(qss.averageWaitingTimeInQueue*1000.0)/1000.0)+ " minutes");
                htqValue.setText(String.valueOf(Math.round(qss.averageTimeInSystem*1000.0)/1000.0)+ " minutes");
            }


        });
    }
    // Start the queuing simulation
    public static void main(String[] args) {
        ManufacturingSystemGUI qs = new ManufacturingSystemGUI();
    }


}
