package GuestBook;

import GuestBook.messageDAO.InterfaceDAO;
import GuestBook.messageDAO.MessageDAO;
import GuestBook.messageDAO.Models.Message;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OpenSite implements HttpHandler {

    private final String fileContent;


    OpenSite(String fileName) {
       this.fileContent = makeFileContent(fileName);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        if(method.equals("GET")){
            response = fileContent;
        }

        if(method.equals("POST")){
//            TODO: refactor
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println(formData);
            Map<String,String> inputs = parseFormData(formData);

            InterfaceDAO<Message> dao = new MessageDAO();
            Message msg = new Message(inputs.get("name"), inputs.get("message"), "data");
            try {
                dao.add(msg);
            } catch(SQLException e) {}


            response = fileContent;
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String makeFileContent(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/main/resources/static/html/" + fileName));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
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
