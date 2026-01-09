<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.MonAn.MonAn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý món</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="css/admin.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>

<body class="bg-light">

<jsp:include page="layout/adminSidebar.jsp"/>

<div class="admin-content">
    <h3 class="mb-4">🍽️ Quản lý Món ăn</h3>

		<form class="d-flex mb-3" action="QLMonController">
			<input type="hidden" name="action" value="search">
			<input class="form-control w-25 me-2" name="key" placeholder="Tìm món...">
			<button class="btn btn-primary">Tìm</button>
			<a href="QLMonController?action=add" class="btn btn-success ms-2">➕ Thêm</a>
		</form>

		<table class="table table-bordered table-hover">
			<tr class="table-dark">
				<th>ID</th><th>Tên</th><th>Giá</th><th>Trạng thái</th><th>Thao tác</th>
			</tr>

			<%
				ArrayList<MonAn> ds = (ArrayList<MonAn>) request.getAttribute("dsMon");
				for (MonAn m : ds) {
			%>
			<tr>
				<td><%=m.getMaMon()%></td>
				<td><%=m.getTenMon()%></td>
				<td><%=m.getGia()%> đ</td>
				<td>
					<span class="badge <%=m.getTrangThaiMon()==1?"bg-success":"bg-secondary"%>">
						<%=m.getTrangThaiMon()==1?"BestSeller":"Ngừng bán"%>
					</span>
				</td>
				<td>
					<a class="btn btn-warning btn-sm"
					   href="QLMonController?action=edit&id=<%=m.getMaMon()%>">Sửa</a>

					<form action="QLMonController" method="post" style="display:inline">
						<input type="hidden" name="action" value="delete">
						<input type="hidden" name="maMon" value="<%=m.getMaMon()%>">
						<button class="btn btn-danger btn-sm"
							onclick="return confirm('Xóa món này?')">Xóa</button>
					</form>
				</td>
			</tr>
			<% } %>
		</table>
	</main>

</div>
</div>
</body>
</html>
