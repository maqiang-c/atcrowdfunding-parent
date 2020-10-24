<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ma
  Date: 2020/10/16
  Time: 11:08
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

    <%--动态包含引用css格式--%>
    <jsp:include page="/WEB-INF/common/css.jsp"></jsp:include>


    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/common/nav.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">


        <jsp:include page="/WEB-INF/common/side.jsp"></jsp:include>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <%--点击查询是进行提交需要有提交的地址以及提交的方式--%>
                    <form id="queryFrom" class="form-inline" role="form" style="float:left;" action="${appPath}/admin/index" method="post">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <%--查询条件回显 param在同一次回话中收据储存 --%>
                                <input name="keyWord" value="${param.keyWord}" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <%--单击事件属性--%>
                        <button onclick="$('#queryFrom').submit()" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="deleteBath"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${appPath}/admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%--传过来的数据存放在pageinfo中
                                 var Status (状态)
                                 varstarus.count  会为显示出的数据编号
                            --%>
                                <c:forEach items="${pageInfo.list}" var="admin" varStatus="state">
                                    <tr>
                                        <td>${state.count}</td>
                                        <td><input type="checkbox" class="adminCheckBox" id="${admin.id}"></td>
                                        <td>${admin.loginacct}</td>
                                        <td>${admin.username}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <button type="button"  class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
                                            <button type="button" class="btn btn-primary btn-xs"
                                                    onclick="location.href='${appPath}/admin/toEdit?pageNum=${pageInfo.pageNum}&id=${admin.id}'">
                                                <i class=" glyphicon glyphicon-pencil"></i></button>
                                            <%--<button type="button" class="btn btn-primary btn-xs" onclick="location.href='${appPath}/admin/toEdit?id=${admin.id}'" ><i class=" glyphicon glyphicon-pencil"></i></button>--%>
                                            <button type="button" class="btn btn-danger btn-xs" onclick="location.href='${appPath}/admin/del?id=${admin.id}'"><i class=" glyphicon glyphicon-remove"></i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <%--判断当前页面是否是第一页如果是第一页按钮不能用  disabled(是去能力的)--%>
                                        <c:if test="${pageInfo.isFirstPage}">
                                            <li class="disabled"><a href="#">上一页</a></li>
                                        </c:if>
                                            <%--如果不是第一页进行页面的跳转--%>
                                            <c:if test="${not pageInfo.isFirstPage}">
                                                <li><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pageNum -1}">上一页</a></li>
                                            </c:if>

                                         <%--pageInfo.navigatepageNums 是一个数组存放所分得页数  逻辑页数 5--%>
                                        <c:forEach items="${pageInfo.navigatepageNums}" var= "i" >
                                            <%--判断是否是当前页--%>
                                            <c:if test="${pageInfo.pageNum==i}">
                                                <li class="active"><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${i}">${i} <span class="sr-only">(current)</span></a></li>
                                            </c:if>
                                            <c:if test="${pageInfo.pageNum!=i}">
                                                <li><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${i}">${i} <span class="sr-only">(current)</span></a></li>
                                            </c:if>

                                        </c:forEach>


                                        <%--判断当前页面是否是最后一页--%>
                                        <c:if test="${pageInfo.isLastPage}">
                                            <li class="disabled"><a href="#">下一页</a></li>
                                        </c:if>
                                            <%--如果不是最后一页--%>
                                            <c:if test="${not pageInfo.isLastPage}">
                                                <li><a href="${appPath}/admin/index?keyWord=${param.keyWord}&pageNum=${pageInfo.pageNum +1}">下一页</a></li>
                                            </c:if>
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
        //全选全不选判断
        //1获取单选标签
        var checkAll = $("#checkAll");  //总复选框  是一个jquery对对象
        var adminCheckBox = $(".adminCheckBox")//每一行中的复选框 admincheckbox是一个集合

        //给check绑定一个单击事件
        checkAll.click(function(){
            //查看复选框选中的属性checked
            // jQuery对象转换为dom对象的方法 1.checkall[0]    checkall.get()
            $.each(adminCheckBox,function(i,checkBox){
                //将总复选框的状态付给子复选框
                checkBox.checked = checkAll[0].checked ;
            });
        });


        //给总删除按钮绑定点击事件
        $("#deleteBath").click(function(){
            //获取每个复选框  :checked  过滤选择器只要选中的才会选择
            var adminChecks = $(".adminCheckBox:checked");
            //添加样式
            if (adminChecks.length == 0) {
                //使用样式
                layer.msg("没有选中要删除的对象");
            } else {

                layer.confirm("是否删除");
                //常见一个ids的数组用于保存遍历的id
                var ids = new Array();

                //将复选框集合进行遍历
                for (var i=0; i<adminChecks.length ;i++){
                    //获取复选框内绑定的id
                    //attr()  是获取jQuery对象属性的方法
                    //adminchecks[1]  是一个dom对象需要转化为jQuery对象 使用工厂函数即可转换
                    var id = $(adminChecks[i]).attr("id");
                    //ids.push(id)添加的方法  push(增加)
                    ids.push(id);
                };
                //跳转到后台程序  href  (超链接)  location(地点)
                location.href="${appPath}/admin/deleteBath?ids="+ids;

            }

        });


    });

</script>
</body>
</html>

