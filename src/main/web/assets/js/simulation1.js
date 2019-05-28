function data_delete(){
    if(confirm('确定要删除这些数据吗？'))
        window.open("/admin/unit/x1_delete","_self");

}

sendMsg = function(){
    var form = document.getElementById("form1");
    form.action = '/admin/unit/X1MsgAdd';
    if($("#opType").find("option:selected").text()==''){
        alert('操作类型不能为空。');
        return false;
    }
    if($("#opType").find("option:selected").text()=='BKMB设定请求' && form.number.value==''){
        alert("目标标识不能为空！");
        return false;
    }
    if($("#opType").find("option:selected").text()=='批量设定' && form.number.value==''){
        alert("目标标识不能为空！");
        return false;
    }
    if($("#opType").find("option:selected").text()=='批量撤销' && form.number.value==''){
        alert("目标标识不能为空！");
        return false;
    }
    if($("#opType").find("option:selected").text()=='用户信息查询' && form.number.value==''){
        alert("目标标识不能为空！");
        return false;
    }
    if($("#opType").find("option:selected").text()=='BKMB撤销请求' && form.number.value==''){
        alert("目标标识不能为空！");
        return false;
    }
    if($("#opType").find("option:selected").text()=='批量设定' && form.numberCount.value==''){
        alert("批量数量不能为空！");
        return false;
    }
    if($("#opType").find("option:selected").text()=='批量撤销' && form.numberCount.value==''){
        alert("批量数量不能为空！");
        return false;
    }
    form.submit();
}

search = function(){
    var form = document.getElementById("form1");
    form.action = "/admin/unit/simulation1";
    form.handle.value = "search";
    form.submit();
}

