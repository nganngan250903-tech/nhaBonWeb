<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
    Long maHD = (Long) request.getAttribute("maHD");
    Long tongTien = (Long) request.getAttribute("tongTien");
    String paymentMethod = (String) request.getAttribute("paymentMethod");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán thành công - Nhà Bon</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .success-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 0 20px;
            text-align: center;
        }

        .success-card {
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }

        .success-icon {
            width: 100px;
            height: 100px;
            background: linear-gradient(135deg, #28a745, #20c997);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 30px;
            font-size: 50px;
            color: white;
        }

        .success-title {
            color: #28a745;
            font-size: 32px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .order-info {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin: 30px 0;
        }

        .order-number {
            font-size: 24px;
            font-weight: bold;
            color: #ff8c00;
            margin-bottom: 10px;
        }

        .order-amount {
            font-size: 28px;
            font-weight: bold;
            color: #28a745;
        }

        .next-steps {
            background: #e8f4f8;
            border-radius: 10px;
            padding: 20px;
            margin: 30px 0;
            text-align: left;
        }

        .step {
            display: flex;
            align-items: flex-start;
            margin-bottom: 15px;
        }

        .step-number {
            width: 30px;
            height: 30px;
            background: #ff8c00;
            color: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            margin-right: 15px;
            flex-shrink: 0;
        }

        .btn-home {
            background: linear-gradient(135deg, #ff8c00, #ff6a00);
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 8px;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
        }

        .btn-home:hover {
            background: linear-gradient(135deg, #ff6a00, #ff4500);
            text-decoration: none;
            color: white;
            transform: translateY(-2px);
        }

        .contact-info {
            background: #fff3cd;
            border-radius: 10px;
            padding: 20px;
            margin: 30px 0;
        }

        @keyframes checkmark {
            0% { transform: scale(0); }
            50% { transform: scale(1.2); }
            100% { transform: scale(1); }
        }

        .checkmark {
            animation: checkmark 0.6s ease-in-out;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container success-container">
        <div class="success-card">
            <div class="success-icon checkmark">
                <i class="fas fa-check"></i>
            </div>

            <h1 class="success-title">Thanh toán thành công!</h1>

            <p class="lead text-muted mb-4">
                Cảm ơn bạn đã đặt hàng tại Nhà Bon.<br>
                Đơn hàng của bạn đã được xác nhận và đang được xử lý.
            </p>

            <div class="order-info">
                <div class="order-number">
                    Mã đơn hàng: #<%=maHD != null ? String.format("%06d", maHD) : "N/A"%>
                </div>
                <div class="order-amount">
                    <%=formatter.format(tongTien != null ? tongTien : 0)%> VNĐ
                </div>
                <div class="mt-3">
                    <small class="text-muted">
                        Phương thức thanh toán:
                        <% if ("cash".equals(paymentMethod)) { %>
                            <i class="fas fa-money-bill-wave"></i> Tiền mặt
                        <% } else if ("card".equals(paymentMethod)) { %>
                            <i class="fas fa-credit-card"></i> Thẻ tín dụng
                        <% } else if ("momo".equals(paymentMethod)) { %>
                            <i class="fas fa-mobile-alt"></i> Ví MoMo
                        <% } else if ("zalopay".equals(paymentMethod)) { %>
                            <i class="fas fa-wallet"></i> ZaloPay
                        <% } else { %>
                            N/A
                        <% } %>
                    </small>
                </div>
            </div>

            <div class="next-steps">
                <h5 class="mb-3"><i class="fas fa-list-check text-primary"></i> Các bước tiếp theo:</h5>

                <div class="step">
                    <div class="step-number">1</div>
                    <div>
                        <strong>Nhà bếp đang chuẩn bị món ăn</strong><br>
                        <small class="text-muted">Thời gian chuẩn bị khoảng 15-20 phút</small>
                    </div>
                </div>

                <div class="step">
                    <div class="step-number">2</div>
                    <div>
                        <strong>Giao hàng đến địa chỉ</strong><br>
                        <small class="text-muted">Shipper sẽ liên hệ trước khi giao</small>
                    </div>
                </div>

                <div class="step">
                    <div class="step-number">3</div>
                    <div>
                        <strong>Thanh toán và nhận hàng</strong><br>
                        <small class="text-muted">
                            <% if ("cash".equals(paymentMethod)) { %>
                                Thanh toán bằng tiền mặt khi nhận hàng
                            <% } else { %>
                                Đã thanh toán online, chỉ cần nhận hàng
                            <% } %>
                        </small>
                    </div>
                </div>
            </div>

            <div class="contact-info">
                <h6><i class="fas fa-info-circle text-warning"></i> Cần hỗ trợ?</h6>
                <p class="mb-1"><i class="fas fa-phone"></i> Hotline: 1900 XXX XXX</p>
                <p class="mb-0"><i class="fas fa-envelope"></i> Email: support@nhabon.com</p>
            </div>

            <a href="TrangChuController" class="btn btn-primary btn-home">
                <i class="fas fa-home"></i> Về trang chủ
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
