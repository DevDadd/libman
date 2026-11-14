package model;

public class LoanSlipItem3 {
    private String documentName;
    private String returnDate;
    private String status;
    private Integer quantity;
    private Integer id;
    private Document3 document;

    public LoanSlipItem3() {
    }

    public LoanSlipItem3(String documentName, String returnDate, String status, Integer quantity, Integer id,
            Document3 document) {
        this.documentName = documentName;
        this.returnDate = returnDate;
        this.status = status;
        this.quantity = quantity;
        this.id = id;
        this.document = document;
    }

    public String getDocumentName() { return documentName; }
    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public String getReturnDate() { return returnDate; }
    public void setReturnDate(String returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Document3 getDocument() { return document; }
    public void setDocument(Document3 document) { this.document = document; }
}
