package bit.project.server.dao;

import bit.project.server.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface ItemDao extends JpaRepository<Item, Integer>{
    Item findByCode(String code);
    Item findByName(String name);
}
