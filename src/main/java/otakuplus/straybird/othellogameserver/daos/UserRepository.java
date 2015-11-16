package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.User;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    User findOneByUsername(String username);

}
