
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--需要对传过来的的数据进行判断引用c标签--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<div class="col-sm-3 col-md-2 sidebar">
    <div class="tree">
        <ul style="padding-left:0px;" class="list-group">
            <!--父节点内无子节点-->
            <c:forEach items="${sessionScope.parentMenu}" var="parent" >
               <c:if test="${parent.childMenus.size() == 0}">
                    <li class="list-group-item tree-closed" >
                        <a href="${appPath}/${parent.url}"><i class="${parent.icon}"></i> ${parent.name}</a>
                    </li>
                </c:if>
            <!--父节点内有子节点-->
                <c:if test="${parent.childMenus.size() != 0}">
                    <li class="list-group-item tree-closed">
                        <span><i class="${parent.icon}"></i> ${parent.name} <span class="badge" style="float:right">${parent.childMenus.size()}</span></span>
                        <ul style="margin-top:10px;display:none;">
                            <%--子节点--%>
                            <c:forEach items="${parent.childMenus}" var="child">
                                <li style="height:30px;">
                                    <a href="${appPath}/${child.url}"><i class="${child.icon}"></i> ${child.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
</div>