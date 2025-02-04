package bit.project.server.dao;

import bit.project.server.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface CustomerDao extends JpaRepository<Customer, Integer>{

    //Check for the unique attributes
    Customer findByEmail(String email);
    Customer findByCode(String code);
}
