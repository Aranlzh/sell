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
                    <form role="form" method="post" action="/sell/seller/save">
                        <div class="form-group">
                            <label>电话：</label>
                            <input name="phone" type="text" class="form-control" value="${(sellInfo.phone)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>输入密码：</label>
                            <input name="password" type="password" class="form-control" value="${(sellInfo.password)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>用户状态：</label>
                            <select class="form-control" style="width: 150px;" name="status">
                                <option value="0" selected>停用</option>
                                <option value="1">启用</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>用户角色：</label>
                            <select class="form-control" style="width: 150px;" name="role">
                                <option value="0">超级管理员</option>
                                <option value="1" selected>普通管理员</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>