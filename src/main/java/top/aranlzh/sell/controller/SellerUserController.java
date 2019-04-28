package top.aranlzh.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import top.aranlzh.sell.constant.CookieConstant;
import top.aranlzh.sell.constant.RedisConstant;
import top.aranlzh.sell.dataobject.SellerInfo;
import top.aranlzh.sell.enums.ResultEnum;
import top.aranlzh.sell.exception.SellException;
import top.aranlzh.sell.form.SellerLoginForm;
import top.aranlzh.sell.form.SellerRegisterForm;
import top.aranlzh.sell.service.SellerService;
import top.aranlzh.sell.utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Aranlzh
 * @create 2019-03-12 16:56
 * @desc 卖家用户
 **/
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("admin/login");
    }

    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        String phone = sellerService.getTokenValue();
        SellerInfo sellerInfo = sellerService.findOneByPhone(phone);
        if (sellerInfo.getRole().equals(0)){
            List<SellerInfo> sellerInfoList  = sellerService.findList();
            map.put("sellerInfoList", sellerInfoList);
            return new ModelAndView("admin/list", map);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            map.put("msg", ResultEnum.INSUFFICIENT_PERMISSIONS.getMessage());
            map.put("url", "/sell/seller/order/dateList?date="+date);
            return new ModelAndView("common/error", map);
        }
    }

    @GetMapping("/index")
    public ModelAndView index(Map<String, Object> map){
        String phone = sellerService.getTokenValue();
        SellerInfo sellerInfo = sellerService.findOneByPhone(phone);
        if (sellerInfo.getRole().equals(0)) {
            return new ModelAndView("admin/index");
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            map.put("msg", ResultEnum.INSUFFICIENT_PERMISSIONS.getMessage());
            map.put("url", "/sell/seller/order/dateList?date="+date);
            return new ModelAndView("common/error", map);
        }
    }

    @GetMapping("/stop")
    public ModelAndView stop(@RequestParam("phone") String phone,
                             Map<String, Object> map){
        try {
            SellerInfo sellerInfo = sellerService.findOneByPhone(phone);
            sellerService.stop(sellerInfo);
        } catch (SellException e) {
            log.error("【停用用户权限】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.SELLER_STOP_SUCCESS.getMessage());
        map.put("url", "/sell/seller/list");
        return new ModelAndView("common/success");
    }

    @GetMapping("/use")
    public ModelAndView use(@RequestParam("phone") String phone,
                            Map<String, Object> map){
        try {
            SellerInfo sellerInfo = sellerService.findOneByPhone(phone);
            sellerService.use(sellerInfo);
        } catch (SellException e) {
            log.error("【启用用户权限】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/list");
            return new ModelAndView("common/error", map);
        }

        map.put("msg", ResultEnum.SELLER_USE_SUCCESS.getMessage());
        map.put("url", "/sell/seller/list");
        return new ModelAndView("common/success");
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid SellerRegisterForm form,
                                   BindingResult bindingResult,
                                   HttpServletResponse response,
                                   Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/list");
            return new ModelAndView("common/error", map);
        }
        log.info("form={}",form);
        SellerInfo sellerInfo = new SellerInfo();
        try{
            BeanUtils.copyProperties(form,sellerInfo);
            log.info("sellInfo={}",sellerInfo);
            sellerService.save(sellerInfo);
        }catch(Exception e){
            map.put("msg", ResultEnum.REGISTER_FAIL.getMessage());
            map.put("url", "/sell/seller/list");
            return new ModelAndView("common/error");
        }

        map.put("msg", ResultEnum.REGISTER_SUCCESS.getMessage());
        map.put("url", "/sell/seller/list");
        return new ModelAndView("common/success", map);
    }


    @PostMapping("/login/adminlogin")
    public ModelAndView adminLogin(@Valid SellerLoginForm form,
                                   BindingResult bindingResult,
                                   HttpServletResponse response,
                                   Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/login");
            return new ModelAndView("common/error", map);
        }

        String phone = form.getPhone();
        String password = form.getPassword();

        if ( phone==null || password==null ){
            map.put("mag",ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/login");
            return new ModelAndView("common/error",map);
        }else{
            SellerInfo sellerInfo = sellerService.findSellerInfoByPhoneAndPassword(phone, password);
            if (sellerInfo == null) {
                map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
                map.put("url", "/sell/seller/login");
                return new ModelAndView("common/error",map);
            }else if (sellerInfo.getStatus()==0){
                map.put("msg", ResultEnum.LOGIN_STATUS_FAIL.getMessage());
                map.put("url", "/sell/seller/login");
                return new ModelAndView("common/error",map);
            }

            //2. 设置token至redis
            String token = UUID.randomUUID().toString();
            Integer expire = RedisConstant.EXPIRE;

            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), phone, expire, TimeUnit.SECONDS);

            //3. 设置token至cookie
            CookieUtil.set(response, CookieConstant.TOKEN, token, expire);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());

            map.put("msg", ResultEnum.LOGIN_SUCCESS.getMessage());
            map.put("url", "/sell/seller/order/dateList?date="+date);

            return new ModelAndView( "common/success",map);
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        //1. 从cookie里查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            //2. 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3. 清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/login");
        return new ModelAndView("common/success", map);
    }
}
