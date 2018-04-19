/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness;

/**
 *
 * @author Ian Piskulic
 */
public class Annuity {
    public static final String AMTDESC = "Monthly Deposit:";
    public static final String RESULTDESC = "Final Annuity Value:";
    
    private double amt, rate;
    private int term;
    private String errmsg;
    private double[] bbal, iearn, ebal;
    private boolean built;
    
    public Annuity (double amt, double rate, int term) {
        this.amt = amt;
        this.rate = rate;
        this.term = term;
        this.built = false;
        
        if (isValid()) {
            buildAnnuity();
        }
        else {
            this.amt = 0;
            this.rate = 0;
            this.term = 0;
        }
    } // End of constructor
    private boolean isValid() {
        this.errmsg = "";
        if (this.amt <= 0) {
            this.errmsg += "Bad amount (<=0): " + this.amt;
        }
        if (this.rate < 0) {
            this.errmsg += " Bad rate (<0): " + this.rate;
        }
        if (this.term <=0) {
            this.errmsg += " Bad term (<=0): " + this.term;
        }
        return this.errmsg.isEmpty();
    }
    private void buildAnnuity() {
        try {
            this.bbal = new double[this.term];
            this.iearn = new double[this.term];
            this.ebal = new double[this.term];
            
            double morate = this.rate / 12.0;
            this.bbal[0] = 0;
            for (int i=0; i < this.term; i++) {
                if (i > 0) {
                    // Past first month
                    this.bbal[i] = this.ebal[i-1];
                }
                this.iearn[i] = (this.bbal[i] + this.amt) * morate;
                this.ebal[i] = this.bbal[i] + this.amt + this.iearn[i];
            }
            this.built = true;
        }
        catch (Exception e) {
            this.errmsg += " Build error: " + e.getMessage();
            this.built = false;
        }
    } // End of buildAnnuity
    
    public double getDeposit() {
        return this.amt;
    }
    public double getRate() {
        return this.rate;
    }
    public int getTerm() {
        return this.term;
    }
    public String getErrorMsg() {
        return this.errmsg;
    }
    public double getFinalValue() {
        if (!this.built) {
            buildAnnuity();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.ebal[this.term - 1];
    }
    public double getBegBal(int month) {
        if (!this.built) {
            buildAnnuity();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.bbal[month - 1];
    }
    public double getIntEarned(int month) {
        if (!this.built) {
            buildAnnuity();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.iearn[month - 1];
    }
    public double getEndBal(int month) {
        if (!this.built) {
            buildAnnuity();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.ebal[month - 1];
    }
}

