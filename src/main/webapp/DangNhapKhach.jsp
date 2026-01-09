<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String err = (String) request.getAttribute("err");
    String msg = (String) request.getAttribute("msg");
    Boolean yeuCauTen = (Boolean) request.getAttribute("yeuCauTen");
    String sdtGiuLai = (String) request.getAttribute("sdt");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập khách hàng</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    body {
        background: linear-gradient(135deg, #ff9800, #ff5722);
        min-height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .login-card {
        width: 420px;
        background: #fff;
        border-radius: 16px;
        box-shadow: 0 10px 30px rgba(0,0,0,.2);
        padding: 30px;
    }

    .brand {
        font-weight: bold;
        color: #ff5722;
    }
</style>
</head>

<body>

<div class="login-card">
    <h4 class="text-center brand mb-1">🍲 Ốc Nhà Bon</h4>
    <p class="text-center text-muted mb-4">
        <% if (yeuCauTen != null && yeuCauTen) { %>
            Đăng ký tài khoản mới
        <% } else { %>
            Đăng nhập khách hàng
        <% } %>
    </p>

    <form action="DangNhapKhachController" method="post">

        <!-- SỐ ĐIỆN THOẠI -->
        <div class="mb-3">
            <label class="form-label fw-semibold">Số điện thoại</label>
            <input type="tel" name="sdt"
                   class="form-control"
                   placeholder="Nhập số điện thoại"
                   value="<%= sdtGiuLai != null ? sdtGiuLai : "" %>"
                   required>
        </div>

        <!-- CHỈ HIỆN KHI CẦN ĐĂNG KÝ -->
        <% if (yeuCauTen != null && yeuCauTen) { %>
        <div class="mb-3">
            <label class="form-label fw-semibold">Tên khách hàng</label>
            <input type="text" name="tenKH"
                   class="form-control"
                   placeholder="Nhập tên của bạn"
                   required>
        </div>
        <% } %>

        <!-- THÔNG BÁO -->
        <% if (msg != null) { %>
        <div class="alert alert-info text-center py-2">
            <%= msg %>
        </div>
        <% } %>

        <% if (err != null) { %>
        <div class="alert alert-danger text-center py-2">
            <%= err %>
        </div>
        <% } %>

        <div class="d-grid mt-4">
            <button class="btn btn-danger btn-lg">
                <% if (yeuCauTen != null && yeuCauTen) { %>
                    ✅ Đăng ký & Đặt món
                <% } else { %>
                    🍽️ Tiếp tục đặt món
                <% } %>
            </button>
        </div>

        <div class="text-center mt-3">
            <small class="text-muted">
                <% if (yeuCauTen != null && yeuCauTen) { %>
                    Nhập tên để tạo tài khoản mới
                <% } else { %>
                    Đăng nhập nhanh – không cần mật khẩu
                <% } %>
            </small>
            <% if (yeuCauTen != null && yeuCauTen) { %>
            <br>
            <small><a href="DangNhapKhachController" class="text-decoration-none">← Thay đổi số điện thoại</a></small>
            <% } %>
        </div>

    </form>
</div>

</body>
</html>
