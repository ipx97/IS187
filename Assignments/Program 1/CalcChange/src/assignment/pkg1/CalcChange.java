// Programmer: Ian Piskulic
// Date: 6/12/16
// Purpose: To make a change calculator used to add up change

package assignment.pkg1;
import java.util.Scanner;

public class CalcChange {
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {    
        String choice;
        int h,q,d,n,p;
        
        //Welcome the user and ask for change
        System.out.println("Welcome to the Change Calculator!");
        System.out.print("Do you have any change? (Y/N):");
        choice = sc.nextLine();
        
        // If the user has change
        while (!choice.isEmpty() && choice.substring(0,1).equalsIgnoreCase("Y")) {
            
            // Gets the amount of coins for each type
            h = getCoin("half-dollars");
            q = getCoin("quarters");
            d = getCoin("dimes");
            n = getCoin("nickels");
            p = getCoin("pennies");
            sc.nextLine();
            
            // Displays the coin totals
            coinTotal(h,q,d,n,p);
            
            // Checks if you have more change
            System.out.print("Do you have more change? (Y/N)");
            choice = sc.nextLine();
           
        } // End of while
        System.out.println("\nGoodbye!");
    } // End of main
    
    public static int getCoin(String cointype){
        int ccount = 0;
        
        // Loops until the amount is valid
        do{
            try {
                System.out.print("How many " + cointype + " do you have? ");
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
    
    public static void coinTotal(int hd, int qr, int dm, int ni, int pn){
        int dollars,cents,totalCents;
        
        // Perform calculations
        totalCents = (hd * 50) + (qr * 25) + (dm * 10) + (ni * 5) + pn;
        dollars = totalCents / 100;
        cents = totalCents % 100;
        
        // Display results
        System.out.println("You have " + totalCents + " cents " + 
            "which is " + dollars + " dollars and " + cents + " cents.");
    }
}
