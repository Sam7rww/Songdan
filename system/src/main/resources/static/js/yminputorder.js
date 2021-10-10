$(function(){
    $('#excel-import-excel').change(function(e) {//浙江野马导出excel，导入excel
        var files = e.target.files;
        var excel = layui.excel;
        try {
            // 可以在读取过程中梳理数据
            excel.importExcel(files, {
                fields: {
                    'waterid': 'J'
                    ,'ordernum': 'L'
                    ,'productid': 'C'
                    ,'productname': 'D'
                    ,'productname2': 'K'
                    ,'unit': 'E'
                    ,'num': 'G'
                    ,'outputdate': 'F'
                    ,'demand': 'M'
                    ,'price':'N'
                    ,'neijing':'Q'
                    ,'calculate':'R'
                    ,'gecengban':'S'
                }
            }, function(data) {
                // console.log(data);
                var arr = new Array();
                if(data[0].Sheet1){
                    for(i = 1; i < data[0].Sheet1.length; i++){
                        var outd = data[0].Sheet1[i].outputdate;
                        if(!isNaN(outd)){
                            // console.log("outputdate是日期格式！")
                            outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                        }
                        var tt = {
                            waterid : data[0].Sheet1[i].waterid,
                            ordernum : data[0].Sheet1[i].ordernum,
                            productid: data[0].Sheet1[i].productid,
                            productname: data[0].Sheet1[i].productname,
                            productname2: data[0].Sheet1[i].productname2,
                            unit: data[0].Sheet1[i].unit,
                            num: data[0].Sheet1[i].num,
                            outputdate: outd,
                            demand: data[0].Sheet1[i].demand,
                            price: data[0].Sheet1[i].price,
                            neijing: data[0].Sheet1[i].neijing,
                            calculate: data[0].Sheet1[i].calculate,
                            gecengban: data[0].Sheet1[i].gecengban,
                        };
                        arr.push(tt);
                    }
                }else if(data[0].sheet1){
                    for(i = 1; i < data[0].sheet1.length; i++){
                        var outd = data[0].sheet1[i].outputdate;
                        if(!isNaN(outd)){
                            // console.log("outputdate是日期格式！")
                            outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                        }
                        var tt = {
                            waterid : data[0].sheet1[i].waterid,
                            ordernum : data[0].sheet1[i].ordernum,
                            productid: data[0].sheet1[i].productid,
                            productname: data[0].sheet1[i].productname,
                            productname2: data[0].sheet1[i].productname2,
                            unit: data[0].sheet1[i].unit,
                            num: data[0].sheet1[i].num,
                            outputdate: outd,
                            demand: data[0].sheet1[i].demand,
                            price: data[0].sheet1[i].price,
                            neijing: data[0].sheet1[i].neijing,
                            calculate: data[0].sheet1[i].calculate,
                            gecengban: data[0].sheet1[i].gecengban,
                        };
                        arr.push(tt);
                    }
                }else{
                    alert("输入Excel文件有问题，请检查Sheet名.");
                }

                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: 'importYMOrders',
                    data: JSON.stringify(arr),
                    contentType : "application/json",
                    success: function (res) {
                        if(res.success == "true"){
                            layer.msg(res.message);
                        }else{
                            //表格导入失败后，重载文件上传
                            layer.alert(res.error+"请重新上传",{icon : 2});
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown) {
                        alert('无法上传，请联系管理员!!!');
                    }
                })

            });
        } catch (e) {
            layer.alert(e.message);
        }
    });

    $('#new-import-excel').change(function(e) { //野马新网站导入excel订单
        var files = e.target.files;
        var excel = layui.excel;
        try {
            // 可以在读取过程中梳理数据
            excel.importExcel(files, {
                fields: {
                    'waterid': 'F'
                    ,'ordernum': 'K'
                    ,'productid': 'H'
                    ,'productname': 'I'
                    ,'productname2': 'AA'    //null
                    ,'unit': 'N'
                    ,'num': 'J'
                    ,'outputdate': 'E'
                    ,'demand': 'AB'
                    ,'price':'AC'
                    ,'neijing':'O'
                    ,'calculate':'P'
                    ,'gecengban':'Q'
                }
            }, function(data) {
                // console.log(data);
                var arr = new Array();
                if(data[0].Sheet1){
                    for(i = 1; i < data[0].Sheet1.length; i++){
                        var outd = data[0].Sheet1[i].outputdate;
                        if(!isNaN(outd)){
                            // console.log("outputdate是日期格式！")
                            outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                        }
                        var tt = {
                            waterid : data[0].Sheet1[i].waterid,
                            ordernum : data[0].Sheet1[i].ordernum,
                            productid: data[0].Sheet1[i].productid,
                            productname: data[0].Sheet1[i].productname,
                            productname2: "",
                            unit: data[0].Sheet1[i].unit,
                            num: data[0].Sheet1[i].num,
                            outputdate: outd,
                            demand: "",
                            price: 0,
                            neijing: data[0].Sheet1[i].neijing,
                            calculate: data[0].Sheet1[i].calculate,
                            gecengban: data[0].Sheet1[i].gecengban,
                        };
                        arr.push(tt);
                    }
                }else if(data[0].sheet1){
                    for(i = 1; i < data[0].sheet1.length; i++){
                        var outd = data[0].sheet1[i].outputdate;
                        if(!isNaN(outd)){
                            // console.log("outputdate是日期格式！")
                            outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                        }
                        var tt = {
                            waterid : data[0].sheet1[i].waterid,
                            ordernum : data[0].sheet1[i].ordernum,
                            productid: data[0].sheet1[i].productid,
                            productname: data[0].sheet1[i].productname,
                            productname2: data[0].sheet1[i].productname2,
                            unit: data[0].sheet1[i].unit,
                            num: data[0].sheet1[i].num,
                            outputdate: outd,
                            demand: data[0].sheet1[i].demand,
                            price: data[0].sheet1[i].price,
                            neijing: data[0].sheet1[i].neijing,
                            calculate: data[0].sheet1[i].calculate,
                            gecengban: data[0].sheet1[i].gecengban,
                        };
                        arr.push(tt);
                    }
                }else{
                    alert("输入Excel文件有问题，请检查Sheet名.");
                }

                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: 'importYMOrders',
                    data: JSON.stringify(arr),
                    contentType : "application/json",
                    success: function (res) {
                        if(res.success == "true"){
                            layer.msg(res.message);
                        }else{
                            //表格导入失败后，重载文件上传
                            layer.alert(res.error+"请重新上传",{icon : 2});
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown) {
                        alert('无法上传，请联系管理员!!!');
                    }
                })

            });
        } catch (e) {
            layer.alert(e.message);
        }
    });



    // 监听上传文件的事件
    $('#LAY-excel-import-excel').change(function(e) {//浙江野马
        var files = e.target.files;
        var excel = layui.excel;
        try {
            // 可以在读取过程中梳理数据
            excel.importExcel(files, {
                fields: {
                    'waterid': 'B'
                    ,'ordernum': 'J'
                    ,'productid': 'D'
                    ,'productname': 'E'
                    ,'productname2': 'O'
                    ,'unit': 'H'
                    ,'num': 'G'
                    ,'outputdate': 'I'
                    ,'demand': 'L'
                    ,'price':'M'
                    ,'neijing':'Q'
                    ,'calculate':'R'
                    ,'gecengban':'S'
                }
            }, function(data) {
                // console.log(data);
                var arr = new Array();
                for(i = 1; i < data[0].Sheet1.length; i++){
                    var outd = data[0].Sheet1[i].outputdate;
                    if(!isNaN(outd)){
                        // console.log("outputdate是日期格式！")
                        outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                    }
                    var tt = {
                        waterid : data[0].Sheet1[i].waterid,
                        ordernum : data[0].Sheet1[i].ordernum,
                        productid: data[0].Sheet1[i].productid,
                        productname: data[0].Sheet1[i].productname,
                        productname2: data[0].Sheet1[i].productname2,
                        unit: data[0].Sheet1[i].unit,
                        num: data[0].Sheet1[i].num,
                        outputdate: outd,
                        demand: data[0].Sheet1[i].demand,
                        price: data[0].Sheet1[i].price,
                        neijing: data[0].Sheet1[i].neijing,
                        calculate: data[0].Sheet1[i].calculate,
                        gecengban: data[0].Sheet1[i].gecengban,
                    };
                    arr.push(tt);
                }

                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: 'importYMOrders',
                    data: JSON.stringify(arr),
                    contentType : "application/json",
                    success: function (res) {
                        if(res.success == "true"){
                            layer.msg(res.message);
                        }else{
                            //表格导入失败后，重载文件上传
                            layer.alert(res.error+"请重新上传",{icon : 2});
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown) {
                        alert('无法上传，请联系管理员!!!');
                    }
                })

            });
        } catch (e) {
            layer.alert(e.message);
        }
    });

    $('#LAY-excel-import-excel3').change(function(e) { //宁波野马
        var files = e.target.files;
        var excel = layui.excel;
        try {
            // 可以在读取过程中梳理数据
            excel.importExcel(files, {
                fields: {
                    'waterid': 'B'
                    ,'ordernum': 'J'
                    ,'productid': 'D'
                    ,'productname': 'E'
                    ,'productname2': 'L'
                    ,'unit': 'H'
                    ,'num': 'G'
                    ,'outputdate': 'O'
                    ,'demand': 'I'
                    ,'price':'M'
                    ,'neijing':'P'
                    ,'calculate':'Q'
                    ,'gecengban':'R'
                }
            }, function(data) {
                // console.log(data);
                var arr = new Array();
                for(i = 1; i < data[0].Sheet1.length; i++){
                    var outd = data[0].Sheet1[i].outputdate;
                    if(!isNaN(outd)){
                        // console.log("outputdate是日期格式！")
                        outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                    }
                    var tt = {
                        waterid : data[0].Sheet1[i].waterid,
                        ordernum : data[0].Sheet1[i].ordernum,
                        productid: data[0].Sheet1[i].productid,
                        productname: data[0].Sheet1[i].productname,
                        productname2: data[0].Sheet1[i].productname2,
                        unit: data[0].Sheet1[i].unit,
                        num: data[0].Sheet1[i].num,
                        outputdate: outd,
                        demand: data[0].Sheet1[i].demand,
                        price: data[0].Sheet1[i].price,
                        neijing: data[0].Sheet1[i].neijing,
                        calculate: data[0].Sheet1[i].calculate,
                        gecengban: data[0].Sheet1[i].gecengban,
                    };
                    arr.push(tt);
                }

                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: 'importYMOrders',
                    data: JSON.stringify(arr),
                    contentType : "application/json",
                    success: function (res) {
                        if(res.success === "true"){
                            layer.msg(res.message);
                        }else{
                            //表格导入失败后，重载文件上传
                            layer.alert(res.error+"请重新上传",{icon : 2});
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown) {
                        alert('无法上传，请联系管理员!!!');
                    }
                })

            });
        } catch (e) {
            layer.alert(e.message);
        }
    });

    $('#LAY-excel-import-excel2').change(function(e) {//邮箱订单导入
        var files = e.target.files;
        var excel = layui.excel;
        try {
            // 可以在读取过程中梳理数据
            excel.importExcel(files, {
                fields: {
                    'waterid': 'B'
                    ,'ordernum': 'C'
                    ,'productid': 'D'
                    ,'productname': 'E'
                    ,'productname2': 'F'
                    ,'unit': 'G'
                    ,'num': 'H'
                    ,'outputdate': 'I'
                    ,'demand': 'J'
                    ,'price':'K'
                    ,'neijing':'L'
                    ,'calculate':'M'
                    ,'gecengban':'N'
                }
            }, function(data) {
                // console.log(data);
                var arr = new Array();
                if(data[0].Sheet1){
                    for(i = 1; i < data[0].Sheet1.length; i++){
                        var outd = data[0].Sheet1[i].outputdate;
                        if(!isNaN(outd)){
                            // console.log("outputdate是日期格式！")
                            outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                        }
                        var tt = {
                            waterid : data[0].Sheet1[i].waterid,
                            ordernum : data[0].Sheet1[i].ordernum,
                            productid: data[0].Sheet1[i].productid,
                            productname: data[0].Sheet1[i].productname,
                            productname2: data[0].Sheet1[i].productname2,
                            unit: data[0].Sheet1[i].unit,
                            num: data[0].Sheet1[i].num,
                            outputdate: outd,
                            demand: data[0].Sheet1[i].demand,
                            price: data[0].Sheet1[i].price,
                            neijing: data[0].Sheet1[i].neijing,
                            calculate: data[0].Sheet1[i].calculate,
                            gecengban: data[0].Sheet1[i].gecengban,
                        };
                        arr.push(tt);
                    }
                }else if(data[0].sheet1){
                    for(i = 1; i < data[0].sheet1.length; i++){
                        var outd = data[0].sheet1[i].outputdate;
                        if(!isNaN(outd)){
                            // console.log("outputdate是日期格式！")
                            outd = excel.dateCodeFormat(outd,'YYYY/MM/DD')
                        }
                        var tt = {
                            waterid : data[0].sheet1[i].waterid,
                            ordernum : data[0].sheet1[i].ordernum,
                            productid: data[0].sheet1[i].productid,
                            productname: data[0].sheet1[i].productname,
                            productname2: data[0].sheet1[i].productname2,
                            unit: data[0].sheet1[i].unit,
                            num: data[0].sheet1[i].num,
                            outputdate: outd,
                            demand: data[0].sheet1[i].demand,
                            price: data[0].sheet1[i].price,
                            neijing: data[0].sheet1[i].neijing,
                            calculate: data[0].sheet1[i].calculate,
                            gecengban: data[0].sheet1[i].gecengban,
                        };
                        arr.push(tt);
                    }
                }else{
                    alert("输入Excel文件有问题，请检查Sheet名.");
                }

                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: 'importYMOrders',
                    data: JSON.stringify(arr),
                    contentType : "application/json",
                    success: function (res) {
                        if(res.success === "true"){
                            layer.msg(res.message);
                        }else{
                            //表格导入失败后，重载文件上传
                            layer.alert(res.error+"请重新上传",{icon : 2});
                        }
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown) {
                        alert('无法上传，请联系管理员!!!');
                    }
                })

            });
        } catch (e) {
            layer.alert(e.message);
        }
    });

});
