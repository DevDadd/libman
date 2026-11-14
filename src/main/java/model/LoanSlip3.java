package model;

public class LoanSlip3 {
    private String name;
    private String documentName;
    private String borrowDate;
    private String returnDate;
    private String status;
    private Integer id;
    private Reader3 reader;
    private LoanSlipItem3[] loanItems;

    public LoanSlip3() {
    }

    public LoanSlip3(String name, String documentName, String borrowDate, String returnDate, String status,
            Integer id, Reader3 reader, LoanSlipItem3[] loanItems) {
        this.name = name;
        this.documentName = documentName;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
        this.id = id;
        this.reader = reader;
        this.loanItems = loanItems;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDocumentName() { return documentName; }
    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public String getBorrowDate() { return borrowDate; }
    public void setBorrowDate(String borrowDate) { this.borrowDate = borrowDate; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Reader3 getReader() { return reader; }
    public void setReader(Reader3 reader) { this.reader = reader; }

    public LoanSlipItem3[] getLoanItems() { return loanItems; }
    public void setLoanItems(LoanSlipItem3[] loanItems) { this.loanItems = loanItems; }
}
