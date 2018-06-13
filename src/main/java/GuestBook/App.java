package GuestBook;

import GuestBook.messageDAO.ConnectionProvider;
import GuestBook.messageDAO.InterfaceDAO;
import GuestBook.messageDAO.MessageDAO;
import GuestBook.messageDAO.Models.Message;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {




        // crete a server on port 7171
        HttpServer server = HttpServer.create(new InetSocketAddress(7171), 0);

        // set routes
        server.createContext("/hello", new OpenSite("index.html"));
        server.createContext("/static", new Static());
        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();

    }
}
