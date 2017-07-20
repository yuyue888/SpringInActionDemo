package core.service;

import core.entity.Goods;
import core.enums.CountModifyType;

import java.util.List;
import java.util.Map;

/**
 * Created by ssc on 2017/6/26.
 */
public interface GoodService {
    List<Goods> findAllGoods();

    List<Goods> findByName(String name);

    Goods getGoodsById(Integer id);

    Map<String, Object> addNewGoods(Goods goods);

    Map<String, Object> modifyGoodsCount(Integer id, Integer count, CountModifyType type);

    Map<String, Object> deleteGoods(Integer id) ;
}
