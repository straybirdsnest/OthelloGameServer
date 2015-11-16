package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.GameRecord;

/**
 * Created by otakuplus on 2015/9/22.
 */
@Transactional
public interface GameRecordRepository extends CrudRepository<GameRecord, Long> {
}
