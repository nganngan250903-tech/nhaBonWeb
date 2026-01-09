<%@page import="Model.GioHang.GioHangBo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🍜 ỐC NHÀ BON - MENU</title>

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<!-- ================= NAVBAR ================= -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand fw-bold" href="TrangChuController">🍜 ỐC NHÀ BON</a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="menu">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link active" href="TrangChuController">Menu</a></li>
        <li class="nav-item"><a class="nav-link" href="GioiThieuController">Giới thiệu</a></li>
        <li class="nav-item"><a class="nav-link" href="DonHangController">Đơn hàng</a></li>
        <li class="nav-item"><a class="nav-link" href="ThanhToanController">Thanh toán</a></li>
        <li class="nav-item"><a class="nav-link" href="LichSuController">Lịch sử</a></li>
      </ul>

      <!-- GIỎ HÀNG -->
<a href="DonHangController" class="position-relative text-white text-decoration-none">
    🛒
    <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
        <c:choose>
            <c:when test="${sessionScope.gio != null}">
                ${sessionScope.gio.tongSoLuong()}
            </c:when>
            <c:otherwise>0</c:otherwise>
        </c:choose>
    </span>
</a>


    </div>
  </div>
</nav>

<!-- ================= MAIN ================= -->
<div class="container mt-4">
  <div class="row">

    <!-- ===== DANH MỤC ===== -->
    <div class="col-md-3">
      <h5 class="mb-3">📂 Danh mục</h5>
      <ul class="list-group">
        <li class="list-group-item">
          <a href="TrangChuController" class="fw-bold text-decoration-none">
            🔹 Tất cả món
          </a>
        </li>

        <c:forEach var="dm" items="${dsDanhMuc}">
          <li class="list-group-item">
            <a href="TrangChuController?maDM=${dm.maDM}" class="text-decoration-none">
              ${dm.tenDM}
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>

    <!-- ===== MENU MÓN ĂN ===== -->
    <div class="col-md-9">

      <!-- 🔍 TÌM KIẾM -->
      <form class="d-flex mb-4" action="TrangChuController" method="get">
        <input class="form-control me-2" type="text" name="txtTim"
               placeholder="Tìm món ăn...">
        <button class="btn btn-outline-success">Tìm</button>
      </form>

      <div class="row">

        <!-- DANH SÁCH MÓN -->
        <c:forEach var="m" items="${dsMon}">
          <div class="col-md-4 mb-4">
            <div class="card h-100 shadow-sm">

              <!-- ẢNH -->
              <img src="${pageContext.request.contextPath}/${m.hinhAnh}"
                   class="card-img-top"
                   style="height:200px; object-fit:cover;">

              <div class="card-body">
                <!-- TÊN -->
                <h5 class="card-title">
                  ${m.tenMon}

                  <!-- BESTSELLER -->
                  <c:if test="${m.trangThaiMon == 1}">
                    <span class="badge bg-warning text-dark ms-1">⭐ Bestseller</span>
                  </c:if>
                </h5>

                <!-- MÔ TẢ -->
                <p class="card-text small text-muted">${m.moTa}</p>

                <!-- GIÁ -->
                <p class="text-danger fw-bold">
                  ${m.gia} đ
                </p>

                <!-- SỐ LƯỢNG -->
                <c:choose>
                  <c:when test="${m.soLuong == 0}">
                    <span class="badge bg-danger">Hết hàng</span>
                  </c:when>
                  <c:otherwise>
                    <span class="badge bg-success">
                      Còn ${m.soLuong}
                    </span>
                  </c:otherwise>
                </c:choose>
              </div>

              <!-- NÚT ĐẶT -->
              <div class="card-footer bg-white text-center">
                <c:if test="${m.soLuong > 0}">
                  <a href="GioHangController?maMon=${m.maMon}&action=add"
                     class="btn btn-success btn-sm">
                    ➕ Đặt món
                  </a>
                </c:if>
              </div>

            </div>
          </div>
        </c:forEach>

        <!-- KHÔNG CÓ MÓN -->
        <c:if test="${empty dsMon}">
          <div class="col-12">
            <div class="alert alert-warning">
              Không tìm thấy món ăn phù hợp.
            </div>
          </div>
        </c:if>

      </div>
    </div>

  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
