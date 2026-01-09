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
<!-- Bootstrap 5 -->
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

<!-- ================= NAVBAR ================= -->
<jsp:include page="layout/customerNavbar.jsp" />

<!-- ================= NỘI DUNG ================= -->
<div class="container mt-5">
    <div class="row">
        <div class="col-12">
            <h1 class="text-center mb-4">📋 Theo dõi đơn hàng của bàn</h1>

            <!-- SUCCESS MESSAGE -->
            <c:if test="${not empty success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle"></i> ${success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <c:if test="${sessionScope.khachhang == null}">
                <div class="alert alert-warning text-center">
                    <h4>Vui lòng đăng nhập để theo dõi đơn hàng</h4>
                    <a href="DangNhapKhachController" class="btn btn-primary">Đăng nhập</a>
                </div>
            </c:if>

            <c:if test="${sessionScope.khachhang != null}">
                <c:if test="${empty donHang}">
                    <div class="alert alert-info text-center">
                        <h4>Bạn chưa có đơn hàng nào đang xử lý</h4>
                        <a href="TrangChuController" class="btn btn-primary">Đặt món ngay</a>
                    </div>
                </c:if>

                <c:if test="${not empty donHang}">
                    <!-- Thông tin đơn hàng -->
                    <div class="card mb-4">
                        <div class="card-header">
                            <h5>📄 Thông tin đơn hàng #${maHD}</h5>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <p><strong>Khách hàng:</strong> ${sessionScope.khachhang.tenKH}</p>
                                    <p><strong>Số điện thoại:</strong> ${sessionScope.khachhang.sdt}</p>
                                </div>
                                <div class="col-md-6">
                                    <p><strong>Thời gian đặt:</strong>
                                        <fmt:formatDate value="${donHang[3]}" pattern="dd/MM/yyyy HH:mm"/>
                                    </p>
                                    <p><strong>Bàn số:</strong> ${donHang[1]}</p>
                                    <p><strong>Trạng thái thanh toán:</strong>
                                        <c:choose>
                                            <c:when test="${donHang[6] == 0}">
                                                <span class="badge bg-danger">Chưa thanh toán</span>
                                            </c:when>
                                            <c:when test="${donHang[6] == 1}">
                                                <span class="badge bg-success">Đã thanh toán</span>
                                            </c:when>
                                            <c:when test="${donHang[6] == 2}">
                                                <span class="badge bg-warning">
                                                    <i class="fas fa-clock"></i> Chờ xác nhận thanh toán
                                                </span>
                                            </c:when>
                                            <c:when test="${donHang[6] == 3}">
                                                <c:choose>
                                                    <c:when test="${hasCompletedItems and not hasProcessingItems}">
                                                        <span class="badge bg-success">
                                                            <i class="fas fa-check-circle"></i> Đã hoàn thành
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${hasProcessingItems}">
                                                        <span class="badge bg-warning">
                                                            <i class="fas fa-clock"></i> Đang chế biến
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-info">
                                                            <i class="fas fa-utensils"></i> Đã đặt món
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Không xác định</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>

                                    <p><strong>Trạng thái món ăn:</strong>
                                        <c:choose>
                                            <c:when test="${hasCompletedItems and not hasProcessingItems}">
                                                <span class="badge bg-success">
                                                    <i class="fas fa-check-circle"></i> Đã làm xong
                                                </span>
                                            </c:when>
                                            <c:when test="${hasProcessingItems}">
                                                <span class="badge bg-warning">
                                                    <i class="fas fa-clock"></i> Đang làm
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-info">
                                                    <i class="fas fa-utensils"></i> Đã đặt món
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Danh sách món ăn -->
                    <div class="card">
                        <div class="card-header">
                            <h5>🍽️ Chi tiết món ăn</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Món ăn</th>
                                            <th>Hình ảnh</th>
                                            <th>Số lượng</th>
                                            <th>Đơn giá</th>
                                            <th>Thành tiền</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="ct" items="${dsChiTiet}">
                                            <tr>
                                                <td>${ct[3]}</td> <!-- TenMon -->
                                                <td>
                                                    <img src="${ct[4]}" alt="${ct[3]}" style="width: 50px; height: 50px; object-fit: cover;">
                                                </td>
                                                <td>${ct[5]}</td> <!-- SoLuong -->
                                                <td><fmt:formatNumber value="${ct[6]}" pattern="#,##0"/>đ</td> <!-- DonGia -->
                                                <td><fmt:formatNumber value="${ct[5] * ct[6]}" pattern="#,##0"/>đ</td> <!-- ThanhTien -->
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${ct[7] == 0}">
                                                            <span class="status-badge status-doing">
                                                                <i class="fas fa-spinner fa-spin"></i> Đang làm
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${ct[7] == 1}">
                                                            <span class="status-badge status-done">
                                                                <i class="fas fa-check-circle"></i> Đã xong
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">Chờ xử lý</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <tfoot>
                                        <tr class="table-primary">
                                            <td colspan="4" class="text-end fw-bold">Tổng cộng:</td>
                                            <td class="fw-bold"><fmt:formatNumber value="${donHang[5]}" pattern="#,##0"/>đ</td>
                                            <td></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Nút thanh toán -->
                    <div class="text-center mt-4">
                        <c:if test="${donHang[6] == 0}">
                            <a href="QRPayment.jsp?maBan=${donHang[1]}&tongTien=${donHang[5]}&maHD=${maHD}" class="btn btn-success btn-lg">
                                <i class="fas fa-qrcode"></i> Thanh toán QR Code
                            </a>
                        </c:if>
                        <c:if test="${donHang[6] == 1}">
                            <div class="alert alert-success">
                                <i class="fas fa-check-circle"></i>
                                Đơn hàng này đã được thanh toán thành công!
                            </div>
                        </c:if>
                        <c:if test="${donHang[6] == 2}">
                            <div class="alert alert-warning">
                                <i class="fas fa-clock"></i>
                                Đơn hàng đang chờ xác nhận thanh toán từ nhân viên.
                                <br><small>Vui lòng liên hệ nhân viên để được hỗ trợ.</small>
                            </div>
                        </c:if>
                        <c:if test="${donHang[6] == 3 and hasCompletedItems}">
                            <a href="PaymentController" class="btn btn-success btn-lg">
                                <i class="fas fa-credit-card"></i> Thanh toán ngay
                            </a>
                            <div class="alert alert-info mt-3">
                                <i class="fas fa-info-circle"></i>
                                <small>Nhấn nút "Thanh toán ngay" khi bạn đã ăn xong các món đã hoàn thành.</small>
                            </div>
                        </c:if>

                        <c:if test="${donHang[6] == 3 and not hasCompletedItems and hasProcessingItems}">
                            <div class="alert alert-warning">
                                <i class="fas fa-clock"></i>
                                <strong>Đang chế biến món ăn</strong><br>
                                <small>Vui lòng chờ nhà bếp hoàn thành món ăn trước khi thanh toán.</small>
                            </div>
                        </c:if>
                    </div>
                </c:if>
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