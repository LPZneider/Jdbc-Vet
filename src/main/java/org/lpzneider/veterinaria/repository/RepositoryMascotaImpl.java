package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Mascota;

import java.util.List;

@Repositorio
public class RepositoryMascotaImpl implements Repository<Mascota> {
    @Inject
    private EntityManager em;

    @Override
    public List<Mascota> read() {
        return em.createQuery("from Mascota", Mascota.class).getResultList();
    }

    @Override
    public Mascota getById(Long id) {
        return em.find(Mascota.class, id);
    }

    @Override
    public Mascota saveOrEdit(Mascota mascota) {
        if (mascota.getId() != null && mascota.getId() > 0) {
            em.persist(mascota);
        } else {
            em.merge(mascota);
        }
        return mascota;
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }
}
