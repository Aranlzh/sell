package top.aranlzh.sell.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import top.aranlzh.sell.exception.SellerAuthorizeException;

/**
 * @author Aranlzh
 * @create 2019-03-13 15:28
 * @desc
 **/
@ControllerAdvice
public class SellExceptionHandler {

    //拦截登录异常
    //http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat("/seller/login"));
    }
}
