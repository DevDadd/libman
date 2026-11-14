package model;

public class Document3 {
    private String name;
    private String producer;
    private String yearProduce;
    private String author;
    private String status;
    private Integer quantity;
    private Integer id;
    private DocumentTypeDocument3 documentTypeDocument;

    public Document3() {
    }

    public Document3(String name, String producer, String yearProduce, String author, String status,
            Integer quantity, Integer id, DocumentTypeDocument3 documentTypeDocument) {
        this.name = name;
        this.producer = producer;
        this.yearProduce = yearProduce;
        this.author = author;
        this.status = status;
        this.quantity = quantity;
        this.id = id;
        this.documentTypeDocument = documentTypeDocument;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProducer() { return producer; }
    public void setProducer(String producer) { this.producer = producer; }

    public String getYearProduce() { return yearProduce; }
    public void setYearProduce(String yearProduce) { this.yearProduce = yearProduce; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public DocumentTypeDocument3 getDocumentTypeDocument() { return documentTypeDocument; }
    public void setDocumentTypeDocument(DocumentTypeDocument3 documentTypeDocument) {
        this.documentTypeDocument = documentTypeDocument;
    }
}
