package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>{
	
	public User findByUsername(@Param("username") String username);
	
}
