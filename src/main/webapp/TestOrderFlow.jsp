<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Test Luồng Đặt hàng - Nhà Bon</title>
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

        .flow-diagram {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin: 40px 0;
            flex-wrap: wrap;
        }

        .flow-step {
            text-align: center;
            flex: 1;
            min-width: 150px;
            margin: 10px;
        }

        .step-icon {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 15px;
            font-size: 24px;
            color: white;
        }

        .step-1 { background: linear-gradient(135deg, #ff8c00, #ff6a00); }
        .step-2 { background: linear-gradient(135deg, #28a745, #20c997); }
        .step-3 { background: linear-gradient(135deg, #007bff, #0056b3); }
        .step-4 { background: linear-gradient(135deg, #dc3545, #c82333); }

        .arrow {
            font-size: 24px;
            color: #6c757d;
            margin: 0 10px;
        }

        .status-indicator {
            display: inline-block;
            width: 12px;
            height: 12px;
            border-radius: 50%;
            margin-right: 8px;
        }

        .status-pending { background: #ffc107; }
        .status-eating { background: #17a2b8; }
        .status-paying { background: #fd7e14; }
        .status-paid { background: #28a745; }

        .test-scenarios {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }

        .scenario-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            border-left: 4px solid #ff8c00;
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

        .status-explanation {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin: 20px 0;
        }

        .status-item {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container test-container">
        <div class="test-card">
            <h2 class="text-center mb-4">
                <i class="fas fa-route text-primary"></i>
                Test Luồng Đặt hàng Mới
            </h2>

            <div class="alert alert-success">
                <h5><i class="fas fa-check-circle"></i> Luồng mới đã được cập nhật:</h5>
                <ul class="mb-0">
                    <li><strong>Đặt món</strong> → Lưu vào DB với trạng thái "Đã đặt món"</li>
                    <li><strong>Theo dõi</strong> → Trạng thái từng món (Đang làm/Đã xong)</li>
                    <li><strong>Thanh toán</strong> → Sau khi ăn xong (có món đã hoàn thành)</li>
                    <li><strong>Mã bàn</strong> → Lưu trong session (mặc định bàn 1)</li>
                    <li><strong>Thông tin</strong> → Lấy từ tài khoản đăng nhập</li>
                </ul>
            </div>

            <!-- Flow Diagram -->
            <div class="flow-diagram">
                <div class="flow-step">
                    <div class="step-icon step-1">
                        <i class="fas fa-shopping-cart"></i>
                    </div>
                    <h5>1. Đặt món</h5>
                    <p class="text-muted small">Chọn món ăn và gửi đơn</p>
                </div>

                <div class="arrow">→</div>

                <div class="flow-step">
                    <div class="step-icon step-2">
                        <i class="fas fa-utensils"></i>
                    </div>
                    <h5>2. Chế biến</h5>
                    <p class="text-muted small">Theo dõi trạng thái từng món</p>
                </div>

                <div class="arrow">→</div>

                <div class="flow-step">
                    <div class="step-icon step-3">
                        <i class="fas fa-credit-card"></i>
                    </div>
                    <h5>3. Thanh toán</h5>
                    <p class="text-muted small">Sau khi ăn xong</p>
                </div>

                <div class="arrow">→</div>

                <div class="flow-step">
                    <div class="step-icon step-4">
                        <i class="fas fa-check-circle"></i>
                    </div>
                    <h5>4. Hoàn thành</h5>
                    <p class="text-muted small">Thanh toán thành công</p>
                </div>
            </div>

            <!-- Status Explanation -->
            <div class="status-explanation">
                <h5><i class="fas fa-info-circle text-info"></i> Giải thích trạng thái đơn hàng:</h5>
                <div class="row">
                    <div class="col-md-6">
                        <div class="status-item">
                            <span class="status-indicator status-pending"></span>
                            <strong>0:</strong> Chưa thanh toán
                        </div>
                        <div class="status-item">
                            <span class="status-indicator status-eating"></span>
                            <strong>3:</strong> Đang ăn
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="status-item">
                            <span class="status-indicator status-paying"></span>
                            <strong>2:</strong> Chờ xác nhận thanh toán
                        </div>
                        <div class="status-item">
                            <span class="status-indicator status-paid"></span>
                            <strong>1:</strong> Đã thanh toán
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Test Scenarios -->
        <div class="test-scenarios">
            <div class="scenario-card">
                <h5><i class="fas fa-user"></i> Kịch bản Khách hàng</h5>
                <ol>
                    <li>Đăng nhập tài khoản</li>
                    <li>Chọn món ăn</li>
                    <li>Đến trang DonHang</li>
                    <li>Chọn món và đặt hàng</li>
                    <li>Theo dõi trạng thái từng món (Đang làm/Đã xong)</li>
                    <li>Sau khi có món đã xong → Thanh toán</li>
                </ol>
                <a href="TrangChuController" class="btn btn-sm btn-outline-primary">Bắt đầu test</a>
            </div>

            <div class="scenario-card">
                <h5><i class="fas fa-utensils"></i> Kịch bản Đang ăn</h5>
                <ol>
                    <li>Đơn hàng hiển thị trạng thái "Đang ăn"</li>
                    <li>Khách hàng có thể xem chi tiết món</li>
                    <li>Giỏ hàng vẫn giữ nguyên (có thể đặt thêm)</li>
                    <li>Hiển thị nút "Thanh toán ngay"</li>
                    <li>Click để chuyển sang thanh toán</li>
                </ol>
                <a href="TheoDoiDonHangController" class="btn btn-sm btn-outline-info">Xem trạng thái</a>
            </div>

            <div class="scenario-card">
                <h5><i class="fas fa-credit-card"></i> Kịch bản Thanh toán</h5>
                <ol>
                    <li>Hiển thị thông tin từ tài khoản</li>
                    <li>Chọn phương thức: Tiền mặt hoặc QR</li>
                    <li>Không cần nhập thông tin khách hàng</li>
                    <li>Xử lý thanh toán tương ứng</li>
                    <li>Admin xác nhận (nếu QR)</li>
                </ol>
                <a href="PaymentController" class="btn btn-sm btn-outline-success">Test thanh toán</a>
            </div>

            <div class="scenario-card">
                <h5><i class="fas fa-utensils"></i> Kịch bản Bếp</h5>
                <ol>
                    <li>Vào "Trạng thái món ăn"</li>
                    <li>Xem món đang chế biến</li>
                    <li>Cập nhật trạng thái "Hoàn thành"</li>
                    <li>Theo dõi tiến độ từng món</li>
                    <li>Thông báo khách hàng khi xong</li>
                </ol>
                <a href="AdminDishStatusController" class="btn btn-sm btn-outline-warning">Quản lý bếp</a>
            </div>

            <div class="scenario-card">
                <h5><i class="fas fa-cog"></i> Kịch bản Thu ngân</h5>
                <ol>
                    <li>Xem đơn hàng "Đang ăn"</li>
                    <li>Theo dõi đơn hàng "Chờ xác nhận"</li>
                    <li>Xác nhận thanh toán QR</li>
                    <li>Xem thống kê thanh toán</li>
                    <li>Quản lý trạng thái đơn hàng</li>
                </ol>
                <a href="AdminPaymentController" class="btn btn-sm btn-outline-danger">Thu ngân</a>
            </div>
        </div>

        <div class="test-card">
            <h5><i class="fas fa-code"></i> Thay đổi kỹ thuật</h5>
            <div class="row">
                <div class="col-md-6">
                    <h6>Backend Changes:</h6>
                    <ul>
                        <li>TrangChuController: Set session.setAttribute("maBan", 1L)</li>
                        <li>DonHangController: Lấy maBan từ session thay vì hardcode</li>
                        <li>TheoDoiDonHangController: Sử dụng mã bàn từ session</li>
                        <li>PaymentController: Lấy mã bàn từ session cho QR payment</li>
                        <li>HoaDon: Liên kết với mã bàn từ session</li>
                    </ul>
                </div>
                <div class="col-md-6">
                    <h6>Frontend Changes:</h6>
                    <ul>
                        <li>ThanhToan.jsp: Hiển thị info tự động</li>
                        <li>TheoDoiDonHang.jsp: Trạng thái "Đang ăn"</li>
                        <li>adminPayment.jsp: Quản lý đơn đang ăn</li>
                        <li>Responsive design & UX cải thiện</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
