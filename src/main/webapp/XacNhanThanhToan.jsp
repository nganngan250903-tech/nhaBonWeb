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

    String paymentMethod = (String) request.getAttribute("paymentMethod");
    String customerName = (String) request.getAttribute("customerName");
    String customerPhone = (String) request.getAttribute("customerPhone");
    String customerEmail = (String) request.getAttribute("customerEmail");
    String deliveryAddress = (String) request.getAttribute("deliveryAddress");
    String notes = (String) request.getAttribute("notes");

    // Map payment method to display name
    String paymentMethodName = "";
    switch (paymentMethod != null ? paymentMethod : "") {
        case "cash":
            paymentMethodName = "Tiền mặt";
            break;
        case "card":
            paymentMethodName = "Thẻ tín dụng/ghi nợ";
            break;
        case "momo":
            paymentMethodName = "Ví MoMo";
            break;
        case "zalopay":
            paymentMethodName = "ZaloPay";
            break;
        default:
            paymentMethodName = "Chưa chọn";
    }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác nhận thanh toán - Nhà Bon</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .confirmation-container {
            max-width: 800px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .confirmation-card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }

        .order-details {
            background: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
        }

        .customer-info {
            background: #e8f4f8;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
        }

        .payment-info {
            background: #fff3cd;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
        }

        .total-price {
            font-size: 32px;
            font-weight: bold;
            color: #ff8c00;
            text-align: center;
            margin: 20px 0;
        }

        .btn-confirm {
            background: linear-gradient(135deg, #28a745, #20c997);
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 8px;
            width: 100%;
            margin-top: 20px;
        }

        .btn-confirm:hover {
            background: linear-gradient(135deg, #218838, #1aa085);
            transform: translateY(-2px);
        }

        .btn-back {
            background: #6c757d;
            border: none;
            padding: 15px 30px;
            font-size: 18px;
            font-weight: bold;
            border-radius: 8px;
            width: 100%;
            margin-top: 10px;
        }

        .btn-back:hover {
            background: #5a6268;
            transform: translateY(-2px);
        }

        .cart-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 0;
            border-bottom: 1px solid #dee2e6;
        }

        .cart-item:last-child {
            border-bottom: none;
        }

        .item-info {
            display: flex;
            align-items: center;
        }

        .item-image {
            width: 60px;
            height: 60px;
            border-radius: 8px;
            margin-right: 15px;
            object-fit: cover;
        }

        .item-details {
            flex-grow: 1;
        }

        .item-name {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .item-quantity {
            color: #6c757d;
            font-size: 14px;
        }

        .item-price {
            font-weight: bold;
            color: #ff8c00;
        }

        .section-title {
            font-weight: bold;
            color: #495057;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
        }

        .section-title i {
            margin-right: 10px;
            color: #ff8c00;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container confirmation-container">
        <div class="confirmation-card">
            <h2 class="text-center mb-4">
                <i class="fas fa-check-circle text-success"></i>
                Xác nhận đơn hàng
            </h2>

            <!-- Order Details -->
            <div class="order-details">
                <h5 class="section-title">
                    <i class="fas fa-shopping-cart"></i>
                    Chi tiết đơn hàng
                </h5>

                <% if (gioHang != null && !gioHang.getDs().isEmpty()) {
                    List<GioHang> items = gioHang.getDs();
                    for (GioHang item : items) { %>
                    <div class="cart-item">
                        <div class="item-info">
                            <img src="<%=request.getContextPath()%>/images/monan/<%=item.getHinhAnh() != null ? item.getHinhAnh() : "default.jpg"%>"
                                 alt="<%=item.getTenMon()%>" class="item-image">
                            <div class="item-details">
                                <div class="item-name"><%=item.getTenMon()%></div>
                                <div class="item-quantity">Số lượng: <%=item.getSoLuong()%></div>
                            </div>
                        </div>
                        <div class="item-price"><%=formatter.format(item.getGia())%> VNĐ</div>
                    </div>
                <% } } %>

                <hr>
                <div class="d-flex justify-content-between align-items-center">
                    <span class="fw-bold fs-5">Tổng cộng:</span>
                    <span class="total-price"><%=formatter.format(tongTien != null ? tongTien : 0)%> VNĐ</span>
                </div>
            </div>

            <!-- Customer Information -->
            <div class="customer-info">
                <h5 class="section-title">
                    <i class="fas fa-user"></i>
                    Thông tin khách hàng
                </h5>
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>Họ tên:</strong> <%=customerName != null ? customerName : "N/A"%></p>
                        <p><strong>Số điện thoại:</strong> <%=customerPhone != null ? customerPhone : "N/A"%></p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Email:</strong> <%=customerEmail != null && !customerEmail.trim().isEmpty() ? customerEmail : "Không có"%></p>
                        <p><strong>Địa chỉ:</strong> <%=deliveryAddress != null && !deliveryAddress.trim().isEmpty() ? deliveryAddress : "Không có"%></p>
                    </div>
                </div>
                <% if (notes != null && !notes.trim().isEmpty()) { %>
                <div class="mt-3">
                    <p><strong>Ghi chú:</strong> <%=notes%></p>
                </div>
                <% } %>
            </div>

            <!-- Payment Information -->
            <div class="payment-info">
                <h5 class="section-title">
                    <i class="fas fa-credit-card"></i>
                    Phương thức thanh toán
                </h5>
                <div class="d-flex align-items-center">
                    <% if ("cash".equals(paymentMethod)) { %>
                        <i class="fas fa-money-bill-wave fa-2x text-success me-3"></i>
                    <% } else if ("card".equals(paymentMethod)) { %>
                        <i class="fas fa-credit-card fa-2x text-primary me-3"></i>
                    <% } else if ("momo".equals(paymentMethod)) { %>
                        <i class="fas fa-mobile-alt fa-2x text-pink me-3"></i>
                    <% } else if ("zalopay".equals(paymentMethod)) { %>
                        <i class="fas fa-wallet fa-2x text-info me-3"></i>
                    <% } %>
                    <div>
                        <h6 class="mb-0"><%=paymentMethodName%></h6>
                        <small class="text-muted">
                            <% if ("cash".equals(paymentMethod)) { %>
                                Thanh toán khi nhận hàng
                            <% } else { %>
                                Thanh toán online
                            <% } %>
                        </small>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <form action="PaymentController" method="post">
                <input type="hidden" name="action" value="confirmPayment">

                <button type="submit" class="btn btn-success btn-confirm">
                    <i class="fas fa-check"></i> Xác nhận đặt hàng
                </button>

                <button type="button" class="btn btn-secondary btn-back" onclick="history.back()">
                    <i class="fas fa-arrow-left"></i> Quay lại chỉnh sửa
                </button>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
