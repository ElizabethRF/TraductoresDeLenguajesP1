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
        int iStateGlobal = 0; 
        while(i < word.length() - 1 && iState != -1){
            iState = m.getLeft(iState, word.charAt(i));
            if(iState != -1){
                iStateGlobal = iState; 
            }
            System.out.println("\n actual char: " + word.charAt(i));
            System.out.println(" iState: " + iState);
            System.out.println(" iStateGlobal: " + iStateGlobal);
            System.out.println(" i : " + i);
            i++;
        }

        if(i == word.length() - 1){
            int tempState = m.getLeft(iStateGlobal, word.charAt(i));
            if(-1 != tempState){ // para las que terminen dentro del autómata 
                m.setFinalState(tempState);

            }
        }
        
        if(i == word.length() ){
                System.out.println(" word length - 2 (penúltima) " + i);
                m.addTransition(iState, m.getFinalState().get(0), word.charAt(i));
        }
        else{
            System.out.println(" evaluación de derecha a izquierda");
            System.out.println(" i para substring " + i);
            String subWord = word.substring(i);
            System.out.println(" subword: "+ subWord );
            System.out.println(" subwordLength: "+ subWord.length());
            // Ahora lo voy a cambiar al longest path del primer estado final, pero debe ser para cada uno 
            int[] longestPath = getLongestPath(m.getFinalState().get(0), subWord, subWord.length() - 1);
            j -= longestPath[1];
            
            for(i--; i < j; i++){
                System.out.println(" \nvalor i " + i);
                System.out.println(" valor en i " + word.charAt(i));
                System.out.println(" valor j " + j);
                System.out.println(" valor en j " + word.charAt(j));
                System.out.println(" iStateGlobal " + iStateGlobal);
                int newState = m.addState();
                m.addTransition(iStateGlobal, newState, word.charAt(i));
                iStateGlobal = newState;
            }
            m.addTransition(iStateGlobal, longestPath[0], word.charAt(i));
            
        }
    }

    public int[] getLongestPath(int rightState, String word, int i){
        System.out.println("\n Get longest path method " );
        System.out.println(" rightState: " + rightState);
        System.out.println(" word " + word);
        System.out.println(" i " + i);

        Stack<Integer> statesRight = m.getRight(rightState, word.charAt(i));
        int[] actualPath = new int[2];
        actualPath[0] = rightState;
        actualPath[1] = 0;
        int[] maxPath = new int[2];
        maxPath[0] = rightState;
        maxPath[1] = 0;
       

        while(!statesRight.isEmpty() && i >= 0){
            actualPath[1] = 0;
            int state = statesRight.pop();
            if(m.getFinalState().indexOf(state) == -1){
                if(i == 0){
                    maxPath[0] = state; 
                    maxPath[1] = 1;
                }else{
                    actualPath = getLongestPath(state, word, i - 1);
                    actualPath[1]++;
                    if(actualPath[1] > maxPath[1]){
                        maxPath[0] = actualPath[0];
                        maxPath[1] = actualPath[1];
                    }
                }
            }
            else{
                System.out.println("omite el camino con final state para el estado "+ state);
            }
            
        }

        System.out.println(" return " + maxPath[0] + " , " +maxPath[1]);
        return maxPath;
    }

    public void printMatrix(){
        System.out.println(m.toString());
    }
}