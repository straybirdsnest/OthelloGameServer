package otakuplus.straybird.othellogameserver.models;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GameTableRepository extends PagingAndSortingRepository<GameTable, Long> {
}
