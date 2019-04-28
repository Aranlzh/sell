<html>
<#include "../common/header.ftl">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="/sell/laydate/laydate.js"></script>
<script src="/sell/js/echarts.min.js"></script>
<body>
<div id="wrapper" class="toggled">

    <#--边栏sidebar-->
    <#include "../common/nav.ftl">

    <div id="page-content-wrapper">
        <nav class="navbar navbar-default" role="navigation">
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="点击选择日期" name="choiceDate" id="choiceDate"/>
                    </div>
                    <button class="btn btn-default" onclick="searchOneDateReport()">查看日报</button>
                </div>
                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="点击选择日期" name="choiceRangeDate" id="choiceRangeDate"/>
                    </div>
                    <button class="btn btn-default" onclick="searchWeekReport()">查看报表</button>
                </div>

            </div>
        </nav>
        <div class="container">
            <div class="row clearfix">
                <#if startDate==endDate>
                    <h3 style="width: 100%; text-align: center;">(${startDate})报表</h3><br/>
                </#if>
                <#if startDate!=endDate>
                    <h3 style="width: 100%; text-align: center;">(${startDate}-${endDate})报表</h3><br/>
                </#if>
                <#if reportDetail.reportNum!=0>
                    <#--营业额及渠道收款-->
                    <br/><h4 style="width: 100%; text-align: center;">营业额及渠道收款</h4><br/>
                    <div class="col-md-6 column" style="display:inline-block;" >
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>营业额</th>
                                <#list reportDetail.payTypeDetailDTOList as payTypeList>
                                    <th>${payTypeList.getPayTypesEnum().message}</th>
                                </#list>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>￥${reportDetail.saleAmount}</td>
                                <#list reportDetail.payTypeDetailDTOList as payTypeList>
                                    <td>￥${payTypeList.sum}</td>
                                </#list>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6 column">
                        <div id="firstECharts" style="width: 100%;height:400px;"></div>
                    </div>
                    <#--时间段订单数-->
                    <br/><h4 style="width: 100%; text-align: center;">时间段订单数</h4><br/>
                    <div class="col-md-6 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>时间段</th>
                                <th>订单数</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list reportDetail.timeSaleDetailDTOList as timeSaleList>
                                <tr>
                                    <td>${timeSaleList.getTimeFormatEnum().message}</td>
                                    <td>${timeSaleList.count}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6 column">
                        <div id="secondECharts" style="width: 100%;height:400px;"></div>
                    </div>
                <#--商品销售情况-->
                    <br/><h4 style="width: 100%; text-align: center;">商品销售情况</h4><br/>
                    <div class="col-md-6 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>商品</th>
                                <th>销售数量</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list reportDetail.productSaleDetailDTOList as productSaleList>
                                <tr>
                                    <td>${productSaleList.productName}</td>
                                    <td>${productSaleList.count}</td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6 column">
                        <div id="thirdECharts" style="width: 100%;height:400px;"></div>
                    </div>
                </#if>
                <#if reportDetail.reportNum==0>
                    <h4 style="width: 100%; text-align: center;">还没有订单哦~</h4>
                </#if>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var myFirstECharts = echarts.init(document.getElementById('firstECharts'));
    var payType = [];
    var testList = [];
    var resultList=new Array();
    for(var a=0;a<4;a++){
        resultList[a]=new Array();
        for(var b=0;b<2;b++){
            resultList[a][b]=0;
        }
    }
    <#if reportDetail.payTypeDetailDTOList??>
        <#list reportDetail.payTypeDetailDTOList as payList>
            payType.push('${payList.getPayTypesEnum().message}');
            testList.push('${payList.sum}','${payList.getPayTypesEnum().message}');
        </#list>
    </#if>
    var k=0;
    var l=testList.length;
    for(var i=0; i<4; i++){
        if (k<l){
            for(var j=0; j<2; j++){
                if (k<l){
                    if (j==0){
                        resultList[i][j] = testList[k];
                        k++;
                    } else{
                        resultList[i][j] = testList[k];
                        k++;
                    }

                } else {
                    break;
                }
            }
        } else{
            break;
        }
    }
    option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: payType
        },
        series: [
            {
                name:'支付来源',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                // data: resultList
                data:[
                    {value:resultList[0][0], name:resultList[0][1]},
                    {value:resultList[1][0], name:resultList[1][1]},
                    {value:resultList[2][0], name:resultList[2][1]},
                    {value:resultList[3][0], name:resultList[3][1]}
                ]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myFirstECharts.setOption(option);

    var mySecondECharts = echarts.init(document.getElementById('secondECharts'));
    var timeFormat = [];
    var orderCount = [];
    <#if reportDetail.timeSaleDetailDTOList??>
    <#list reportDetail.timeSaleDetailDTOList as timeList>
    timeFormat.push('${timeList.getTimeFormatEnum().message}');
    orderCount.push('${timeList.count}');
    </#list>
    </#if>
    console.log(timeFormat);
    console.log(orderCount);
    option1 = {
        xAxis: {
            type: 'category',
            data: timeFormat
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: orderCount,
            type: 'line'
        }]
    };
    mySecondECharts.setOption(option1);

    var myThirdECharts = echarts.init(document.getElementById('thirdECharts'));
    var productName = [];
    var productNum = [];
    <#if reportDetail.productSaleDetailDTOList??>
    <#list reportDetail.productSaleDetailDTOList as productList>
    productName.push('${productList.productName}');
    productNum.push('${productList.count}');
    </#list>
    </#if>
    console.log(productName);
    console.log(productNum);
    option2 = {
        xAxis: {
            type: 'category',
            data: productName
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: productNum,
            type: 'bar'
        }]
    };
    myThirdECharts.setOption(option2);
</script>
<script type="text/javascript">
    laydate.render({
        elem: '#choiceDate'
        ,showBottom: false
        ,theme: '#393D49'
    });
    laydate.render({
        elem: '#choiceRangeDate'
        ,range: true
        ,theme: '#393D49'
    });
</script>
<script type="text/javascript">
    function searchOneDateReport() {
        var choiceDate = $('#choiceDate').val();
        if (choiceDate==""){
            alert("请选择日期");
            return false;
        }
        window.location.href = "/sell/seller/order/report?startDate="+choiceDate+'&endDate='+choiceDate;
    }
    function searchWeekReport() {
        var rangeDate = $('#choiceRangeDate').val();
        var startDate = rangeDate.substr(0,10);
        var endDate = rangeDate.substr(13,10);
        if (rangeDate==""){
            alert("请选择日期");
            return false;
        }
        window.location.href = "/sell/seller/order/report?startDate="+startDate+"&endDate="+endDate;
    }
</script>

</body>
</html>