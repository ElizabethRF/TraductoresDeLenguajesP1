import java.util.Stack;

public class Processor{
    private Matrix m;

    public Processor(){
        m = new Matrix();
    }

    public void processFirstWord(String word){
        for(int i = 0; i < word.length(); i++){
            m.addState();
            m.addTransition(i, i + 1, word.charAt(i));
        }
        m.setFinalState(word.length());
    }

    public void processWord(String word){
        int i = 0, j = word.length() - 1;
        int iState = 0;
        while(i < word.length() - 2 && iState != -1){
            iState = m.getLeft(iState, word.charAt(i));
            i++;
        }

        if(i == word.length() - 2){
            m.addTransition(iState, m.getFinalState(), word.charAt(i + 1));
        }
        else{
            String subWord = word.substring(i + 2);
            int[] longestPath = getLongestPath(m.getFinalState(), subWord, subWord.length() - 1);
            j -= longestPath[1];
            for(; i < j; i++){
                int newState = m.addState();
                m.addTransition(iState, newState, word.charAt(i + 1));
                iState = newState;
            }
            m.addTransition(iState, longestPath[0], word.charAt(i + 1));
            
        }
    }

    public int[] getLongestPath(int rightState, String word, int i){
        Stack<Integer> statesRight = m.getRight(rightState, word.charAt(i));
        int[] actualPath = new int[2];
        actualPath[0] = rightState;
        actualPath[1] = 0;
        int[] maxPath = new int[2];
        maxPath[0] = rightState;
        maxPath[1] = 0;

        while(!statesRight.isEmpty()){
            actualPath[1] = 0;
            int state = statesRight.pop();
            actualPath = getLongestPath(state, word, i - 1);
            actualPath[1]++;
            if(actualPath[1] > maxPath[1]){
                maxPath[0] = actualPath[0];
                maxPath[1] = actualPath[1];
            }
        }
        return maxPath;
    }

    public void printMatrix(){
        System.out.println(m.toString());
    }
}