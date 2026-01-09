<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý hóa đơn</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .status-badge {
        font-size: 0.8em;
        padding: 4px 8px;
        border-radius: 12px;
    }
    .status-unpaid {
        background-color: #fff3cd;
        color: #856404;
        border: 1px solid #ffeaa7;
    }
    .status-paid {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }
</style>
</head>

<body>
    <!-- Include admin sidebar -->
    <jsp:include page="layout/adminSidebar.jsp" />

    <div class="content-wrapper" style="margin-left: 250px; padding: 20px;">
        <div class="container-fluid">
            <h2 class="mb-4">📄 Quản lý hóa đơn</h2>

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

            <c:if test="${empty dsHoaDon}">
                <div class="alert alert-info">
                    <h5>Không có hóa đơn nào</h5>
                </div>
            </c:if>

            <c:if test="${not empty dsHoaDon}">
                <div class="card">
                    <div class="card-header">
                        <h5>📋 Danh sách hóa đơn</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Mã HD</th>
                                        <th>Khách hàng</th>
                                        <th>SĐT</th>
                                        <th>Thời gian</th>
                                        <th>Tổng tiền</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="hoaDon" items="${dsHoaDon}">
                                        <tr>
                                            <td>#${hoaDon[0]}</td>
                                            <td>${hoaDon[6]}</td>
                                            <td>${hoaDon[7]}</td>
                                            <td>
                                                <fmt:formatDate value="${hoaDon[2]}" pattern="dd/MM/yyyy HH:mm"/>
                                            </td>
                                            <td><fmt:formatNumber value="${hoaDon[4]}" pattern="#,##0"/>đ</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${hoaDon[5] == 0}">
                                                        <span class="status-badge status-unpaid">Chưa thanh toán</span>
                                                    </c:when>
                                                    <c:when test="${hoaDon[5] == 1}">
                                                        <span class="status-badge status-paid">Đã thanh toán</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">Không xác định</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <a href="QuanLyTrangThaiController?maHD=${hoaDon[0]}"
                                                   class="btn btn-primary btn-sm">
                                                    👁️ Xem chi tiết
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>