package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	User findByUsername(@Param("username") String username);
	
}
