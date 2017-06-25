package core.service;

import core.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ssc on 2017/6/26.
 */
@Service
public class GoodService {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Goods> findAllGoods(){
        return entityManager.createQuery("FROM Goods g").getResultList();
    }
}
