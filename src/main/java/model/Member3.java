package model;

public class Member3 {
    private String name;
    private String age;
    private String dob;
    private String idNumber;
    private String password;
    private String address;
    private String role;
    private String note;
    private ReaderCard3 readerCard;

    public Member3(String name, String age, String dob, String idNumber, String password, String address,
            String role, String note, ReaderCard3 readerCard) {
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.idNumber = idNumber;
        this.password = password;
        this.address = address;
        this.role = role;
        this.note = note;
        this.readerCard = readerCard;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }

    public String getNote() {
        return note;
    }

    public ReaderCard3 getReaderCard() {
        return readerCard;
    }

    public void setReaderCard(ReaderCard3 readerCard) {
        this.readerCard = readerCard;
    }
}
