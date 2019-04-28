package top.aranlzh.sell.form;

import lombok.Data;

/**
 * @author Aranlzh
 * @create 2019-03-19 17:27
 * @desc 管理员登录表单
 **/
@Data
public class SellerLoginForm {
    /** 用户名(电话号码). */
    private String phone;
    /** 密码. */
    private String password;
}
