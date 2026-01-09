<%@page import="Model.KhachHang.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🍜 ỐC NHÀ BON - Lịch sử đơn hàng</title>
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
            <h1 class="text-center mb-4">📋 Lịch sử đơn hàng</h1>

            <c:if test="${sessionScope.khachhang == null}">
                <div class="alert alert-warning text-center">
                    <h4>Vui lòng đăng nhập để xem lịch sử đơn hàng</h4>
                    <a href="DangNhapKhachController" class="btn btn-primary">Đăng nhập</a>
                </div>
            </c:if>

            <c:if test="${sessionScope.khachhang != null}">
                <div class="card">
                    <div class="card-header">
                        <h5>Thông tin khách hàng</h5>
                    </div>
                    <div class="card-body">
                        <p><strong>Tên:</strong> ${sessionScope.khachhang.tenKH}</p>
                        <p><strong>Số điện thoại:</strong> ${sessionScope.khachhang.sdt}</p>
                    </div>
                </div>

                <div class="mt-4">
                    <h4>Lịch sử đơn hàng</h4>
                    <div class="alert alert-info">
                        <strong>Thông báo:</strong> Tính năng lịch sử đơn hàng đang được phát triển.
                        Hiện tại bạn có thể xem đơn hàng hiện tại tại <a href="DonHangController">trang đơn hàng</a>.
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">
                    ${error}
                </div>
            </c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>