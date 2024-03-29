package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Veterinario;

import java.sql.Connection;
import java.util.List;
@Repositorio
public class RepositoryVeterinarioImpl implements Repository<Veterinario> {

    @Inject
    private EntityManager em;

    @Override
    public List<Veterinario> read() {
        return em.createQuery("from Veterinario", Veterinario.class).getResultList();
    }

    @Override
    public Veterinario getById(Long id) {
        return em.find(Veterinario.class, id);
    }

    @Override
    public Veterinario saveOrEdit(Veterinario veterinario) {
        if (veterinario.getId() != null && veterinario.getId() > 0) {
            em.persist(veterinario);
        } else {
            em.merge(veterinario);
        }
        return veterinario;
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }

}
