<html>
<#include "../common/header.ftl">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>手机号</th>
                            <th>密码</th>
                            <th>管理员状态</th>
                            <th>管理员角色</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list sellerInfoList as sellerInfo>
                            <tr>
                                <td>${sellerInfo.phone}</td>
                                <td>${sellerInfo.password}</td>
                                <td>${sellerInfo.getSellerStatusEnum().message}</td>
                                <td>${sellerInfo.getSellerRoleEnum().message}</td>
                                <td>${sellerInfo.createTime}</td>
                                <td>${sellerInfo.updateTime}</td>
                                <td>
                                    <#if sellerInfo.status == 1>
                                        <a href="/sell/seller/stop?phone=${sellerInfo.phone}">停用</a>
                                    </#if>
                                    <#if sellerInfo.status == 0>
                                        <a href="/sell/seller/use?phone=${sellerInfo.phone}">启用</a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>