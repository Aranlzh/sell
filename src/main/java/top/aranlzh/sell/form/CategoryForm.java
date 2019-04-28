package top.aranlzh.sell.form;

import lombok.Data;

/**
 * @author Aranlzh
 * @create 2019-03-13 15:29
 * @desc
 **/
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;
}
