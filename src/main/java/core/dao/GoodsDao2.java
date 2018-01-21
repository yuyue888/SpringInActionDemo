package core.dao;

import core.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsDao2 {
    int update(Goods pojo);
}
