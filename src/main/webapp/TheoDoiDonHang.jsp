<%@page import="Model.KhachHang.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🍜 ỐC NHÀ BON - Theo dõi đơn hàng</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .status-badge {
        font-size: 0.8em;
        padding: 4px 8px;
        border-radius: 12px;
    }
    .status-doing {
        background-color: #fff3cd;
        color: #856404;
        border: 1px solid #ffeaa7;
    }
    .status-done {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }
</style>
</head>

<body>

<jsp:include page="layout/customerNavbar.jsp" />

<div class="container mt-5">
    <div class="row">
        <div class="col-12">
            <h1 class="text-center mb-4">📋 Theo dõi đơn hàng của bàn</h1>

            <c:if test="${not empty success}">
                <div class="alert alert-success alert-dismissible fade show">
                    ${success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <c:if test="${sessionScope.khachhang != null and not empty donHang}">

                <!-- ================= THÔNG TIN ĐƠN ================= -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5>📄 Thông tin đơn hàng #${maHD}</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <p><strong>Khách hàng:</strong> ${sessionScope.khachhang.tenKH}</p>
                                <p><strong>SĐT:</strong> ${sessionScope.khachhang.sdt}</p>
                            </div>
                            <div class="col-md-6">
                                <p><strong>Thời gian:</strong>
                                    <fmt:formatDate value="${donHang[3]}" pattern="dd/MM/yyyy HH:mm"/>
                                </p>
                                <p><strong>Bàn số:</strong> ${donHang[1]}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- ================= DANH SÁCH MÓN ================= -->
                <div class="card">
                    <div class="card-header">
                        <h5>🍽️ Chi tiết món ăn</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Món</th>
                                        <th>Hình</th>
                                        <th>SL</th>
                                        <th>Đơn giá</th>
                                        <th>Thành tiền</th>
                                        <th>Trạng thái</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <!-- 🔥 KHỞI TẠO TỔNG TIỀN -->
                                    <c:set var="tongTien" value="0"/>

                                    <c:forEach var="ct" items="${dsChiTiet}">
                                        <!-- 🔥 CỘNG DỒN -->
                                        <c:set var="tongTien" value="${tongTien + (ct[5] * ct[6])}"/>

                                        <tr>
                                            <td>${ct[3]}</td>
                                            <td>
                                                <img src="${ct[4]}" style="width:50px;height:50px;object-fit:cover">
                                            </td>
                                            <td>${ct[5]}</td>
                                            <td><fmt:formatNumber value="${ct[6]}" pattern="#,##0"/>đ</td>
                                            <td><fmt:formatNumber value="${ct[5] * ct[6]}" pattern="#,##0"/>đ</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${ct[7] == 0}">
                                                        <span class="status-badge status-doing">Đang làm</span>
                                                    </c:when>
                                                    <c:when test="${ct[7] == 1}">
                                                        <span class="status-badge status-done">Đã xong</span>
                                                    </c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>

                                <tfoot>
                                    <tr class="table-primary">
                                        <td colspan="4" class="text-end fw-bold">Tổng cộng:</td>
                                        <td class="fw-bold">
                                            <fmt:formatNumber value="${tongTien}" pattern="#,##0"/>đ
                                        </td>
                                        <td></td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- ================= THANH TOÁN ================= -->
                <div class="text-center mt-4">
                   
                        <a href="QRPayment.jsp?maBan=${donHang[1]}&tongTien=${tongTien}&maHD=${maHD}"
                           class="btn btn-success btn-lg">
                            Thanh toán QR
                        </a>
               
                </div>

            </c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
