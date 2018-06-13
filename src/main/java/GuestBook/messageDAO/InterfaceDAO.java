package GuestBook.messageDAO;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAO<E> {

    public void add(E model) throws SQLException;
    public void update(E model) throws SQLException;
    public void delete(E model) throws SQLException;
    public E get(int ID) throws SQLException;
    public List<E> get() throws SQLException;
}
