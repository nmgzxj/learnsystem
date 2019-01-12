/**
 * Created by JS2 on 2017/6/26.
 */
//var HH = 0;//时
//var mm = 0;//分
//var ss = 0;//秒
//var timeState = true;//时间状态 默认为true 开启时间
var questions = QuestionJosn;
var itemList = ["A", "B", "C", "D", "E", "F"];
var itemLIsts = ["一", "二", "三", "四", "五", "六"];
var activeQuestion = 0; //当前操作的考题编号
var questioned = 0; //
var checkQues = []; //已做答的题的集合

/*选中考题*/
var Question;
function clickTrim(source) {
    console.log(source);
    var id = source.id;
    $("#" + id).find("input").prop("checked", "checked");
    $("#" + id).addClass("clickTrim");
    $("#ques" + activeQuestion).removeClass("question_id").addClass("clickQue");
    var ques = 0;
    for (var i = 0; i < checkQues.length; i++) {
        if (checkQues[i].id == activeQuestion && checkQues[i].item != id) {
            ques = checkQues[i].id;
            checkQues[i].item = id;//获取当前考题的选项ID
            checkQues[i].answer = $("#" + id).find("input[name=item]:checked").val();//获取当前考题的选项值
        }
    }
    if (checkQues.length == 0 || Question != activeQuestion && activeQuestion != ques) {
        var check = {};
        check.id = activeQuestion;//获取当前考题的编号
        check.item = id;//获取当前考题的选项ID
        check.answer = $("#" + id).find("input[name=item]:checked").val();//获取当前考题的选项值
        checkQues.push(check);
    }
    $(".question_info").each(function () {
        var otherId = $(this).attr("id");
        if (otherId != id) {
            $("#" + otherId).find("input").prop("checked", false);
            $("#" + otherId).removeClass("clickTrim");
        }
    })
    Question = activeQuestion;
}
/*保存考题状态 已做答的状态*/
function saveQuestionState(clickId) {
    console.log(clickId)
    $(".list-unstyled .questionId").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        var idx = clickId;
        $(".answer_arr .list-unstyled").eq(idx).addClass("acur").siblings().removeClass("acur")
    })
    //showQuestion(clickId)
}

$(function () {
    $(".middle-top-left").width($(".middle-top").width() - $(".middle-top-right").width())
    $(".progress-left").width($(".middle-top-left").width() - 200);
    /*答题卡的切换*/
    $("#openCard").click(function () {
        $("#closeCard").show();
        $("#answerCard").slideDown();
        $(this).hide();
    })
    $("#closeCard").click(function () {
        $("#openCard").show();
        $("#answerCard").slideUp();
        $(this).hide();
    })

//    //提交试卷
//    $("#submitQuestions").click(function () {
//        /*alert(JSON.stringify(checkQues));*/
//        alert("已做答:" + ($(".clickQue").length) + "道题,还有" + (questions.length - ($(".clickQue").length)) + "道题未完成");
//    })

    $(".btn5").click(function () {
        alert("已经是最后一题")
    });

    //切换显示X2/X3列表
    function isOne() {
        if ($("#show-table-one").is(":checked") == true && $("#show-table-two").is(":checked") == false) {
            $("#table_one").css({"height": ""})
        }
        if ($("#show-table-one").is(":checked") == false && $("#show-table-two").is(":checked") == true) {
            $("#table_two").css({"height": ""})
        }
    }

    function isTwo() {
        if ($("#show-table-one").is(":checked") == true && $("#show-table-two").is(":checked") == true) {

            $("#table_one").css({"height": "310px", "overflow-y": "auto"})
            $("#table_two").css({"height": "310px", "overflow-y": "auto"})
        }
    }

    $('#show-table-one').click(function () {

        isOne();
        isTwo();
        if ($("#table_one").css("display") == "none") {

            $("#table_one").show();

        } else {

            $("#table_one").hide();

        }
        ;

    });

    //点击显示第二个表
    $('#show-table-two').click(function () {

        isOne();
        isTwo();
        if ($("#table_two").css("display") == "none") {

            $("#table_two").show();

        } else {

            $("#table_two").hide();

        }
    });


    //点击折叠
    //获取到需要点击的对象
    $('.tpl-sidebar-user-panel').click(function () {
        //如果没有这个nav-mini,就要变小,同时margin-left变成59px并加上nav-mini
        if(!$(".tpl-sidebar-user-panel").hasClass('nav-mini')){
            $(".left-sidebar").addClass("mini-left-sidebar")
            $(".tpl-content-wrapper").css("margin-left","59px")
            $(".tpl-sidebar-user-panel").addClass('nav-mini')

        //    else如果有nav-mini,就要变大,并移出nav-mini
        }else{
            $(".left-sidebar").removeClass("mini-left-sidebar")
            $(".tpl-content-wrapper").css("margin-left","240px")
            $(".left-sidebar").addClass("left-sidebar")
            $(".tpl-sidebar-user-panel").removeClass('nav-mini')
        }
    })

})