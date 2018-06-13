package GuestBook.messageDAO;

import GuestBook.messageDAO.Models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO implements InterfaceDAO<Message> {


    PreparedStatement preparedStatement;

    String ADD_QUERY;
    String EDIT_QUERY;
    String DELETE_QUERY;
    String GET_QUERY;

    public MessageDAO() {
        this.ADD_QUERY = "INSERT INTO messages(name, message, data) VALUES(?,?,?)";
        this.EDIT_QUERY = "UPDATE artifacts SET name=?, description=?, price=? WHERE id=?";
        this.DELETE_QUERY = "DELETE * FROM artifacts WHERE id=?";
        this.GET_QUERY = "SELECT * FROM messages";
//        String EDIT_QUERY_KEY_INDEX = 4;
    }

    @Override
    public void add(Message model) throws SQLException {
        Connection c = new ConnectionProvider().getConnection();
        this.preparedStatement = c.prepareStatement(ADD_QUERY);
        this.preparedStatement.setString(1, model.getName());
        this.preparedStatement.setString(2, model.getMessage());
        this.preparedStatement.setString(3, model.getData());
        this.preparedStatement.executeUpdate();
        c.close();
    }

    @Override
    public void update(Message model) {
//        TODO: no need for now
    }

    @Override
    public void delete(Message model) {
//        TODO: no need for now
    }

    @Override
    public Message get(int ID) {
        //TODO: no need for now
        return null;
    }

    @Override
    public List<Message> get() throws SQLException {
        List<Message> messages = new ArrayList<>();
        Connection c = new ConnectionProvider().getConnection();
        this.preparedStatement = c.prepareStatement(GET_QUERY);
        ResultSet resultSet = this.preparedStatement.executeQuery();
        while(resultSet.next()) {
            Message msg = new Message(resultSet.getString("name"),
                                      resultSet.getString("message"),
                                      resultSet.getString("data"));
            messages.add(msg);
        }
        return messages;
    }
}
