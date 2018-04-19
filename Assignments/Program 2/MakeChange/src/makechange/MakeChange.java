// Programmer: Ian Piskulic
// Date: 6/21/16
// Purpose: To make a change calculator used to add up change

package makechange;
import java.util.Scanner;

public class MakeChange {

    static Scanner sc = new Scanner(System.in);
    static int qoh = 0,doh = 0,noh = 0,poh = 0;
    static boolean useset = false;
    
    public static void main(String[] args) {
        int cents;
        
        // Welcomes the user and gets the amount of change
        System.out.println("Welcome to the Make Change calculator!\n");
        getCoinSet();
        cents = getCents();
        
        // Loops until the user quits
        while (cents != 0) {
            if (cents <= 100) {                
                if (useset) {
                    perfectChange(cents);
                }
                else {
                    makeChange(cents);
                }
            }
            else if (cents == 101) {
                getCoinSet();
            }
            else if (cents == 102) {
                allOutput();
            }
            else {
                System.out.println("Unknown cents value.");
            }
            cents = getCents();
        } // End of while
        
        System.out.println("\nThanks for using the Make Change calculator!");
    }
   
    public static int getCents() {
        int c = 0;
        
        do {
            try {
                // Gets the amount of change
                System.out.print("\nChange? (1-100 cents, 101=new coin set, "
                    + "102=all, 0=quit): ");
                c = sc.nextInt();
                sc.nextLine();
                
                // Checks the amount to see if it is valid
                if (c < 0) {
                    System.out.println("Amount of change cannot be negative.");
                }
                else if (c > 102) {
                    System.out.println("0-102 only please.");
                }
            }
            catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
                sc.nextLine();
                c = -1;
            }
        } while(c < 0 || c > 102);
        
        return c;
    }
    
    public static void makeChange(int cents) {
        int q,d,n,p,r = cents;
        
        // Quarters
        q = r / 25;
        r -= q * 25;
        
        // Dimes
        d = r / 10;
        r -= d * 10;
        
        // Nickels
        n = r / 5;
        r -= n * 5;
        
        // Pennies
        p = r;
        
        // Prints the result
        System.out.println("For " + cents + " cent(s), I give: " + q + 
            " quarters, " + d + " dimes, " + n + " nickels, " + p + " pennies");
    }
    
    public static void perfectChange(int cents) {
        int q,d,n,p,r = cents;
        
        // Quarters
        q = r / 25;
        if (q > qoh) { q = qoh; }
        r -= q * 25;
        
        // Dimes
        d = r / 10;
        if (d > doh) { d = doh; }
        r -= d * 10;
        
        // Nickels
        n = r / 5;
        if (n > noh) { n = noh; }
        r -= n * 5;
        
        // Pennies
        p = r;
        if (p > poh) { p = poh; }
        r -= p;
        
        // Checks that remainder is zero
        if (r == 0) {
            System.out.println("For " + cents + " cent(s), I give: " + q + 
                " quarters, " + d + " dimes, " + n + " nickels, " + p + " pennies"
                + ", leaving: " + (qoh-q) + " quarters, " + (doh-d) + " dimes, "
                + (noh - n) + " nickels, " + (poh-p) + " pennies.");
        }
        else {
            System.out.println("I could not make change for: " + cents +
                " (I am short: " + r + " cent(s))");
        }

    }
    
    public static int getCoins(String cointype) {
        int ccount = 0;
        
        // Loops until the amount is valid
        do{
            try {
                System.out.print("How many " + cointype + " do you have?: ");
                ccount = sc.nextInt();
                if (ccount < 0){
                    System.out.println("Coin counts must be non-negative.");
                }
            }
            catch (Exception e) {
                System.out.println("Input error: " + e.getMessage());
                sc.nextLine();
                ccount = -1;
            }
        } while (ccount < 0);

        return ccount;
    }
    
    public static void getCoinSet() {
        String choice;
        
        // Asks if you want to use a set of coins
        System.out.print("Specific set of coins? (Y/N): ");
        choice = sc.nextLine();
        
        // If yes, user types in the amount for each coin type
        if (!choice.isEmpty() && choice.substring(0,1).equals("Y")) {
            qoh = getCoins("quarters");
            doh = getCoins("dimes");
            noh = getCoins("nickels");
            poh = getCoins("pennies");
            useset = true;
        }
        else {
            qoh = doh = noh = poh = 0;
            useset = false;
        }
        
    }
    
    public static void allOutput() {
        if (useset) {
            for (int c = 1; c <= 100; c++) {
                perfectChange(c);
            }
        }
        else {
            for (int c = 1; c <= 100; c++) {
                makeChange(c);
            }
        }
    }
}
