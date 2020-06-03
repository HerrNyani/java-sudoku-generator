package main;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SudokuGamedata implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    //fields
    private SudokuPointValue[][] boardData;//[row][col]
    
    //get&set
    public SudokuPointValue[][] getBoardData()
    {
	return boardData;
    }
    
    public int[][] getBoardDataAsArray()
    {
	int[][] boardArray= new int[boardData.length][boardData[0].length];
	
	for(int row=0;row<boardData.length;row++)
	{
	    for(int col=0;col<boardData[0].length;col++)
	    {
		boardArray[row][col] = boardData[row][col].getEnteredValue();
	    }
	}
	
	return boardArray;
    }
    
    public void setSolution(int[][] solution)
    {
	for(int row=0;row<solution.length;row++)
	{
	    for(int col=0;col<solution[0].length;col++)
	    {
		boardData[row][col].setSolutionValue(solution[row][col]);
	    }
	}
    }
    
    public void setBoard(int[][] puzzle) {
	for(int row=0;row<puzzle.length;row++)
	{
	    for(int col=0;col<puzzle[0].length;col++)
	    {
		boardData[row][col].setEnteredValue(puzzle[row][col]);
	    }
	}
    }
    
    //ctors
    public SudokuGamedata()
    {}

    public SudokuGamedata(int boardSize)
    {
	boardData = new SudokuPointValue[boardSize][boardSize];
	
	for(int row=0;row<boardSize;row++)
	{
	    for(int col=0;col<boardSize;col++)
	    {
		boardData[row][col]=new SudokuPointValue();
	    }
	}
    }
    
    
    
    //Methods
    public static SudokuGamedata loadGame(String path)
    {
	ObjectInputStream istream = null;
	try {
	    istream = new ObjectInputStream(new FileInputStream(path));

	    return (SudokuGamedata) istream.readObject();

	} catch (Exception ex) {
	    Logger.getLogger(SudokuGamedata.class.getName()).log(Level.SEVERE, "Loading data failed.");
	} finally {
	    try {
		istream.close();
	    } catch (IOException e) {
		Logger.getLogger(SudokuGamedata.class.getName()).log(Level.WARNING, "Error closing inStream.");
	    }
	}

	return null;
    }
    
    public void saveGame(String path)
    {
	ObjectOutputStream ostream = null;
	try {
	    ostream = new ObjectOutputStream(new FileOutputStream(path));
	    ostream.writeObject(this);
	} catch (IOException e) {
	    Logger.getLogger(SudokuGamedata.class.getName()).log(Level.SEVERE, "Couldn't save data.");
	} finally {
	    try {
		ostream.close();
	    } catch (IOException e) {
		Logger.getLogger(SudokuGamedata.class.getName()).log(Level.WARNING, "Error closing outStream.");
	    }
	}
    }

    
}
