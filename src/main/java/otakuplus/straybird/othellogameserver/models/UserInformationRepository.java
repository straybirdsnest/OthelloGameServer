package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

//@RepositoryRestResource(collectionResourceRel = "userinformations", path = "userinformations")
@Transactional
public interface UserInformationRepository extends CrudRepository<UserInformation, Long>{
}