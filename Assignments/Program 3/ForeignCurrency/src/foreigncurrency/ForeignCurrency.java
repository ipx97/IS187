// Programmer: Ian Piskulic
// Date: 6/28/16
// Purpose: To make a foreign currency calculator

package foreigncurrency;
import java.util.Scanner;
import java.text.NumberFormat;

public class ForeignCurrency {

    static Scanner sc = new Scanner(System.in);
    static double rGBP, rEUR, rJPY, rCAD, rRUB;

    public static void main(String[] args) {
        System.out.println("Welcome to the Foreign Currency Calculator!\n");
        getRates();
        doValuation();
        System.out.println("\nThanks for using the Foreign Currency Calculator!");
    }
    
    public static void getRates() {
        System.out.println("Please enter currency rates per US $:");
        
        rEUR = getOneRate("EUR");
        rGBP = getOneRate("GBP");
        rJPY = getOneRate("JPY");
        rCAD = getOneRate("CAD");
        rRUB = getOneRate("RUB");
    }
    
    public static double getOneRate(String currency) {
        double rate = 0;
        do {
            try {
                // Get the rate
                System.out.print(currency + ":");
                rate = sc.nextDouble();
                
                // Check if the rate is valid
                if (rate <= 0) {
                    System.out.println("Illegeal rate: rate must be greater than zero.");
                }
            }
            catch (Exception e) {
                System.out.println("Illegal rate: must be positive value.");
                sc.nextLine();
                rate = 0;
            }
        } while(rate <= 0);
        
        return rate;
    }
    
    public static void doValuation() {
        int choice, quantity;
        int uEUR = 0, uGBP = 0, uJPY = 0, uCAD = 0, uRUB = 0;
        double cEUR = 0, cGBP = 0, cJPY = 0, cCAD = 0, cRUB = 0;
        double cVal = 0, totalCVal = 0;
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        
        // Gets the choice
        choice = getChoice();
        while (choice != 0) {
            switch (choice) {
                case 1: 
                    quantity = getQty("Euros");
                    cVal = quantity * rEUR;
                    uEUR += quantity;
                    cEUR += cVal;
                    System.out.println(quantity + " Euros has a value of: " + 
                        curr.format(cVal));
                    break;
                case 2:
                    quantity = getQty("Pounds Sterling");
                    cVal = quantity * rGBP;
                    uGBP += quantity;
                    cGBP += cVal;
                    System.out.println(quantity + " Pounds has a value of: " + 
                        curr.format(cVal));
                    break;
                case 3:
                    quantity = getQty("Yen");
                    cVal = quantity * rJPY;
                    uJPY += quantity;
                    cJPY += cVal;
                    System.out.println(quantity + " Yen has a value of: " + 
                        curr.format(cVal));
                    break;
                case 4:
                    quantity = getQty("Canadian dollars");
                    cVal = quantity * rCAD;
                    uCAD += quantity;
                    cCAD += cVal;
                    System.out.println(quantity + " Canadian dollars has a value of: " + 
                        curr.format(cVal));
                    break;
                case 5:
                    quantity = getQty("Russian Rubles");
                    cVal = quantity * rRUB;
                    uRUB += quantity;
                    cRUB += cVal;
                    System.out.println(quantity + " Russian Rubles has a value of: " + 
                        curr.format(cVal));
                    break;
                case 9:
                    getRates();
                    cVal = 0;
                    break;
                default:
                    System.out.println("Unknown currency type.");
                    break;
            } // End switch           
            choice = getChoice();
            totalCVal += cVal;
        }
        // Print off a summary of costs
        System.out.println("\nEUR: " + uEUR + " units costing " + curr.format(cEUR));
        System.out.println("GBP: " + uGBP + " units costing " + curr.format(cGBP));
        System.out.println("JPY: " + uJPY + " units costing " + curr.format(cJPY));
        System.out.println("CAD: " + uCAD + " units costing " + curr.format(cCAD));
        System.out.println("RUB: " + uRUB + " units costing " + curr.format(cRUB));
        System.out.println("The total value of your purchases was: " + curr.format(totalCVal));
        
    }
    
    public static int getChoice() {
        int choice = -1;
        do {
            try {
                // Get the choice
                System.out.print("\nSelect Currency (1=EUR, 2=GBP, 3=JPY, 4=CAD, 5=RUB, 9=New Rates, 0=Quit): ");
                choice = sc.nextInt();
                
                // Check if the choice is valid
                if (choice < 0 || (choice > 5 && choice != 9)) {
                    System.out.println("Invalid choice: From 0-5 or 9 only");
                    choice = -1;
                }
            }
            catch (Exception e) {
                System.out.println("Input input: not an integer 0-5 or 9");
                sc.nextLine();
                choice = -1;
            }
        } while(choice < 0);
        
        return choice;
    }
    
    public static int getQty(String currency) {
        int qty = -1;
        do {
            try {
                // Get the rate
                System.out.print("How many " + currency + " are you buying?: ");
                qty = sc.nextInt();
                
                // Check if the rate is valid
                if (qty < 0) {
                    System.out.println("Quantity must be non-negative.");
                }
            }
            catch (Exception e) {
                System.out.println("Input quantity: must be a non-negative integer.");
                sc.nextLine();
                qty = -1;
            }
        } while(qty < 0);
        
        return qty;
    }
}
