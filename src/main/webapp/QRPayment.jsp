<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

    // Lấy thông tin từ request parameters
    String maBan = request.getParameter("maBan");
    String tongTienStr = request.getParameter("tongTien");
    String maHD = request.getParameter("maHD");

    // Nếu không có trong parameters, lấy từ session
    if (maBan == null) {
        Object maBanObj = session.getAttribute("maBan");
        maBan = maBanObj != null ? maBanObj.toString() : "1";
    }

    if (tongTienStr == null) {
        Object tongTienObj = session.getAttribute("tongTienThanhToan");
        tongTienStr = tongTienObj != null ? tongTienObj.toString() : "0";
    }

    if (maHD == null) {
        Object maHDObj = session.getAttribute("maHDGoc");
        maHD = maHDObj != null ? maHDObj.toString() : null;
    }

    long tongTien = 0;
    try {
        tongTien = Long.parseLong(tongTienStr != null ? tongTienStr : "0");
    } catch (NumberFormatException e) {
        tongTien = 0;
    }

    if (maBan == null || maBan.trim().isEmpty()) {
        maBan = "1"; // Default bàn số 1
    }

    // Tạo nội dung QR code
    String qrContent = "Thanh toán bàn ăn số " + maBan + " - Số tiền: " + formatter.format(tongTien) + " VNĐ - Mã HD: " + maHD;
    String encodedContent = java.net.URLEncoder.encode(qrContent, "UTF-8");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QR Thanh toán - Nhà Bon</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .qr-payment-container {
            max-width: 600px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .qr-card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            text-align: center;
        }

        .qr-code-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 15px;
            padding: 30px;
            margin: 20px 0;
            color: white;
        }

        .qr-code {
            background: white;
            padding: 20px;
            border-radius: 10px;
            display: inline-block;
            margin: 20px 0;
        }

        .payment-details {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin: 20px 0;
        }

        .detail-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #dee2e6;
        }

        .detail-row:last-child {
            border-bottom: none;
        }

        .amount-highlight {
            font-size: 28px;
            font-weight: bold;
            color: #dc3545;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
        }

        .copy-btn {
            background: #28a745;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 12px;
        }

        .copy-btn:hover {
            background: #218838;
        }

        .instructions {
            background: #fff3cd;
            border: 1px solid #ffeaa7;
            border-radius: 10px;
            padding: 20px;
            margin: 20px 0;
        }

        .instruction-step {
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

        .back-btn {
            background: linear-gradient(135deg, #6c757d, #495057);
            border: none;
            padding: 12px 25px;
            border-radius: 8px;
            color: white;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
        }

        .back-btn:hover {
            background: linear-gradient(135deg, #5a6268, #343a40);
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container qr-payment-container">
        <div class="qr-card">
            <h2 class="mb-4">
                <i class="fas fa-qrcode text-primary"></i>
                Thanh toán qua QR Code
            </h2>

            <!-- QR Code Section -->
            <div class="qr-code-section">
                <h4 class="mb-3">Quét mã để thanh toán</h4>

                <div class="qr-code">
                    <img src="https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=<%=encodedContent%>&bgcolor=ffffff&color=000000"
                         alt="QR Code thanh toán" class="img-fluid">
                </div>

                <p class="mb-0">Sử dụng ứng dụng ngân hàng hoặc ví điện tử để quét mã</p>
            </div>

            <!-- Payment Details -->
            <div class="payment-details">
                <h5 class="mb-3">
                    <i class="fas fa-info-circle text-primary"></i>
                    Thông tin thanh toán
                </h5>

                <div class="detail-row">
                    <span><strong>Người nhận:</strong></span>
                    <span>Nguyễn Thị Thanh Ngân</span>
                </div>

                <div class="detail-row">
                    <span><strong>Ngân hàng:</strong></span>
                    <span>[Tên ngân hàng của bạn]</span>
                </div>

                <div class="detail-row">
                    <span><strong>Số tài khoản:</strong></span>
                    <div>
                        <span>0388197765</span>
                        <button class="copy-btn ms-2" onclick="copyToClipboard('[Số tài khoản của bạn]')">
                            <i class="fas fa-copy"></i> Copy
                        </button>
                    </div>
                </div>

                <div class="detail-row">
                    <span><strong>Số tiền:</strong></span>
                    <span class="amount-highlight"><%=formatter.format(tongTien)%> VNĐ</span>
                </div>

                <div class="detail-row">
                    <span><strong>Nội dung:</strong></span>
                    <div style="max-width: 200px;">
                        <span style="word-break: break-word;">Thanh toán bàn ăn số <%=maBan%></span>
                        <button class="copy-btn ms-2" onclick="copyToClipboard('Thanh toán bàn ăn số <%=maBan%>')">
                            <i class="fas fa-copy"></i> Copy
                        </button>
                    </div>
                </div>

                <div class="detail-row">
                    <span><strong>Mã đơn hàng:</strong></span>
                    <span>#<%=maHD != null ? maHD : "N/A"%></span>
                </div>
            </div>

            <div class="alert alert-info mt-3">
                <i class="fas fa-info-circle"></i>
                <strong>Lưu ý:</strong> Sau khi chuyển khoản, nhân viên sẽ kiểm tra và xác nhận thanh toán.
                Bạn sẽ nhận được thông báo khi thanh toán được duyệt.
            </div>

            <!-- Instructions -->
            <div class="instructions">
                <h6 class="text-center mb-3">
                    <i class="fas fa-list-check text-warning"></i>
                    Hướng dẫn thanh toán
                </h6>

                <div class="instruction-step">
                    <div class="step-number">1</div>
                    <div>Mở ứng dụng ngân hàng hoặc ví điện tử trên điện thoại</div>
                </div>

                <div class="instruction-step">
                    <div class="step-number">2</div>
                    <div>Chọn chức năng "Quét mã QR" hoặc "Pay QR"</div>
                </div>

                <div class="instruction-step">
                    <div class="step-number">3</div>
                    <div>Quét mã QR ở trên và xác nhận thanh toán</div>
                </div>

                <div class="instruction-step">
                    <div class="step-number">4</div>
                    <div>Kiểm tra lại thông tin người nhận và số tiền trước khi chuyển</div>
                </div>
            </div>

            <!-- Back Button -->
            <a href="TheoDoiDonHangController" class="back-btn">
                <i class="fas fa-arrow-left"></i>
                Quay lại trang theo dõi đơn hàng
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function copyToClipboard(text) {
            navigator.clipboard.writeText(text).then(function() {
                // Hiển thị thông báo thành công
                const btn = event.target.closest('.copy-btn');
                const originalText = btn.innerHTML;
                btn.innerHTML = '<i class="fas fa-check"></i> Đã copy';
                btn.style.background = '#28a745';

                setTimeout(function() {
                    btn.innerHTML = originalText;
                    btn.style.background = '#28a745';
                }, 2000);
            }, function(err) {
                console.error('Không thể copy: ', err);
                alert('Không thể sao chép. Vui lòng copy thủ công.');
            });
        }

        // Auto refresh để kiểm tra thanh toán (có thể implement sau)
        // setInterval(function() {
        //     // Kiểm tra trạng thái thanh toán
        // }, 5000);
    </script>
</body>
</html>
