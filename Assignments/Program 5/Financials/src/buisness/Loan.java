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
public class Loan {
    public static final String AMTDESC = "Loan Amount:";
    public static final String RESULTDESC = "Monthly Payment:";
    
    private double amt, rate, mopmt;
    private int term;
    private String errmsg;
    private double[] bbal, ichg, ebal;
    private boolean built;
    
    public Loan(double amt, double rate, int term) {
        this.amt = amt;
        this.rate = rate;
        this.term = term;
        this.mopmt = 0;
        this.built = false;
        
        if (isValid()) {
            buildLoan();
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
    private void buildLoan() {
        double morate, denom;
        try {
            // Create the double arrays for the table
            this.bbal = new double[this.term];
            this.ebal = new double[this.term];
            this.ichg = new double[this.term];
            
            morate = this.rate / 12.0;
            denom = Math.pow(1+morate,this.term) - 1.0;
            this.mopmt = (morate + morate/denom) * this.amt;
            
            bbal[0] = this.amt;
            for (int i=0; i < this.term; i++) {
                if (i > 0) {
                    this.bbal[i] = this.ebal[i-1];
                }
                this.ichg[i] = this.bbal[i] * morate;
                this.ebal[i] = this.bbal[i] + this.ichg[i] - this.mopmt;
            }
            
            this.built = true;
        }
        catch (Exception e) {
            this.errmsg = " Build error: " + e.getMessage();
            this.built = false;
        }
    } // End of buildLoan
    
    public String getErrorMsg() {
        return this.errmsg;
    }
    public double getMonthlyPmt() {
        if (!this.built) {
            buildLoan();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.mopmt;
    }
    public double getPrinciple() {
        return amt;
    }
    public double getIntRate() {
        return rate;
    }
    public int getTerm() {
        return term;
    }
    public double getBegBal(int month) {
        if (!this.built) {
            buildLoan();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.bbal[month - 1];
    }
    public double getIntCharg(int month) {
        if (!this.built) {
            buildLoan();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.ichg[month - 1];
    }
    public double getEndBal(int month) {
        if (!this.built) {
            buildLoan();
        }
        if (!this.built) {
            return -1.0;
        }
        return this.ebal[month - 1];
    }
}
