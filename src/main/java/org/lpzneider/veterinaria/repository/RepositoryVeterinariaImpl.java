package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Usuario;
import org.lpzneider.veterinaria.models.Veterinaria;

import java.util.List;

@Repositorio
public class RepositoryVeterinariaImpl implements RepositoryRegister<Veterinaria> {

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
    public void saveOrEdit(Veterinaria veterinaria) throws Exception {
        if (veterinaria.getId() != null && veterinaria.getId() > 0) {
            em.merge(veterinaria);
        } else {
            em.persist(veterinaria);
        }
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }

    @Override
    public Veterinaria byEmail(String email) throws Exception {
        return em.createQuery("select v from Veterinaria v where v.registro.email = :email", Veterinaria.class)
                .setParameter("email", email)
                .getSingleResult();
    }

}
