package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by otakuplus on 2015/9/22.
 */
@Transactional
public interface GameRecordRepository extends CrudRepository<GameRecord, Long> {
}
