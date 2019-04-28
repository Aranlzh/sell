<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <#--主要内容content-->
    <div id="page-content-wrapper">
        <nav class="navbar navbar-default" role="navigation">
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="输入订单号" name="orderId" id="orderId"/>
                    </div>
                    <button class="btn btn-default" onclick="searchOneOrder()">查找订单</button>
                </div>
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 按订单状态检索<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/sell/seller/order/listByOrderStatus?orderStatus=0">新订单</a></li>
                            <li><a href="/sell/seller/order/listByOrderStatus?orderStatus=1">完结</a></li>
                            <li><a href="/sell/seller/order/listByOrderStatus?orderStatus=2">已取消</a></li>
                        </ul>
                    </li>
                </ul>
                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="点击选择日期" name="choiceDate" id="choiceDate"/>
                    </div>
                    <button class="btn btn-default" onclick="searchOneDateOrder()">查找订单</button>
                </div>
            </div>
        </nav>
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>桌号</th>
                            <th>金额</th>
                            <th>就餐方式</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>支付方式</th>
                            <th>创建时间</th>
                            <th colspan="3">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list orderDTOPage.content as orderDTO>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.tableId}</td>
                            <td>￥${orderDTO.orderAmount}</td>
                            <td>${orderDTO.getEatTypeEnum().message}</td>
                            <td>${orderDTO.getOrderStatusEnum().message}</td>
                            <#if orderDTO.getOrderStatusEnum().message == "已取消">
                                <td></td>
                                <td></td>
                            </#if>
                            <#if orderDTO.getOrderStatusEnum().message != "已取消">
                                <td>${orderDTO.getPayStatusEnum().message}</td>
                                <td>${orderDTO.getPayTypesEnum().message}</td>
                            </#if>
                            <td>${orderDTO.createTime}</td>
                            <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                            <td>
                                <#if orderDTO.getOrderStatusEnum().message != "已取消" && orderDTO.getPayStatusEnum().message == "未支付">
                                    <a href="/sell/seller/order/paid?orderId=${orderDTO.orderId}">支付</a>
                                </#if>
                            </td>
                            <td>
                                <#if orderDTO.getOrderStatusEnum().message == "新订单">
                                    <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..orderDTOPage.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
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
<script src="/sell/laydate/laydate.js"></script>
<script src="/sell/js/webSocketFunc.js"></script>
<script>
    laydate.render({
        elem: '#choiceDate'
        ,theme: '#393D49'
    });
    function searchOneOrder() {
        var orderId = $('#orderId').val();
        // console.log(orderId);
        if (orderId==""){
            alert("请输入订单号");
            return false;
        }
        window.location.href = "/sell/seller/order/detail?orderId="+orderId;
    }
    function searchOneDateOrder() {
        var choiceDate = $('#choiceDate').val();
        // console.log(choiceDate);
        if (choiceDate==""){
            alert("请选择日期");
            return false;
        }
        window.location.href = "/sell/seller/order/dateList?date="+choiceDate;
    }
</script>

</body>
</html>