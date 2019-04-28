package top.aranlzh.sell.service;

import top.aranlzh.sell.dataobject.SellerInfo;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-12 16:58
 * @desc 卖家端
 **/
public interface SellerService {

    SellerInfo findSellerInfoByPhoneAndPassword(String phone, String password);

    SellerInfo findOneByPhone(String phone);

    String getTokenValue();

    /** 添加用户. */
    SellerInfo save(SellerInfo sellerInfo);

    /** 停用用户. */
    SellerInfo stop(SellerInfo sellerInfo);

    /** 启用用户. */
    SellerInfo use(SellerInfo sellerInfo);

    /** 查询用户列表. */
    List<SellerInfo> findList();
}
