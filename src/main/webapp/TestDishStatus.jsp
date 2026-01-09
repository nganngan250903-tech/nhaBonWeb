<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Theo dõi trạng thái món ăn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .test-container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 0 20px;
        }

        .test-card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }

        .status-demo {
            display: flex;
            justify-content: space-around;
            margin: 30px 0;
            flex-wrap: wrap;
        }

        .status-example {
            text-align: center;
            padding: 20px;
            border-radius: 10px;
            margin: 10px;
            min-width: 200px;
        }

        .status-doing {
            background: linear-gradient(135deg, #fff3cd, #ffeaa7);
            border: 2px solid #ffc107;
        }

        .status-done {
            background: linear-gradient(135deg, #d4edda, #c3e6cb);
            border: 2px solid #28a745;
        }

        .status-order {
            background: linear-gradient(135deg, #d1ecf1, #bee5eb);
            border: 2px solid #17a2b8;
        }

        .status-icon {
            font-size: 48px;
            margin-bottom: 15px;
        }

        .btn-test {
            background: linear-gradient(135deg, #ff8c00, #ff6a00);
            border: none;
            padding: 12px 25px;
            border-radius: 8px;
            color: white;
            text-decoration: none;
            display: inline-block;
            margin: 10px;
            transition: transform 0.2s;
        }

        .btn-test:hover {
            background: linear-gradient(135deg, #ff6a00, #ff4500);
            color: white;
            text-decoration: none;
            transform: translateY(-2px);
        }

        .flow-explanation {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin: 20px 0;
        }

        .feature-highlight {
            background: linear-gradient(135deg, #e8f4f8, #d1ecf1);
            border-left: 4px solid #17a2b8;
            padding: 15px;
            margin: 10px 0;
            border-radius: 0 8px 8px 0;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container test-container">
        <div class="test-card">
            <h2 class="text-center mb-4">
                <i class="fas fa-eye text-primary"></i>
                Test Theo dõi trạng thái món ăn
            </h2>

            <div class="alert alert-success">
                <h5><i class="fas fa-check-circle"></i> Tính năng mới đã hoàn thành!</h5>
                <p>Khách hàng có thể theo dõi trạng thái từng món ăn thay vì chỉ trạng thái tổng thể đơn hàng.</p>
            </div>

            <!-- Status Examples -->
            <div class="status-demo">
                <div class="status-example status-order">
                    <div class="status-icon text-info">
                        <i class="fas fa-shopping-cart"></i>
                    </div>
                    <h5>Đã đặt món</h5>
                    <p class="mb-0">Món vừa được đặt, chờ bếp xử lý</p>
                </div>

                <div class="status-example status-doing">
                    <div class="status-icon text-warning">
                        <i class="fas fa-spinner fa-spin"></i>
                    </div>
                    <h5>Đang làm</h5>
                    <p class="mb-0">Bếp đang chế biến món ăn</p>
                </div>

                <div class="status-example status-done">
                    <div class="status-icon text-success">
                        <i class="fas fa-check-circle"></i>
                    </div>
                    <h5>Đã xong</h5>
                    <p class="mb-0">Món đã hoàn thành, sẵn sàng phục vụ</p>
                </div>
            </div>

            <!-- Feature Highlights -->
            <div class="flow-explanation">
                <h5><i class="fas fa-lightbulb text-warning"></i> Điểm nổi bật:</h5>

                <div class="feature-highlight">
                    <strong>🎯 Theo dõi chi tiết:</strong> Khách hàng biết chính xác món nào đang làm, món nào đã xong
                </div>

                <div class="feature-highlight">
                    <strong>👨‍🍳 Quản lý bếp:</strong> Admin có giao diện riêng để cập nhật trạng thái từng món
                </div>

                <div class="feature-highlight">
                    <strong>💳 Thanh toán thông minh:</strong> Chỉ cho phép thanh toán khi có món đã hoàn thành
                </div>

                <div class="feature-highlight">
                    <strong>📱 Real-time updates:</strong> Tự động refresh để cập nhật trạng thái mới nhất
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="test-card">
                    <h5><i class="fas fa-user"></i> Test từ phía Khách hàng</h5>
                    <ol>
                        <li>Đặt món ăn</li>
                        <li>Vào trang "Theo dõi đơn hàng"</li>
                        <li>Xem trạng thái từng món</li>
                        <li>Chờ món hoàn thành để thanh toán</li>
                    </ol>
                    <a href="TheoDoiDonHangController" class="btn btn-sm btn-outline-primary">Theo dõi đơn hàng</a>
                </div>
            </div>

            <div class="col-md-6">
                <div class="test-card">
                    <h5><i class="fas fa-cog"></i> Test từ phía Admin</h5>
                    <ol>
                        <li>Đăng nhập admin</li>
                        <li>Vào "Trạng thái món ăn"</li>
                        <li>Xem danh sách món đang làm</li>
                        <li>Click "Hoàn thành" khi xong</li>
                        <li>Kiểm tra cập nhật real-time</li>
                    </ol>
                    <a href="AdminDishStatusController" class="btn btn-sm btn-outline-success">Quản lý món ăn</a>
                </div>
            </div>
        </div>

        <div class="test-card">
            <h5><i class="fas fa-code"></i> Chi tiết kỹ thuật</h5>
            <div class="row">
                <div class="col-md-6">
                    <h6>Database:</h6>
                    <ul>
                        <li>Session: maBan = 1 (mặc định bàn số 1)</li>
                        <li>HoaDon: Liên kết với mã bàn từ session</li>
                        <li>ChiTietHoaDon.TrangThai: 0=Đang làm, 1=Đã xong</li>
                        <li>HoaDon.TrangThai: 3=Đã đặt món</li>
                        <li>JOIN queries để lấy thông tin món ăn theo bàn</li>
                    </ul>
                </div>
                <div class="col-md-6">
                    <h6>Logic:</h6>
                    <ul>
                        <li>Session.getAttribute("maBan"): Mã bàn mặc định</li>
                        <li>hasProcessingItems: Có món đang làm</li>
                        <li>hasCompletedItems: Có món đã xong</li>
                        <li>Chỉ thanh toán khi có món hoàn thành</li>
                        <li>Admin cập nhật trạng thái từng món theo bàn</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
