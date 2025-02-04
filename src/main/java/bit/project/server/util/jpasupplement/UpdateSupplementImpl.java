package bit.project.server.util.jpasupplement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public class UpdateSupplementImpl<T> implements UpdateSupplement<T> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public T update(T object) {
        em.detach(object);
        T o = em.merge(object);
        return o;
    }
}
