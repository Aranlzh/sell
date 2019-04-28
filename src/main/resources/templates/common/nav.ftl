<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
                卖家管理系统
            </a>
        </li>
        <li>
            <a <#--href="/sell/seller/order/list"--> onclick="dateOrderList()"><i class="fa fa-fw fa-list-alt"></i>订单</a>
        </li>
        <li>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 商品 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/product/list">列表</a></li>
                <li><a href="/sell/seller/product/index">新增</a></li>
            </ul>
        </li>
        <li>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 类目 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/category/list">列表</a></li>
                <li><a href="/sell/seller/category/index">新增</a></li>
            </ul>
        </li>

        <li >
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 管理员列表 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/list">列表</a></li>
                <li><a href="/sell/seller/index">新增</a></li>
            </ul>
        </li>
        <li>
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true"><i class="fa fa-fw fa-plus"></i> 数据管理 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/sell/seller/order/list">所有订单</a></li>
                <li><a onclick="dateReport()">日报</a></li>
                <#--<li><a href="/sell/seller/order/">周报</a></li>-->
                <li><a onclick="monthReport()">月报</a></li>
                <#--<li><a href="/sell/seller/order/">季报</a></li>-->
                <li><a onclick="yearReport()">年报</a></li>
            </ul>
        </li>
        <li>
            <a href="/sell/seller/logout"><i class="fa fa-fw fa-list-alt"></i> 登出</a>
        </li>
    </ul>
</nav>
<script language="javascript" type="text/javascript">
    var date = new Date();
    var seperator1 = "-";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate;
    function isInArray(arr,value){
        for(var i = 0; i < arr.length; i++){
            if(value === arr[i]){
                return true;
            }
        }
        return false;
    }
    function dateOrderList() {
        window.location='/sell/seller/order/dateList?date='+currentdate;
    }
    function dateReport() {
        window.location='/sell/seller/order/report?startDate='+currentdate+'&endDate='+currentdate;
    }
    function monthReport() {
        var currentMonthStart =  year + seperator1 + month + seperator1 + '01';
        var bigMonth= ['01','03','05','07','08','10','12'];
        var smallMonth = ['04','06','09','11'];
        if (isInArray(bigMonth,month)){
            var currentMonthEnd = year + seperator1 + month + seperator1 + '31';
        } else if (isInArray(smallMonth,month)){
            var currentMonthEnd = year + seperator1 + month + seperator1 + '30';
        } else{
            if (year%4==0 && year%100!=0){
                var currentMonthEnd = year + seperator1 + month + seperator1 + '29';
            } else if (year%400==0){
                var currentMonthEnd = year + seperator1 + month + seperator1 + '29';
            } else{
                var currentMonthEnd = year + seperator1 + month + seperator1 + '28';
            }
        }
        window.location='/sell/seller/order/report?startDate='+currentMonthStart+'&endDate='+currentMonthEnd;
    }
    function yearReport() {
        var currentYearStart =  year + seperator1 + '01' + seperator1 + '01';
        var currentYearEnd =  year + seperator1 + '12' + seperator1 + '31';
        window.location='/sell/seller/order/report?startDate='+currentYearStart+'&endDate='+currentYearEnd;
    }

</script>