<%@page import="Model.NhanVien.NhanVien"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <jsp:include page="layout/authAdmin.jsp" />
 
    <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang quản trị - Nhà Bon</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
.admin-content {
	margin-left: 220px;   /* CHỪA CHỖ CHO SIDEBAR */
	padding: 30px;
}
</style>
</head>

<body class="bg-light">

<!-- SIDEBAR -->
<jsp:include page="layout/adminSidebar.jsp" />

<!-- NỘI DUNG CHÍNH -->
<div class="admin-content">


	<h2 class="fw-bold mb-4">👋 Chào mừng, ${sessionScope.nhanvien.tenNV}</h2>
	

	<div class="row">
		<div class="col-md-4">
			<div class="card shadow text-center">
				<div class="card-body">
					<h5>Tổng bàn</h5>
					<h2>${tongBan}</h2>
				</div>
			</div>
		</div>

		<div class="col-md-4">
			<div class="card shadow text-center">
				<div class="card-body">
					<h5>Tổng món</h5>
					<h2>${tongMon}</h2>
				</div>
			</div>
		</div>

		<div class="col-md-4">
			<div class="card shadow text-center">
				<div class="card-body">
					<h5>Hóa đơn</h5>
					<h2>${tongHD}</h2>
				</div>
			</div>
		</div>
	</div>

</div>

</body>
</html>
