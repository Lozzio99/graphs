package graphs.src;

class ChromaticMin
{
  public static Lowest min;
  private int count;
  private int possible ;
  public int [] solution ;
  public static int [] path;
  public int [][] graph;
  public GiveHint allSolutions [] ;

  public static double start ;

  public ChromaticMin ()
  {
    start = System.nanoTime();
    count = 0;
    min = null;
    solution= null;
    path = null;
    graph= null;
    allSolutions = new GiveHint [0];
  }
  public void add (Lowest x )
  {

    if (x != null)
    {

      if ((count == 0)||(x.chromatic() <= min.chromatic()))
      {
        possible ++;
        allSolutions = new GiveHint[possible];
        allSolutions[possible-1] = new GiveHint(x.getRouteB(),x.colouredArray());

        System.out.print(((System.nanoTime()-start)/(1000000000)) + " seconds ");
        System.out.println(" iteration > "+ (count+1) + "|| cn ->" + x.chromatic());
        min = x;
        solution = x.colouredArray();
        int [] oo =x.getRouteB();
        path = oo;
        graph= x.getGraph();
      }
      count++;
    }
  }
  public Lowest getMinimum()
  {
    System.out.println("possible solutions for the lowest>"+possible);
    return min;
  }
  public GiveHint [] getHint()
  {
    return allSolutions;
  }

  public int []  getSolution()
  {
    return solution;
  }
  public int [] getRouteB()
  {
    return path;
  }
  public int [][] getGraph()
  {
    return graph;
  }



  public class GiveHint
  {
    public int [] route_followed;
    public int [] all_colours;
    public GiveHint(int [] route, int [] solution)
    {
      route_followed = route;
      all_colours = solution;
    }
  }

}
