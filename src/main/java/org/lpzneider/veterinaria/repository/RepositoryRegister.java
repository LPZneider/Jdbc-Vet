package org.lpzneider.veterinaria.repository;

import java.util.List;

public interface RepositoryRegister<T> {


    List<T> read() throws Exception;

    T getById(Long id) throws Exception;

    void saveOrEdit(T t) throws Exception;

    void delete(Long id) throws Exception;

    T byEmail(String email) throws Exception;
}
