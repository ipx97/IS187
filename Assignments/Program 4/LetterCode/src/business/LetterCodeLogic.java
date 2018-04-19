// Programmer: Ian Piskulic
// Date : 7/6/16
// Purpose: To encode and decode messages

package business;

public class LetterCodeLogic {
    public static String Encode(String msg) {
        String result = "";
        String m = msg.toUpperCase(); // Ensures correct format
        char c;
        int x;
        
        for (int i = 0; i < m.length(); i++) {
            c = m.charAt(i);
            x = c;
            if (x == 32) {
                x = 0; // Spaces = zero in cypher
            }
            else {
                x -= 64;
                if (x < 1 || x > 26) {
                    // Original character not A-Z
                    x = 99;
                }
            }
            result += String.valueOf(x) + " ";
        }
        
        return result;
    }
    
    public static String Decode(String msg) {
        String result = "";
        int x;
        char c;
        
        String[] nums = msg.split(","); // Split the input message
        for (int i = 0; i < nums.length; i++) {
            // If invalid input, set to 99
            try {
                x = Integer.parseInt(nums[i]);
            }
            catch (Exception e) {
                x = 99;
            }

            // Decode the seperated numbers
            if (x == 0) {
                c = ' ';
            }
            else if (x < 1 || x > 26) {
                c = '?';
            }
            else {
                x += 64;
                c = (char) x;
            }
            result += c;
        }
        
        return result;
    }
}
