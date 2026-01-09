<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>🍜 ỐC NHÀ BON - Đơn hàng</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<!-- ================= NAVBAR ================= -->
<jsp:include page="layout/customerNavbar.jsp" />
<div class="container mt-4">
    <h3 class="mb-4">🛒 ĐƠN HÀNG CỦA BẠN</h3>

    <form action="DonHangController" method="post">
        <table class="table align-middle table-bordered">
            <thead class="table-light">
                <tr>
                    <th>
                        <input type="checkbox" id="checkAll">
                    </th>
                    <th>Ảnh</th>
                    <th>Tên món</th>
                    <th>Số lượng</th>
                    <th>Giá</th>
                    <th>Thành tiền</th>
                    <th>Ghi chú cho bếp</th>
                    <th></th>
                </tr>
            </thead>

            <tbody>
                <c:forEach var="i" items="${sessionScope.gio.ds}">
                    <tr>
                        <!-- CHECKBOX -->
                        <td class="text-center">
                            <input type="checkbox"
                                   class="chonMon"
                                   name="chonMon"
                                   value="${i.maMon}"
                                   data-thanhtien="${i.thanhTien}">
                        </td>

                        <!-- ẢNH -->
                        <td>
                            <img src="${pageContext.request.contextPath}/${i.hinhAnh}"
                                 width="70">
                        </td>

                        <!-- TÊN -->
                        <td>${i.tenMon}</td>

                        <!-- SỐ LƯỢNG -->
                        <td style="width:110px">
                            <input type="number" min="1"
                                   name="soLuong_${i.maMon}"
                                   value="${i.soLuong}"
                                   class="form-control">
                        </td>

                        <!-- GIÁ -->
                        <td>${i.gia} đ</td>

                        <!-- THÀNH TIỀN -->
                        <td class="fw-bold text-danger">
                            ${i.thanhTien} đ
                        </td>

                        <!-- GHI CHÚ -->
                        <td>
                            <input type="text"
                                   name="ghiChu_${i.maMon}"
                                   class="form-control"
                                   placeholder="VD: ít cay, không hành...">
                        </td>

                        <!-- XÓA -->
                        <td class="text-center">
                            <a href="GioHangController?action=xoa&maMon=${i.maMon}"
                               class="btn btn-danger btn-sm">
                               Trả lại
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- TỔNG TIỀN -->
        <div class="row mt-3">
            <div class="col-md-6">
                <b>Tổng tiền món đã chọn:</b>
                <span class="text-success fw-bold">
                    <span id="tongChon">0</span> VNĐ
                </span>
            </div>

            <div class="col-md-6 text-end">
                <button type="submit"
                        name="action"
                        value="datmon"
                        class="btn btn-success">
                    ✅ Xác nhận đặt món
                </button>
            </div>
        </div>
    </form>

    <div class="mt-4">
        <a href="TrangChuController" class="btn btn-secondary">
            ⬅ Tiếp tục mua
            
        </a>
    </div>
</div>

<!-- SCRIPT -->
<script>
const checkAll = document.getElementById("checkAll");
const checks = document.querySelectorAll(".chonMon");
const tongChon = document.getElementById("tongChon");

function tinhTong() {
    let tong = 0;
    checks.forEach(cb => {
        if (cb.checked) {
            tong += parseFloat(cb.dataset.thanhtien);
        }
    });
    tongChon.innerText = tong.toLocaleString();
}

checkAll.addEventListener("change", function () {
    checks.forEach(cb => cb.checked = this.checked);
    tinhTong();
});

checks.forEach(cb => cb.addEventListener("change", tinhTong));
</script>

</body>
</html>
