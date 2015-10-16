package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserOnlineRepository extends CrudRepository<UserOnline, Long> {
    List<UserOnline> findByOnlineState(@Param("onlineState") int onlinestate);
}
