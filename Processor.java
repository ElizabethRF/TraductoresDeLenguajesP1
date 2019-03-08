import java.util.ArrayList;
import java.util.Stack;

public class Processor{
    private Matrix m;
    private ArrayList<Integer> degrees; 

    public Processor(){
        m = new Matrix();
        degrees = new ArrayList<Integer>(); 
        
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
            // System.out.println("\n actual char: " + word.charAt(i));
            // System.out.println(" iState: " + iState);
            // System.out.println(" iStateGlobal: " + iStateGlobal);
            // System.out.println(" i : " + i);
            i++;
        }

        if(i == word.length() - 1 && iState != -1){ // missing just the last letter
            int tempState = m.getLeft(iStateGlobal, word.charAt(i));
            if(-1 != tempState){ // word finish inside the automaton
                m.setFinalState(tempState);

            }else{
                // System.out.println("falta útima transicion");
                m.addTransition(iStateGlobal, m.getFinalState().get(0), word.charAt(i));
            }
        }
        /*else if(i == (word.length() - 1) && iState == -1){ // missing the last two letters as i grew but it didnt process the previus letter
                System.out.println(" faltan dos letras");
                
        }*/
        else{
            // System.out.println(" evaluación de derecha a izquierda");
            // System.out.println(" i para substring " + i);
            String subWord = word.substring(i);
            // System.out.println(" subword: "+ subWord );
            // System.out.println(" subwordLength: "+ subWord.length()); 
            getOutputDegree(); // calcular el output degree para evaluar abajo
            int[] longestPath = getLongestPath(m.getFinalState().get(0), subWord, subWord.length() - 1);
            j -= longestPath[1];
            
            for(i--; i < j; i++){ // i-- returns to the value of it that was not proceeded 
                // System.out.println(" \nvalor i " + i);
                // System.out.println(" valor en i " + word.charAt(i));
                // System.out.println(" valor j " + j);
                // System.out.println(" valor en j " + word.charAt(j));
                // System.out.println(" iStateGlobal " + iStateGlobal);
                int newState = m.addState();
                m.addTransition(iStateGlobal, newState, word.charAt(i));
                iStateGlobal = newState;
            }
            m.addTransition(iStateGlobal, longestPath[0], word.charAt(i));
            
        }
    }

    public int[] getLongestPath(int rightState, String word, int i){
        // System.out.println("\n Get longest path method " );
        // System.out.println(" rightState: " + rightState);
        // System.out.println(" word " + word);
        // System.out.println(" i " + i);

        Stack<Integer> statesRight = m.getRight(rightState, word.charAt(i));

        // System.out.println(" states right"); 
        // for(int a = 0; a< statesRight.size(); a++){
        //     System.out.println(statesRight.get(0)); 
        // }
        // System.out.println(" end states right"); 

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
                if(degrees.get(state) <= 1){

                    if(i == 0){
                        maxPath[0] = state; 
                        maxPath[1] = 1;
                    }else{
                        actualPath = getLongestPath(state, word, i - 1);
                        // System.out.println("recursivooo");
                        actualPath[1]++;
                        if(actualPath[1] > maxPath[1]){
                            maxPath[0] = actualPath[0];
                            maxPath[1] = actualPath[1];
                        }
                    }
                }else{
                    // System.out.println("grado mayor a 1");
                }
            }
            else{
                // System.out.println("omite el camino con final state para el estado "+ state);
            }
            
        }

        // System.out.println(" return " + maxPath[0] + " , " +maxPath[1]);
        return maxPath;
    }

    public void printMatrix(){
        System.out.println(m.toString());
    }

    public void createDegrees(ArrayList<ArrayList<String>> matrix){
        degrees = new ArrayList<Integer>(); 
        for(int i = 0 ; i< matrix.size() ; i++){
            degrees.add(0); 
        }
    }

    public void getOutputDegree(){
        ArrayList<ArrayList<String>> matrix = m.getMatrix();
        int totalOut = 0;  
        createDegrees(matrix); // create all degrees needed 
        for(int i = 0 ; i< matrix.size() ; i++){
            totalOut = 0; 
            for(int j = 0; j< matrix.size(); j++){
                if(matrix.get(i).get(j) != ""){
                    totalOut += 1; 
                }
            }
            degrees.set(i,totalOut);

        }
        //imprimir grados 
        // System.out.println(" GRADOS");
        // for(int i = 0; i< degrees.size(); i++){
        //     System.out.println(" grados para " + i + " : "+  degrees.get(i));
        // }
        // System.out.println("FINGRADOS\n ");

    }
}