package core.dao.impl;

import core.dao.GoodsDao;
import core.entity.Goods;
import core.exceptions.GoodsNotExistException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ssc on 2017/7/20 0020.
 */
@Repository
public class GoodsDaoImpl implements GoodsDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Goods> findAllGoods() {
        String hql = "FROM Goods g";
        return entityManager.createQuery(hql).getResultList();
    }

    @Override
    public List<Goods> findByGoodsName(String name) {
        String hql = "FROM Goods g ";
        if (StringUtils.isNotEmpty(name)) {
            hql += " where gname like :name";
        }
        return entityManager.createQuery(hql).setParameter("name", name).getResultList();
    }

    @Override
    public void addNewGoods(Goods goods) {
        entityManager.persist(goods);
    }

    @Override
    @Transactional
    public void updateGoods(Goods goods) throws GoodsNotExistException {
        Goods oldGoods = entityManager.find(Goods.class, goods.getGid());
        if(oldGoods == null){
            throw new GoodsNotExistException();
        }
        BeanUtils.copyProperties(oldGoods,goods,"gid");
        entityManager.merge(goods);
    }

    @Override
    public void deleteGoodsById(Integer id) throws GoodsNotExistException {
        Goods oldGoods = entityManager.find(Goods.class, id);
        if(oldGoods == null){
            throw new GoodsNotExistException();
        }
        entityManager.remove(oldGoods);
    }

    @Override
    public Goods getGoodsById(Integer id) {
        return entityManager.find(Goods.class, id);
    }
}
