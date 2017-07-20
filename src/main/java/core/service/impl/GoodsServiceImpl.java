package core.service.impl;

import core.dao.GoodsDao;
import core.entity.Goods;
import core.enums.CountModifyType;
import core.exceptions.GoodsNotExistException;
import core.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ssc on 2017/7/20 0020.
 */
@Service
public class GoodsServiceImpl implements GoodService {
    @Autowired
    private GoodsDao goodsDao;

    @Override
    public List<Goods> findAllGoods() {
        return goodsDao.findAllGoods();
    }

    @Override
    public List<Goods> findByName(String name) {
        return goodsDao.findByGoodsName(name);
    }

    @Override
    public Goods getGoodsById(Integer id) {
        return goodsDao.getGoodsById(id);
    }

    @Override
    @Transactional
    public Map<String, Object> addNewGoods(Goods goods) {
        return addNewGoods(goods);
    }

    @Override
    @Transactional
    public Map<String, Object> modifyGoodsCount(Integer id, Integer count, CountModifyType type) {
        Goods goods = getGoodsById(id);
        Map<String, Object> result = new HashMap<>();
        if (goods == null) {
            result.put("success", false);
            result.put("msg", "商品不存在");
            return result;
        }
        if (type == CountModifyType.INCREASE) {
            goods.setGcount(goods.getGcount() + count);
        } else {
            Integer oldCount = goods.getGcount();
            goods.setGcount(oldCount - count < 0 ? 0 : oldCount - count);
        }

        try {
            goodsDao.updateGoods(goods);
        } catch (GoodsNotExistException e) {
            result.put("success", false);
            result.put("msg", "商品不存在");
            return result;
        }
        result.put("success", true);
        result.put("msg", "");
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteGoods(Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            goodsDao.deleteGoodsById(id);
        } catch (GoodsNotExistException e) {
            result.put("success", false);
            result.put("msg", "商品不存在");
        }
        result.put("success", true);
        result.put("msg", "");
        return result;
    }
}
