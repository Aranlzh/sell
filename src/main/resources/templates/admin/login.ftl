<html>
    <#include "../common/header.ftl">
    <body>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <h3 class="text-center">
                        卖家管理系统
                    </h3>
                    <form class="form-horizontal" role="form" method="post" action="/sell/seller/login/adminlogin">
                        <div class="form-group">
                            <label for="username" class="col-sm-2 control-label">手机号：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="username" name="phone" value="${(sellerInfo.phone)!''}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码：</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="password" name="password" value="${(sellerInfo.password)!''}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">登录</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>