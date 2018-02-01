package core.dao;

import java.util.List;
import core.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GoodsDao {
    int insert( Goods pojo);

    List<Goods> getAllGoods();

    List<Goods> findByName(String name);

    @Select("select * from goods where name like %#{name}% order by gprice")
    List<Goods> findByNameOrderByGprice(@Param("name") String name);

    int getTotalCount();

    int update(Goods pojo);
}
