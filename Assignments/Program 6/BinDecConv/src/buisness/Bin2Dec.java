// Programmer: Ian Piskulic
// Date: 7/18/2016
// Purpose: To convert binary to decimal and decimal to binary

package buisness;

import java.util.ArrayList;

/**
 *
 * @author Ian Piskulic
 */
public class Bin2Dec {
    private int result;
    private String errmsg;
    private ArrayList<String> steps;
    
    public Bin2Dec(String value) {
    errmsg = "";
        try {
            // Checks if binary and positive
            if (!value.matches("[01]+")) {
                errmsg = "Value is not binary.";
            }
            
            // Converts the binary number to decimal
            else {
                result = 0;
                steps = new ArrayList<String>();
                convert(value);
            }
        }
        catch (NumberFormatException e) {
            errmsg = "Illegal value: not an integer.";
        }
    }
    
    public String getResult() {
        return String.valueOf(result);
    }
    
    public String getErrorMessage() {
        return errmsg;
    }
    
    public ArrayList<String> getSteps() {
        return steps;
    }
    
    private void convert(String value) {
        value = new StringBuilder(value).reverse().toString();
                
        // Goes through each character in the string
        for (int i = 0; i <= value.length() - 1; i++) {
            int n = Character.getNumericValue(value.charAt(i));               
            if (n == 1) {
                int dec = (int)Math.pow(2, i);
                steps.add("There is a(n) " + dec + " in the value (2^" + i + ")");
                result += dec;
            }
        }
    }
    
}
