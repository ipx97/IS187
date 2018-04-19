/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buisness;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 *
 * @author Ian Piskulic
 */
public class Card {
    private int acctno;
    private double limit, baldue;
    private String errmsg, actmsg;
    
    public Card() {
        this.acctno = 0;        
        this.limit = this.baldue = 0;
        this.errmsg = this.actmsg = "";
        
        while (this.acctno == 0) {
            try {
                this.acctno = (int)(Math.random() * 1000000);
                BufferedReader in = new BufferedReader(
                    new FileReader("CC" + this.acctno + ".txt"));
                in.close();
                this.acctno = 0;
            }
            catch (IOException e) {
                this.limit = 1000;
                if (writeStatus() == true) {
                    // Update log
                    this.actmsg = "Account " + this.acctno + " opened.";
                    if (writeLog(this.actmsg) == false) {
                        // Fail on log update
                        this. limit = this.acctno = 0;
                    }
                }
                else {
                    // Fail on status update
                    this. limit = this.acctno = 0;
                }
            }
        } // End of while
    }
    
    public Card(int a) {
        this.acctno = a;        
        this.limit = this.baldue = 0;
        this.errmsg = this.actmsg = "";
        try {
            BufferedReader in = new BufferedReader(
                new FileReader("CC" + this.acctno + ".txt"));
            this.limit = Double.parseDouble(in.readLine());
            this.baldue = Double.parseDouble(in.readLine());
            in.close();
            this.actmsg = "Account " + this.acctno + " re-opened.";
        }
        catch (IOException e) {
            this.errmsg = "Error opening account: " +
                this.acctno + " " + e.getMessage();
            this.acctno = 0;
            this.limit = this.baldue = 0;
        }
        catch (NumberFormatException e) {
            this.errmsg = "Account file for " + this.acctno +
                " corrupted - account not re-opened.";
            this.acctno = 0;
            this.limit = this.baldue = 0;
        }
    }
    
    private boolean writeStatus() {
        // Create or re-create the CC file
        boolean result = true;
        try {
            PrintWriter out = new PrintWriter(
                new FileWriter("CC" + this.acctno + ".txt"));
            out.println(this.limit);
            out.println(this.baldue);
            out.close();
        }
        catch (IOException e) {
            // Failure to create/re-create file
            result = false;
            this.errmsg = "Write status failure: " + e.getMessage();
        }
        return result;
    }
    
    private boolean writeLog(String msg) {
        boolean result = true;
        try {
            PrintWriter out = new PrintWriter(
                new FileWriter("CCL" + this.acctno + ".txt",true));
            Calendar cal = Calendar.getInstance();
            DateFormat df = DateFormat.getDateTimeInstance();
            String ts = df.format(cal.getTime());
            out.println(ts + ":\t" + msg);
            out.close();
        }
        catch (IOException e) {
            result = false;
            this.errmsg = "Write status failure: " + e.getMessage();
        }
        return result;
    }
    
    public int getAcctNo() {
        return this.acctno;
    }
    
    public double getLimit() {
        return this.limit;
    }
    
    public double getBalDue() {
        return this.baldue;
    }
    
    public double getAvail() {
        return (this.limit - this.baldue);
    }
    
    public String getErrorMsg() {
        return this.errmsg;
    }
    
    public String getActionMsg() {
        return this.actmsg;
    }
    
    public void setCharge(double charge, String desc) {
        this.actmsg = this.errmsg = "";
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        
        if (this.acctno <= 0) {
            this.errmsg = "Charge attempted on unopened account";
            return;
        }
        if (charge <= 0) {
            this.actmsg = "Charge of " + curr.format(charge) + " declined: must be greater than zero";
            writeLog(this.actmsg);
        }
        else if ((this.baldue + charge) > this.limit) {
            this.actmsg = "Charge of " + curr.format(charge) + " declined - insufficient credit.";
            writeLog(this.actmsg);
        }
        else {
            this.baldue += charge;
            if (writeStatus ()) {
                this.actmsg = "Charge of " + curr.format(charge) + " for " + desc + " posted.";
                writeLog(this.actmsg);
            }
            else {
                this.baldue -= charge;
                this.actmsg = "";
            }
        }
    }
    
    public void setPayment(double payment) {
        this.actmsg = this.errmsg = "";
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        
        if (this.acctno <= 0) {
            this.errmsg = "Payment attempted on unopened account";
            return;
        }
        if (payment <= 0) {
            this.actmsg = "Payment of " + curr.format(payment) + " declined: must be greater than zero";
            writeLog(this.actmsg);
        }
        else {
            this.baldue -= payment;
            if (writeStatus()) {
                this.actmsg = "Payment of " + curr.format(payment) + " posted.";
                writeLog(this.actmsg);
            }
            else {
                this.baldue += payment;
                this.actmsg = "";
            }
        }
    }
    
    public void setLimitIncrease(double increase) {
        this.actmsg = this.errmsg = "";
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        
        if (this.acctno <= 0) {
            this.errmsg = "Credit increase attempted on unopened account";
            return;
        }
        if (increase <= 0) {
            this.actmsg = "Credit increase of " + curr.format(increase) +
                " declined: must be greater than zero";
            writeLog(this.actmsg);
        }
        if ((increase % 100) != 0) {
            this.actmsg = "Credit increase of " + curr.format(increase) +
                " declined: must be in increments of 100.";
            writeLog(this.actmsg);
        }
        else if (Math.round(Math.random()) == 0) {
                this.actmsg = "Credit increase of " + curr.format(increase) + " has been denied.";
                writeLog(this.actmsg);
            }
        else {
            this.limit += increase;
            if (writeStatus()) {
                this.actmsg = "Credit increase of " + curr.format(increase) + " has been approved!";
                writeLog(this.actmsg);
            }
            else {
                this.limit -= increase;
                this.actmsg = "";
            }
        }
    }
    
    public void setInterest(double rate) {
        this.actmsg = this.errmsg = "";
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        double monthRate = rate / 12;
        
        if (this.acctno <= 0) {
            this.errmsg = "Interest charge attempted on unopened account";
            return;
        }
        if (rate <= 0) {
            this.actmsg = "Interest charge of " + rate + " declined: must be greater than zero";
            writeLog(this.actmsg);
        }
        else if ((this.baldue + (monthRate * this.baldue)) > this.limit) {
            this.actmsg = "Interest charge of " + rate + " declined - insufficient credit.";
            writeLog(this.actmsg);
        }
        else {
            double intCharge = this.baldue * monthRate;
            this.baldue += intCharge;
            if (writeStatus ()) {
                this.actmsg = "Interest charge of " + curr.format(intCharge) +
                        " at a rate of " + rate + " per year was posted.";
                writeLog(this.actmsg);
            }
            else {
                this.baldue -= intCharge ;
                this.actmsg = "";
            }
        }
    }
    
    public ArrayList<String> getLog() {
        ArrayList<String> log = new ArrayList<String>();
        
        if (this.acctno <= 0) {
            this.errmsg = "Log request on non-active account";
            return log;
        }
        try {
            BufferedReader in = new BufferedReader(
                new FileReader("CCL" + this.acctno + ".txt"));
            String s = in.readLine();
            while (s != null) {
                log.add(s);
                s = in.readLine();
            }
            in.close();
            this.actmsg = "Log returned for account: " + this.acctno;
        }
        catch (FileNotFoundException e) {
            this.errmsg = "Log file missing for account: " + this.acctno;
        }
        catch (IOException e) {
            this.errmsg = "Error reading log file for account: " + this.acctno;
        }
        return log;
    }
}
