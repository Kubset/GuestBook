package GuestBook;

import GuestBook.messageDAO.ConnectionProvider;
import GuestBook.messageDAO.InterfaceDAO;
import GuestBook.messageDAO.MessageDAO;
import GuestBook.messageDAO.Models.Message;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws IOException {
//
//        InterfaceDAO<Message> dao = new MessageDAO();
//        Message msg = new Message("aaa","bbb","ccc");
//        try {
//            dao.add(msg);
//        } catch(SQLException e) {
//            System.out.println(e);
//        }

        // create a server on port 7171
        HttpServer server = HttpServer.create(new InetSocketAddress(7171), 0);

        // set routes
        server.createContext("/hello", new OpenSite("index.html"));
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();

    }
}
