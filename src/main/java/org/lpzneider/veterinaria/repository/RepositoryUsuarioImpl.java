package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Usuario;

import java.util.List;
@Repositorio
public class RepositoryUsuarioImpl implements RepositoryRegister<Usuario> {

    @Inject
    private EntityManager em;

    @Override
    public List<Usuario> read() {
        return em.createQuery("from Usuario", Usuario.class).getResultList();
    }

    @Override
    public Usuario getById(Long id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public void saveOrEdit(Usuario usuario) throws Exception{

        if (usuario.getId() != null && usuario.getId() > 0) {
            em.merge(usuario);
        } else {
            em.persist(usuario);
        }
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }

    @Override
    public Usuario byEmail(String email) throws Exception {
        return em.createQuery("select u from Usuario u where u.registro.email  = :email", Usuario.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
