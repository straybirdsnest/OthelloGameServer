package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.GameRecord;

@Repository
@Transactional
public interface GameRecordRepository extends PagingAndSortingRepository<GameRecord, Integer> {
}
