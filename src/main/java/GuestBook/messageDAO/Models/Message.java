package GuestBook.messageDAO.Models;

    public class Message {
//    private int ID;
    private String message;
    private String data;
    private String name;


    public Message(String name, String message, String data) {
//        this.ID = ID;
        this.name = name;
        this.message = message;
        this.data = data;
    }

//    public int getID() {
//        return ID;
//    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

}
