<%--
  Created by IntelliJ IDEA.
  User: pjw
  Date: 2020/10/17
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@ include file="/WEB-INF/common/css.jsp" %>
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }

        a:hover {
            cursor: pointer
        }
    </style>
</head>

<body>

<%@ include file="/WEB-INF/common/nav.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/common/side.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyWord" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="btnSearch" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="deleteBath" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;" id="btnSave"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="tbody">
                            <!--角色信息,通过异步的形式获取-->

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <!---分页信息通过异步获取-->
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">新增</h4>
            </div>
            <div class="modal-body">

                <form role="form" method="post">

                    <div class="form-group">
                        <label for="exampleInputPassword1">角色名称</label>
                        <input name="name" type="text" class="form-control" id="exampleInputPassword1"
                               placeholder="请输入角色名称">
                    </div>

                </form>


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="btnAdd">保存</button>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/common/js.jsp"></jsp:include>
<script src="${appPath}/static/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        //异步获取方法页面加载完后   默认加载第一页
        loadData(1);  // 1  表示当前页码
    });

    var keyWord = "";
    //异步获取角色以及分页信息的方法  load(加载) data(数据)
    function loadData(pageNum) {  //使用pageNum接收请求页码
        //异步请求的集中方法
        //1.$.ajax({url})   2.$.get  3. $.post  4.$.getJSON
        //   加载地址    加载的参数   回调参数

        $.getJSON("${appPath}/role/loadData",{"pageNum":pageNum,"pageSize":2,"keyWord":keyWord}, function(res){
            //res是服务器异步响应的数据(角色信息,分页信息)

            showRole(res.list);//调用展示角色方法
            showPage(res);//调用展示分页方法

        });
    }
    //展示角色信息
    function showRole(roleList){

        var content ="";
        for (var i =0;i<roleList.length;i++){

            content+='<tr>';
            content+='	<td>'+(i+1)+'</td>';
            content+='	<td><input type="checkbox" class="roleCheck"  id="'+roleList[i].id+'" ></td>';
            content+='	<td>'+(roleList[i].name)+'</td>';
            content+='	<td>';
            content+='		<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            content+='		<button type="button" class="btn btn-primary btn-xs" onclick="getRole('+roleList[i].id+')" ><i class=" glyphicon glyphicon-pencil"></i></button>';
            content+='		<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
            content+='	</td>';
            content+='</tr>';
        }
        $("#tbody").html(content)

    }
    //展示分页信息
    function showPage(pageInfo) {

        var content = "";
        if (pageInfo.isFirstPage){
            content+='<li class="disabled"><a href="#">上一页</a></li>' ;
        } else {
            content+='<li ><a onclick="loadData('+(pageInfo.pageNum-1)+')" >上一页</a></li>' ;
        }

        for (var i= 0;i<pageInfo.navigatepageNums.length;i++) {
            if (pageInfo.pageNum == pageInfo.navigatepageNums[i]) {
                content+='<li class="active"><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+pageInfo.navigatepageNums[i]+'</span></a></li>';
            } else {
                content+='<li><a onclick="loadData('+(pageInfo.navigatepageNums[i])+')">'+pageInfo.navigatepageNums[i]+'</span></a></li>';
            }
        }

        if (pageInfo.isLastPage) {
            content+='<li class="disabled"><a href="#">下一页</a></li>';
        } else {
            content+='<li><a onclick="loadData('+(pageInfo.pageNum+1)+')">下一页</a></li>';
        }

        $(".pagination").html(content)
    }

    //查询绑定单击事件
    $("#btnSearch").click(function () {
        //获取输入框中的值
        keyWord = $("input[name='keyWord']").val();
        loadData(1);
    });


    //打开添加的,模态框
    $("#btnSave").click(function () {
        $('#myModal').modal({
            show:true,   //立即展示
            backdrop:'static' , //点击页面空白处是否取消显示

            keyboard:false  //键盘上的 esc 键被按下时关闭模态框
        });
    });
    //给模态框的添加按钮绑定单击事件
    $("#btnAdd").click(function () {
        //获取参数
        var roleName = $("input[name='name']").val();
        //异步请求  请求地址  参数   回显函数
        $.get("${appPath}/role/roleSave?name="+roleName,null,function (res) {
            if (res=="yes"){
                layer.msg("添加成功",{time:1000,icon:6}, function () {
                    $('#myModal').modal("hide");//关闭模态框
                    loadData(100000000);//重新查询
                });
            }else {
                layer.msg("添加失败");
            }
        })
    });

    //
    // //给复选框绑定单击
     $("#checkAll").click(function () {
            var roleChecks = $("#tbody .roleCheck");//单选框
        //遍历单选框集合
         $.each(roleChecks,function(i,check) {
            check.checked = $("#checkAll")[0].checked ;
        });
    });

     //删除绑定单价事件
    $("#deleteBath").click(function () {
        //获取每个单选框内的属性
        var checks = $("#tbody .roleCheck:checked");
        var ids = new Array();
        for (var  i =0 ; i<checks.length;i++) {
            ids.push($(checks[i]).attr("id"));
        }

        layer.confirm("你确定要删除吗?",{btn:["确定","取消"]},function () {
            $.get("${appPath}/role/deleteRoles?ids="+ids,null,function (res) {
                if (res =="yes") {
                    layer.alert("删除成功");
                    loadData(1);
                    $("#checkAll").attr("checked",false);
                }
            });
        }

        ,function () {

        });

    });
    
    function getRole() {
        
    }




</script>
</body>
</html>

