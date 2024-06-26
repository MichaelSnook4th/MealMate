import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MealPreferenceList extends JFrame {

    static JLabel label;
    private JPanel panel;
    private ButtonGroup buttonGroup;

    public void MealPreferenceFrame() {
    	setTitle("Meal Preference");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        setLayout(new BorderLayout());

        String[] meals ={"Mexican", "Italian", "Asian", "Indian", "Greek", "Caribbean"};
        
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        buttonGroup = new ButtonGroup();
        
        for (String meal : meals){
        	JRadioButton radioButton = new JRadioButton(meal);
        	radioButton.setActionCommand(meal);
        	buttonGroup.add(radioButton);
        	panel.add(radioButton);
        }
        
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        
        JButton button = new JButton("Show Selection");
        button.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ButtonModel selectedModel = buttonGroup.getSelection();
        		if (selectedModel != null) {
        			label.setText("Selected meal: " + selectedModel.getActionCommand());
        		} else {
        			label.setText("No meal selected");
        		}
        	}
        });
        
        label = new JLabel("Select a meal from the list");
        add(label, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
        
        setVisible(true);
    }
}
