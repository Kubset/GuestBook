package GuestBook;

import GuestBook.messageDAO.InterfaceDAO;
import GuestBook.messageDAO.MessageDAO;
import GuestBook.messageDAO.Models.Message;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

public class OpenSite implements HttpHandler {

    private String fileContent;
    InterfaceDAO<Message> dao;
    String fileName;

    //java-js problem
//    private List<Message> addedMessages = new ArrayList<>();
    Message addedMessage;

    OpenSite(String fileName) {
       this.fileName = fileName;
       dao = new MessageDAO();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();


        if(method.equals("GET")){
            response = makeFileContent(fileName);
        }

        if(method.equals("POST")){
//            TODO: refactor
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map<String,String> inputs = parseFormData(formData);

            try {
                addedMessage = new Message(inputs.get("name"), inputs.get("message"), new Date().toString());
                dao.add(addedMessage);
                //java-js problem
//                addedMessages.add(msg);
                prepareJson();
            } catch(SQLException e) {}

            response = makeFileContent(fileName);
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    //java js problem
    private String prepareJson() {
        List<Message> messages = new ArrayList<>();
        MessageDAO dao = new MessageDAO();
        try {
            messages = dao.get();
        }
        catch (Exception e) {
            System.out.println("blad");}



        StringBuilder array = new StringBuilder("{\"messages\": [");
        for(Message msg : messages) {
            array.append(msg.Serialize());
            array.append(",");
        }
        array.deleteCharAt(array.length()-1);
        array.append("]}");

        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter("src/main/resources/static/data/exampleData.json"));
            writer.write(array.toString());
            System.out.println("zapisano!!");

        }
        catch ( IOException e)
        {
            System.out.println(e);
        }
        finally
        {
            try
            {
                if ( writer != null)
                    writer.close( );
            }
            catch ( IOException e)
            {
                System.out.println(e);
            }
        }

        return array.toString();

    }

    private String makeFileContent(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/main/resources/static/html/" + fileName));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);

                //java-js problem
                if(str.contains("<div id=\"msg2\">") && addedMessage != null) {
//                    String messageBody;
//                    for(Message msg : addedMessages) {
//                       messageBody = "<div class=\"message-element\"><h4>Name:" + msg.getName() +
//                                     "</h4><p>Message:" + msg.getMessage() +
//                                     "</p><p>Data:" + msg.getData() + "</p></div>";
//                       contentBuilder.append(messageBody);
                     String messageBody;
                       messageBody = "<div class=\"message-element\"><h4>Name:" + addedMessage.getName() +
                                     "</h4><p>Message:" + addedMessage.getMessage() +
                                     "</p><p>Data:" + addedMessage.getData() + "</p></div>";
                       contentBuilder.append(messageBody);
                       addedMessage = null;

                }


            }
            in.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return contentBuilder.toString();
    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

}
