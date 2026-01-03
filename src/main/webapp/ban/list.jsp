<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.BanAn.BanAn" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar.jsp" %>

<h3>Danh sách bàn</h3>
<a href="ban?action=add" class="btn btn-primary mb-3">Thêm bàn</a>

<table class="table table-bordered table-hover">
    <tr class="table-dark">
        <th>Mã</th>
        <th>Tên</th>
        <th>Trạng thái</th>
        <th>Mức</th>
        <th>Hành động</th>
    </tr>

<%
    ArrayList<BanAn> ds = (ArrayList<BanAn>) request.getAttribute("dsBan");
    for (BanAn b : ds) {
%>
<tr>
    <td><%=b.getMaBan()%></td>
    <td><%=b.getTenBan()%></td>
    <td><%=b.getTrangThai()==0?"Trống":"Đang dùng"%></td>
    <td><%=b.getMucBan()%></td>
    <td>
        <a class="btn btn-warning btn-sm"
           href="ban?action=edit&maBan=<%=b.getMaBan()%>">Sửa</a>
        <a class="btn btn-danger btn-sm"
           href="ban?action=delete&maBan=<%=b.getMaBan()%>"
           onclick="return confirm('Xóa bàn?')">Xóa</a>
    </td>
</tr>
<% } %>
</table>

<%@ include file="../layout/footer.jsp" %>
