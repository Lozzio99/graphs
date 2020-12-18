package graphs.src;
import java.util.Arrays;
public class ChromaticNumber
{
  public  int [][] graph;
  public int [] result ;
  public Casual random;
  public BackTracking bt;
  public Lowest min ;
  public int [] route;
  public static double start ;

  public ChromaticNumber(ColEdge [] e , int s , int m , int t)
  {
    start = System.nanoTime();
    ChromaticMin back = new ChromaticMin();
    int [][] matrix = new int [s][s];
		matrix =createGraphMatrix(e , s , m);
		graph = matrix;
    int [] path = new int [s];
    for (int i = 0; i< s; i++)
    {
      path[i] = i;
    }
    route = path;
    for (int i = 0;i<500000;i++)
    {
      random = new Casual (graph, route, 1);
      graph = random.changeMatrix();
      int [] randomly = new int [graph.length];
      randomly = random.getRoute();
      BackTracking bt = new BackTracking (graph, randomly);
      result = bt.colouredArray();
      back.add(bt);
    }
    System.out.println("-----------");
    min = back.getMinimum();

    System.out.println("minimum is " + min.chromatic());
    System.out.println("colors >"+Arrays.toString(min.colouredArray()));
    System.out.println("route >"+ Arrays.toString(min.getRouteB()));
    System.out.println("End of analysis at >" +((System.nanoTime()-start)/(1000000000)) + " seconds" );
    System.out.println(back.getHint().length);
  }

  public int [][] createGraphMatrix(ColEdge[] vertexEdges, int vertexCount, int edgeCount)
	{
		int[][] matrix = new int[vertexCount][vertexCount];

			for (int i = 0; i < vertexEdges.length; i++)
			 {
				matrix[vertexEdges[i].u-1][vertexEdges[i].v-1] = 1;
				matrix[vertexEdges[i].v-1][vertexEdges[i].v-1] = 1;
				matrix[vertexEdges[i].v-1][vertexEdges[i].u-1] = 1;
				matrix[vertexEdges[i].u-1][vertexEdges[i].u-1] = 1;
				}
		return matrix;
	}
  public int [][] getMatrix ()
  {
    return graph;
  }


}
