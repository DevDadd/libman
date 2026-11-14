package model;

public class DocumentStatusReport3 {
    private String status;
    private int totalBorrowed;
    private int totalReturned;
    private int currentBorrowed;
    private LoanSlip3 loanSlip;

    public DocumentStatusReport3() {
    }

    public DocumentStatusReport3(String status, int totalBorrowed, int totalReturned, int currentBorrowed,
            LoanSlip3 loanSlip) {
        this.status = status;
        this.totalBorrowed = totalBorrowed;
        this.totalReturned = totalReturned;
        this.currentBorrowed = currentBorrowed;
        this.loanSlip = loanSlip;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getTotalBorrowed() { return totalBorrowed; }
    public void setTotalBorrowed(int totalBorrowed) { this.totalBorrowed = totalBorrowed; }

    public int getTotalReturned() { return totalReturned; }
    public void setTotalReturned(int totalReturned) { this.totalReturned = totalReturned; }

    public int getCurrentBorrowed() { return currentBorrowed; }
    public void setCurrentBorrowed(int currentBorrowed) { this.currentBorrowed = currentBorrowed; }

    public LoanSlip3 getLoanSlip() { return loanSlip; }
    public void setLoanSlip(LoanSlip3 loanSlip) { this.loanSlip = loanSlip; }
}
