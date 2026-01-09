<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký tài khoản</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    body {
        background: #f5f6fa;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .register-box {
        width: 450px;
        padding: 30px;
        background: white;
        border-radius: 12px;
        box-shadow: 0px 0px 12px rgba(0,0,0,0.1);
    }
</style>
</head>

<body>

<div class="register-box">

    <h3 class="text-center mb-4 text-success">📝 Đăng ký tài khoản</h3>

    <form action="dangkiController" method="post">

        <div class="mb-3">
            <label class="form-label">Tên đăng nhập</label>
            <input type="text" name="txtun" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Mật khẩu</label>
            <input type="password" name="pass" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Họ và tên</label>
            <input type="text" name="hoten" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Địa chỉ</label>
            <input type="text" name="diachi" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Số điện thoại</label>
            <input type="text" name="sodt" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control">
        </div>
	<<!-- Mặc định quyền = Nhân viên -->
<input type="hidden" name="maQuyen" value="2">

        <button class="btn btn-success w-100 mt-2">Đăng ký</button>

        <div class="text-center mt-3">
            <span>Đã có tài khoản?</span>
            <a href="dangnhapController" class="text-decoration-none fw-semibold">
                Đăng nhập
            </a>
        </div>

    </form>

</div>

</body>
</html>
S