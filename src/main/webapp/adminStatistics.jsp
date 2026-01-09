<%@page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <jsp:include page="layout/authAdmin.jsp" />

    <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thống kê - Nhà Bon</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
.admin-content {
	margin-left: 220px;
	padding: 30px;
}

.stats-card {
	border-radius: 10px;
	box-shadow: 0 4px 6px rgba(0,0,0,0.1);
	transition: transform 0.3s ease;
}

.stats-card:hover {
	transform: translateY(-5px);
}

.chart-container {
	position: relative;
	height: 400px;
	margin-bottom: 30px;
}

.table-responsive {
	border-radius: 10px;
	box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.stats-number {
	font-size: 2rem;
	font-weight: bold;
	color: #ff8c00;
}

.stats-label {
	font-size: 0.9rem;
	color: #666;
	text-transform: uppercase;
	font-weight: 600;
}
</style>
</head>

<body class="bg-light">
<!-- SIDEBAR -->
<jsp:include page="layout/adminSidebar.jsp" />

<!-- NỘI DUNG CHÍNH -->
<div class="admin-content">
	<h2 class="fw-bold mb-4">📊 Thống kê hệ thống</h2>

	<%
		NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
		List<long[]> doanhThu7Ngay = (List<long[]>) request.getAttribute("doanhThu7Ngay");
		List<Object[]> topMonAn = (List<Object[]>) request.getAttribute("topMonAn");
		List<Object[]> thongKeNhanVien = (List<Object[]>) request.getAttribute("thongKeNhanVien");
		List<Object[]> thongKeBan = (List<Object[]>) request.getAttribute("thongKeBan");
	%>

	<!-- OVERVIEW CARDS -->
	<div class="row mb-4">
		<div class="col-md-3">
			<div class="card stats-card text-center p-3">
				<div class="stats-number"><%=formatter.format(request.getAttribute("tongDoanhThu") != null ? request.getAttribute("tongDoanhThu") : 0)%> VNĐ</div>
				<div class="stats-label">Tổng doanh thu</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card stats-card text-center p-3">
				<div class="stats-number"><%=request.getAttribute("tongHoaDon") != null ? request.getAttribute("tongHoaDon") : 0%></div>
				<div class="stats-label">Tổng đơn hàng</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card stats-card text-center p-3">
				<div class="stats-number"><%=request.getAttribute("tongBan") != null ? request.getAttribute("tongBan") : 0%></div>
				<div class="stats-label">Tổng bàn</div>
			</div>
		</div>
		<div class="col-md-3">
			<div class="card stats-card text-center p-3">
				<div class="stats-number"><%=request.getAttribute("tongNhanVien") != null ? request.getAttribute("tongNhanVien") : 0%></div>
				<div class="stats-label">Nhân viên</div>
			</div>
		</div>
	</div>

	<!-- REVENUE CHART -->
	<div class="row mb-4">
		<div class="col-md-8">
			<div class="card">
				<div class="card-header">
					<h5 class="mb-0">📈 Doanh thu 7 ngày gần nhất</h5>
				</div>
				<div class="card-body">
					<div class="chart-container">
						<canvas id="revenueChart"></canvas>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="card">
				<div class="card-header">
					<h5 class="mb-0">💰 Doanh thu theo thời gian</h5>
				</div>
				<div class="card-body">
					<div class="mb-3">
						<div class="d-flex justify-content-between">
							<span>Hôm nay:</span>
							<strong><%=formatter.format(request.getAttribute("doanhThuHomNay") != null ? request.getAttribute("doanhThuHomNay") : 0)%> VNĐ</strong>
						</div>
					</div>
					<div class="mb-3">
						<div class="d-flex justify-content-between">
							<span>Tuần này:</span>
							<strong><%=formatter.format(request.getAttribute("doanhThuTuanNay") != null ? request.getAttribute("doanhThuTuanNay") : 0)%> VNĐ</strong>
						</div>
					</div>
					<div class="mb-3">
						<div class="d-flex justify-content-between">
							<span>Tháng này:</span>
							<strong><%=formatter.format(request.getAttribute("doanhThuThangNay") != null ? request.getAttribute("doanhThuThangNay") : 0)%> VNĐ</strong>
						</div>
					</div>
					<hr>
					<div class="mb-3">
						<div class="d-flex justify-content-between">
							<span>Đã thanh toán:</span>
							<strong><%=request.getAttribute("soHoaDonDaThanhToan") != null ? request.getAttribute("soHoaDonDaThanhToan") : 0%></strong>
						</div>
					</div>
					<div class="mb-3">
						<div class="d-flex justify-content-between">
							<span>Chưa thanh toán:</span>
							<strong><%=request.getAttribute("soHoaDonChuaThanhToan") != null ? request.getAttribute("soHoaDonChuaThanhToan") : 0%></strong>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- TOP DISHES CHART -->
	<div class="row mb-4">
		<div class="col-md-6">
			<div class="card">
				<div class="card-header">
					<h5 class="mb-0">🍽️ Top 5 món ăn bán chạy</h5>
				</div>
				<div class="card-body">
					<div class="chart-container">
						<canvas id="topDishesChart"></canvas>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="card">
				<div class="card-header">
					<h5 class="mb-0">📋 Chi tiết món ăn</h5>
				</div>
				<div class="card-body">
					<div class="table-responsive">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Món ăn</th>
									<th class="text-center">Số lượng bán</th>
								</tr>
							</thead>
							<tbody>
								<% if (topMonAn != null) {
									for (Object[] mon : topMonAn) {
										if (mon != null && mon.length >= 2) { %>
								<tr>
									<td><%=mon[0] != null ? mon[0] : "N/A"%></td>
									<td class="text-center"><%=mon[1] != null ? mon[1] : 0%></td>
								</tr>
								<% } } } %>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>


<script>
// Revenue Chart
const revenueCtx = document.getElementById('revenueChart').getContext('2d');
<%
	if (doanhThu7Ngay != null && !doanhThu7Ngay.isEmpty()) {
%>
const revenueData = {
	labels: [
		<% for (int i = 0; i < doanhThu7Ngay.size(); i++) {
			long[] data = doanhThu7Ngay.get(i);
			java.util.Date date = new java.util.Date(data[0]);
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM");
		%>
			'<%=sdf.format(date)%>'<% if (i < doanhThu7Ngay.size() - 1) { %>,<% } %>
		<% } %>
	],
	datasets: [{
		label: 'Doanh thu (VNĐ)',
		data: [
			<% for (int i = 0; i < doanhThu7Ngay.size(); i++) {
				long[] data = doanhThu7Ngay.get(i);
			%>
				<%=data[1]%><% if (i < doanhThu7Ngay.size() - 1) { %>,<% } %>
			<% } %>
		],
		borderColor: '#ff8c00',
		backgroundColor: 'rgba(255, 140, 0, 0.1)',
		tension: 0.4,
		fill: true
	}]
};

new Chart(revenueCtx, {
	type: 'line',
	data: revenueData,
	options: {
		responsive: true,
		maintainAspectRatio: false,
		scales: {
			y: {
				beginAtZero: true,
				ticks: {
					callback: function(value) {
						return new Intl.NumberFormat('vi-VN').format(value) + ' VNĐ';
					}
				}
			}
		}
	}
});
<% } %>

// Top Dishes Chart
const topDishesCtx = document.getElementById('topDishesChart').getContext('2d');
<%
	if (topMonAn != null && !topMonAn.isEmpty()) {
%>
const topDishesData = {
	labels: [
		<% for (int i = 0; i < topMonAn.size(); i++) {
			Object[] mon = topMonAn.get(i);
		%>
			'<%=mon[0]%>'<% if (i < topMonAn.size() - 1) { %>,<% } %>
		<% } %>
	],
	datasets: [{
		label: 'Số lượng bán',
		data: [
			<% for (int i = 0; i < topMonAn.size(); i++) {
				Object[] mon = topMonAn.get(i);
			%>
				<%=mon[1]%><% if (i < topMonAn.size() - 1) { %>,<% } %>
			<% } %>
		],
		backgroundColor: [
			'rgba(255, 99, 132, 0.8)',
			'rgba(54, 162, 235, 0.8)',
			'rgba(255, 205, 86, 0.8)',
			'rgba(75, 192, 192, 0.8)',
			'rgba(153, 102, 255, 0.8)'
		],
		borderWidth: 1
	}]
};

new Chart(topDishesCtx, {
	type: 'bar',
	data: topDishesData,
	options: {
		responsive: true,
		maintainAspectRatio: false,
		scales: {
			y: {
				beginAtZero: true
			}
		}
	}
});
<% } %>
</script>

</body>
</html>
