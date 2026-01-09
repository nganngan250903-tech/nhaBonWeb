<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
.admin-sidebar {
	width: 220px;
	height: 100vh;
	background: linear-gradient(180deg, #ff8c00, #ff6a00);
	position: fixed;
	top: 0;
	left: 0;
	padding-top: 20px;
	box-shadow: 3px 0 10px rgba(0,0,0,0.2);
	z-index: 1000;
}

.admin-sidebar h4 {
	color: #fff;
	font-weight: bold;
	margin-bottom: 20px;
}

.admin-sidebar a {
	color: #fff;
	padding: 12px 20px;
	display: block;
	text-decoration: none;
	font-size: 15px;
	border-radius: 8px;
	margin: 6px 10px;
	transition: all 0.3s ease;
}

.admin-sidebar a:hover {
	background: rgba(255,255,255,0.2);
	transform: translateX(5px);
}
</style>

<nav class="admin-sidebar">
	<h4 class="text-center">QUẢN TRỊ</h4>
	<hr class="text-white mx-3">

	<a href="StatisticsController">📊 Thống kê</a>
	<a href="QLMonController">🍽️ Quản lý Món</a>
	<a href="QLDanhMucController">📂 Danh mục</a>
	<a href="QLBanController">🪑 Bàn ăn</a>
	<a href="HoaDonAdminController">🧾 Đơn hàng</a>
	<a href="QuanLyTrangThaiController">⚙️ Trạng thái món</a>

	<a href="dangxuatAdminController" class="fw-bold mt-3 text-danger">
		🚪 Đăng xuất
	</a>
</nav>
