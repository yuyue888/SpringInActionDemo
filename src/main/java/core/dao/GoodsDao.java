package core.dao;

import java.util.List;
import core.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface GoodsDao {
    int insert( Goods pojo);

    List<Goods> getAllGoods();

    List<Goods> findByName(String name);

    int getTotalCount();

    int update(Goods pojo);
}
