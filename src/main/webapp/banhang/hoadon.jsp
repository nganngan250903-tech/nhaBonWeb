<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.HoaDon.ChiTietHoaDon" %>

<h4>🧾 Hóa đơn bàn ${ban.tenBan}</h4>

<table class="table table-bordered table-sm">
    <thead>
        <tr>
            <th>Món</th>
            <th>SL</th>
            <th>Giá</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <%
            ArrayList<ChiTietHoaDon> dsCT = 
                (ArrayList<ChiTietHoaDon>) request.getAttribute("dsCTHD");
            if(dsCT != null){
                for(ChiTietHoaDon ct : dsCT){
        %>
        <tr>
            <td><%= ct.getTenMon() %></td>
            <td>
                <a href="ChiTietHoaDonController?action=giam&id=<%=ct.getMaCTHD()%>">−</a>
                <%= ct.getSoLuong() %>
                <a href="ChiTietHoaDonController?action=tang&id=<%=ct.getMaCTHD()%>">+</a>
            </td>
            <td><%= ct.getThanhTien() %></td>
            <td>
                <a href="ChiTietHoaDonController?action=xoa&id=<%=ct.getMaCTHD()%>">❌</a>
            </td>
        </tr>
        <% }} %>
    </tbody>
</table>

<h5 class="text-end">
    Tổng tiền: <b>${tongTien} đ</b>
</h5>

<a href="HoaDonController?action=thanhtoan&maBan=${ban.maBan}"
   class="btn btn-success w-100">
   THANH TOÁN
</a>
    