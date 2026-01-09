<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý trạng thái món ăn</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .status-select {
        min-width: 120px;
    }
    .order-card {
        margin-bottom: 20px;
    }
    .status-doing {
        background-color: #fff3cd;
        color: #856404;
    }
    .status-done {
        background-color: #d4edda;
        color: #155724;
    }
</style>
</head>

<body>
    <!-- Include admin sidebar -->
    <jsp:include page="layout/adminSidebar.jsp" />

    <div class="content-wrapper" style="margin-left: 250px; padding: 20px;">
        <div class="container-fluid">
            <h2 class="mb-4">🍽️ Quản lý trạng thái món ăn</h2>

            <!-- Thông báo -->
            <c:if test="${not empty sessionScope.msgSuccess}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${sessionScope.msgSuccess}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="msgSuccess" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.msgError}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${sessionScope.msgError}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="msgError" scope="session"/>
            </c:if>

            <c:if test="${empty dsDonHang}">
                <div class="alert alert-info">
                    <h5>Không có đơn hàng nào đang xử lý</h5>
                </div>
            </c:if>

            <c:if test="${viewMode == 'detail' && not empty hoaDonInfo && not empty dsChiTiet}">
                <!-- Hiển thị chi tiết hóa đơn cụ thể -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5>📄 Chi tiết hóa đơn #${hoaDonInfo[0]}</h5>
                        <a href="HoaDonAdminController" class="btn btn-secondary btn-sm">← Quay lại danh sách</a>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <p><strong>Khách hàng:</strong> ${hoaDonInfo[6]}</p>
                                <p><strong>Số điện thoại:</strong> ${hoaDonInfo[7]}</p>
                                <p><strong>Thời gian đặt:</strong>
                                    <fmt:formatDate value="${hoaDonInfo[2]}" pattern="dd/MM/yyyy HH:mm"/>
                                </p>
                            </div>
                            <div class="col-md-6">
                                <p><strong>Bàn số:</strong> ${hoaDonInfo[1]}</p>
                                <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${hoaDonInfo[4]}" pattern="#,##0"/>đ</p>
                                <p><strong>Trạng thái thanh toán:</strong>
                                    <c:choose>
                                        <c:when test="${hoaDonInfo[5] == 0}">
                                            <span class="badge bg-warning">Chưa thanh toán</span>
                                        </c:when>
                                        <c:when test="${hoaDonInfo[5] == 1}">
                                            <span class="badge bg-success">Đã thanh toán</span>
                                        </c:when>
                                    </c:choose>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Chi tiết món ăn -->
                <div class="card">
                    <div class="card-header">
                        <h5>🍽️ Trạng thái món ăn</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th>Món ăn</th>
                                        <th>Hình ảnh</th>
                                        <th>Số lượng</th>
                                        <th>Trạng thái hiện tại</th>
                                        <th>Cập nhật trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ct" items="${dsChiTiet}">
                                        <tr>
                                            <td>${ct[3]}</td> <!-- TenMon -->
                                            <td>
                                                <img src="${ct[4]}" alt="${ct[3]}" style="width: 40px; height: 40px; object-fit: cover;">
                                            </td>
                                            <td>${ct[5]}</td> <!-- SoLuong -->
                                            <td>
                                                <c:choose>
                                                    <c:when test="${ct[7] == 0}">
                                                        <span class="badge status-doing">Dang thuc hien</span>
                                                    </c:when>
                                                    <c:when test="${ct[7] == 1}">
                                                        <span class="badge status-done">Da hoan thanh</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">Khong xac dinh</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <form method="post" action="QuanLyTrangThaiController" class="d-inline">
                                                    <input type="hidden" name="action" value="updateStatus">
                                                    <input type="hidden" name="maCTHD" value="${ct[0]}">
                                                    <input type="hidden" name="maHD" value="${hoaDonInfo[0]}">
                                                    <select name="trangThai" class="form-select form-select-sm status-select">
                                                        <option value="0" ${ct[7] == 0 ? 'selected' : ''}>Dang thuc hien</option>
                                                        <option value="1" ${ct[7] == 1 ? 'selected' : ''}>Da hoan thanh</option>
                                                    </select>
                                                    <button type="submit" class="btn btn-success btn-sm ms-2">
                                                        💾 Lưu
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty dsDonHang}">
                <c:forEach var="donHang" items="${dsDonHang}">
                    <div class="card order-card">
                        <div class="card-header">
                            <h5 class="mb-0">
                                📄 Đơn hàng #${donHang[0]} - ${donHang[4]} (${donHang[5]})
                                <small class="text-muted ms-2">
                                    <fmt:formatDate value="${donHang[2]}" pattern="dd/MM/yyyy HH:mm"/>
                                </small>
                            </h5>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-8">
                                    <p><strong>Bàn số:</strong> ${donHang[1]}</p>
                                    <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${donHang[3]}" pattern="#,##0"/>đ</p>
                                </div>
                                <div class="col-md-4 text-end">
                                    <a href="QuanLyTrangThaiController?action=viewDetail&maHD=${donHang[0]}"
                                       class="btn btn-primary btn-sm">
                                        👁️ Xem chi tiết
                                    </a>
                                </div>
                            </div>

                            <!-- Hiển thị chi tiết món ăn -->
                            <div class="mt-3">
                                <h6>🍽️ Trạng thái món ăn:</h6>
                                <div class="table-responsive">
                                    <table class="table table-sm">
                                        <thead>
                                            <tr>
                                                <th>Món ăn</th>
                                                <th>Hình ảnh</th>
                                                <th>Số lượng</th>
                                                <th>Trạng thái hiện tại</th>
                                                <th>Cập nhật trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="ct" items="${donHang[6]}">
                                                <tr>
                                                    <td>${ct[3]}</td> <!-- TenMon -->
                                                    <td>
                                                        <img src="${ct[4]}" alt="${ct[3]}" style="width: 40px; height: 40px; object-fit: cover;">
                                                    </td>
                                                    <td>${ct[5]}</td> <!-- SoLuong -->
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${ct[7] == 0}">
                                                                <span class="badge status-doing">Dang thuc hien</span>
                                                            </c:when>
                                                            <c:when test="${ct[7] == 1}">
                                                                <span class="badge status-done">Da hoan thanh</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-secondary">Không xác định</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <form method="post" action="QuanLyTrangThaiController" class="d-inline">
                                                            <input type="hidden" name="action" value="updateStatus">
                                                            <input type="hidden" name="maCTHD" value="${ct[0]}">
                                                            <select name="trangThai" class="form-select form-select-sm status-select">
                                                                <option value="0" ${ct[7] == 0 ? 'selected' : ''}>Dang thuc hien</option>
                                                                <option value="1" ${ct[7] == 1 ? 'selected' : ''}>Da hoan thanh</option>
                                                            </select>
                                                            <button type="submit" class="btn btn-success btn-sm ms-2">
                                                                💾 Lưu
                                                            </button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">
                    ${error}
                </div>
            </c:if>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>