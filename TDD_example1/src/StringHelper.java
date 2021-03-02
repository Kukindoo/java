public class StringHelper {
    public String swapLastTwoChars(String s){
        int length = s.length();
        if(length <= 1){
            return s;
        }
        char firstChar = s.charAt(length-2);
        char secondChar = s.charAt(length-1);
        String subString = s.substring(0,length-2);
        return subString + secondChar + firstChar;
    }
}
