package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Mascota;

import java.util.List;
import java.util.logging.Logger;

@Repositorio
public class RepositoryMascotaImpl implements Repository<Mascota> {
    @Inject
    private EntityManager em;

    @Override
    public List<Mascota> read() throws Exception {
        return em.createQuery("from Mascota", Mascota.class).getResultList();
    }

    @Override
    public Mascota getById(Long id) throws Exception {
        return em.find(Mascota.class, id);
    }

    @Override
    public void saveOrEdit(Mascota mascota) throws Exception {
        if (mascota.getId() != null && mascota.getId() > 0) {
            em.merge(mascota);
        } else {
            em.persist(mascota);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        em.remove(getById(id));
    }
}
