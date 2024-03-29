package org.lpzneider.veterinaria.interceptor;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import org.lpzneider.veterinaria.exceptions.ServiceJpaException;

@Transactional
@Interceptor
public class TransactionalInterceptor {
    @Inject
    private EntityManager em;

    @AroundInvoke
    public Object transactional(InvocationContext invocation) throws Exception{
        try {
            em.getTransaction().begin();
            Object result = invocation.proceed();
            em.getTransaction().commit();
            return result;
        }catch (ServiceJpaException e){
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
