import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JLabel awtValue;
    private JLabel awtLabel;
    private JLabel atsValue;
    private JLabel aqtValue;
    private JLabel udpValue;
    private JPanel leftPanel;
    private JScrollPane scrollPane;

    // Defining headers for the table
    String [] headers = {"Entity No.", "Time", "Event type", "Q(t)",
            "B(t)", "In Queue", "In service", "P", "N", "ΣWQ", "WQ*", "ΣTQ", "TS*", "∫Q", "∫Q*", "∫B"};

    // Initializing the results array
    Object[][] results = new Object[1][1];

    // Initializing variables to keep track of the maximum time and customer values
    double maxTime = 0;

    // Constructor for the QueuingSystemGUI class
    public ManufacturingSystemGUI(){
        // Calling the constructor of the JFrame superclass and setting properties of the GUI
        super("midterm-act2");
        Border raisedEtched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border compound = BorderFactory.createCompoundBorder(raisedEtched, empty);
        inputPanel.setBorder(compound);
        AnalysisPanel.setBorder(empty);
        setContentPane(mainPanel);
        setResizable(false);
        setSize(1300, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        leftPanel.setPreferredSize(new Dimension(450, 550));
        // Initializing the table with the headers and results array
        scrollPane.setPreferredSize(new Dimension(800, 550));
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
                boolean accept = true;

                // Getting User input
                maxTime = Double.parseDouble(input.getText());

//                if (maxTime > 39) {
////                    JOptionPane.showMessageDialog(null, "Invalid input, it must be lower than 40");
//                    accept = true;
//                } else {
//                    accept = true;
//                }

                if (accept) {
                    // Creating a new ManufacturingSystem object
                    ManufacturingSystem ms = new ManufacturingSystem();
                    ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
                    rows.add(ms.simulation);
                    for (double time = ms.time; time < maxTime; ) {
                        try {
                            ArrayList<Object> row = new ArrayList<Object>(ms.simulation);
                            row.set(5, row.get(5).toString());
                            rows.add(row);
                            ms.goToNextEvent();
                            time = ms.time;
                        } catch (Exception ex) {
                            break;
                        }
                    }
                    //put the first element at the end
                    ArrayList<Object> firstElement = rows.remove(0);
                    rows.add(firstElement);
//                    if (Double.parseDouble(firstElement.get(1).toString()) < maxTime) {
//                        rows.add(firstElement);
//                    }
                    //converting arraylist to array

                    results = rows.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);

                    if (((Number) results[results.length - 1][1]).doubleValue() > maxTime) {
                        results[results.length - 1][0] = " ";
                        results[results.length - 1][1] = maxTime;
                        results[results.length - 1][2] = "End";
                        results[results.length - 1][3] = results[results.length - 2][3];
                        results[results.length - 1][4] = results[results.length - 2][4];
                        results[results.length - 1][5] = results[results.length - 2][5];
                        results[results.length - 1][6] = results[results.length - 2][6];
                        results[results.length - 1][7] = results[results.length - 2][7];
                        results[results.length - 1][8] = results[results.length - 2][8];
                        results[results.length - 1][9] = results[results.length - 2][9];
                        results[results.length - 1][10] = results[results.length - 2][10];
                        results[results.length - 1][11] = results[results.length - 2][11];
                        results[results.length - 1][12] = results[results.length - 2][12];
                        results[results.length - 1][13] = Double.parseDouble(results[results.length - 2][13].toString())+(maxTime - Double.parseDouble(results[results.length - 2][1].toString())) * Double.parseDouble(results[results.length - 2][3].toString());
                        results[results.length - 1][14] = results[results.length - 2][14];
                        results[results.length - 1][15] = Double.parseDouble(results[results.length - 2][15].toString())+(maxTime - Double.parseDouble(results[results.length - 2][1].toString())) * Double.parseDouble(results[results.length - 2][4].toString());

                    }

                    double qtSum = computeAreaUnderCurve(results, 'q');
                    double btSum = computeAreaUnderCurve(results, 'b');

                    reformatTable();
                    // Updating the table with the new results
                    table.setModel(new DefaultTableModel(
                            results,
                            headers
                    ));

                    // Updating the analytics panel with the new values
                    nppValue.setText((results[results.length - 1][7])+ " ") ;
                    ltsValue.setText((results[results.length - 1][10]) + " mins");
                    twtValue.setText(String.valueOf(results[results.length - 1][9]));
                    double awt =  ((Number)results[results.length - 1][9]).doubleValue() / ((Number)results[results.length - 1][8]).doubleValue();
                    awtValue.setText( String.format("%.2f", awt) + " mins/part");
                    nptValue.setText(String.valueOf(results[results.length - 1][8]));
                    ltValue.setText(String.valueOf(results[results.length - 1][12]));
                    double ats = (((Number)results[results.length - 1][11]).doubleValue() / ((Number)results[results.length - 1][7]).doubleValue());
                    atsValue.setText( String.format("%.2f", ats)+ " mins/part");
                    auqvalue.setText(String.format("%.2f",qtSum));
                    htqValue.setText(getMax(results));
                    aqtValue.setText(String.format("%.2f", (qtSum / maxTime)) + " part"); //
                    ausValue.setText(String.valueOf(btSum));
                    udpValue.setText(String.format("%.2f", (btSum/maxTime * 100)) + "%"); //
                }

            }
        }
        );
    }


    public double computeAreaUnderCurve(Object[][] table, char cat){
        double sum = 0;
        for(int i = 2; i <table.length;i++){
            if(cat == 'q'){
                sum += (((Number) table[i][1]).doubleValue() - ((Number) table[i-1][1]).doubleValue()) * ((Number) table[i-1][3]).doubleValue() ;
            }

            if(cat == 'b'){
                sum += (((Number) table[i][1]).doubleValue() - ((Number) table[i-1][1]).doubleValue()) * ((Number) table[i-1][4]).doubleValue() ;
            }
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

    public void reformatTable(){
        for(int i = 0; i < results.length; i++){
            if(Double.parseDouble(results[i][6].toString())  == -1){
                results[i][6] = " - ";
            }
        }
    }
    // Start the queuing simulation
    public static void main(String[] args) {
        ManufacturingSystemGUI qs = new ManufacturingSystemGUI();
    }

}
