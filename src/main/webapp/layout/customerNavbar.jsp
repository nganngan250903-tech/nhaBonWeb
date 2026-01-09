<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- ================= NAVBAR ================= -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand fw-bold" href="TrangChuController">🍜 ỐC NHÀ BON</a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menu">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="menu">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link ${activeMenu == 'trangchu' ? 'active' : ''}" href="TrangChuController">Menu</a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${activeMenu == 'gioithieu' ? 'active' : ''}" href="GioiThieuController">Giới thiệu</a>
        </li>
        <c:if test="${sessionScope.khachhang != null}">
          <li class="nav-item">
            <a class="nav-link ${activeMenu == 'donhang' ? 'active' : ''}" href="DonHangController">Đơn hàng</a>
          </li>
          <li class="nav-item">
            <a class="nav-link ${activeMenu == 'theodoi' ? 'active' : ''}" href="TheoDoiDonHangController">Theo dõi đơn</a>
          </li>
          <li class="nav-item">
            <a class="nav-link ${activeMenu == 'thanhtoan' ? 'active' : ''}" href="ThanhToanController">Thanh toán</a>
          </li>
          <li class="nav-item">
            <a class="nav-link ${activeMenu == 'lichsu' ? 'active' : ''}" href="LichSuController">Lịch sử</a>
          </li>
        </c:if>
      </ul>

      <!-- GIỎ HÀNG + ĐĂNG NHẬP -->
      <c:if test="${sessionScope.khachhang != null}">
        <span class="text-light me-3">Xin chào ${sessionScope.khachhang.tenKH}</span>
        <a href="DangXuatKhachController" class="btn btn-outline-light btn-sm">Đăng xuất</a>
      </c:if>

      <!-- GIỎ HÀNG -->
      <c:if test="${sessionScope.khachhang != null}">
        <a href="DonHangController" class="position-relative text-white text-decoration-none ms-3">
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
      </c:if>

      <!-- NẾU CHƯA ĐĂNG NHẬP -->
      <c:if test="${sessionScope.khachhang == null}">
        <li class="nav-item me-3" style="list-style: none;">
          <a href="DangNhapKhachController" class="btn btn-outline-light btn-sm">
            🔐 Đăng nhập
          </a>
        </li>
      </c:if>
    </div>
  </div>
</nav>