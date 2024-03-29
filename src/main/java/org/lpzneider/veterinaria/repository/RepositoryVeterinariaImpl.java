package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Veterinaria;

import java.util.List;
@Repositorio
public class RepositoryVeterinariaImpl implements Repository<Veterinaria> {

    @Inject
    private EntityManager em;

    @Override
    public List<Veterinaria> read() {
        return em.createQuery("from Veterinaria", Veterinaria.class).getResultList();
    }

    @Override
    public Veterinaria getById(Long id) {
        return em.find(Veterinaria.class, id);
    }

    @Override
    public Veterinaria saveOrEdit(Veterinaria veterinaria) {
        if (veterinaria.getId() != null && veterinaria.getId() > 0) {
            em.persist(veterinaria);
        } else {
            em.merge(veterinaria);
        }
        return veterinaria;
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }

}
