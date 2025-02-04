package bit.project.server.dao;

import bit.project.server.entity.Employeestatus;
import bit.project.server.entity.Itemprice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface ItempriceDao extends JpaRepository<Itemprice, Integer>{
}
