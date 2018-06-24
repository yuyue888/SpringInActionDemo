package core.entity;

import core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Table(name = "t_user")
@Data
@ToString
public class User extends BaseEntity {

    private static final long serialVersionUID = -8794258596980110974L;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String nickname;

    @Column
    private Integer enable;

}
