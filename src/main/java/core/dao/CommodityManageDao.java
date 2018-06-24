package core.dao;

import core.entity.CommdityManage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface CommodityManageDao extends PagingAndSortingRepository<CommdityManage, Long> {
    CommdityManage findByBizNoOrderByCreateTimeDesc(String bizNo);
    Page<CommdityManage> findByCreateTimeBetweenOrderByCreateTimeDesc(Date startTime , Date endTime, Pageable pageable);
    Page<CommdityManage> findByCommodityIdAndCreateTimeBetweenOrderByCreateTimeDesc(Long commodityId, Date startTime ,Date endTime, Pageable pageable);
}
