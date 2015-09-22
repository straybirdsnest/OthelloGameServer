package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

//@RepositoryRestResource(collectionResourceRel = "users", path = "user)
@Transactional
public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByUsername(@Param("username") String username);
	
}
