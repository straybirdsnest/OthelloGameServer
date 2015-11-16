package otakuplus.straybird.othellogameserver.daos;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import otakuplus.straybird.othellogameserver.models.UserInformation;

@Transactional
public interface UserInformationRepository extends PagingAndSortingRepository<UserInformation, Long> {
}
