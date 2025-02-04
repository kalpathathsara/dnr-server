package bit.project.server.dao;

import bit.project.server.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported=false)
public interface CampaignDao extends JpaRepository<Campaign, Integer>{

    Campaign findByName(String name);
    Campaign findByCode(String code);
}
