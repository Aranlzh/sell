package top.aranlzh.sell.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.aranlzh.sell.dataobject.SellerInfo;

import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-12 17:00
 * @desc
 **/
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    List<SellerInfo> findByPhone(String phone);
    SellerInfo findByPhoneAndPassword(String phone, String password);
}
