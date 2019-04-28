package top.aranlzh.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.aranlzh.sell.enums.SellerRoleEnum;
import top.aranlzh.sell.enums.SellerStatusEnum;
import top.aranlzh.sell.utils.EnumUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Aranlzh
 * @create 2019-03-12 16:58
 * @desc
 **/
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfo {

    /** 用户名（手机号码）. */
    @Id
    private String phone;

    /** 用户密码. */
    private String password;

    /** 用户状态. */
    private Integer status;

    /** 用户角色. */
    private Integer role;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    public SellerInfo(String phone, String password, Integer status, Integer role) {
        this.phone = phone;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    @JsonIgnore
    public SellerStatusEnum getSellerStatusEnum() {
        return EnumUtil.getByCode(status, SellerStatusEnum.class);
    }

    @JsonIgnore
    public SellerRoleEnum getSellerRoleEnum() {
        return EnumUtil.getByCode(role, SellerRoleEnum.class);
    }
}
