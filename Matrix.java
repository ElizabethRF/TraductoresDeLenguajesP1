import java.util.*;

public class Matrix{
    private ArrayList<ArrayList<String>> matrix;
    private ArrayList<Integer> finalState;

    public Matrix(){
        matrix = new ArrayList<ArrayList<String>>();
        finalState = new ArrayList<Integer>();
        addState();
    }

    public void setFinalState(int state){
        finalState.add(state);
    }

    public ArrayList<Integer> getFinalState(){
        return finalState;
    }

    public int addState(){
        matrix.add(new ArrayList<String>());
        for(int i = 0; i < matrix.size() - 1; i++){
            matrix.get(i).add("");
            matrix.get(matrix.size() - 1).add("");
        }
        matrix.get(matrix.size() - 1).add("");

        return matrix.size() - 1;
    }

    public boolean addTransition(int originState, int destinyState, char letter){
        String transString = matrix.get(originState).get(destinyState);
        if(transString.indexOf(letter) < 0){
            matrix.get(originState).set(destinyState, transString + letter);
            return true;
        }

        return false;
    }

    public int getLeft(int state, char letter){
        for(int i = 0; i < matrix.size(); i++){
            if(matrix.get(state).get(i).indexOf(letter) >= 0){
                return i;
            }
        }

        return -1;
    }

    public Stack<Integer> getRight(int state, char letter){
        Stack<Integer> stateStack = new Stack<Integer>();
        for(int i = 0; i < matrix.size(); i++){
            if(matrix.get(i).get(state).indexOf(letter) >= 0){
                stateStack.add(i);
            }
        }
        
        return stateStack;
    }

    public ArrayList<ArrayList<String>> getMatrix(){
        return matrix; 
    }

    @Override
    public String toString(){
        String matrixString = "\t";
        for(int i = 0; i < matrix.size(); i++){
            if(finalState.contains(i)){
                matrixString += "Q";
            }
            else{
                matrixString += "q";
            }
            matrixString += i + "\t";
        }
        matrixString += "\n";
        
        for(int i = 0; i < matrix.size(); i++){
            if(finalState.contains(i)){
                matrixString += "Q";
            }
            else{
                matrixString += "q";
            }
            matrixString += i + "\t";

            for(int j = 0; j < matrix.size(); j++){
                matrixString += matrix.get(i).get(j) + "\t";
            }
            matrixString += "\n";
        }

        return matrixString;
    }
}