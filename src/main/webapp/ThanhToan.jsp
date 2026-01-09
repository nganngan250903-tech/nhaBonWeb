<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="Model.GioHang.GioHangBo"%>
<%@page import="Model.GioHang.GioHang"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
    GioHangBo gioHang = (GioHangBo) request.getAttribute("gioHang");
    Long tongTien = (Long) request.getAttribute("tongTien");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>🍜 ỐC NHÀ BON - Thanh toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .payment-container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .order-summary {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
        }

        .payment-form {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .payment-method {
            border: 2px solid #e9ecef;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .payment-method:hover {
            border-color: #ff8c00;
        }

        .payment-method.selected {
            border-color: #ff8c00;
            background-color: #fff3cd;
        }

        .payment-method input[type="radio"] {
            display: none;
        }

        .payment-method-icon {
            font-size: 24px;
            color: #ff8c00;
            margin-right: 15px;
        }

        .total-amount {
            font-size: 28px;
            font-weight: bold;
            color: #ff8c00;
            text-align: center;
            margin: 20px 0;
        }

        .btn-payment {
            background: linear-gradient(135deg, #ff8c00, #ff6a00);
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 8px;
            width: 100%;
            margin-top: 20px;
        }

        .btn-payment:hover {
            background: linear-gradient(135deg, #ff6a00, #ff4500);
            transform: translateY(-2px);
        }

        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }

        .cart-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #eee;
        }

        .cart-item:last-child {
            border-bottom: none;
        }

        .item-info {
            display: flex;
            align-items: center;
        }

        .item-image {
            width: 50px;
            height: 50px;
            border-radius: 5px;
            margin-right: 15px;
            object-fit: cover;
        }
    </style>
</head>
<body class="bg-light">
<!-- ================= NAVBAR ================= -->
<jsp:include page="layout/customerNavbar.jsp" />
    <div class="container payment-container">
        <div class="row">
            <!-- Order Summary -->
            <div class="col-lg-4">
                <div class="order-summary">
                    <h4 class="mb-4"><i class="fas fa-shopping-cart"></i> Đơn hàng của bạn</h4>

                    <% if (gioHang != null && !gioHang.getDs().isEmpty()) {
                        List<GioHang> items = gioHang.getDs();
                        for (GioHang item : items) { %>
                        <div class="cart-item">
                            <div class="item-info">
                                <img src="<%=request.getContextPath()%>/images/monan/<%=item.getHinhAnh() != null ? item.getHinhAnh() : "default.jpg"%>"
                                     alt="<%=item.getTenMon()%>" class="item-image">
                                <div>
                                    <div class="fw-bold"><%=item.getTenMon()%></div>
                                    <small class="text-muted">Số lượng: <%=item.getSoLuong()%></small>
                                </div>
                            </div>
                            <div class="fw-bold"><%=formatter.format(item.getGia())%> VNĐ</div>
                        </div>
                    <% } } %>

                    <hr>
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="fw-bold fs-5">Tổng cộng:</span>
                        <span class="total-amount"><%=formatter.format(tongTien != null ? tongTien : 0)%> VNĐ</span>
                    </div>
                </div>
            </div>

            <!-- Payment Form -->
            <div class="col-lg-8">
                <div class="payment-form">
                    <h3 class="mb-4"><i class="fas fa-credit-card"></i> Thông tin thanh toán</h3>

                    <% if (error != null) { %>
                    <div class="error-message">
                        <i class="fas fa-exclamation-triangle"></i> <%=error%>
                    </div>
                    <% } %>

                    <form action="PaymentController" method="post">
                        <input type="hidden" name="action" value="processPayment">

                        <!-- Customer Information -->
                        <h5 class="mb-3">Thông tin khách hàng</h5>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="customerName" class="form-label">Họ tên <span class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="customerName" name="customerName"
                                       placeholder="Nhập họ tên" required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="customerPhone" class="form-label">Số điện thoại <span class="text-danger">*</span></label>
                                <input type="tel" class="form-control" id="customerPhone" name="customerPhone"
                                       placeholder="Nhập số điện thoại" required>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="customerEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="customerEmail" name="customerEmail"
                                       placeholder="Nhập email">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="deliveryAddress" class="form-label">Địa chỉ giao hàng</label>
                                <input type="text" class="form-control" id="deliveryAddress" name="deliveryAddress"
                                       placeholder="Nhập địa chỉ giao hàng">
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="notes" class="form-label">Ghi chú</label>
                            <textarea class="form-control" id="notes" name="notes" rows="3"
                                      placeholder="Ghi chú đặc biệt (tùy chọn)"></textarea>
                        </div>

                        <!-- Payment Methods -->
                        <h5 class="mb-3 mt-4">Phương thức thanh toán</h5>
                        <div class="payment-methods">
                            <div class="payment-method selected" onclick="selectPaymentMethod('qr')">
                                <input type="radio" id="qr" name="paymentMethod" value="qr" required checked>
                                <i class="fas fa-qrcode payment-method-icon"></i>
                                <div>
                                    <div class="fw-bold">Chuyển khoản QR Code</div>
                                    <small class="text-muted">Quét mã QR và chờ nhân viên xác nhận</small>
                                </div>
                            </div>
                        </div>

                        <!-- Submit Button -->
                        <button type="submit" class="btn btn-primary btn-payment">
                            <i class="fas fa-shopping-bag"></i> Đặt hàng và thanh toán
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function selectPaymentMethod(method) {
            // Remove selected class from all methods
            document.querySelectorAll('.payment-method').forEach(el => {
                el.classList.remove('selected');
            });

            // Add selected class to clicked method
            event.currentTarget.classList.add('selected');

            // Check the radio button
            document.getElementById(method).checked = true;
        }

        // Auto-select first payment method
        document.addEventListener('DOMContentLoaded', function() {
            const firstMethod = document.querySelector('.payment-method');
            if (firstMethod) {
                firstMethod.click();
            }
        });
    </script>
</body>
</html>