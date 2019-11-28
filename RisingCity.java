import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Gowtham
 * risingCity is the main class that makes use of the instances of RBTree and MinHeap to solve the risingCity problem.
 */
public class risingCity {

    public static void main(String[] args) throws Exception {

        String inputFileName = new String();
        if(args[0].length()>0)
            inputFileName = args[0];        //The file name of input.
        String outputFileName = "output_file.txt";       //The file name of output.

        File inputFile = new File(inputFileName);
        Scanner scanner = new Scanner(inputFile);


        String output = new String("");     //To store the output before printing it to a File.
        CommandParser parser = new CommandParser();     //Instance of the parser for the file.
        CommandParser cmd = null;       //Instance to hold the values of parsed command from the file.

        PrintWriter writer = new PrintWriter(outputFileName);       //To write the output to the Output file.

        RBTree rbTree = new RBTree();       //Instance of Red-Black tree.
        MinHeap minHeap = new MinHeap();        //Instance of Min-Heap.
        MinHeapNode minBuildingNode = null;     //Node to hold the building with minimum executed time.
        int globalTime = 0;         //Counter to keep track of the total number of days.
        int timeElapsedForMinBuilding = 0;      //Counter to keep track of the number of days we have worked continuously on a building.

        /**
         * To read the file, parse it and execute the commands in it until there no more commands left and all the buildings have been completed.
         */
        while (scanner.hasNextLine() || cmd != null || !rbTree.isEmpty()) {

            /**
             * To read the command from the file.
             */
            if (cmd == null && scanner.hasNextLine())
                cmd = parser.parse(scanner.nextLine());

            /**
             * To parse and execute the respective command when the time matches the global counter.
             */
            while (cmd != null) {
                if (globalTime == cmd.getInsertionTime()) {     //If the time of command is equal to the global time, we execute the command.
                    switch (cmd.getCmd()) {
                        case "Insert":      //We insert the building into Red-black tree and the min-heap.
                            RBNode rbNode = rbTree.addNode(cmd.getArg1(), cmd.getArg2());
                            minHeap.addNode(0, rbNode);
                            break;
                        case "PrintBuilding":       //We print the building tuple from the red-black tree.
                            output = rbTree.print(cmd.getArg1());
                            writer.println(output);
                            //Write to File
                            break;
                        case "PrintBuilding2":      //We print the set of tuples from the red-black tree.
                            output = rbTree.print(cmd.getArg1(), cmd.getArg2());
                            writer.println(output);
                            break;
                    }
                    cmd = scanner.hasNextLine() ? parser.parse(scanner.nextLine()) : null;      //We read the next input line from the file.
                } else
                    break;
            }

            /**
             * If the executed time for a building becomes the total time needed for it's construction, we remove the respective node from Red-Black tree.
             */
            if (minBuildingNode != null && minBuildingNode.getTotalTime() - minBuildingNode.getExecTime() == 0) {
                writer.println("(" + minBuildingNode.getBuildingNum() + ", " + globalTime + ")" + " ");     //We print the building tuple on completion of construction.
                rbTree.remove(minBuildingNode.getBuildingNum());        //We remove that building from Red-Black tree.
                minBuildingNode = minHeap.getMin();     //Now we pick up a new building with minimum execution time.
                minHeap.remove(0);      //After that we remove that minimum building from the min-heap.
                timeElapsedForMinBuilding = 0;      //We reset the continuous time spent on that building construction.
            }
            /**
             * If we construct a building for continuously 5 days, then we re-insert the node into min-heap and again choose a node with minimum executed time.
             */
            else if (timeElapsedForMinBuilding % 5 == 0) {

                if(minBuildingNode!=null)
                    minHeap.addNode(minBuildingNode.getExecTime(), minBuildingNode.rbNode);     //We reinsert the building back into the min-heap.

                minBuildingNode = minHeap.getMin();    //Now we pick up a new building with minimum execution time.
                minHeap.remove(0);      //After that we remove that minimum building from the min-heap.
                timeElapsedForMinBuilding = 0;      //We reset the continuous time spent on that building construction.
            }

            /**
             * If there exists a building with minimum executed time, we then increase it's executed time by 1 day.
             */
            if (minBuildingNode != null) {
                minBuildingNode.execTime+=1;        //We increase the execution of the minimum building by 1.
            }

            /**
             * We increase the counter for working a building with minimum executed time.
             */
            if(minBuildingNode!=null)
                timeElapsedForMinBuilding++;        //We increase the counter for the continuous time spent on the building by 1.

            /**
             * We increase the globalTime counter.
             */
            globalTime++;       //We increase the global time by 1 day for every iteration.
        }
        writer.close();         //Close the output-stream and return the System resources.
    }


}

