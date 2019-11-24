public class CommandParser {
    String cmd;
    int insertionTime;
    int arg1;
    int arg2;

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

    public CommandParser parse(String command){
        CommandParser commandParser = new CommandParser();
        String[] parsedCommand = command.split("[: (,)]+");
        commandParser.setInsertionTime(Integer.parseInt(parsedCommand[0]));
        commandParser.setCmd(parsedCommand[1]);
        commandParser.setArg1(Integer.parseInt(parsedCommand[2]));

        if(parsedCommand.length==4){
            commandParser.setArg2(Integer.parseInt(parsedCommand[3]));
            if(commandParser.getCmd().equals("PrintBuliding"))
                commandParser.setCmd("PrintBuliding2");
        }

        return commandParser;
    }

}
