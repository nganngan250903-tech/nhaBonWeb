-- Script tạo database cho Ốc Nhà Bon

-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    MaKH BIGINT IDENTITY(1,1) PRIMARY KEY,
    TenKH NVARCHAR(50) NOT NULL,
    SĐT NVARCHAR(11) UNIQUE NOT NULL
);

-- Tạo bảng NhanVien
CREATE TABLE NhanVien (
    MaNV NVARCHAR(10) PRIMARY KEY,
    TenNV NVARCHAR(100) NOT NULL,
    MaQuyen INT NOT NULL DEFAULT 2, -- 1: Admin, 2: Nhân viên
    UserName NVARCHAR(50) UNIQUE NOT NULL,
    Pass NVARCHAR(255) NOT NULL,
    TrangThaiNV BIT NOT NULL DEFAULT 1 -- 1: Hoạt động, 0: Ngưng hoạt động
);

-- Tạo bảng DanhMuc
CREATE TABLE DanhMuc (
    MaDM BIGINT IDENTITY(1,1) PRIMARY KEY,
    TenDM NVARCHAR(100) NOT NULL,
    TrangThaiDM BIT NOT NULL DEFAULT 1
);

-- Tạo bảng MonAn
CREATE TABLE MonAn (
    MaMon BIGINT IDENTITY(1,1) PRIMARY KEY,
    TenMon NVARCHAR(100) NOT NULL,
    Gia BIGINT NOT NULL,
    HinhAnh NVARCHAR(255),
    MoTa NVARCHAR(500),
    MaDM BIGINT FOREIGN KEY REFERENCES DanhMuc(MaDM),
    TrangThaiMon BIT NOT NULL DEFAULT 1,
    SoLuong INT NOT NULL DEFAULT 0
);

-- Tạo bảng HoaDon
CREATE TABLE HoaDon (
    MaHD BIGINT IDENTITY(1,1) PRIMARY KEY,
    MaBan BIGINT,
    MaNV NVARCHAR(10) FOREIGN KEY REFERENCES NhanVien(MaNV),
    GioVao DATETIME NOT NULL DEFAULT GETDATE(),
    GioRa DATETIME,
    TongTien BIGINT NOT NULL DEFAULT 0,
    ThanhToan BIT NOT NULL DEFAULT 0, -- 0: Chưa thanh toán, 1: Đã thanh toán
    MaKH BIGINT FOREIGN KEY REFERENCES KhachHang(MaKH)
);

-- Tạo bảng ChiTietHoaDon
CREATE TABLE ChiTietHoaDon (
    MaCTHD BIGINT IDENTITY(1,1) PRIMARY KEY,
    MaHD BIGINT FOREIGN KEY REFERENCES HoaDon(MaHD),
    MaMon BIGINT FOREIGN KEY REFERENCES MonAn(MaMon),
    SoLuong INT NOT NULL,
    DonGia BIGINT NOT NULL,
    TrangThai BIT NOT NULL DEFAULT 0, -- 0: Đang làm, 1: Hoàn thành
    ChietKhau BIGINT NOT NULL DEFAULT 0
);

-- Tạo bảng BanAn
CREATE TABLE BanAn (
    MaBan BIGINT IDENTITY(1,1) PRIMARY KEY,
    TenBan NVARCHAR(50) NOT NULL,
    TrangThai BIT NOT NULL DEFAULT 0, -- 0: Trống, 1: Đang sử dụng
    QRCode NVARCHAR(50)
);

-- Thêm dữ liệu mẫu
INSERT INTO NhanVien (MaNV, TenNV, MaQuyen, UserName, Pass, TrangThaiNV) VALUES
('NV001', N'Administrator', 1, 'admin', '4297f44b13955235245b2497399d7a93', 1),
('NV002', N'Nhân Viên', 2, 'nhanvien', '4297f44b13955235245b2497399d7a93', 1);

INSERT INTO DanhMuc (TenDM, TrangThaiDM) VALUES
(N'Hải sản sống', 1),
(N'Hải sản chế biến', 1),
(N'Đồ uống', 1);

INSERT INTO MonAn (TenMon, Gia, HinhAnh, MoTa, MaDM, TrangThaiMon, SoLuong) VALUES
(N'Tôm hùm sống', 1500000, 'images/monan/tomhum.jpg', N'Tôm hùm tươi sống nhập khẩu', 1, 1, 10),
(N'Cua biển', 800000, 'images/monan/cuabien.jpg', N'Cua biển tươi sống', 1, 1, 15),
(N'Mực ống', 450000, 'images/monan/mucong.jpg', N'Mực ống tươi', 1, 1, 20);

INSERT INTO BanAn (TenBan, TrangThai) VALUES
(N'Bàn 01', 0),
(N'Bàn 02', 0),
(N'Bàn 03', 0),
(N'Bàn VIP 01', 0);