<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm danh mục</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>

<body class="bg-light">

<jsp:include page="layout/adminSidebar.jsp"/>

<div class="admin-content">

    <h3 class="mb-4">➕ Thêm Danh Mục</h3>

    <form action="QLDanhMucController"
          method="post"
          class="w-50">

        <input type="hidden" name="action" value="insert">

        <input class="form-control mb-3"
               name="tenDM"
               placeholder="Tên danh mục"
               required>

        <textarea class="form-control mb-3"
                  name="moTa"
                  placeholder="Mô tả"></textarea>

        <button class="btn btn-success">Lưu</button>
        <a href="QLDanhMucController" class="btn btn-secondary ms-2">Hủy</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
