<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="Model.BanAn.BanAn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quản lý bàn</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>

<body class="bg-light">

<%
    String msgSuccess = (String) session.getAttribute("msgSuccess");
    String msgError   = (String) session.getAttribute("msgError");
%>

<jsp:include page="layout/adminSidebar.jsp"/>

<div class="admin-content">

    <% if (msgSuccess != null) { %>
    <div class="alert alert-success alert-dismissible fade show">
        <%= msgSuccess %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("msgSuccess"); } %>

    <% if (msgError != null) { %>
    <div class="alert alert-danger alert-dismissible fade show">
        <%= msgError %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
    <% session.removeAttribute("msgError"); } %>

    <h3 class="mb-3">🪑 Quản lý Bàn</h3>

    <p class="fw-bold">
        Đang sử dụng:
        <span class="text-danger"><%= request.getAttribute("banDangDung") %></span>
        /
        <span class="text-primary"><%= request.getAttribute("tongBan") %></span>
        bàn
    </p>

    <!-- FORM THÊM BÀN -->
    <form action="QLBanController" method="post" class="d-flex mb-4">
        <input type="hidden" name="action" value="insert">
        <input class="form-control w-25 me-2"
               name="tenBan"
               placeholder="Tên bàn"
               required>
        <button class="btn btn-success">➕ Thêm bàn</button>
    </form>

    <!-- DANH SÁCH BÀN -->
    <div class="row">
        <%
            ArrayList<BanAn> ds = (ArrayList<BanAn>) request.getAttribute("dsBan");
            if (ds != null) {
                for (BanAn b : ds) {
        %>
        <div class="col-md-3 mb-3">
            <div class="card text-center
                <%= b.getTrangThai()==1 ? "border-danger" : "border-success" %>">

                <div class="card-body">
                    <h5 class="card-title"><%= b.getTenBan() %></h5>

                    <span class="badge
                        <%= b.getTrangThai()==1 ? "bg-danger" : "bg-success" %>">
                        <%= b.getTrangThai()==1 ? "Đang sử dụng" : "Trống" %>
                    </span>

                    <div class="mt-3">
                        <!-- SỬA -->
                        <button type="button"
                                class="btn btn-warning btn-sm"
                                data-bs-toggle="modal"
                                data-bs-target="#editBan<%=b.getMaBan()%>">
                            Sửa
                        </button>

                        <!-- XÓA -->
                        <form action="QLBanController" method="post"
                              class="d-inline"
                              onsubmit="return confirm('Xóa bàn này?')">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="maBan" value="<%=b.getMaBan()%>">
                            <button class="btn btn-danger btn-sm">Xóa</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- ===== MODAL SỬA BÀN (ĐẶT NGAY SAU CARD) ===== -->
        <div class="modal fade" id="editBan<%=b.getMaBan()%>" tabindex="-1">
          <div class="modal-dialog">
            <form class="modal-content" action="QLBanController" method="post">
              <div class="modal-header">
                <h5 class="modal-title">Sửa bàn</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
              </div>

              <div class="modal-body">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="maBan" value="<%=b.getMaBan()%>">

                <label class="form-label">Tên bàn</label>
                <input class="form-control"
                       name="tenBan"
                       value="<%=b.getTenBan()%>"
                       required>
              </div>

              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button class="btn btn-success">Lưu</button>
              </div>
            </form>
          </div>
        </div>
        <!-- ===== END MODAL ===== -->

        <% } } %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
