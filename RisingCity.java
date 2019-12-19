import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class RisingCity {

    public static void main(String[] args) throws Exception {

        String inputFileName = "/Users/gowtham/Desktop/Sample_input1.txt";      //The file name of input.
        String outputFileName = "/Users/gowtham/Desktop/output_file.txt";       //The file name of output.

        File inputFile = new File(inputFileName);
        Scanner scanner = new Scanner(inputFile);

        String output = new String("");
        CommandParser parser = new CommandParser();     //Instance of the parser for the file.
        CommandParser cmd = null;       //Instance to hold the values of parsed command from the file.

        PrintWriter writer = new PrintWriter(outputFileName);

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
                if (globalTime == cmd.getInsertionTime()) {
                    switch (cmd.getCmd()) {

                        case "Insert":
                            RBNode rbNode = rbTree.addNode(cmd.getArg1(), cmd.getArg2());
                            minHeap.addNode(0, rbNode);
                            break;
                        case "PrintBuliding":
                            output = rbTree.print(cmd.getArg1());
                            writer.println(output);
                            //Write to File
                            break;
                        case "PrintBuliding2":
                            output = rbTree.print(cmd.getArg1(), cmd.getArg2());
                            writer.println(output);
                            break;
                    }
                    cmd = scanner.hasNextLine() ? parser.parse(scanner.nextLine()) : null;
                } else
                    break;
            }

            /**
             * If the executed time for a building becomes the total time needed for it's construction, we remove the respective node from Red-Black tree.
             */
            if (minBuildingNode != null && minBuildingNode.getTotalTime() - minBuildingNode.getExecTime() == 0) {
                int index = minHeap.getIndex(minBuildingNode);
                writer.println("(" + minBuildingNode.getBuildingNum() + ", " + globalTime + ")" + " ");
                rbTree.remove(minBuildingNode.getBuildingNum());
                minBuildingNode = minHeap.getMin();
                minHeap.remove(0);
                timeElapsedForMinBuilding = 0;
            }
            /**
             * If we construct a building for continuously 5 days, then we re-insert the node into min-heap and again choose a node with minimum executed time.
             */
            else if (timeElapsedForMinBuilding % 5 == 0) {

                if(minBuildingNode!=null)
                    minHeap.addNode(minBuildingNode.getExecTime(), minBuildingNode.rbNode);

                minBuildingNode = minHeap.getMin();
                minHeap.remove(0);
                timeElapsedForMinBuilding = 0;
            }

            /**
             * If there exists a building with minimum executed time, we then increase it's executed time by 1 day.
             */
            if (minBuildingNode != null) {
                minBuildingNode.execTime+=1;
            }

            /**
             * We increase the counter for working a building with minimum executed time.
             */
            if(minBuildingNode!=null)
                timeElapsedForMinBuilding++;

            /**
             * We increase the globalTime counter.
             */
            globalTime++;
        }

        writer.close();
    }


}

