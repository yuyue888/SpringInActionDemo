package core.entity;

import core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Table(name = "t_category")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 1698938644222319596L;

    @Column
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column
    private String description;

    @Column(name = "parent_id")
    private Long parentId;
}
