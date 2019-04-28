package top.aranlzh.sell.form;

import lombok.Data;

/**
 * @author Aranlzh
 * @create 2019-03-19 23:41
 * @desc 新用户注册表
 **/
@Data
public class SellerRegisterForm {
    /** 电话. */
    private String phone;
    /** 密码. */
    private String password;
    /** 状态. */
    private Integer status;
    /** 角色. */
    private Integer role;

}
