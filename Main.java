import java.util.*;

public class Main{

	private static int finalState; 
	private static HashMap<Integer, Integer> transitions; 
	public static void main(String[] args){

		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String line = sc.nextLine();
			String[] words = line.split(" ");
			ArrayList<ArrayList<String>> matrixGUI = process(words);
			GUI gui = new GUI(matrixGUI, finalState, transitions); 
		}

		
	}
	public static ArrayList<ArrayList<String>>  process(String[] words){
		Processor p = new Processor();
		
		p.processFirstWord(words[0]);
		System.out.println("Primera palabra correcta\n");
		p.printMatrix();
		for(int i = 1; i < words.length; i++){
			
			p.processWord(words[i]);
			System.out.printf("\nPalabra %d correcta %s\n",i,words[i]);
			p.printMatrix();
		}
		p.printMatrix();
		finalState = p.getFinalState();
		transitions =  p.getTransitions();  
		return p.getMatrix(); 
	}
}
