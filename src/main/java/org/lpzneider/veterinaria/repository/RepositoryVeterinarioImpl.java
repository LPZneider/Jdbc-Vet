package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Veterinario;

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
    public void saveOrEdit(Veterinario veterinario)throws Exception {
        if (veterinario.getId() != null && veterinario.getId() > 0) {
            em.merge(veterinario);
        } else {
            em.persist(veterinario);
        }
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }

}
