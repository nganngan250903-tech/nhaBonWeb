<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thêm món</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>

<body class="bg-light">


<!-- SIDEBAR -->
<jsp:include page="layout/adminSidebar.jsp"/>

<!-- NỘI DUNG CHÍNH -->
<div class="admin-content">
	
	
    <h3>➕ Thêm món</h3>

    <form action="QLMonController" method="post"  enctype="multipart/form-data" class="w-50">
        <input type="hidden" name="action" value="insert">

      <input class="form-control mb-2" name="tenMon" placeholder="Tên món" required>

    <input class="form-control mb-2" name="gia" type="number" placeholder="Giá" required>

    <div class="mb-2">
        <label>Ảnh món</label>
        <input type="file"
               name="anh"
               class="form-control"
               accept="image/*"
               onchange="previewImage(this)"
               required>
    </div>

    <!-- preview ảnh -->
    <div class="mb-3 text-center">
        <img id="preview"
             src="<%=request.getContextPath()%>/images/no-image.png"
             style="max-width:200px;border:1px solid #ddd;padding:5px">
    </div>

    <textarea class="form-control mb-2" name="moTa" placeholder="Mô tả"></textarea>

    <input class="form-control mb-2" name="maDM" placeholder="Mã danh mục">

    <input class="form-control mb-2" name="soLuong" type="number" placeholder="Số lượng">

    <select class="form-select mb-3" name="trangThai">
        <option value="1">BestSeller</option>
        <option value="0">Bình thường</option>
    </select>

    <button class="btn btn-success">Lưu</button>
</form>
<script>
function previewImage(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = e => document.getElementById("preview").src = e.target.result;
        reader.readAsDataURL(input.files[0]);
    }
}
</script>


</div>

</body>
</html>
