package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Producto;

import java.util.List;

@Repositorio
public class RepositoryProductoImpl implements Repository<Producto> {
    @Inject
    private EntityManager em;

    @Override
    public List<Producto> read() throws Exception {
        return em.createQuery("from Producto", Producto.class).getResultList();
    }

    @Override
    public Producto getById(Long id) throws Exception {
        return em.find(Producto.class, id);
    }

    @Override
    public void saveOrEdit(Producto producto) throws Exception {
        if (producto.getId() != null && producto.getId() > 0) {
            em.merge(producto);
        } else {
            em.persist(producto);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        em.remove(getById(id));
    }
}
