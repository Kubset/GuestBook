package GuestBook.messageDAO;

import GuestBook.messageDAO.Models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class MessageDAO implements InterfaceDAO<Message> {


    PreparedStatement preparedStatement;
    String ADD_QUERY;
    String EDIT_QUERY;
    String DELETE_QUERY;

    public MessageDAO() {
        this.ADD_QUERY = "INSERT INTO messages(name, message, data) VALUES(?,?,?)";
        this.EDIT_QUERY = "UPDATE artifacts SET name=?, description=?, price=? WHERE id=?";
        this.DELETE_QUERY = "DELETE * FROM artifacts WHERE id=?";
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




    }

    @Override
    public void update(Message model) {

    }

    @Override
    public void delete(Message model) {

    }

    @Override
    public Message get(int ID) {
        return null;
    }

    @Override
    public List<Message> get() {
        return null;
    }
}
