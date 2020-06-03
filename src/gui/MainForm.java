package gui;

import gui.components.SudokuBoard;
import gui.components.SudokuControlpanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MainForm extends JFrame {

    // --- Main method ---
    public static void main(String[] args) {
	new MainForm();
    }
    
    
    //Fields
    private static final long serialVersionUID = 1L;
        
    
    //Ctors
    public MainForm() {
	super("Sudoku");
	
	createGUI();
	centerOnScreen();
	setVisible(true);
	
	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e)
	    {
		System.exit(0);
	    }
	});
    }
    
    
    //Methods
    private void centerOnScreen()
    {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation((int)Math.round((screen.getWidth()-getWidth())/2), (int)Math.round((screen.getHeight()-getHeight())/2));
    }

    private void createGUI() {
	
	setBounds(0, 0, 500, 580);
	setLayout(new GridBagLayout());
	GridBagConstraints gCon = new GridBagConstraints();
	
	setMinimumSize(new Dimension(500,580));
	SudokuBoard board = new SudokuBoard(9);
	
	SudokuControlpanel ctrl = new SudokuControlpanel(board);
	
	
	//set up constr
	gCon.gridy=0;
	gCon.gridwidth=10;
	gCon.gridheight=1;
	gCon.insets.top=10;
	//add
	add(board,gCon);
	
	
	//set up constr
	gCon.gridy=1;
	gCon.insets.top=10;
	gCon.insets.bottom=10;
	//add
	add(ctrl,gCon);
    }
}
