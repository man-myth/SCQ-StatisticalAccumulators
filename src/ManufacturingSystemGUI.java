import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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
    Object[][] results = new Object[1][1];

    // Initializing variables to keep track of the maximum time and customer values
    double maxTime = 0;

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
                ManufacturingSystem ms = new ManufacturingSystem();
                ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
                rows.add(ms.simulation);

                for(double time = ms.time; time < maxTime;){
                    try {
                        ArrayList<Object> row = new ArrayList<Object>(ms.simulation);
                        row.set(5, row.get(5).toString());
                        rows.add(row);
                        ms.goToNextEvent();
                        time = ms.time;
                    }catch (Exception ex){
                        System.out.println(ex);
                        break;
                    }
                }

                //put the first element at the end
                ArrayList<Object> firstElement = rows.remove(0);
                rows.add(firstElement);

                //converting arraylist to array
                results = rows.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);

                if(((Number) results[results.length-1][1]).doubleValue() > maxTime){
                    results[results.length-1][0] = " ";
                    results[results.length-1][1] = maxTime;
                    results[results.length-1][2] = "End";
                }

                // Updating the table with the new results
                table.setModel(new DefaultTableModel(
                        results,
                        headers
                ));

                double qtSum = computeAreaUnderCurve(results,'q');
                double btSum = computeAreaUnderCurve(results,'b');

                // Updating the analytics panel with the new values
                nppValue.setText(String.valueOf(results[results.length-1][7]));
                twtValue.setText(String.valueOf(results[results.length-1][9]));
                nptValue.setText(String.valueOf(results[results.length-1][8]));
                ltsValue.setText(String.valueOf(results[results.length-1][10]));
                ltValue.setText(String.valueOf(results[results.length-1][12]));
                auqvalue.setText(String.valueOf(qtSum));
                htqValue.setText(getMax(results));
                ausValue.setText(String.valueOf(btSum));
            }


        });
    }
    // Start the queuing simulation
    public static void main(String[] args) {
        ManufacturingSystemGUI qs = new ManufacturingSystemGUI();
    }

    public double computeAreaUnderCurve(Object[][] table, char cat){
        System.out.println("For " + cat);
        double sum = 0;
        for(int i = 2; i <table.length-1;i++){
            if(cat == 'q'){
//                System.out.println("time = "+((Number) table[i][1]).doubleValue());
//                System.out.println("time prev = "+((Number) table[i-1][1]).doubleValue());
//                System.out.println("q = " +((Number) table[i][3]).doubleValue());
                sum += (((Number) table[i][1]).doubleValue() - ((Number) table[i-1][1]).doubleValue()) * ((Number) table[i-1][3]).doubleValue() ;
            }

            if(cat == 'b'){
                sum += (((Number) table[i][1]).doubleValue() - ((Number) table[i-1][1]).doubleValue()) * ((Number) table[i-1][4]).doubleValue() ;
            }
            System.out.println(sum);
        }
        return sum;
    }

    public String getMax(Object[][] table){
        int max = 0;
        for(int i = 0; i <table.length;i++){
            max = Math.max(max, (Integer) table[i][3]);
        }
        return String.valueOf(max);
    }

}