function change_unit(sel){
    //1  MME
    //2  S-GW
    //3  p-GW
    //4  HSS
    //5  S4-SGSN
    //6  HSGW
    //7  3GPP AAA
    //8  SBC
    //9  P-CSCF
    //10  S-CSCF
    //11  AS
    //12  AGCF
    //13  ATCF
    //14  ATGW
    //15  SBC(T)
    //16  P-CSCF(T)
    //17  S-CSCF(T)
    //18  AS(T)
    //19  AGCF(T)
    //20  ATCF(T)
    //21  ATGW(T)
    //22  SBC(O)
    //23  P-CSCF(O)
    //24  S-CSCF(O)
    //25  AS(O)
    //26  AGCF(O)
    //27  ATCF(O)
    //28  ATGW(O)
    // 29  BNAS
    // 30  NAS
    // 31  AC
    // 32  NAT
    //互联网：AAA、BNAS、NAS、AC、NAT;
    //4G:S-GW、P-GW、MME、HSS
    //IMS: AS、HSS、AGCF、P-CSCF、S-CSCF、SBC、ATCF、ATGW

    //fillOption();
    var unitType = Number(getUnitType(sel)) ;
    var netType;

    switch (unitType) {
        case 1:
        case 2:
        case 3:
        case 5:
        case 6:
            netType = '4G';
            break;
        case 4:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
            netType = 'IMS';
            break;
        case 7:
        case 29:
        case 30:
        case 31:
        case 32:
            netType = 'WWW';
            break;
        default:
            netType = '4G';
    }

    if(netType=='WWW'){

        document.getElementById("opDiv").innerHTML = '<div class="mt_bottom">\n' +
            '                                    <span>X2地址:</span>\n' +
            '                                    <input type="text" name="x2ip" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X2端口:</span>\n' +
            '                                    <input type="text" name="x2port" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X3地址:</span>\n' +
            '                                    <input type="text" name="x3ip" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X3端口:</span>\n' +
            '                                    <input type="text" name="x3port" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>监听方式类型:</span>\n' +
            '                                    <select name="monType" style="width:150px;">\n' +
            '\t\t                                <option value="0">非VPDN域</option>\n' +
            '\t\t                                <option value="1">VPDN域</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>访问地址类型:</span>\n' +
            '                                    <select name="addType" style="width:150px;">\n' +
            '                                    \t\t<option value="0">unused</option>\n' +
            '                                    \t\t<option value="1">IP</option>\n' +
            '                                    \t\t<option value="2">IP_PORT</option>\n' +
            '                                    \t\t<option value="3">IP_ADDRESS_FIELD</option>\n' +
            '                                    \t\t<option value="4">Domain_name</option>\n' +
            '                                    \t\t<option value="5">DST_PORT</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>访问地址标识:</span>\n' +
            '                                    <input type="text" name="add" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>传输层协议:&nbsp;&nbsp;\t</span>\n' +
            '                                    <select name="tp" style="width:150px;">\n' +
            '                                        <option value="0">all</option>\n' +
            '                                        <option value="1">tcp</option>\n' +
            '                                        <option value="2">udp</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>应用业务类型:</span>\n' +
            '                                    <select name="appBizType" style="width:150px;">\n' +
            '                                        <option value="0">all</option>\n' +
            '                                        <option value="1">VOIP</option>\n' +
            '                                        <option value="2">WEB</option>\n' +
            '                                        <option value="3">邮件</option>\n' +
            '                                        <option value="4">即时消息</option>\n' +
            '                                        <option value="5">多媒体业务</option>\n' +
            '                                        <option value="255">其他业务</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>LNS地址:</span>\n' +
            '                                    <input type="text" name="lnsAdd" value="">\n' +
            '                                </div>';
        document.getElementById("opType").innerHTML = '<span>操作类型:</span>\n' +
            '                                    <select name="operType" style="width: 168px;" >\n' +
            '                                        <option value=""></option>\n' +
            '                                        <option value="createConnection">连接建立请求</option>\n' +
            '                                        <option value="a2">连接释放通知</option>\n' +
            '                                        <option value="a3">BKMB设定请求</option>\n' +
            '                                        <option value="a4">BKMB撤销请求</option>\n' +
            '                                        <option value="a5">BKMB列示</option>\n' +
            '                                        <option value="a6">用户信息查询</option>\n' +
            '                                        <option value="a7">网元时间查询</option>\n' +
            '                                        <option value="a8">BK目标参数修改</option>\n' +
            '                                        <option value="a9">BK目标参数查询</option>\n' +
            '                                        <option value="a10">BK目标位置定位查询</option>\n' +
            '                                        <option value="a11">目标内/外网IP地址关联查询</option>\n' +
            '                                        <option value="a15">批量设定</option>\n' +
            '                                        <option value="a14">批量撤销</option>\n' +
            '                                    </select>';
        document.getElementById("numberFormatDiv").innerHTML = '<span>号码格式:</span>\n' +
            '                                    <select name="numberFormat" style="width: 168px;">\n' +
            '                                        <option value="ip">IP</option>\n' +
            '                                        <option value="ip_time">IP_time</option>\n' +
            '                                        <option value="ip_port">IP-PORT</option>\n' +
            '                                        <option value="ip_address_field">Ip_address_field</option>\n' +
            '                                        <option value="nai">NAI</option>\n' +
            '                                        <option value="phone_number">Phone_number</option>\n' +
            '                                        <option value="all">ALL</option>\n' +
            '                                    </select>';
    }
    else if(netType=='IMS'){

        document.getElementById("opDiv").innerHTML = '<div class="mt_bottom">\n' +
            '                                    <span>X2地址:</span>\n' +
            '                                    <input type="text" name="x2ip" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X2端口:</span>\n' +
            '                                    <input type="text" name="x2port" value="">\n' +
            '                                </div>\n';
        if(unitType=='24'){
            document.getElementById("opDiv").innerHTML = '<div class="mt_bottom">\n' +
                '                                    <span>X2地址:</span>\n' +
                '                                    <input type="text" name="x2ip" value="">\n' +
                '                                </div>\n' +
                '                                <div class="mt_bottom">\n' +
                '                                    <span>X2端口:</span>\n' +
                '                                    <input type="text" name="x2port" value="">\n' +
                '                                </div>\n'+
                '                                <div class="mt_bottom">\n' +
                '                                    <span>IMPI:</span>\n' +
                '                                    <input type="text" name="impi" value="">\n' +
                '                                </div>\n'+
                '                                <div class="mt_bottom">\n' +
                '                                    <span>tel:</span>\n' +
                '                                    <input type="text" name="tel" value="">\n' +
                '                                </div>\n'+
                '                                <div>\n' +
                '                                    <span>sip:</span>\n' +
                '                                    <input type="text" name="sip" value="">\n' +
                '                                </div>\n';
        }
        document.getElementById("opType").innerHTML = '<span>操作类型:</span>\n' +
            '                                    <select name="operType" style="width: 168px;" >\n' +
            '                                        <option value=""></option>\n' +
            '                                        <option value="createConnection">连接建立请求</option>\n' +
            '                                        <option value="a2">连接释放通知</option>\n' +
            '                                        <option value="a3">BKMB设定请求</option>\n' +
            '                                        <option value="a4">BKMB撤销请求</option>\n' +
            '                                        <option value="a5">BKMB列示</option>\n' +
            '                                        <option value="a6">用户信息查询</option>\n' +
            '                                        <option value="a7">网元时间查询</option>\n' +
            '                                        <option value="a8">BK目标参数修改</option>\n' +
            '                                        <option value="a9">BK目标参数查询</option>\n' +
            '                                        <option value="a12">IMPI关联标识查询</option>\n' +
            '                                        <option value="a13">IMPU关联标识查询</option>\n' +
            '                                        <option value="a15">批量设定</option>\n' +
            '                                        <option value="a14">批量撤销</option>\n' +
            '                                    </select>';
        document.getElementById("numberFormatDiv").innerHTML = '<span>号码格式:</span>\n' +
            '                                    <select name="numberFormat" style="width: 168px;">\n' +
            '                                        <option value="msisdn">MSISDN</option>\n' +
            '                                        <option value="imei">IMEI</option>\n' +
            '                                        <option value="imsi">IMSI</option>\n' +
            '                                        <option value="SIP">SIP</option>\n' +
            '                                        <option value="TEL">TEL</option>\n' +
            '                                        <option value="all">ALL</option>\n' +
            '                                    </select>';
    }
    else  if(netType=='4G') {

        document.getElementById("opDiv").innerHTML = '';
        document.getElementById("opType").innerHTML = '<span>操作类型:</span>\n' +
            '                                    <select name="operType" style="width: 168px;" >\n' +
            '                                        <option value=""></option>\n' +
            '                                        <option value="createConnection">连接建立请求</option>\n' +
            '                                        <option value="a2">连接释放通知</option>\n' +
            '                                        <option value="a3">BKMB设定请求</option>\n' +
            '                                        <option value="a4">BKMB撤销请求</option>\n' +
            '                                        <option value="a5">BKMB列示</option>\n' +
            '                                        <option value="a6">用户信息查询</option>\n' +
            '                                        <option value="a7">网元时间查询</option>\n' +
            '                                        <option value="a15">批量设定</option>\n' +
            '                                       <option value="a14">批量撤销</option>\n' +
            '                                    </select>';
        document.getElementById("numberFormatDiv").innerHTML = '<span>号码格式:</span>\n' +
            '                                    <select name="numberFormat" style="width: 168px;">\n' +
            '                                        <option value="msisdn">MSISDN</option>\n' +
            '                                        <option value="imei">IMEI</option>\n' +
            '                                        <option value="imsi">IMSI</option>\n' +
            '                                        <option value="all">ALL</option>\n' +
            '                                    </select>';
    }

}

