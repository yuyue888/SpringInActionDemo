package core.controller;

import core.entity.Goods;
import core.enums.CountModifyType;
import core.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by ssc on 2017/7/20 0020.
 */
@RequestMapping("/")
@RestController
public class GoodsController {

    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    public List<Goods> getGoodsList() {
        return goodService.findAllGoods();
    }

    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    public Map<String, Object> addGoods(@RequestBody Goods goods) {
        return goodService.addNewGoods(goods);
    }

    @RequestMapping(value = "/goods_list", method = RequestMethod.GET)
    public List<Goods> getGoodsListByName(@RequestParam("name") String name) {
        return goodService.findByName(name);
    }

    @RequestMapping(value = "/goods/{id}/count", method = RequestMethod.PUT)
    public Map<String, Object> updateGoodsCount(@PathVariable("id") Integer id, @RequestParam("type") CountModifyType type, @RequestParam("count") Integer count) {
        return goodService.modifyGoodsCount(id, count, type);
    }
}
