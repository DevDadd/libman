package model;

public class DocumentType3 {
    private String name;
    private String description;
    private Integer id;
    private DocumentTypeDocument3 documentTypeDocument;

    public DocumentType3() {
    }

    public DocumentType3(String name, String description, Integer id,
            DocumentTypeDocument3 documentTypeDocument) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.documentTypeDocument = documentTypeDocument;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public DocumentTypeDocument3 getDocumentTypeDocument() { return documentTypeDocument; }
    public void setDocumentTypeDocument(DocumentTypeDocument3 documentTypeDocument) {
        this.documentTypeDocument = documentTypeDocument;
    }
}
