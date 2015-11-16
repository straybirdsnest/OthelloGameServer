package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.User;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	
	User findByUsername(@Param("username") String username);
	
}
