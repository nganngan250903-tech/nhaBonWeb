<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
    List<Object[]> dsChoXacNhan = (List<Object[]>) request.getAttribute("dsChoXacNhan");
    List<Object[]> dsDaThanhToan = (List<Object[]>) request.getAttribute("dsDaThanhToan");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thanh toán - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    <style>
        .payment-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .status-badge {
            font-size: 0.8em;
            padding: 4px 8px;
            border-radius: 12px;
        }

        .status-pending {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffeaa7;
        }

        .status-paid {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .status-unpaid {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .btn-confirm {
            background: linear-gradient(135deg, #28a745, #20c997);
            border: none;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 0.9em;
        }

        .btn-confirm:hover {
            background: linear-gradient(135deg, #218838, #1aa085);
            color: white;
        }

        .btn-reject {
            background: linear-gradient(135deg, #dc3545, #c82333);
            border: none;
            color: white;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 0.9em;
        }

        .btn-reject:hover {
            background: linear-gradient(135deg, #c82333, #a02622);
            color: white;
        }

        .payment-summary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
        }

        .summary-stat {
            text-align: center;
        }

        .summary-number {
            font-size: 2rem;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .summary-label {
            font-size: 0.9rem;
            opacity: 0.9;
        }
    </style>
</head>

<body class="bg-light">
    <!-- SIDEBAR -->
    <jsp:include page="layout/adminSidebar.jsp" />

    <!-- MAIN CONTENT -->
    <div class="admin-content">
        <h2 class="fw-bold mb-4">
            <i class="fas fa-credit-card"></i> Quản lý thanh toán
        </h2>

        <!-- SUCCESS/ERROR MESSAGES -->
        <% if (success != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle"></i> <%=success%>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <% if (error != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-triangle"></i> <%=error%>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% } %>

        <!-- PAYMENT SUMMARY -->
        <div class="payment-summary">
            <div class="row">
                <div class="col-md-4 summary-stat">
                    <div class="summary-number">
                        <%= dsChoXacNhan != null ? dsChoXacNhan.size() : 0 %>
                    </div>
                    <div class="summary-label">Đơn chờ xác nhận</div>
                </div>
                <div class="col-md-4 summary-stat">
                    <div class="summary-number">
                        <%= dsDaThanhToan != null ? dsDaThanhToan.size() : 0 %>
                    </div>
                    <div class="summary-label">Đơn đã thanh toán</div>
                </div>
                <div class="col-md-4 summary-stat">
                    <div class="summary-number">
                        <%=formatter.format((dsChoXacNhan != null ? dsChoXacNhan.size() : 0) +
                           (dsDaThanhToan != null ? dsDaThanhToan.size() : 0))%>
                    </div>
                    <div class="summary-label">Tổng đơn hàng</div>
                </div>
            </div>
        </div>

        <!-- PENDING PAYMENTS -->
        <div class="payment-card">
            <h5 class="mb-3">
                <i class="fas fa-clock text-warning"></i> Đơn hàng chờ xác nhận thanh toán
                <span class="badge bg-warning ms-2"><%= dsChoXacNhan != null ? dsChoXacNhan.size() : 0 %></span>
            </h5>

            <% if (dsChoXacNhan == null || dsChoXacNhan.isEmpty()) { %>
            <div class="text-center py-4">
                <i class="fas fa-check-circle fa-3x text-success mb-3"></i>
                <h5>Không có đơn hàng nào chờ xác nhận</h5>
                <p class="text-muted">Tất cả đơn hàng đều đã được xử lý</p>
            </div>
            <% } else { %>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Mã HD</th>
                            <th>Bàn</th>
                            <th>Thời gian</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Object[] donHang : dsChoXacNhan) { %>
                        <tr>
                            <td><strong>#<%=donHang[0]%></strong></td>
                            <td>Bàn <%=donHang[1]%></td>
                            <td>
                                <jsp:useBean id="dateValue" class="java.util.Date"/>
                                <jsp:setProperty name="dateValue" property="time" value="<%=((java.sql.Timestamp)donHang[3]).getTime()%>"/>
                                <fmt:formatDate value="${dateValue}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td class="fw-bold text-primary"><%=formatter.format(donHang[5])%> VNĐ</td>
                            <td>
                                <span class="status-badge status-pending">
                                    <i class="fas fa-clock"></i> Chờ xác nhận
                                </span>
                            </td>
                            <td>
                                <form method="post" class="d-inline">
                                    <input type="hidden" name="action" value="confirmPayment">
                                    <input type="hidden" name="maHD" value="<%=donHang[0]%>">
                                    <button type="submit" class="btn-confirm" onclick="return confirm('Xác nhận thanh toán cho đơn hàng #<%=donHang[0]%>?')">
                                        <i class="fas fa-check"></i> Xác nhận
                                    </button>
                                </form>
                                <form method="post" class="d-inline ms-2">
                                    <input type="hidden" name="action" value="rejectPayment">
                                    <input type="hidden" name="maHD" value="<%=donHang[0]%>">
                                    <button type="submit" class="btn-reject" onclick="return confirm('Từ chối thanh toán cho đơn hàng #<%=donHang[0]%>?')">
                                        <i class="fas fa-times"></i> Từ chối
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            <% } %>
        </div>

        <!-- COMPLETED PAYMENTS -->
        <div class="payment-card">
            <h5 class="mb-3">
                <i class="fas fa-check-circle text-success"></i> Đơn hàng đã thanh toán gần đây
                <span class="badge bg-success ms-2"><%= dsDaThanhToan != null ? dsDaThanhToan.size() : 0 %></span>
            </h5>

            <% if (dsDaThanhToan == null || dsDaThanhToan.isEmpty()) { %>
            <div class="text-center py-4">
                <i class="fas fa-shopping-cart fa-3x text-muted mb-3"></i>
                <h5>Chưa có đơn hàng nào được thanh toán</h5>
                <p class="text-muted">Đơn hàng đã thanh toán sẽ hiển thị ở đây</p>
            </div>
            <% } else { %>
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Mã HD</th>
                            <th>Bàn</th>
                            <th>Thời gian</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Object[] donHang : dsDaThanhToan) { %>
                        <tr>
                            <td><strong>#<%=donHang[0]%></strong></td>
                            <td>Bàn <%=donHang[1]%></td>
                            <td>
                                <jsp:useBean id="dateValue2" class="java.util.Date"/>
                                <jsp:setProperty name="dateValue2" property="time" value="<%=((java.sql.Timestamp)donHang[3]).getTime()%>"/>
                                <fmt:formatDate value="${dateValue2}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td class="fw-bold text-success"><%=formatter.format(donHang[5])%> VNĐ</td>
                            <td>
                                <span class="status-badge status-paid">
                                    <i class="fas fa-check"></i> Đã thanh toán
                                </span>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
            <% } %>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Auto refresh every 30 seconds -->
    <script>
        setTimeout(function() {
            location.reload();
        }, 30000); // 30 seconds
    </script>
</body>
</html>
