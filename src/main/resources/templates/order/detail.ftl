<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">


    <#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>订单总金额</th>
                            <th>就餐方式</th>
                            <#if orderDTO.getPayStatusEnum().message == "支付成功">
                                <th>支付状态</th>
                                <th>支付方式</th>
                            </#if>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>￥${orderDTO.orderAmount}</td>
                            <td>${orderDTO.getEatTypeEnum().message}</td>
                            <#if orderDTO.getPayStatusEnum().message == "支付成功">
                                <td>${orderDTO.getPayStatusEnum().message}</td>
                                <td>${orderDTO.getPayTypesEnum().message}</td>
                            </#if>
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
                    <#if orderDTO.getOrderStatusEnum().message == "新订单">
                        <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">完结订单</a>
                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
                    </#if>
                    <#if orderDTO.getPayStatusEnum().message == "未支付">
                        <a href="/sell/seller/order/paid?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">支付订单</a>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
<#--弹窗-->
<div class="modal fade" id="myModal_notice" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                <span id="noticeMsg"></span>
            </div>
            <div class="modal-footer">
                <button onclick="javascript:document.getElementById('new').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button onclick="scanThisOrder()" type="button" class="btn btn-primary">查看该订单(3s后自动进入)</button>
            </div>
        </div>
    </div>
</div>

<#--播放音乐-->
<audio id="new" loop="loop">
    <source src="/sell/mp3/new.mp3" type="audio/mpeg" />
</audio>
<audio id="cancel" loop="loop">
    <source src="/sell/mp3/cancel.mp3" type="audio/mpeg" />
</audio>
<audio id="urge" loop="loop">
    <source src="/sell/mp3/urge.mp3" type="audio/mpeg" />
</audio>
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/sell/js/webSocketFunc.js"></script>
</body>
</html>