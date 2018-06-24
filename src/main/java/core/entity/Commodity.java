package core.entity;

import core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "t_commodity")
@Data
@ToString
public class Commodity extends BaseEntity {
    private static final long serialVersionUID = -3878959894726227352L;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String description;

    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 库存
     */
    @Column
    private Integer stock;

    /**
     * 单位
     */
    @Column
    private String unit;

    /**
     * 规格
     */
    @Column
    private String specification;

    /**
     * 生产商
     */
    @Column
    private String manufacturer;

    /**
     * 备注
     */
    @Column
    private String memo;

    /**
     * 操作员
     */
    @Column
    private String operator;

    @Column
    private Integer enable;

}
