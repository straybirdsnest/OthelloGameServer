package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.User;

@RepositoryRestResource
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findOneByUsername(String username);

}
