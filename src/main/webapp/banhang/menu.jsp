<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.MonAn.MonAn" %>

<h4>🍽️ MENU</h4>

<div class="row">
<%
    ArrayList<MonAn> dsMon =
        (ArrayList<MonAn>) request.getAttribute("dsMon");
    if(dsMon != null){
        for(MonAn m : dsMon){
%>
    <div class="col-md-3 mb-2">
        <form action="ChiTietHoaDonController" method="post">
            <input type="hidden" name="maMon" value="<%=m.getMaMon()%>">
            <input type="hidden" name="maBan" value="${ban.maBan}">
            
            <div class="card p-2">
                <b><%=m.getTenMon()%></b>
                <span><%=m.getGia()%> đ</span>
                <button class="btn btn-primary btn-sm mt-2">
                    Thêm
                </button>
            </div>
        </form>
    </div>
<% }} %>
</div>
    