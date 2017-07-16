package core.controller;

import core.entity.Goods;
import core.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * Created by ssc on 2017/7/13 0013.
 */
@RestController
@RequestMapping("/")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    public int addGoods(@RequestBody Goods goods) {
        return goodsService.insert(goods);
    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    public List<Goods> findGoods() {
        return goodsService.getAllGoods();
    }
}
