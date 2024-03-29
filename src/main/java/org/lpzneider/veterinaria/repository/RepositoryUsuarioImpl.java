package org.lpzneider.veterinaria.repository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.configs.Repositorio;
import org.lpzneider.veterinaria.models.Usuario;

import java.util.List;
@Repositorio
public class RepositoryUsuarioImpl implements Repository<Usuario> {

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
    public Usuario saveOrEdit(Usuario usuario) {
        if (usuario.getId() != null && usuario.getId() > 0) {
            em.persist(usuario);
        } else {
            em.merge(usuario);
        }
        return usuario;
    }

    @Override
    public void delete(Long id) {
        em.remove(getById(id));
    }

}
