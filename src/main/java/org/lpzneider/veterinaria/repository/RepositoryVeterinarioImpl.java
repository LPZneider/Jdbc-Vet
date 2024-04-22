package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;
import org.lpzneider.veterinaria.models.Veterinario;

import java.util.List;

@Repositorio
public class RepositoryVeterinarioImpl implements RepositoryRegister<Veterinario> {

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

    @Override
    public Veterinario byEmail(String email) throws Exception {
        return em.createQuery("select v from Veterinario v where v.registro.email = :email", Veterinario.class)
                .setParameter("email", email)
                .getSingleResult();
    }

}
