package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Tratamiento;

import java.util.List;

@Repositorio
public class RepositoryTratamientoImpl implements Repository<Tratamiento> {
    @Inject
    private EntityManager em;

    @Override
    public List<Tratamiento> read() throws Exception {
        return em.createQuery("from Tratamiento", Tratamiento.class).getResultList();
    }

    @Override
    public Tratamiento getById(Long id) throws Exception {
        return em.find(Tratamiento.class, id);
    }

    @Override
    public void saveOrEdit(Tratamiento tratamiento) throws Exception {
        if (tratamiento.getId() != null && tratamiento.getId() > 0) {
            em.merge(tratamiento);
        } else {
            em.persist(tratamiento);
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        em.remove(getById(id));
    }
}
