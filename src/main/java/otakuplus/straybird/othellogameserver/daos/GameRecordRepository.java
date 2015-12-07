package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.GameRecord;

import java.util.List;

@RepositoryRestResource
@Transactional
public interface GameRecordRepository extends PagingAndSortingRepository<GameRecord, Integer> {
    @Query("SELECT gameRecord FROM GameRecord gameRecord WHERE LOWER(gameRecord.playerA) = LOWER(:username) OR LOWER(gameRecord.playerB) = LOWER(:username)")
    List<GameRecord> findByUsername(@Param("username") String username);
}
