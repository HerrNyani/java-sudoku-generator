package gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class SudokuControlpanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final String EXTENSION = ".sudoku";
    private SudokuBoard board;

    public SudokuControlpanel(SudokuBoard board) {
	super();
	this.board=board;
	setPreferredSize(new Dimension(450, 40));
	setLayout(new FlowLayout());
	buildGUI();
    }
    
    
    private void buildGUI() {
	
	final JButton btnGenerateNew = new JButton("New");
	final JButton btnSolution = new JButton("Solution");
	final JButton btnSave = new JButton("Save...");
	final JButton btnLoad = new JButton("Load...");
	
	btnSave.setEnabled(false);
	btnSolution.setEnabled(false);
	
	
	btnGenerateNew.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		board.createSudoku();
		board.requestFocusInWindow();
		btnSave.setEnabled(true);
		btnSolution.setEnabled(true);
	    }
	});
	
	
	
	btnSolution.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		board.showSolution();
		board.requestFocusInWindow();
		btnSave.setEnabled(false);
		btnSolution.setEnabled(false);
	    }
	});
	
	
	
	btnSave.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		

		Container parent = ((Container) e.getSource()).getParent();
		if (fc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
		    String path = fc.getSelectedFile().getAbsolutePath();
		    if (!path.endsWith(EXTENSION)) {
			path = path+EXTENSION;
		    }
		    board.saveGame(path);
		}
		
		board.requestFocusInWindow();
	    }
	});
	
	
	btnLoad.addActionListener(new ActionListener() {
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc =new JFileChooser();
		fc.setFileFilter(new FileFilter() {

		    @Override
		    public String getDescription() {
			return "Sudoku savefile";
		    }

		    @Override
		    public boolean accept(File f) {
			if (f.isDirectory()) {
			    return true;
			} else {
			    String path = f.getAbsolutePath().toLowerCase();
			    
			    if (path.endsWith(EXTENSION)) {
				return true;
			    }
			    return false;
			}
		    }
		});
		
		
		Container parent = ((Container)e.getSource()).getParent();
		if(fc.showDialog(parent,"Load sudoku")==JFileChooser.APPROVE_OPTION)
		{
		    board.loadGame(fc.getSelectedFile().getAbsolutePath());
		}
		
		board.requestFocusInWindow();
	    }
	});
	
	
	
	add(btnGenerateNew);
	add(btnSolution);
	add(btnSave);
	add(btnLoad);
	
	paintComponents(this.getGraphics());
    }

    
    public void paint(Graphics g) {
	super.paint(g);

	//Draw BG
	g.setColor(Color.WHITE);
	g.fillRect(0, 0, getWidth(), getHeight());

	g.setColor(Color.BLACK);
	g.drawLine(0,0, getWidth(), 0);
	g.drawLine(0,getHeight()-1, getWidth(), getHeight()-1);
	
	g.drawLine(0,0, 0, getHeight());
	g.drawLine(getWidth()-1,0, getWidth()-1, getHeight());
	
	//Draw children
	paintComponents(g);
    }
}
