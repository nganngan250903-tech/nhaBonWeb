<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Model.DanhMuc.DanhMuc"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý danh mục</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>

<body class="bg-light">

<%
    String msgSuccess = (String) session.getAttribute("msgSuccess");
    String msgError   = (String) session.getAttribute("msgError");
%>

<jsp:include page="layout/adminSidebar.jsp"/>

<div class="admin-content">

    <% if (msgSuccess != null) { %>
    <div class="alert alert-success alert-dismissible fade show">
        <%= msgSuccess %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("msgSuccess"); } %>

    <% if (msgError != null) { %>
    <div class="alert alert-danger alert-dismissible fade show">
        <%= msgError %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("msgError"); } %>

    <h3 class="mb-4">📂 Quản lý Danh Mục</h3>

    <a href="QLDanhMucController?action=add"
       class="btn btn-success mb-3">➕ Thêm danh mục</a>

    <table class="table table-bordered table-hover">
        <tr class="table-dark">
            <th>ID</th>
            <th>Tên danh mục</th>
            <th>Mô tả</th>
            <th>Thao tác</th>
        </tr>

        <%
            ArrayList<DanhMuc> ds =
                (ArrayList<DanhMuc>) request.getAttribute("dsDM");

            if (ds != null) {
                for (DanhMuc dm : ds) {
        %>
        <tr>
            <td><%= dm.getMaDM() %></td>
            <td><%= dm.getTenDM() %></td>
            <td><%= dm.getMoTa() %></td>
            <td>
                <a class="btn btn-warning btn-sm"
                   href="QLDanhMucController?action=edit&id=<%=dm.getMaDM()%>">
                   Sửa
                </a>

                <form action="QLDanhMucController"
                      method="post"
                      style="display:inline">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="maDM" value="<%=dm.getMaDM()%>">
                    <button class="btn btn-danger btn-sm"
                        onclick="return confirm('Xóa danh mục này?')">
                        Xóa
                    </button>
                </form>
            </td>
        </tr>
        <% } } %>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
