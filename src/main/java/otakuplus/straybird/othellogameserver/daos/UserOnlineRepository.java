package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.UserOnline;

import java.util.List;

@RepositoryRestResource
@Transactional
public interface UserOnlineRepository extends PagingAndSortingRepository<UserOnline, Integer> {
    List<UserOnline> findByOnlineState(@Param("onlineState") int onlinestate);
}
