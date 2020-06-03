package main;

import java.io.Serializable;

public class SudokuPointValue implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int solutionValue, enteredValue;

    public int getEnteredValue() {
        return enteredValue;
    }

    public void setEnteredValue(int enteredValue) {
        this.enteredValue = enteredValue;
    }

    public int getSolutionValue() {
        return solutionValue;
    }

    public void setSolutionValue(int solutionValue) {
        this.solutionValue = solutionValue;
    }
}
