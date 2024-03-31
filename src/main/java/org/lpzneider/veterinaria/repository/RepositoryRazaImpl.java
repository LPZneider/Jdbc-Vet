package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Raza;

import java.util.List;

@Repositorio
public class RepositoryRazaImpl implements Repository<Raza> {
    @Inject
    private EntityManager em;


    @Override
    public List<Raza> read() {
        return em.createQuery("from Raza", Raza.class).getResultList();
    }

    @Override
    public Raza getById(Long id) {
        return em.find(Raza.class, id);
    }

    @Override
    public void saveOrEdit(Raza raza)throws Exception {
        if (raza.getId() != null && raza.getId() > 0) {
            em.merge(raza);
        } else {
            em.persist(raza);
        }
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }
}
