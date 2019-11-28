/**
 * @author Gowtham
 * An utility class to parse the lines from the input file and return command object consisting of the Insertion time, Command and the arguments.
 */
public class CommandParser {
    String cmd;     //Command to be performed: Insert, PrintBuilding, PrintBuilding2 {PrintBuilding with 2 args}.
    int insertionTime;      // Time at which the command is to be executed.
    int arg1;       //First argument of the command.
    int arg2;       //Second argument of the command.

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void setInsertionTime(int insertionTime) {
        this.insertionTime = insertionTime;
    }

    public void setArg1(int arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(int arg2) {
        this.arg2 = arg2;
    }

    public String getCmd() {
        return cmd;
    }

    public int getInsertionTime() {
        return insertionTime;
    }

    public int getArg1() {
        return arg1;
    }

    public int getArg2() {
        return arg2;
    }

    /**
     * To parse a line read from a file into a command object, that consists of the command name, it's arguments and time.
     * @param command: The line read from the input file.
     * @return: An instance of the commandParser object with the set values of command, time and arguments.
     */
    public CommandParser parse(String command){
        CommandParser commandParser = new CommandParser();
        String[] parsedCommand = command.split("[: (,)]+");     //We parse the input lin from the file using a regular expression.
        commandParser.setInsertionTime(Integer.parseInt(parsedCommand[0]));     //Parse and store insertion time.
        commandParser.setCmd(parsedCommand[1]);      //Parse and store command.
        commandParser.setArg1(Integer.parseInt(parsedCommand[2])); //Parse and store first argument.

        if(parsedCommand.length==4){     //Parse and store 2nd argument if it exists, either for insert/PrintBuilding for a range.
            commandParser.setArg2(Integer.parseInt(parsedCommand[3]));
            if(commandParser.getCmd().equals("PrintBuilding"))      //If the command is a printBuilding with 2 arguments we append 2 to it's name.
                commandParser.setCmd("PrintBuilding2");
        }

        return commandParser;
    }

}
