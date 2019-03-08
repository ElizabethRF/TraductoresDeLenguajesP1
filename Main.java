import java.util.*;

public class Main{
	public static void main(String[] args){

		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			String line = sc.nextLine();
			String[] words = line.split(" ");
			System.out.println("antes");
			for(int i = 0; i< words.length; i++){
				System.out.println(words[i]);
			}
			process(words);
		}
	}
	public static void process(String[] words){
		Processor p = new Processor();
			
		// lambda expression to order strings in descending mode 
		Arrays.sort(words, (x,y) -> Integer.compare(y.length(),x.length()));

		System.out.println("\nInsertion order:");
		for(int i = 0; i< words.length; i++){
			System.out.println(i + ".- " + words[i]);
		}
		
		p.processFirstWord(words[0]);
		// System.out.println("Primera palabra correcta\n");
		// p.printMatrix();
		for(int i = 1; i < words.length; i++){
			
			p.processWord(words[i]);
			// System.out.printf("\nPalabra %d correcta %s\n",i,words[i]);
			// p.printMatrix();
		}
		p.printMatrix();
	}
}
