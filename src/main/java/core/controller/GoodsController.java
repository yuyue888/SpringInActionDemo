package core.controller;

import core.entity.Goods;
import core.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/goods/all", method = RequestMethod.GET)
    public List<Goods> findGoods() {
        return goodsService.getAllGoods();
    }

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    public List<Goods> findByName(@RequestParam("name") String name){
        return goodsService.findByName(name);
    }

    @RequestMapping(value = "/goods/count", method = RequestMethod.GET)
    public int getTotalCount() {
        return goodsService.getTotalCount();
    }
}
