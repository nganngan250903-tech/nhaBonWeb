<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
    List<Object[]> dsMonDangLam = (List<Object[]>) request.getAttribute("dsMonDangLam");
    List<Object[]> dsMonDaXong = (List<Object[]>) request.getAttribute("dsMonDaXong");
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý trạng thái món ăn - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    <style>
        .dish-card {
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

        .status-processing {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffeaa7;
        }

        .status-completed {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .btn-complete {
            background: linear-gradient(135deg, #28a745, #20c997);
            border: none;
            color: white;
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 0.85em;
        }

        .btn-complete:hover {
            background: linear-gradient(135deg, #218838, #1aa085);
            color: white;
        }

        .dish-info {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .dish-icon {
            width: 40px;
            height: 40px;
            background: linear-gradient(135deg, #ff8c00, #ff6a00);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            margin-right: 15px;
        }

        .dish-details {
            flex-grow: 1;
        }

        .order-time {
            color: #6c757d;
            font-size: 0.9em;
        }

        .processing-animation {
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0% { opacity: 1; }
            50% { opacity: 0.5; }
            100% { opacity: 1; }
        }
    </style>
</head>

<body class="bg-light">
    <!-- SIDEBAR -->
    <jsp:include page="layout/adminSidebar.jsp" />

    <!-- MAIN CONTENT -->
    <div class="admin-content">
        <h2 class="fw-bold mb-4">
            <i class="fas fa-utensils"></i> Quản lý trạng thái món ăn
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

        <!-- PROCESSING DISHES -->
        <div class="dish-card">
            <h5 class="mb-3">
                <i class="fas fa-spinner fa-spin text-warning"></i> Món đang chế biến
                <span class="badge bg-warning ms-2"><%= dsMonDangLam != null ? dsMonDangLam.size() : 0 %></span>
            </h5>

            <% if (dsMonDangLam == null || dsMonDangLam.isEmpty()) { %>
            <div class="text-center py-4">
                <i class="fas fa-check-circle fa-3x text-success mb-3"></i>
                <h5>Không có món nào đang chế biến</h5>
                <p class="text-muted">Tất cả món ăn đều đã hoàn thành</p>
            </div>
            <% } else { %>
            <div class="row">
                <% for (Object[] mon : dsMonDangLam) { %>
                <div class="col-md-6 col-lg-4 mb-3">
                    <div class="card h-100 processing-animation">
                        <div class="card-body">
                            <div class="dish-info">
                                <div class="dish-icon">
                                    <i class="fas fa-utensils"></i>
                                </div>
                                <div class="dish-details">
                                    <h6 class="mb-1"><%=mon[3]%></h6> <!-- TenMon -->
                                    <div class="order-time">
                                        <i class="fas fa-clock"></i>
                                        Đơn #<%=mon[1]%> • <%=mon[4]%> phần • <%=formatter.format(mon[5])%>đ
                                    </div>
                                </div>
                            </div>

                            <div class="d-flex justify-content-between align-items-center">
                                <span class="status-badge status-processing">
                                    <i class="fas fa-spinner fa-spin"></i> Đang làm
                                </span>

                                <form method="post" class="d-inline">
                                    <input type="hidden" name="action" value="markCompleted">
                                    <input type="hidden" name="maCTHD" value="<%=mon[0]%>">
                                    <button type="submit" class="btn-complete" onclick="return confirm('Đánh dấu món <%=mon[3]%> đã hoàn thành?')">
                                        <i class="fas fa-check"></i> Hoàn thành
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } %>
        </div>

        <!-- COMPLETED DISHES -->
        <div class="dish-card">
            <h5 class="mb-3">
                <i class="fas fa-check-circle text-success"></i> Món đã hoàn thành
                <span class="badge bg-success ms-2"><%= dsMonDaXong != null ? dsMonDaXong.size() : 0 %></span>
            </h5>

            <% if (dsMonDaXong == null || dsMonDaXong.isEmpty()) { %>
            <div class="text-center py-4">
                <i class="fas fa-utensils fa-3x text-muted mb-3"></i>
                <h5>Chưa có món nào hoàn thành</h5>
                <p class="text-muted">Món ăn đã hoàn thành sẽ hiển thị ở đây</p>
            </div>
            <% } else { %>
            <div class="row">
                <% for (Object[] mon : dsMonDaXong) { %>
                <div class="col-md-6 col-lg-4 mb-3">
                    <div class="card h-100">
                        <div class="card-body">
                            <div class="dish-info">
                                <div class="dish-icon bg-success">
                                    <i class="fas fa-check"></i>
                                </div>
                                <div class="dish-details">
                                    <h6 class="mb-1"><%=mon[3]%></h6> <!-- TenMon -->
                                    <div class="order-time">
                                        <i class="fas fa-clock"></i>
                                        Đơn #<%=mon[1]%> • <%=mon[4]%> phần • <%=formatter.format(mon[5])%>đ
                                    </div>
                                </div>
                            </div>

                            <div class="status-badge status-completed">
                                <i class="fas fa-check-circle"></i> Đã xong
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>
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
