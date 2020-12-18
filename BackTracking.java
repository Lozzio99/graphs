package graphs.src;

public class BackTracking implements Lowest
{
  public int [][] graphB;
  public int [] xB;
  public int [] pathB;
  public int t;
  public int max= 0;

  public BackTracking (int [][] matrix, int [] route)
  {
    int [] solution = new int [matrix.length];
    xB = solution;
    graphB = matrix;
    pathB = new int [route.length];
    System.arraycopy(route, 0, pathB, 0, route.length);
    t = matrix.length;
    int k = 0;
		graphColour(k);
  }
  /**
	method to apply the backtracking algorithm
	@param k the index to be coloured first
	return one valid color per index via print method
	*/
  public void graphColour (int k )
	{
		for (int c = 1; c<= t; c++)
			{
				if (iCanAssign(k,c))
				{
					//if the colour is valid according to the adjacency matrix
						xB[k]=c;
						//it get assigned at the position
						//and recursively go again for the next position
						if ((k+1)<t)
            {
              graphColour(k+1);
            }
						  return;
				}
			}
	}
	/**
	method to check if the selected colour respect the adjacency parameters
	@param k the position to be checked
	@param c the colour to be checked
	@return true if the colour can be assigned at that place
	*/
	public boolean iCanAssign (int k, int c)
	{
		for (int j = 0; j< t; j++)
		{
			if (graphB[k][j]==1 && c==xB[j])
			{
				return false;
			}
		}
		return true;
	}
  public int [][] getGraph()
  {
    return graphB;
  }
  public int chromatic()
  {
    max = 0;
    for (int i = 0; i<xB.length ; i++)
    {
      if (xB[i]>max)
      {
        max = xB[i];
      }
    }
    return max;
  }
  public int [] colouredArray()
  {
      return xB;
  }
  public int [] getRouteB()
  {
    return pathB;
  }
}
