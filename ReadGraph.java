package graphs.src;
import java.io.*;
import java.util.*;
class ColEdge {
    int u;
	int v;
}

// but where should i put the path of my graph ?  Argument when you run your code e.g java ReadGraph ./graphs/graph01_2020.txt ( for the default ReadGraph code)
//wait a sec look this my cod that I want to put in here
public class ReadGraph {
    public final static boolean DEBUG = false;
    public final static String COMMENT = "//";

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Error! No filename specified.");
            System.exit(0);
        }

        /**
         * e.g ./graphs/graphXXXX_2020.txt 1 1 or ./graphs/graph01_2020.txt
         * If XXXX is specified, it'll automatically fill XXXX with all the iterations.
         * If XXXX is not present, it'll search for the specific file and only use that.
         */
        String inputfile = args[0];

        /**
         * The amount of times each file should be repeated
         * e.g 100 or 1000
         */
        int iterations = Integer.parseInt(args[1]);

        /**
         * The amount of files that should be tested.
         * e.g 20 (from 1 to 20)
         * 
         * This will only be used when XXXX is specified as inputfile.
         */
        int fileCount = Integer.parseInt(args[2]);

        long startTime = System.nanoTime();

        if (inputfile.contains("XXXX")) {
            for (int i = 1; i <= fileCount; i++) {
                String tempFile = inputfile.replace("XXXX", String.format("%02d", i));
    
                for (int j = 0; j < iterations; j++) {
                    start(tempFile);
                }
            }
        } else {
            for (int j = 0; j < iterations; j++) {
                start(inputfile);
            }
        }

       

        long endTime = System.nanoTime();
        double elapsedTimeInSecond = (double) (endTime - startTime) / 1_000_000_000;

		System.out.println(String.format("%s iterations took in total: %f s", iterations, elapsedTimeInSecond));
    }
    
    public static void start(String inputfile) {
        boolean seen[] = null;

        //! n is the number of vertices in the graph
        int n = -1;

        //! m is the number of edges in the graph
        int m = -1;

        //! e will contain the edges of the graph
        ColEdge e[] = null;

        try {
            FileReader fr = new FileReader(inputfile);
            BufferedReader br = new BufferedReader(fr);

            String record = new String();

            //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while ((record = br.readLine()) != null) {
                if (record.startsWith("//")) continue;
                break; // Saw a line that did not start with a comment -- time to start reading the data in!
            }

            if (record.startsWith("VERTICES = ")) {
                n = Integer.parseInt(record.substring(11));
                if (DEBUG) System.out.println(COMMENT + " Number of vertices = " + n);
            }

            seen = new boolean[n + 1];

            record = br.readLine();

            if (record.startsWith("EDGES = ")) {
                m = Integer.parseInt(record.substring(8));
                if (DEBUG) System.out.println(COMMENT + " Expected number of edges = " + m);
            }

            e = new ColEdge[m];

            for (int d = 0; d < m; d++) {
                if (DEBUG) System.out.println(COMMENT + " Reading edge " + (d + 1));
                record = br.readLine();
                String data[] = record.split(" ");
                if (data.length != 2) {
                    System.out.println("Error! Malformed edge line: " + record);
                    System.exit(0);
                }
                e[d] = new ColEdge();

                e[d].u = Integer.parseInt(data[0]);
                e[d].v = Integer.parseInt(data[1]);

                seen[e[d].u] = true;
                seen[e[d].v] = true;

                if (DEBUG) System.out.println(COMMENT + " Edge: " + e[d].u + " " + e[d].v);

            }

            String surplus = br.readLine();
            if (surplus != null) {
                if (surplus.length() >= 2)
                    if (DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '" + surplus + "'");
            }

        } catch (IOException ex) {
            // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file " + inputfile);
            System.exit(0);
        }

        for (int x = 1; x <= n; x++) {
            if (seen[x] == false) {
                if (DEBUG) System.out.println(COMMENT + " Warning: vertex " + x + " didn't appear in any edge : it will be considered a disconnected vertex on its own.");
            }
        }

        //! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
        //! e[1] will be the second edge...
        //! (and so on)
        //! e[m-1] will be the last edge
        //! 
        //! there will be n vertices in the graph, numbered 1 to n

		//! INSERT YOUR CODE HERE!

        int[][] graph = createGraphMatrix(e, n, m); 
       
        
        printMatrix(graph);
        int edges [] = 	calculateEdges(graph);
        
        //System.out.println(Arrays.toString(edges));

        int sortingEdges [] = sortingEdgesArray(edges);
        //System.out.println(" this is the sorting the index of edges   :");
        //System.out.println(Arrays.toString(sortingEdges));

        int [] articesWithColor = coloringVertices(edges,sortingEdges,graph);
        System.out.println("final array which show artices and the colors of them   :"+ Arrays.toString(articesWithColor));
        System.out.println("the Chromatic number is    :   " +  chromaticNumber(articesWithColor) );
        
    };


    public static void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(String.format("%s ", matrix[i][j]));
			}
			System.out.println();
		}
	}


    public static int[][] createGraphMatrix(ColEdge[] vertexEdges, int vertexCount, int edgeCount) {
		int[][] matrix = new int[vertexCount + 1][vertexCount + 1];

		for (int i = 0; i < vertexEdges.length; i++) {
			int vertex = vertexEdges[i].u;
			int relation = vertexEdges[i].v;

			matrix[vertex][relation] = 1;
			matrix[relation][vertex] = 1;
		}

		return matrix;
	}


    //find the edges for each vertices 
    public static int [] calculateEdges(int [][] graph){

        int [] numberOfEdgesForEachVertices = new int [graph.length-1];

        for (int i = 1; i< graph.length; i++){
            for(int j = 1; j< graph.length; j++){
                numberOfEdgesForEachVertices[i-1] =numberOfEdgesForEachVertices[i-1] + graph[i][j];
            }
        }
        
        return numberOfEdgesForEachVertices;
    }

    //sorting the index of the arrays
    public static int [] sortingEdgesArray(int [] arr){
        int[] sortedIndexArr = new int[arr.length];
        sortedIndexArr[0] = 0;

        for(int i=1;i<arr.length;i++){
            int j=i;
            for(;j>=1 && arr[j]>arr[j-1];j--){
                    int temp = arr[j];
                    arr[j] = arr[j-1];
                    sortedIndexArr[j]=sortedIndexArr[j-1];
                    arr[j-1] = temp;
            }
            sortedIndexArr[j]=i;
        }
        return sortedIndexArr;
    }


   // make an array of index from highest to lowest ( THAT'S WEHERE WE FACE WE ISSUE)
    public static int [] coloringVertices(int [] edges, int [] sortingEdges, int [][] graph){
        int[] coloringNumbers = new int [edges.length];
        
        
        for (int c = 1; c < edges.length+2; c++){

            for(int i = 0; i < sortingEdges.length; i++){
                int [] tempEdges = new int[edges[i]];
                int tempIndex = 0;
                boolean isTrue = true;
                
                for(int j = 1 ; j < sortingEdges.length; j++ ){
                    if (graph[sortingEdges[i] + 1 ][j] == 1){
                        tempEdges[tempIndex] = j;
                        tempIndex ++;
                    }
                    
                }

                
                boolean [] isLegalize = new boolean [tempEdges.length];


                for(int k = 0 ; k < tempEdges.length;k++){
                    
                    if(coloringNumbers[tempEdges[k]]  != c && coloringNumbers[tempEdges[k]]  == 0){
                        isLegalize[k] = true;
                    } else if (coloringNumbers[tempEdges[k]] != c && coloringNumbers[tempEdges[k]] != 0){
                        isLegalize[k] = true;
                    }

                }

                for(int y = 0;y <isLegalize.length;y++){
                    if (isLegalize[y] == false){
                         isTrue = false;
                    }
                }

                if(isTrue == true && coloringNumbers[sortingEdges[i]] == 0){
                    coloringNumbers[sortingEdges[i]] = c;
                }

             
            }
        }
        return coloringNumbers;
    }


    // get the chromatic number
    public static int chromaticNumber(int [] arr){
            int max = 0;
            for(int i= 0; i<arr.length; i++){
                if (arr[i] > max){
                    max =arr[i];
                }
            }
            return max;
         }
}

 



