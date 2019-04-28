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
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>￥${orderDTO.orderAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <#--订单详情表数据-->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>商品名称</th>
                            <th>价格</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                            <tr>
                                <td>${orderDetail.productId}</td>
                                <td>${orderDetail.productName}</td>
                                <td>￥${orderDetail.productPrice}</td>
                                <td>${orderDetail.productQuantity}</td>
                                <td>￥${orderDetail.productQuantity * orderDetail.productPrice}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <#--操作-->
                <div class="col-md-12 column">
                    <form class="form-horizontal" role="form" method="post" action="/sell/seller/pay/paid">
                        <div class="form-group" style="margin-left: 5px;">
                            <label>选择支付方式：</label><br/>
                            <select class="form-control" style="width: 100px;" name="payType">
                                <option value="0" selected>未支付</option>
                                <option value="1">现金</option>
                                <option value="2">支付宝</option>
                                <option value="3">微信</option>
                            </select>
                        </div>
                        <input hidden type="text" name="orderId" value="${(orderDTO.orderId)!''}">
                        <button type="submit" class="btn btn-default btn-primary">确认支付</button>
                    </form>
                    <#--<br/>-->
                    <#--<a href="/sell/seller/pay/paid?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">确认支付</a>-->
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>