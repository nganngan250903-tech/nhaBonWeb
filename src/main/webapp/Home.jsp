<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/layout/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <!-- Khu vực hóa đơn -->
        <div class="col-md-8">
            <%@ include file="/banhang/hoadon.jsp" %>
        </div>

        <!-- Danh sách bàn -->
        <div class="col-md-4">
            <%@ include file="/ban/ban-list.jsp" %>
        </div>
    </div>

    <!-- Menu món -->
    <div class="row mt-3">
        <div class="col-md-12">
            <%@ include file="/banhang/menu.jsp" %>
        </div>
    </div>
</div>

<%@ include file="/layout/footer.jsp" %>
