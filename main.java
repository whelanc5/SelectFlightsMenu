

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class main {
    public main() {
        super();
    }

    public static void main(String[] args) {
        
    	 FlightFilter v = new FlightFilter();
        main main = new main();
        
        JFrame abc = new JFrame();
        JPanel ab = new JPanel();
        ArrayList<FlightFilter> flights = new ArrayList<>();
        abc.add(ab);
        
        JButton open = new JButton("selectFlightMenu");
        
        
        JButton colorPanel = new JButton("colorPanel");
        
        ab.add(colorPanel);
        
        colorPanel.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	 DesignIconDialog  test = new DesignIconDialog(new JFrame(), v) ;
			      
			    }
        });
        
        open.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	new SelectFlightsMenu(abc, flights);
			    }
        });
        
        ab.add(open);
        
        abc.pack();
        abc.setVisible(true);
        
        
        
       
        //test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
}
}