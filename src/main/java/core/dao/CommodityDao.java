package core.dao;

import core.entity.Commodity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommodityDao extends PagingAndSortingRepository<Commodity, Long> {
    Page<Commodity> findByNameLike(String name);
}
