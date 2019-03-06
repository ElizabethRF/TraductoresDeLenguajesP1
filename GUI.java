import javax.swing.*; 
import java.awt.*;  
import java.util.*; 

public class GUI extends Canvas{
    
    private int ratio; 
    private ArrayList<ArrayList<String>> graph;
    private int finalState; 
    private int[][] graphXY;
    private HashMap<Integer,Integer> transitions; 

    public GUI(ArrayList<ArrayList<String>>  graph, int finalState, HashMap<Integer,Integer> transitions){
        ratio = 30; 
        this.graph = graph; 
        this.graphXY =  new int[graph.size()][2];
        this.finalState = finalState; 
        this.transitions = transitions; 
        System.out.println("aun no creo el frame");
        JFrame f = new JFrame();
        f.getContentPane().add(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setMinimumSize(new Dimension(1000, 600));
        f.setBounds(100,0,1000,600);
        f.setVisible(true);


    }

    public void paint(Graphics g){
        System.out.println("holi soy paint");
        int x = 30; 
        int y = 300; 
        
        createState(g,x,y,0);
        graphXY[0][0] = x; 
        graphXY[0][1] = y;
         
        x = 90; 
        y = 30; 

        for(int i = 1;i<graph.size(); i++){
            if(i != finalState){
                
                createState(g,x,y,i);

                graphXY[i][0] = x; 
                graphXY[i][1] = y; 
                x += 60;


                if(x > 950){
                    y += 50;
                    x = 200; 
                }
                if(y > 550){
                    y = 100; 
                    x += 60; 
                }

                if(transitions.get(i) == finalState || transitions.get(i) < i){
                    y += 90; 
                    x = 90; 
                }

                
            }else{
                createFinalState(g, 960, 300,i);
                graphXY[i][0] = 960; 
                graphXY[i][1] = 300; 
            }


            
        }
        System.out.println("holii debo ir primero ");

        for(int i = 0;i<graph.size(); i++){
            for(int j = 0;j<graph.size(); j++){
                String elem = graph.get(i).get(j); 
                if(elem != ""){
                    drawTransition(g, i, j, elem);
                }
                
            }
        }
    }

    public void createState(Graphics g, int x, int y, int name){
        g.drawOval(x,y,ratio, ratio);
        g.drawString("q" + name , x + (ratio/2) - 8 , y + (ratio/2) + 4) ; 
    }

    public void createFinalState(Graphics g, int x, int y, int name){
        createState(g,x,y,name);
        g.drawOval(x+4,y+4,ratio - 8, ratio - 8);
    }

    public void drawTransition(Graphics g, int origin, int destiny, String transition){
        //g.drawPolygon(new int[] {10, 15, 20}, new int[] {10, 15, 10},3); 
        System.out.println("holii debo ir despues ");

        System.out.println("graphXY[origin][0] " + graphXY[origin][0]);
        System.out.println("graphXY[origin][1] " + graphXY[origin][1]);
        
        System.out.println("graphXY[destiny][0] " + graphXY[destiny][0]);
        System.out.println("graphXY[destiny][1] " + graphXY[destiny][1]);
        
        int lineSizeX = graphXY[destiny][0] - graphXY[origin][0]; 
        int lineSizeY = graphXY[destiny][1] - graphXY[origin][1]; 
        g.drawString(transition+"", lineSizeX+5,lineSizeY+5); 
        g.drawLine(graphXY[origin][0] + 30, graphXY[origin][1] +15, graphXY[destiny][0] , graphXY[destiny][1] +15); 


        g.drawOval(graphXY[destiny][0] - 7 ,graphXY[destiny][1] + 10 ,8, 8);
        g.fillOval(graphXY[destiny][0] - 7,graphXY[destiny][1] +10,8, 8);   
    }


}