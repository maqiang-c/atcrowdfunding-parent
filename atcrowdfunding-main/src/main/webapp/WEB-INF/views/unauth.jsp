
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%--引用外部配置的css样式  此为静态包含的方法--%>
   <%@ include file="/WEB-INF/common/css.jsp"%>

    <%--此为引用的动态包含的方式--%>
    <%--<jsp:include page="/WEB-INF/common/css.jsp"></jsp:include>--%>
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        .tree-closed {
            height : 40px;
        }
        .tree-expanded {
            height : auto;
        }
    </style>
</head>

<body>

    <%--静态包含引用外部配置页头--%>
    <%@ include file= "/WEB-INF/common/nav.jsp" %>

<div class="container-fluid">
    <div class="row">

        <%@ include file= "/WEB-INF/common/side.jsp" %>


        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">没有权限</h1>


        </div>
    </div>
</div>

    <%@ include file= "/WEB-INF/common/js.jsp" %>

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
    });
</script>
</body>
</html>