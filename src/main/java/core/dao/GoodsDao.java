package core.dao;

import java.util.List;
import core.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsDao {
    int insert( Goods pojo);

    int insertSelective( Goods pojo);

    int insertList(List<Goods> pojo);

    int update(Goods pojo);
}
