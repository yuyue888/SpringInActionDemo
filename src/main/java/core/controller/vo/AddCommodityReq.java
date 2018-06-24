package core.controller.vo;

public class AddCommodityReq {
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

    private Integer enable;
}
