package codeJava.server;

public class Token {
    private String name = "";
    private int arguments = 0;

    public Token(String name, int arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArguments() {
        return arguments;
    }

    public void setArguments(int arguments) {
        this.arguments = arguments;
    }

    public String printInfo_String(){
        return getName() + " and I expect " + getArguments() + " arguments";
    }
    public static boolean checkArgumentCount(String entry){


        return false;
    }
}
