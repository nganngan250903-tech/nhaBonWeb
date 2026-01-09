<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Boolean showCaptcha = (Boolean) request.getAttribute("showCaptcha");
    String captchaCode = (String) session.getAttribute("captcha");
    String err = (String) request.getAttribute("err");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng nhập hệ thống</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    body {
        background: #f5f6fa;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .login-box {
        width: 420px;
        padding: 30px;
        background: #fff;
        border-radius: 12px;
        box-shadow: 0 0 12px rgba(0,0,0,.1);
    }
    .captcha-box {
        background: #eee;
        padding: 6px 14px;
        font-weight: bold;
        letter-spacing: 3px;
        display: inline-block;
        margin-top: 6px;
    }
</style>
</head>

<body>

<div class="login-box">
    <h4 class="text-center mb-3">🍲 Ốc Nhà Bon</h4>
    <p class="text-center text-muted">Đăng nhập hệ thống</p>

    <form action="DangNhapController" method="post">

        

        <div class="mb-3">
            <label class="form-label">Tên đăng nhập</label>
            <input type="text" name="txtun" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Mật khẩu</label>
            <input type="password" name="txtpass" class="form-control" required>
        </div>

        <% if (showCaptcha != null && showCaptcha) { %>
        <div class="mb-3">
            <label class="form-label">Mã xác nhận</label>
            <input type="text" name="txtcaptcha" class="form-control" required>
            <div class="captcha-box"><%= captchaCode %></div>
        </div>
        <% } %>

        <% if (err != null) { %>
            <div class="alert alert-danger py-2"><%= err %></div>
        <% } %>

        <div class="d-grid gap-2">
            <button class="btn btn-danger"
                    onclick="setType('AD')">
                🔐 Đăng nhập Admin
            </button>

            <button class="btn btn-primary"
                    onclick="setType('NV')">
                👤 Đăng nhập Nhân viên
            </button>
        </div>

        <div class="text-center mt-3">
            <a href="dangkiController">➕ Đăng ký tài khoản</a>
        </div>

    </form>
</div>

<script>
    function setType(t){
        document.getElementById("loginType").value = t;
    }
</script>

</body>
</html>
