<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test Thanh toán - Nhà Bon</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4>Test Flow Thanh toán</h4>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-info">
                            <strong>Hướng dẫn test:</strong><br>
                            1. Thêm món ăn vào giỏ hàng<br>
                            2. Đến trang thanh toán<br>
                            3. Điền thông tin và chọn phương thức<br>
                            4. Xác nhận đơn hàng<br>
                            5. Hoàn tất thanh toán
                        </div>

                        <div class="d-grid gap-2">
                            <a href="TrangChuController" class="btn btn-primary">
                                <i class="fas fa-home"></i> Về trang chủ
                            </a>
                            <a href="PaymentController" class="btn btn-success">
                                <i class="fas fa-credit-card"></i> Trang thanh toán
                            </a>
                            <a href="AdminHomeController" class="btn btn-warning">
                                <i class="fas fa-cog"></i> Admin
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
