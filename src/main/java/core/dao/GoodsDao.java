package core.dao;

import core.entity.Goods;
import core.exceptions.GoodsNotExistException;

import java.util.List;

/**
 * Created by ssc on 2017/7/20 0020.
 */
public interface GoodsDao {
    List findAllGoods();

    List<Goods> findByGoodsName(String name);

    void addNewGoods(Goods goods);

    void updateGoods(Goods goods) throws GoodsNotExistException;

    void deleteGoodsById(Integer id) throws GoodsNotExistException;

    Goods getGoodsById(Integer id);
}
