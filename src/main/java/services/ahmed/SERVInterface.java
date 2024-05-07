package services.ahmed;

import java.sql.SQLException;
import java.util.List;

public interface SERVInterface <I>{
    public void create (I i) throws SQLException;
    public void modify (I i) throws SQLException;
    // public void delete (I i) throws SQLException;

    void delete(int i) throws SQLException;

    public List<I> show () throws SQLException;
}
