<html>
<#include "../common/header.ftl">
<link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.bootcss.com/bootstrap-fileinput/4.3.3/css/fileinput.min.css" rel="stylesheet">
<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save" enctype="multipart/form-data">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片(新增商品请先选择文件，随后点击上传！修改商品时如果不需要修改图片，则不需要操作！)</label>
                            <#--<img height="100" width="100" src="${(productInfo.productIcon)!''}" alt="">-->
                            <#--<input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!''}"/>-->
                            <input id="input-b1" name="uploadImgInput" type="file" class="file" data-browse-on-zone-click="true">
                            <input id="productIcon" name="productIcon" type="text" value="${(productInfo.productIcon)!''}" hidden/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                                selected
                                            </#if>
                                        >${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.3.3/js/fileinput.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap-fileinput/4.3.5/js/locales/zh.min.js "></script>
<script type="text/javascript">
    $('#input-b1').fileinput({
        language: "zh",                             //配置语言
        uploadUrl: "/sell/file/uploadImg",                   //这个是配置上传调取的后台地址
        showUpload : true,                          //显示整体上传的按钮
        showRemove : true,                          //显示整体删除的按钮
        uploadAsync: true,                          //默认异步上传
        uploadLabel: "上传",                         //设置整体上传按钮的汉字
        removeLabel: "移除",                         //设置整体删除按钮的汉字
        uploadClass: "btn btn-primary",             //设置上传按钮样式
        showCaption: true,                          //是否显示标题
        dropZoneEnabled: false,                     //是否显示拖拽区域
        maxFileSize : 500,                          //文件大小限制
        maxFileCount: 1,                           //允许最大上传数，可以多个，
        enctype: 'multipart/form-data',
        allowedFileExtensions : ["jpg", "png","gif"],/*上传文件格式限制*/
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        showBrowse: true,
        browseOnZoneClick: true
    }).on('filepreupload', function() {
        //上传中
        console.log('文件正在上传');
    }).on("fileuploaded", function(event, data) {
        var newFileName = data.response.data;
        $('#productIcon').attr("value",newFileName);
        console.log('文件上传成功！');
    }).on('fileerror', function(event, data) {  //一个文件上传失败
        console.log(data.response.msg);
    });
</script>
</body>
</html>