/**
 * checkbox工具类封装
 */
var CheckboxUtil={
    init:function(parentId){
        var that=this;
        var checkboxs=null;
        if(parentId){
            checkboxs=$('#'+parentId).find("[data-checkbox]")
        }else{
            checkboxs=$("body").find("[data-checkbox]");
        }
        if(checkboxs&&checkboxs.length>0){
            var initCheckBoxEvent=function(ck,name,hiddenInputId,handler){
                ck.find("input[type='checkbox'][name='"+name+"']").unbind("change").on("change",function(){
                    if(handler){
                        var exe=eval(handler);
                        if(exe){
                            if(handler=="processHiddenInput"){
                                processHiddenInput(name,hiddenInputId);
                            }else{
                                var input=$(this);
                                exe(input,input.is(":checked"));
                            }

                        }
                    }
                });
            }

            checkboxs.each(function(){
                var ck=$(this);
                var handler=ck.data("handler");
                var name=ck.data("name");
                var value=ck.data("value");
                var defaultValue=ck.data("default");
                var hiddenInputId=ck.data("hiddeninput");
                var url=ck.data("url");
                var label=ck.data("label");

                if(url){
                    that.insertDatas(ck,url,name,label,function(){
                        initCheckBoxEvent(ck,name,hiddenInputId,handler);
                        that.setChecked(name,value,defaultValue);
                    });
                }else{
                    initCheckBoxEvent(ck,name,hiddenInputId,handler);
                    that.setChecked(name,value,defaultValue);
                }

            });
        }
    },insertDatas:function(ck,url,name,label,callback){
        var that=this;
        ck.empty();

        var width=ck.data("width");
        var labelWidth="";
        var radioWidth="";
        if(width){
            var arr=width.split(",");
            labelWidth=arr[0];
            radioWidth=arr[1];
        }else{
            labelWidth="100px";
            radioWidth="col";
        }
        var html='';
        if(label){
            if(labelWidth.indexOf("px")!=-1){
                html= '<label class="col-auto col-form-label" style="width:'+labelWidth+'">'+label+'</label>';
            }else{
                html= '<label class="'+labelWidth+' col-form-label">'+label+'</label>';
            }
        }

        var inline="";
        var isInline=ck.data("inline");
        if(isInline){
            inline="checkbox-inline";
        }
        Ajax.get(url,function(res){
            html+='<div class="'+radioWidth+'"  style="padding-top: 1px;">';
            var list=res.data;
            var nodotname=name.replace("\\.","_");
            if(list&&list.length>0){
                for(var i in list){
                    nodotname=nodotname+"_"+i;
                    var radioHtml = '<div class="checkbox checkbox-primary '+inline+'">'+
                        '<input  id="'+nodotname+'" type="checkbox" name="'+name+'" value="'+list[i].value+'"/>'+
                        '<label for="'+nodotname+'">'+list[i].text+'</label>'+
                        '</div>';
                    html+=radioHtml;
                }
                html+="</div>";
                ck.html(html);

                if(callback){
                    callback();
                }
            }
        });

    },
    checkByArray:function(name,values){
        values=values.toString();
        if(values.indexOf(",")!=-1){
            var arr=values.split(",");
            if(arr&&arr.length>0){
                for(var i in arr){
                    var input=$("input[type='checkbox'][name='"+name+"'][value='"+arr[i]+"']");
                    input.attr("checked","checked");
                }
            }
        }else{
            var input=$("input[type='checkbox'][name='"+name+"'][value='"+values+"']");
            input.attr("checked","checked");
        }



    },
    setChecked:function(name,value,defaultValue){
        var that=this;
        if(value){
            that.checkByArray(name,value);
        }else{
            if(defaultValue||defaultValue==0||defaultValue=="0"){
                that.checkByArray(name,defaultValue);
            }
        }

    }
}
CheckboxUtil.init();