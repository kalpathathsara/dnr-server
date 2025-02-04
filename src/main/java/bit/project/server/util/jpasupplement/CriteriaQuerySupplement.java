package bit.project.server.util.jpasupplement;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;


public interface CriteriaQuerySupplement<T>{
    CriteriaBuilder getCriteriaBuilder();
    <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery);
    Query createQuery(CriteriaUpdate updateQuery);
    Query createQuery(CriteriaDelete deleteQuery);
}
