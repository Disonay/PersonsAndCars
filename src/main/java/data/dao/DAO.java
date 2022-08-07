package data.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> extends AutoCloseable {
    void save(T object) throws SQLException;

    T findById(Long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void clear() throws SQLException;

    @Override
    default void close() throws Exception {
    }
}