package graphs.src;

public class Swap
{
  public int [][] graph = new int [0][0];
  public int [] path= new int [0];
  /**
  This class creates an object referencing to a random variation of a given
  adjacency matrix
  @param matrix the initial matrix (adjacency represented by 1)
  return the reference of the new random matrix (accordingly to adjacency
  parameters)
  first we set the new graph starting from null size
  */
  public Swap (int [][] matrix)
  {
    //we create an array to store the path to be followed
    int [] route = new int [matrix.length];
    //we create an array storing the number of edges per each vertex
    int [] numberOfEdges = checkArray(matrix);
    for (int i = 0; i< route.length; i++)
    {
      // we establish the path accordingly to the highest number of edges
      route [i]= findMax(numberOfEdges);
    }
    int [][] clone = new int [matrix.length][matrix.length];
    //we set again the adjacency parameters
    for (int k = 0;k < route.length;k++)
    {
      for (int j= 0;j< route.length; j++)
      {
        if (matrix[route[j]][route[k]]==1)
        {
          clone[j][k]=1;
        }
      }
    }
    //address the reference of the new matrix generated so far
      graph = clone;
      path= route;

  }
  /**
  method to give the reference of the clone created
  @param clone the new one with random order
  @return the reference of the public graph
  */
  public int [][] changeMatrix ()
  {
    return graph;
  }
  /**
  method to check the number of edges per each vertex
  @param graph with adjacency parameters
  @return the array with the number of edges per each vertex
  */
  public static int [] checkArray(int [][] graph)
  {
     int [] numberOfEdgesForEachVertices = new int [graph.length];
     for (int i = 0; i< graph.length; i++){
         for(int j = 0; j< graph.length; j++){
           int k = 0;
           int x =1;
             if (graph[i][j]==1)
               {
                 numberOfEdgesForEachVertices[i]=numberOfEdgesForEachVertices[i] +x;
                 x++;
               }
               else
               {
                 numberOfEdgesForEachVertices[i]=numberOfEdgesForEachVertices[i]+ k;
               }
             }
         } return numberOfEdgesForEachVertices;
   }
   /**
   method to find the highest value in an array
   @param numbOfEdges the array with the number of edges per each vertex
   @return the index with the maximum number of edges
   */
    public int findMax (int [] numbOfEdges )
     {
       int maximum = 0;
       int index = 0;
       for (int i = 0;i<numbOfEdges.length; i++)
        {
           if (numbOfEdges[i] >= maximum)
           {
             maximum = numbOfEdges[i];
             index = i;
           }
       }
       //set the picked value as -1 to not to be counted the next iteration
       numbOfEdges[index]= -1;
       return index;
     }
     public int [] getRoute()
     {
       return path;
     }

}
