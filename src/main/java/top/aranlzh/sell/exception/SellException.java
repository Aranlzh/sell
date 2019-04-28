package top.aranlzh.sell.exception;

import top.aranlzh.sell.enums.ResultEnum;

/**
 * @author Aranlzh
 * @create 2019-03-12 0:16
 * @desc
 **/
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
