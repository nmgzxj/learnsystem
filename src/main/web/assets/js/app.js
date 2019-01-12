//$(function () {
//    // 读取body data-type 判断是哪个页面然后执行相应页面方法，方法在下面。
//    var dataType = $('body').attr('data-type');
//    console.log(dataType);
//    for (key in pageData) {
//        if (key == dataType) {
//            pageData[key]();
//        }
//    }
//    //     // 判断用户是否已有自己选择的模板风格
//    //    if(storageLoad('SelcetColor')){
//    //      $('body').attr('class',storageLoad('SelcetColor').Color)
//    //    }else{
//    //        storageSave(saveSelectColor);
//    //        $('body').attr('class','theme-black')
//    //    }
//
//    autoLeftNav();
//    $(window).resize(function () {
//        autoLeftNav();
//        console.log($(window).width())
//    });
//
//    //    if(storageLoad('SelcetColor')){
//
//    //     }else{
//    //       storageSave(saveSelectColor);
//    //     }
//})





// 风格切换

//$('.tpl-skiner-toggle').on('click', function () {
//    $('.tpl-skiner').toggleClass('active');
//})
//
//$('.tpl-skiner-content-bar').find('span').on('click', function () {
//    $('body').attr('class', $(this).attr('data-color'))
//    saveSelectColor.Color = $(this).attr('data-color');
//    // 保存选择项
//    storageSave(saveSelectColor);
//
//})


// 侧边菜单开关


//function autoLeftNav() {
//
//
//    $('.tpl-header-switch-button').on('click', function () {
//        if ($('.left-sidebar').is('.active')) {
//            if ($(window).width() > 1024) {
//                $('.tpl-content-wrapper').removeClass('active');
//            }
//            $('.left-sidebar').removeClass('active');
//        } else {
//
//            $('.left-sidebar').addClass('active');
//            if ($(window).width() > 1024) {
//                $('.tpl-content-wrapper').addClass('active');
//            }
//        }
//    })
//
//    if ($(window).width() < 1024) {
//        $('.left-sidebar').addClass('active');
//    } else {
//        $('.left-sidebar').removeClass('active');
//    }
//}


// 侧边菜单
//$('.sidebar-nav-sub-title').on('click', function () {
//    $(this).siblings('.sidebar-nav-sub').slideToggle(80)
//        .end()
//        .find('.sidebar-nav-sub-ico').toggleClass('sidebar-nav-sub-ico-rotate');
//})

//tab切换
//$(function(){
//    //1. 给li注册mouseenter事件
//    $(".sidebar-nav .sidebar-nav-link").click(function () {
//        //2. 让当前li添加active类，并且让其他的li移除active类
//        $(this).addClass("cur").siblings().removeClass("cur");
//        console.log($(this));
//        //3. 让对应下标的div添加selected，并且让其他div移除selected类
//        var idx = $(this).index();
//        console.log(idx)
//        $(".tpl-content-wrapper .row-content").eq(idx).addClass("selected").siblings().removeClass("selected");
//    });
//})

//
var w, h, className;
function getSrceenWH() {
    w = $(window).width();
    h = $(window).height();
    $('#dialogBg').width(w).height(h);
}

window.onresize = function () {
    getSrceenWH();
}
$(window).resize();

$(function () {
    getSrceenWH();

    //显示弹框
    $('.tpl-table-black-operation .editor').click(function () {
        className = $(this).attr('class');
        $('#dialogBg').fadeIn(300);
        $('#dialog').removeAttr('class').addClass('animated ' + className + '').fadeIn();
    });

    //关闭弹窗
    $('.claseDialogBtn').click(function () {
        $('#dialogBg').fadeOut(200, function () {
            $('#dialog').addClass('bounceOutUp').fadeOut();
        });
    });
});



