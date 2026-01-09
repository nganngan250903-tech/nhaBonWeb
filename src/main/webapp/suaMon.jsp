<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Model.MonAn.MonAn" %>
<%
	MonAn m = (MonAn) request.getAttribute("mon");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sửa món</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>

<body class="bg-light">

<jsp:include page="layout/adminSidebar.jsp"/>

<div class="admin-content">
    <h3>✏️ Sửa món</h3>

	<form action="QLMonController" method="post" class="w-50">
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="maMon" value="<%=m.getMaMon()%>">

		<input class="form-control mb-2" name="tenMon" value="<%=m.getTenMon()%>">
		<input class="form-control mb-2" name="gia" value="<%=m.getGia()%>">
		<input class="form-control mb-2" name="hinhAnh" value="<%=m.getHinhAnh()%>">
		<textarea class="form-control mb-2" name="moTa"><%=m.getMoTa()%></textarea>
		<input class="form-control mb-2" name="maDM" value="<%=m.getMaDM()%>">
		<input class="form-control mb-2" name="soLuong" value="<%=m.getSoLuong()%>">

		<select class="form-select mb-3" name="trangThai">
			<option value="1" <%=m.getTrangThaiMon()==1?"selected":""%>>BestSeller</option>
			<option value="0" <%=m.getTrangThaiMon()==0?"selected":""%>>Ngừng bán</option>
		</select>

		<button class="btn btn-primary">Cập nhật</button>
	</form>
	

</div>

</body>
</html>