// $(document).ready(function() {
//     alert(sel);
// });

function getUnitType(sel){
    var rtn = '';

    $.ajax({
        type:"get",
        url:'/admin/unit/getUnitType',
        async:false,
        data:{"unitId":sel.options[sel.selectedIndex].value},
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            rtn=data;
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
            alert("获取字段出错。");
            rtn = "Error!"
        }
    });
    return rtn;
}

//页面加载时，根据当前网元填充表单下拉选项。
function fillOption(unitId) {
    var unitType = 0;
    $.ajax({
        type:"get",
        url:'/admin/unit/getUnitType',
        async:false,
        data:{"unitId":unitId},
        beforeSend:function(){
            //请求前的处理
        },
        success:function(data){
            //请求成功时处理
            unitType=data;
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
            alert("获取字段出错。");
            unitType = 0;
        }
    });
  //  alert("unitType="+unitType);
   // var unitType = Number(getUnitType(sel)) ;
    var netType;

    switch (Number(unitType)) {
        case 1:
        case 2:
        case 3:
        case 5:
        case 6:
            netType = '4G';
            break;
        case 4:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
            netType = 'IMS';
            break;
        case 7:
        case 29:
        case 30:
        case 31:
        case 32:
            netType = 'WWW';
            break;
        default:
            netType = '4G';
    }
   // alert("netType"+netType);
    if(netType=='WWW'){

        document.getElementById("opDiv").innerHTML = '<div class="mt_bottom">\n' +
            '                                    <span>X2地址:</span>\n' +
            '                                    <input type="text" name="x2ip" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X2端口:</span>\n' +
            '                                    <input type="text" name="x2port" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X3地址:</span>\n' +
            '                                    <input type="text" name="x3ip" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X3端口:</span>\n' +
            '                                    <input type="text" name="x3port" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>监听方式类型:</span>\n' +
            '                                    <select name="monType" style="width:150px;">\n' +
            '\t\t                                <option value="0">非VPDN域</option>\n' +
            '\t\t                                <option value="1">VPDN域</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>访问地址类型:</span>\n' +
            '                                    <select name="addType" style="width:150px;">\n' +
            '                                    \t\t<option value="0">unused</option>\n' +
            '                                    \t\t<option value="1">IP</option>\n' +
            '                                    \t\t<option value="2">IP_PORT</option>\n' +
            '                                    \t\t<option value="3">IP_ADDRESS_FIELD</option>\n' +
            '                                    \t\t<option value="4">Domain_name</option>\n' +
            '                                    \t\t<option value="5">DST_PORT</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>访问地址标识:</span>\n' +
            '                                    <input type="text" name="add" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>传输层协议:&nbsp;&nbsp;\t</span>\n' +
            '                                    <select name="tp" style="width:150px;">\n' +
            '                                        <option value="0">all</option>\n' +
            '                                        <option value="1">tcp</option>\n' +
            '                                        <option value="2">udp</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>应用业务类型:</span>\n' +
            '                                    <select name="appBizType" style="width:150px;">\n' +
            '                                        <option value="0">all</option>\n' +
            '                                        <option value="1">VOIP</option>\n' +
            '                                        <option value="2">WEB</option>\n' +
            '                                        <option value="3">邮件</option>\n' +
            '                                        <option value="4">即时消息</option>\n' +
            '                                        <option value="5">多媒体业务</option>\n' +
            '                                        <option value="255">其他业务</option>\n' +
            '                                    </select>\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>LNS地址:</span>\n' +
            '                                    <input type="text" name="lnsAdd" value="">\n' +
            '                                </div>';
        document.getElementById("opType").innerHTML = '<span>操作类型:</span>\n' +
            '                                    <select name="operType" style="width: 168px;" >\n' +
            '                                        <option value=""></option>\n' +
            '                                        <option value="createConnection">连接建立请求</option>\n' +
            '                                        <option value="a2">连接释放通知</option>\n' +
            '                                        <option value="a3">BKMB设定请求</option>\n' +
            '                                        <option value="a4">BKMB撤销请求</option>\n' +
            '                                        <option value="a5">BKMB列示</option>\n' +
            '                                        <option value="a6">用户信息查询</option>\n' +
            '                                        <option value="a7">网元时间查询</option>\n' +
            '                                        <option value="a8">BK目标参数修改</option>\n' +
            '                                        <option value="a9">BK目标参数查询</option>\n' +
            '                                        <option value="a10">BK目标位置定位查询</option>\n' +
            '                                        <option value="a11">目标内/外网IP地址关联查询</option>\n' +
            '                                    </select>';
        document.getElementById("numberFormatDiv").innerHTML = '<span>号码格式:</span>\n' +
            '                                    <select name="numberFormat" style="width: 168px;">\n' +
            '                                        <option value="IP">IP</option>\n' +
            '                                        <option value="IP_time">IP_time</option>\n' +
            '                                        <option value="IP-PORT">IP-PORT</option>\n' +
            '                                        <option value="Ip_address_field">Ip_address_field</option>\n' +
            '                                        <option value="NAI">NAI</option>\n' +
            '                                        <option value="Phone_number">Phone_number</option>\n' +
            '                                        <option value="all">ALL</option>\n' +
            '                                    </select>';
    }
    else if(netType=='IMS'){

        document.getElementById("opDiv").innerHTML = '<div class="mt_bottom">\n' +
            '                                    <span>X2地址:</span>\n' +
            '                                    <input type="text" name="x2ip" value="">\n' +
            '                                </div>\n' +
            '                                <div class="mt_bottom">\n' +
            '                                    <span>X2端口:</span>\n' +
            '                                    <input type="text" name="x2port" value="">\n' +
            '                                </div>\n'+
            '                                <div class="mt_bottom">\n' +
            '                                    <span>IMPI:</span>\n' +
            '                                    <input type="text" name="impi" value="">\n' +
            '                                </div>\n'+
            '                                <div class="mt_bottom">\n' +
            '                                    <span>tel:</span>\n' +
            '                                    <input type="tel" name="tel" value="">\n' +
            '                                </div>\n'+
            '                                <div>\n' +
            '                                    <span>sip:</span>\n' +
            '                                    <input type="email" name="sip" value="">\n' +
            '                                </div>\n';
        document.getElementById("opType").innerHTML = '<span>操作类型:</span>\n' +
            '                                    <select name="operType" style="width: 168px;" >\n' +
            '                                        <option value=""></option>\n' +
            '                                        <option value="createConnection">连接建立请求</option>\n' +
            '                                        <option value="a2">连接释放通知</option>\n' +
            '                                        <option value="a3">BKMB设定请求</option>\n' +
            '                                        <option value="a4">BKMB撤销请求</option>\n' +
            '                                        <option value="a5">BKMB列示</option>\n' +
            '                                        <option value="a6">用户信息查询</option>\n' +
            '                                        <option value="a7">网元时间查询</option>\n' +
            '                                        <option value="a8">BK目标参数修改</option>\n' +
            '                                        <option value="a9">BK目标参数查询</option>\n' +
            '                                        <option value="a12">IMPI关联标识查询</option>\n' +
            '                                        <option value="a13">IMPU关联标识查询</option>\n' +
            '                                        <option value="a15">批量设定</option>\n' +
            '                                    </select>';
        document.getElementById("numberFormatDiv").innerHTML = '<span>号码格式:</span>\n' +
            '                                    <select name="numberFormat" style="width: 168px;">\n' +
            '                                        <option value="msisdn">MSISDN</option>\n' +
            '                                        <option value="imei">IMEI</option>\n' +
            '                                        <option value="imsi">IMSI</option>\n' +
            '                                        <option value="SIP">SIP</option>\n' +
            '                                        <option value="TEL">TEL</option>\n' +
            '                                        <option value="all">ALL</option>\n' +
            '                                    </select>';
    }
    else  if(netType=='4G') {

        document.getElementById("opDiv").innerHTML = '';
        document.getElementById("opType").innerHTML = '<span>操作类型:</span>\n' +
            '                                    <select name="operType" style="width: 168px;" >\n' +
            '                                        <option value=""></option>\n' +
            '                                        <option value="createConnection">连接建立请求</option>\n' +
            '                                        <option value="a2">连接释放通知</option>\n' +
            '                                        <option value="a3">BKMB设定请求</option>\n' +
            '                                        <option value="a4">BKMB撤销请求</option>\n' +
            '                                        <option value="a5">BKMB列示</option>\n' +
            '                                        <option value="a6">用户信息查询</option>\n' +
            '                                        <option value="a7">网元时间查询</option>\n' +
            '                                        <option value="a15">批量设定</option>\n' +
            '                                    </select>';
        document.getElementById("numberFormatDiv").innerHTML = '<span>号码格式:</span>\n' +
            '                                    <select name="numberFormat" style="width: 168px;">\n' +
            '                                        <option value="msisdn">MSISDN</option>\n' +
            '                                        <option value="imei">IMEI</option>\n' +
            '                                        <option value="imsi">IMSI</option>\n' +
            '                                        <option value="all">ALL</option>\n' +
            '                                    </select>';
    }


}

