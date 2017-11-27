package core.service;

import config.support.redis.RedisCache;
import core.dao.GoodsDao;
import core.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public int insert(Goods goods) {
        return goodsDao.insert(goods);
    }

    @RedisCache
    public List<Goods> getAllGoods() {
        return goodsDao.getAllGoods();
    }

    public int update(Goods pojo) {
        return goodsDao.update(pojo);
    }
}
