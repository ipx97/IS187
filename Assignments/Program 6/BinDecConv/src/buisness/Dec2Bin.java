// Programmer: Ian Piskulic
// Date: 7/18/2016
// Purpose: To convert binary to decimal and decimal to binary

package buisness;

import java.util.ArrayList;

/**
 *
 * @author Ian Piskulic
 */
public class Dec2Bin {
    private String result, errmsg;
    private ArrayList<String> steps;
    
    public Dec2Bin(String value) {
        errmsg = "";
        try {
            long n = Long.parseLong(value);
            if (n < 0) {
                errmsg = "Value must be positive integer.";
            }
            else {
                result = "";
                steps = new ArrayList<String>();
                convertByRecursion(n);
            }
        }
        catch (NumberFormatException e) {
            errmsg = "Illegal value: not an integer.";
        }
    }
    
    public String getErrorMessage() {
        return errmsg;
    }
    
    public String getResult() {
        return result;
    }
    
    public ArrayList<String> getSteps() {
        return steps;
    }
    
    private void convertByRecursion(long n) {
        int r;
        
        r = (int)(n % 2);
        long newval = n/2;
        steps.add(n + " divided by 2 = " + newval + " w/remainder of: " + r);
        if (newval > 0) {
            convertByRecursion(newval);
        }
        result += String.valueOf(r);
    }
}
