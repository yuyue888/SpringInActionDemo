package core.service;

import core.dao.GoodsDao;
import core.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService{

    @Autowired
    private GoodsDao goodsDao;

    public int insert(Goods goods){
        return goodsDao.insert(goods);
    }

    public int insertSelective(Goods goods){
        return goodsDao.insertSelective(goods);
    }

    public int insertList(List<Goods> goods){
        return goodsDao.insertList(goods);
    }

    public int update(Goods pojo){
        return goodsDao.update(pojo);
    }
}
