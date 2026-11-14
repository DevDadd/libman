package model;

public class Reader3 extends Member3 {
    private String studentNumber;

    public Reader3(String name, String age, String dob, String idNumber, String password, String address, String role,
            String note, ReaderCard3 readerCard) {
        this(name, age, dob, idNumber, password, address, role, note, readerCard, null);
    }

    public Reader3(String name, String age, String dob, String idNumber, String password, String address, String role,
            String note, ReaderCard3 readerCard, String studentNumber) {
        super(name, age, dob, idNumber, password, address, role, note, readerCard);
        this.studentNumber = studentNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
}
