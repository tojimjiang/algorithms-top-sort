import java.io.*;
import java.util.*;

public class Solution {

    // Vertex Class
    static class vertex {
        public int value;
        public int inDegree;
        public int outDegree;
        ArrayList<vertex> nextTo = new ArrayList<vertex>();

        //Constructor
        public vertex(int value){
            this.value = value;
            this.inDegree = 0;
            this.outDegree = 0;
        }
    }

    // Graph Class
    static class graph {
        int graphSize;
        boolean vertexBool[];
        vertex vertexList[];

        //Constructor
        public graph(int value){
            this.graphSize = value;
            this.vertexBool = new boolean[value + 1];
            this.vertexList = new vertex[value + 1];
        }

        // Form and Edge between two steps
        public void addEdge(int doFirst, int doLater){
            vertex vtxFirst;
            vertex vtxLater;

            // Check if first step Exists or DNE
            if (vertexBool[doFirst] != true) {
                vtxFirst = new vertex(doFirst);
                vertexList[doFirst] = vtxFirst;
                vertexBool[doFirst] = true;
            }
            else {
                vtxFirst = vertexList[doFirst];
            }

            // Check if second step Exists or DNE
            if (vertexBool[doLater] != true) {
                vtxLater = new vertex(doLater);
                vertexList[doLater] = vtxLater;
                vertexBool[doLater] = true;
            }
            else {
                vtxLater = vertexList[doLater];
            }

            // Check for Existing edges, and form edge if non existent
            if (!vtxLater.nextTo.contains(vtxFirst)) {
                vtxLater.nextTo.add(vtxFirst);
            }
            if (!vtxFirst.nextTo.contains(vtxLater)) {
                vtxFirst.nextTo.add(vtxLater);
            }

            // Increment edge counters
            vtxFirst.outDegree++;
            vtxLater.inDegree++;
        }

        // All in One Function for TopSort AND Print of Results (via Aux Func)
        public void topSortAndPrint(){
            // ZeroVerticies in a Queue, basically a minHeap in Java
            PriorityQueue<Integer> queue = new PriorityQueue<Integer>(graphSize);
            // Make the Queue
            for (int index = 0; index < graphSize; index++){
                int callIndex = index + 1;
                if(vertexList[callIndex].inDegree == 0) {
                    queue.add(vertexList[callIndex].value);
                }
            }

            // Empty list of OrderedVertices
            ArrayList<Integer> ordList = new ArrayList<Integer>();

            // Follows Pseduocode Example
            while (!queue.isEmpty()) {
                // Poll is the pop equivalent in a PriorityQueue
                Integer index = queue.poll();
                vertex holdVertex = vertexList[index];
                ordList.add(holdVertex.value);

                // Run though all neighboors
                for (int runCount = 0; runCount < holdVertex.nextTo.size(); runCount++){
                    if(holdVertex.nextTo.get(runCount) != null) {
                        // Decrementer
                        holdVertex.nextTo.get(runCount).inDegree = holdVertex.nextTo.get(runCount).inDegree - 1;
                        // If Vertex is 0 add it to Queue
                        if (holdVertex.nextTo.get(runCount).inDegree == 0) {
                            queue.add(holdVertex.nextTo.get(runCount).value);
                        }
                    }
                }
            }
            // Print Out Solution by calling aux function
            printArrayList(ordList,graphSize);
        }
    }

    static public void printArrayList(ArrayList<Integer> list2Print, int gSize) {
        // If the graph has more steps than the ordList, then there are not enough connections
        if(list2Print.size() + 1 <= gSize) {
            System.out.println(-1);
        }
        // Otherwise printout from the sorted out arraylist.
        else{
            for(int printCount = 0; printCount < list2Print.size(); printCount++){
                System.out.print(list2Print.get(printCount) + " ");
            }
        }
    }

    public static void main(String[] args) {
        // Read input
        Scanner readin = new Scanner(System.in);

        // Read First Line for 2 inputs and break apart
        String line = readin.nextLine();
        String[] line_split = line.split(" ");
        int stagesN = Integer.valueOf(line_split[0]);
        int relationsM = Integer.valueOf(line_split[1]);

        // Make a graph to store information based on parameters passed in from 1st line
        graph stepsGraph = new graph(stagesN);

        //Run this N times times
        for (int inputCount = 0; inputCount < relationsM; inputCount++) {
            // Read input like above
            line = readin.nextLine();
            line_split = line.split(" ");
            // Break into parts
            int initalInt = Integer.valueOf(line_split[0]);
            int afterInt = Integer.valueOf(line_split[1]);
            // Call above function with current graph
            stepsGraph.addEdge(initalInt, afterInt);
        }
        // Once all input is in, call TopSort&Print
        stepsGraph.topSortAndPrint();
    }
}