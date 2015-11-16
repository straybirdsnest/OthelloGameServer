package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.GameTable;

@Repository
@Transactional
public interface GameTableRepository extends PagingAndSortingRepository<GameTable, Integer> {
}
