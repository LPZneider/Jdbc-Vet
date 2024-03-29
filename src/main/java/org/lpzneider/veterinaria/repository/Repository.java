package org.lpzneider.veterinaria.repository;

import java.util.List;

public interface Repository<T> {


    List<T> read();

    T getById(Long id);

    T saveOrEdit(T t);

    void delete(Long id);
}
