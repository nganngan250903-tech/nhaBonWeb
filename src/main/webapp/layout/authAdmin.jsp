<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Model.NhanVien.NhanVien" %>
<%
    NhanVien nv = (NhanVien) session.getAttribute("nhanvien");

    if (nv == null) {
        response.sendRedirect("DangNhapController");
        return;
    }

    if (!"1".equals(nv.getMaQuyen())) {
        response.sendRedirect("403.jsp");
        return;
    }
%>
