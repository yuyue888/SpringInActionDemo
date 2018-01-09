package core.service;

import core.dao.GoodsDao;
import core.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public int insert(Goods goods) {
        return goodsDao.insert(goods);
    }

    @Cacheable(value ="normalCache")
    public List<Goods> getAllGoods() {
        return goodsDao.getAllGoods();
    }

    @Cacheable(value ="findByNameCache")
    public List<Goods> findByName(String name) {
        return goodsDao.findByName(name);
    }

    @Cacheable(value ="totalCountCache")
    public int getTotalCount() {
        return goodsDao.getTotalCount();
    }

    public int update(Goods pojo) {
        return goodsDao.update(pojo);
    }
}
