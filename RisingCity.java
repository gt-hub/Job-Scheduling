import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class RisingCity {

    public static void main(String[] args) throws Exception{

        String inputFileName = "/Users/gowtham/Desktop/Sample_input1.txt";
        String outputFileName = "/Users/gowtham/Desktop/output_file.txt";

        File inputFile = new File(inputFileName);
        Scanner scanner = new Scanner(inputFile);

        File outputFile = new File(inputFileName);
        String output = new String("");
        CommandParser parser = new CommandParser();
        CommandParser cmd = null;

        PrintWriter writer = new PrintWriter(outputFileName);

        RBTree rbTree = new RBTree();
        MinHeap minHeap = new MinHeap();
        MinHeapNode minBuildingNode = null;
        int globalTime = 0, timeElapsedForMinBuilding = 0;
        while(scanner.hasNextLine()||cmd!=null||!rbTree.isEmpty()){

            if(cmd==null&&scanner.hasNextLine())
                cmd = parser.parse(scanner.nextLine());

            // Process Command when insertion time equals to insertion time.
            while(cmd!=null){
                if(globalTime==cmd.getInsertionTime()){
                    switch (cmd.getCmd()){

                        case "Insert":
                            RBNode rbNode = rbTree.addNode(cmd.getArg1(),cmd.getArg2());
                            minHeap.addNode(0,rbNode);
                            break;
                        case "PrintBuliding":
                            output = rbTree.print(cmd.getArg1());
                            writer.println(output);
                            //Write to File
                            break;
                        case "PrintBuliding2":
                            output = rbTree.print(cmd.getArg1(),cmd.getArg2());
                            System.out.println(output);
                            writer.println(output);
                            //Write to File
                            break;
                    }
                    cmd = scanner.hasNextLine() ? parser.parse(scanner.nextLine()) : null;
                }
                else
                    break;
            }

            //To update the execution time for Min Building
            if(timeElapsedForMinBuilding % 5 == 0){

                if(minBuildingNode!=null && minBuildingNode.getTotalTime() - minBuildingNode.getExecTime() ==0){
                    int index = minHeap.getIndex(minBuildingNode);

                    System.out.println("("+ minBuildingNode.getBuildingNum() +", "+globalTime+")" + " ");

                    writer.println("("+ minBuildingNode.getBuildingNum() +", "+globalTime+")" + " ");
                    rbTree.remove(minBuildingNode.getBuildingNum());
                    minHeap.remove(index);
                }

                minBuildingNode = minHeap.getMin();
                //System.out.println(minBuildingNode.rbNode.buildNum + "" + minBuildingNode.getExecTime());
                timeElapsedForMinBuilding = 0;
            }

            if(minBuildingNode!=null && minBuildingNode.getTotalTime() - minBuildingNode.getExecTime() ==0){
                int index = minHeap.getIndex(minBuildingNode);

                System.out.println("("+ minBuildingNode.getBuildingNum() +", "+globalTime+")" + " ");

                writer.println("("+ minBuildingNode.getBuildingNum() +", "+globalTime+")" + " ");
                rbTree.remove(minBuildingNode.getBuildingNum());
                minHeap.remove(index);
                minBuildingNode = minHeap.getMin();
                timeElapsedForMinBuilding=0;
            }else if(minBuildingNode!=null){
                int index = minHeap.getIndex(minBuildingNode);
                minHeap.increaseKey(index, 1);
            }

            timeElapsedForMinBuilding++;
            globalTime++;
        }

        System.out.println(globalTime-1);
        writer.close();
    }











    /*public static void main(String[] arg){
        RBTree tree = new RBTree();
        MinHeap minHeap = new MinHeap();
        RBNode t;

        t = tree.addNode(2,10);
        minHeap.addNode(0, t);

        t = tree.addNode(3,11);
        minHeap.addNode(0, t);

        t = tree.addNode(1,10);
        minHeap.addNode(0, t);

        minHeap.increaseKey(5);
        minHeap.increaseKey(5);
        minHeap.increaseKey(5);
        minHeap.increaseKey(5);

        minHeap.increaseKey(5);

        tree.addNode(2,11);
        tree.addNode(3,11);
        tree.addNode(4,10);
        tree.addNode(1,15);
        tree.remove(1);
        tree.remove(2);
        tree.remove(3);
        tree.remove(4);
        tree.print();

        tree.print(1,3);
        //minHeap.print();
    }
    */
}
