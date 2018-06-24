package core.controller.vo;

import java.util.Date;

public class CommodityVo {
    private Long id;

    private String name;

    private String code;

    private String description;

    private Long categoryId;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 单位
     */
    private String unit;

    /**
     * 规格
     */
    private String specification;

    /**
     * 生产商
     */
    private String manufacturer;

    /**
     * 备注
     */
    private String memo;

    /**
     * 操作员
     */
    private String operator;

    private Integer enable;

    private Date createTime;

    private Date updateTime;
}
