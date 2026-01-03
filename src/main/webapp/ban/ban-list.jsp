<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.BanAn.BanAn" %>

<h4>🪑 Danh sách bàn</h4>

<div class="list-group">
<%
    ArrayList<BanAn> dsBan =
        (ArrayList<BanAn>) request.getAttribute("dsBan");
    if(dsBan != null){
        for(BanAn b : dsBan){
%>
    <a href="HomeController?maBan=<%=b.getMaBan()%>"
       class="list-group-item list-group-item-action
       <%= b.getTrangThai().equals("DANG_DUNG") ? "active" : "" %>">
        <%= b.getTenBan() %>
        - <%= b.getTrangThai() %>
    </a>
<% }} %>
</div>
