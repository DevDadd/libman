package model;

public class DocumentTypeDocument3 {
    private int documentTypeID;
    private int documentID;

    public DocumentTypeDocument3() {
    }

    public DocumentTypeDocument3(int documentTypeID, int documentID) {
        this.documentTypeID = documentTypeID;
        this.documentID = documentID;
    }

    public int getDocumentTypeID() { return documentTypeID; }
    public void setDocumentTypeID(int documentTypeID) { this.documentTypeID = documentTypeID; }

    public int getDocumentID() { return documentID; }
    public void setDocumentID(int documentID) { this.documentID = documentID; }
}
