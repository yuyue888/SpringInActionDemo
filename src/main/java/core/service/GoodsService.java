package core.service;

import core.aop.CalculateTime;
import core.dao.GoodsDao;
import core.dao.GoodsDao2;
import core.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsDao2 goodsDao2;

    @Transactional
    public int insert(Goods goods) {
        return goodsDao.insert(goods);
    }

//    @Cacheable(value ="normalCache")
    public List<Goods> getAllGoods() {
        return goodsDao.getAllGoods();
    }

//    @Cacheable(value ="findByNameCache")
    @CalculateTime
    public List<Goods> findByName(String name) {
        return goodsDao.findByName(name);
    }

//    @Cacheable(value ="totalCountCache")
    public int getTotalCount() {
        return goodsDao.getTotalCount();
    }

    public int update(Goods pojo) {
        return goodsDao.update(pojo);
    }

    public int update2(Goods pojo) {
        return goodsDao2.update(pojo);
    }

    @PostConstruct
    public void postProcess(){}

    @PreDestroy
    public void preDestroy(){}

}
