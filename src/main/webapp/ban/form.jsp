<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Model.BanAn.BanAn" %>
<%@ include file="../layout/header.jsp" %>
<%@ include file="../layout/sidebar.jsp" %>

<%
    BanAn b = (BanAn) request.getAttribute("ban");
    boolean edit = b != null;
%>

<h3><%=edit?"Sửa bàn":"Thêm bàn"%></h3>

<form method="post">
    <div class="mb-3">
        <label>Mã bàn</label>
        <input class="form-control" name="maBan"
               value="<%=edit?b.getMaBan():""%>"
               <%=edit?"readonly":""%>>
    </div>

    <div class="mb-3">
        <label>Tên bàn</label>
        <input class="form-control" name="tenBan"
               value="<%=edit?b.getTenBan():""%>">
    </div>

    <div class="mb-3">
        <label>Mức bàn</label>
        <input class="form-control" name="mucBan"
               value="<%=edit?b.getMucBan():""%>">
    </div>

    <button class="btn btn-success">Lưu</button>
    <% if(edit){ %><input type="hidden" name="edit" value="1"><% } %>
</form>

<%@ include file="../layout/footer.jsp" %>
