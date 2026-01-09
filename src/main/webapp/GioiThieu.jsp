<%@page import="Model.KhachHang.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🍜 ỐC NHÀ BON - Giới thiệu</title>
<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<!-- ================= NAVBAR ================= -->
<jsp:include page="layout/customerNavbar.jsp" />

<!-- ================= NỘI DUNG ================= -->
<div class="container mt-5">
    <div class="row">
        <div class="col-12">
            <h1 class="text-center mb-4">🍜 ỐC NHÀ BON</h1>
            <h2 class="text-center text-muted mb-5">Nhà hàng hải sản tươi ngon</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <h3>Về chúng tôi</h3>
            <p>Ốc Nhà Bon là nhà hàng hải sản chuyên phục vụ các món ăn từ hải sản tươi sống,
            được chế biến theo công thức gia truyền với hương vị đặc trưng.</p>

            <h4 class="mt-4">Tại sao chọn Ốc Nhà Bon?</h4>
            <ul>
                <li>Hải sản tươi sống nhập khẩu hàng ngày</li>
                <li>Đầu bếp chuyên nghiệp với 10+ năm kinh nghiệm</li>
                <li>Không gian sang trọng, thoáng mát</li>
                <li>Phục vụ tận tình, chu đáo</li>
                <li>Giá cả phải chăng</li>
            </ul>
        </div>

        <div class="col-md-6">
            <h3>Thông tin liên hệ</h3>
            <div class="card">
                <div class="card-body">
                    <p><strong>📍 Địa chỉ:</strong> 123 Đường ABC, Quận XYZ, TP.HCM</p>
                    <p><strong>📞 Điện thoại:</strong> 1900 XXX XXX</p>
                    <p><strong>🕒 Giờ mở cửa:</strong></p>
                    <ul>
                        <li>Thứ 2 - Chủ nhật: 10:00 - 22:00</li>
                        <li>Nghỉ ngày lễ</li>
                    </ul>
                    <p><strong>📧 Email:</strong> info@ocnhabon.com</p>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-5">
        <div class="col-12 text-center">
            <a href="TrangChuController" class="btn btn-primary btn-lg">🍽️ Xem thực đơn</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>