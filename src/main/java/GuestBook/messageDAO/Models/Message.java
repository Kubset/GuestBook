package GuestBook.messageDAO.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

    public String Serialize() {
        Gson gson = new GsonBuilder().create();
        String payloadStr = gson.toJson(this);
        System.out.println(payloadStr);
        return payloadStr;
    }
}
