package org.lpzneider.veterinaria.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    void setConn(Connection conn);

    List<T> read() throws SQLException;

    T getById(Long id) throws SQLException;

    T saveOrEdit(T t) throws SQLException;

    void delete(Long id) throws SQLException;
}
