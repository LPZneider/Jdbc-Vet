package org.lpzneider.veterinaria.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.util.JpaUtil;

import java.util.logging.Logger;

@ApplicationScoped
public class ProducerResources {

    @Produces
    private Logger beanLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
    @Produces
    @RequestScoped
    private EntityManager beanEntityManager() {
        return JpaUtil.getEntityManager();
    }

    public void close(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) entityManager.close();
    }
}
