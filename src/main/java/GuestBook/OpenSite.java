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
    private InterfaceDAO<Message> dao;
    private String fileName;

    //java-js problem
    Message addedMessage;

    OpenSite(String fileName) {
       this.fileName = fileName;
       this.dao = new MessageDAO();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();


        if(method.equals("GET")){
            response = makeFileContent(fileName);
        }

        if(method.equals("POST")){
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            Map<String,String> inputs = parseFormData(formData);

            try {
                addedMessage = new Message(inputs.get("name"), inputs.get("message"), new Date().toString());
                dao.add(addedMessage);
                //java-js problem
                prepareJson();
            } catch(SQLException e) {
                System.err.println("Database operation error \n" + e);
            }


            response = makeFileContent(fileName);
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    //java js problem
    private String prepareJson() throws SQLException {

        List<Message> messages = new ArrayList<>();
        messages = dao.get();

        StringBuilder array = new StringBuilder("{\"messages\": [");
        for(Message msg : messages) {
            array.append(msg.Serialize());
            array.append(",");
        }
        array.deleteCharAt(array.length()-1);
        array.append("]}");

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter( new FileWriter("src/main/resources/static/data/exampleData.json"));
            writer.write(array.toString());
            writer.close( );

        }
        catch ( IOException e) {
            System.out.println("String Buffer error \n" + e);
        }

        return array.toString();

    }

    private String makeFileContent(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();
        try {

            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/static/html/" + fileName));
            String str;

            while ((str = reader.readLine()) != null) {
                contentBuilder.append(str);

                //java-js problem
                if(str.contains("<div id=\"msg2\">") && addedMessage != null) {
                     String messageBody;
                       messageBody = "<div class=\"message-element\"><h4>Name:" + addedMessage.getName() +
                                     "</h4><p>Message:" + addedMessage.getMessage() +
                                     "</p><p>Data:" + addedMessage.getData() + "</p></div>";
                       contentBuilder.append(messageBody);
                       addedMessage = null;

                }


            }
            reader.close();
        } catch (IOException e) {
            System.out.println("String Buffer error \n" + e);
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
