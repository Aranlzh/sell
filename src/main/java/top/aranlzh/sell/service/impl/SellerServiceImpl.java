package top.aranlzh.sell.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.aranlzh.sell.constant.CookieConstant;
import top.aranlzh.sell.constant.RedisConstant;
import top.aranlzh.sell.dataobject.SellerInfo;
import top.aranlzh.sell.enums.ResultEnum;
import top.aranlzh.sell.enums.SellerStatusEnum;
import top.aranlzh.sell.exception.SellException;
import top.aranlzh.sell.repository.SellerInfoRepository;
import top.aranlzh.sell.service.SellerService;
import top.aranlzh.sell.utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Aranlzh
 * @create 2019-03-12 16:59
 * @desc
 **/
@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public SellerInfo findSellerInfoByPhoneAndPassword(String phone, String password) {
        return repository.findByPhoneAndPassword(phone, password);
    }

    @Override
    public SellerInfo findOneByPhone(String phone) {
        SellerInfo sellerInfo = repository.findOne(phone);
        if (sellerInfo == null) {
            throw new SellException(ResultEnum.SELLER_NOT_EXIST);
        }
//        List<SellerInfo> sellerInfoList = repository.findByPhone(phone);
//        if (CollectionUtils.isEmpty(sellerInfoList)) {
//            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
//        }
        return sellerInfo;
    }

    @Override
    public String getTokenValue() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        //去redis里查询并返回
        return redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
    }

    @Override
    public SellerInfo save(SellerInfo sellerInfo) {
       return  repository.save(sellerInfo);
    }

    @Override
    @Transactional
    public SellerInfo stop(SellerInfo sellerInfo) {
        // 判断用户状态
        if (sellerInfo.getStatus().equals(SellerStatusEnum.STOP.getCode())){
            log.error("【停用用户】用户状态不正确, sellerUsername={}, sellerStatus={}", sellerInfo.getPhone(), sellerInfo.getStatus());
            throw new SellException(ResultEnum.SELLER_STATUS_ERROR);
        }

        //修改用户状态
        sellerInfo.setStatus(SellerStatusEnum.STOP.getCode());
        SellerInfo updateResult = repository.save(sellerInfo);
        if (updateResult == null) {
            log.error("【停用用户】更新失败, sellInfo={}", sellerInfo);
            throw new SellException(ResultEnum.SELLER_UPDATE_FAIL);
        }

        return sellerInfo;
    }

    @Override
    @Transactional
    public SellerInfo use(SellerInfo sellerInfo) {
        // 判断用户状态
        if (sellerInfo.getStatus().equals(SellerStatusEnum.USE.getCode())){
            log.error("【启用用户】用户状态不正确, sellerUsername={}, sellerStatus={}", sellerInfo.getPhone(), sellerInfo.getStatus());
            throw new SellException(ResultEnum.SELLER_STATUS_ERROR);
        }

        //修改用户状态
        sellerInfo.setStatus(SellerStatusEnum.USE.getCode());
        SellerInfo updateResult = repository.save(sellerInfo);
        if (updateResult == null) {
            log.error("【启用用户】更新失败, sellInfo={}", sellerInfo);
            throw new SellException(ResultEnum.SELLER_UPDATE_FAIL);
        }

        return sellerInfo;
    }

    @Override
    public List<SellerInfo> findList() {
        return repository.findAll();
    }
}
