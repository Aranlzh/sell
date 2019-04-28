package top.aranlzh.sell.VO;

import lombok.Data;

/**
 * @author Aranlzh
 * @create 2019-03-12 10:10
 * @desc http请求返回的最外层对象
 **/
@Data
public class ResultVO<T> {

    /** 错误码 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 具体内容 */
    private T data;
}
