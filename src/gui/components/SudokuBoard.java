package gui.components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

import main.SudokuGamedata;
import main.SudokuPointValue;


public class SudokuBoard extends JPanel {

    private static final long serialVersionUID = 1L;

    // Fields
    private int boardSize;
    private SudokuGamedata data;
    
    private Point errorMarker;
    private Point selection;

    // Get&set
    public int getBoardSize() {
	return boardSize;
    }


    public void setBoardSize(int boardSize) {
	this.boardSize = boardSize;
	setPreferredSize(new Dimension(50 * boardSize + 1, 50 * boardSize));
	initBoard();
    }

    private void setErrormarker(int row, int col) {
	errorMarker.setLocation(row, col);
    }

    // Ctors
    public SudokuBoard(int boardSize) {
	super();
	errorMarker = new Point(-1, -1);
	selection = new Point(-1, -1);

	setBoardSize(boardSize);

	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseReleased(MouseEvent e) {
		if(e.getX()>-1 && e.getX()<=e.getComponent().getWidth() && e.getY()>-1&& e.getY()<=e.getComponent().getHeight())
		{
		    processMouseclick(e);
		}
	    }
	});
	
	addKeyListener(new KeyAdapter() {
	    public void keyTyped(KeyEvent e)
	    {
		processKeyboardEvent(e);
	    }
	});
    }

    // Methods
    private void initBoard() {
	//Create new data
	data = new SudokuGamedata(boardSize);

	// draw
	repaint();
    }

    public void createSudoku() {
	data.setSolution(generateSolution());
	data.setBoard(generatePuzzle(data.getBoardData()));
	
	setErrormarker(-1, -1);
	
	repaint();
    }

    private int[][] generateSolution() {
	int[][] solution = new int[boardSize][boardSize];
	boolean sudoSuccess = true;
	int retryCount = 0;

	// each row
	for (int i = 0; i < boardSize; i++) {
	    boolean rowOk = true;

	    // each number
	    for (int j = 0; j < boardSize; j++) {
		boolean numOk = false;

		for (int tries = 0; tries < 100; tries++) {

		    int val = (int) Math.round((Math.random() * 8 + 1));

		    if (testSudokuRules(solution, val, i, j)) {
			solution[i][j] = val;
			numOk = true;
			break;
		    }
		}

		if (!numOk) {
		    rowOk = false;
		}
	    }

	    if (!rowOk) {
		solution[i] = new int[boardSize];
		retryCount++;
		i--;
	    }

	    if (retryCount > 5) {
		sudoSuccess = false;
		break;
	    }
	}

	if (!sudoSuccess) {
	    return generateSolution();
	}

	return solution;
    }

    private int[][] generatePuzzle(SudokuPointValue[][] solution) {

	int[][] puzzle = new int[boardSize][boardSize];

	// Kopie maken
	for (int i = 0; i < boardSize; i++) {
	    for (int j = 0; j < boardSize; j++) {
		puzzle[i][j] = solution[i][j].getSolutionValue();
	    }
	}

	for (int row = (int) (Math.random() * 8); row < boardSize * 3; row++) {
	    for (int col = (int) (Math.random() * 8); col < boardSize * 3; col++) {
		int solutions = 0;
		int spare = puzzle[row % boardSize][col % boardSize];

		// Testen op single solution
		if (puzzle[row % boardSize][col % boardSize] != 0) {
		    for (int t = 1; t < 10; t++) {

			puzzle[row % boardSize][col % boardSize] = 0;

			if (testSudokuRules(puzzle, t, row % boardSize, col % boardSize)) {
			    solutions++;
			}
		    }

		    if (solutions != 1) {
			// Reset val

			puzzle[row % boardSize][col % boardSize] = spare;

		    }
		}
	    }
	}

	for (int i = 0; i < boardSize; i++) {
	    for (int j = 0; j < boardSize; j++) {
		puzzle[i][j] = -puzzle[i][j];
	    }
	}

	return puzzle;
    }
    
    
    private boolean solvePuzzle(int[][] puzzle,int hints)
    {
	//Count empty fields
	int remaining = boardSize*boardSize-hints;
	
	//Solve
	//--find a blank
	for(int row=0;row<boardSize;row++)
	{
	    for(int col=0;col<boardSize;col++)
	    {
		if(puzzle[row][col]==0)
		{
		    
		}
	    }
	}
	
	
	
	
	
	//Is everything filled out?
	if(remaining==0)
	{
	    return true;
	}
	return false;
    }
    

    public void showSolution() {
	for (int i = 0; i < boardSize; i++) {
	    for (int j = 0; j < boardSize; j++) {
		if(data.getBoardData()[i][j].getEnteredValue()>-1)
		{
		    data.getBoardData()[i][j] = data.getBoardData()[i][j];
		}
	    }
	}
	repaint();
    }

    private void processMouseclick(MouseEvent e) {
	
	selection.setLocation(Math.round(e.getX() / 50), Math.round(e.getY() / 50));
	repaint();
    }
    
    private void processKeyboardEvent(KeyEvent e)
    {
	int number =getTypedNumber(e.getKeyChar());
	
	if(number>0)
	{
	    int row = (int)selection.getY();
		int col=(int)selection.getX();
		
		// Is element gegenereerd?
		if (data.getBoardData()[row][col].getEnteredValue() < 0)
		    return;

		// Set of reset?
		if (data.getBoardData()[row][col].getEnteredValue() == number) {
		    setErrormarker(-1, -1);
		    data.getBoardData()[row][col].setEnteredValue(0);
		} else {
		    // Rij/Col/3x3 testen?
		    if (testSudokuRules(data.getBoardDataAsArray(), number, row, col)) {
			data.getBoardData()[row][col].setEnteredValue(number);
		    }
		}
		
		repaint();
	}
    }
    
    private int getTypedNumber(char key)
    {
	switch (key) {
	case '&':
	case '1':   
	    return 1;
	case 'é':
	case '2':   
	    return 2;
	case '"':
	case '3':   
	    return 3;
	case '\'':
	case '4':   
	    return 4;
	case '(':
	case '5':   
	    return 5;
	case '§':
	case '6':   
	    return 6;
	case 'è':
	case '7':   
	    return 7;
	case '!':
	case '8':   
	    return 8;
	case 'ç':
	case '9':   
	    return 9;
	}
	
	return -1;
    }

    private boolean testSudokuRules(int[][] board, int value, int row, int col) {
	// test row
	for (int c = 0; c < boardSize; c++) {
	    if (Math.abs(board[row][c]) == value && board[row][c] != 0) {
		setErrormarker(row, c);
		return false;
	    }
	}

	// test col
	for (int r = 0; r < boardSize; r++) {
	    if (Math.abs(board[r][col]) == value && board[r][col] != 0) {
		setErrormarker(r, col);
		return false;
	    }
	}

	// test boxes
	int boxX = (int) Math.floor(row / 3) * 3;
	int boxY = (int) Math.floor(col / 3) * 3;

	for (int r = 0; r < 3; r++) {
	    for (int c = 0; c < 3; c++) {
		if (Math.abs(board[r + boxX][c + boxY]) == value && board[r + boxX][c + boxY] != 0) {
		    setErrormarker(r + boxX, c + boxY);
		    return false;
		}
	    }
	}

	// All ok
	setErrormarker(-1, -1);
	return true;
    }

    //load & save
    public void loadGame(String path)
    {
	SudokuGamedata loaded = SudokuGamedata.loadGame(path);
	if(loaded!=null)
	{
	    data = loaded;
	    setErrormarker(-1, -1);
	    repaint();
	}
    }
    
    public void saveGame(String path)
    {
	data.saveGame(path);
    }
    
    @Override
    public void paint(Graphics g) {
	super.paint(g);

	Graphics2D g2d = (Graphics2D) g;

	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	drawGrid(g2d);
	drawErrorMarker(g2d);
	drawSelectionMarker(g2d);
	drawSymbols(g2d);
    }

    private void drawGrid(Graphics2D g2d) {
	g2d.setColor(Color.WHITE);
	g2d.fillRect(0, 0, getWidth(), getHeight());

	g2d.setColor(Color.BLACK);
	for (int i = 0; i < boardSize; i++) {
	    g2d.drawLine(50 * i, 0, 50 * i, getHeight());
	    g2d.drawLine(0, 50 * i, getWidth(), 50 * i);
	    if(i%3==0)
	    {
		g2d.drawLine(50 * i-1, 0, 50 * i-1, getHeight());
		    g2d.drawLine(0, 50 * i-1, getWidth(), 50 * i-1);
	    }
	}

	g2d.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
	g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }

    private void drawErrorMarker(Graphics2D g2d) {
	if (errorMarker.getY() == -1)
	    return;

	g2d.setColor(Color.RED);
	
	for(int width=0;width<3;width++)
	{
	    g2d.drawRect((int) Math.round(errorMarker.getY() * 50)+width, (int) Math.round(errorMarker.getX() * 50)+width, 50-(width*2), 50-(width*2));
	}
	
    }
    
    private void drawSelectionMarker(Graphics2D g2d) {
	if (selection.getY() == -1)
	    return;

	g2d.setColor(Color.BLUE);
	
	for(int width=0;width<3;width++)
	{
	    g2d.drawRect((int) Math.round(selection.getX() * 50)+width, (int) Math.round(selection.getY() * 50)+width, 50-(width*2), 50-(width*2));
	}
    }

    private void drawSymbols(Graphics2D g2d) {
	
	Font f = new Font("Verdana", 0, 40);
	g2d.setFont(f);
	FontMetrics fm = new FontMetrics(f) {
	    private static final long serialVersionUID = 1L;
	};

	for (int r = 0; r < boardSize; r++) {
	    for (int c = 0; c < boardSize; c++) {
		if (data.getBoardData()[r][c].getEnteredValue() != 0) {
		    if(data.getBoardData()[r][c].getEnteredValue()>0)
		    {
			g2d.setColor(Color.BLACK);
		    }
		    else
		    {
			g2d.setColor(Color.GRAY);
		    }
		    String s = "" + Math.abs(data.getBoardData()[r][c].getEnteredValue());
		    g2d.drawString(s, c * 50 + Math.round(fm.getStringBounds(s, g2d).getWidth() / 2), r * 50 + 42);
		}
	    }
	}
    }
}
