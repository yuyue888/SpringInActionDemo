package core.entity;

import core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ToString
@Data
@Table(name = "t_commodity_manage")
public class CommdityManage extends BaseEntity {
    private static final long serialVersionUID = 7407061532299921808L;

    @Column(name = "commodity_id" ,nullable = false)
    private Long commodityId;

    @Column(name = "biz_no" , nullable = false , unique = true)
    private String bizNo;

    @Column(name = "price_in")
    private BigDecimal priceIn;

    @Column(name = "price_out")
    private BigDecimal priceOut;

    @Column
    private Integer number;

    @Column
    private String operator;

    @Column
    private String dealer;

    @Column
    private String memo;
}
