package test;

public class StringCheck {
    public static void main(String[] args) {
        String input = "123.45"; // Change this to test other strings
        
        if (isNumber(input)) {
            System.out.println(input + " is a number.");
        } else if (isCharacter(input)) {
            System.out.println(input + " is a character.");
        } else {
            System.out.println(input + " is neither a number nor a character.");
        }
    }

    public static boolean isNumber(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // Matches integers and floats (both positive and negative)
    }

    public static boolean isCharacter(String str) {
        return str.matches("[a-zA-Z]"); // Matches a single character (both uppercase and lowercase)
    }
}
