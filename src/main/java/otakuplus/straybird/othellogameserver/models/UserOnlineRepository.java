package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserOnlineRepository extends PagingAndSortingRepository<UserOnline, Long> {
    List<UserOnline> findByOnlineState(@Param("onlineState") int onlinestate);
}
