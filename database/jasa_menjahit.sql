-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 14 Jul 2020 pada 04.42
-- Versi Server: 10.1.9-MariaDB
-- PHP Version: 5.5.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jasa_menjahit`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` char(7) NOT NULL,
  `nm_plg` varchar(30) NOT NULL,
  `jenis_kelamin` varchar(13) NOT NULL,
  `almt_plg` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nm_plg`, `jenis_kelamin`, `almt_plg`, `no_telp`) VALUES
('G001', 'Kaka', 'Perempuan', 'Solo', '087980456513'),
('G002', 'Nathan', 'Laki-Laki', 'Semanggi', '087634561232'),
('G003', 'Dina', 'Perempuan', 'Surakarta', '098567342123'),
('G004', 'Raina', 'Perempuan', 'Sukodono', '098567234123'),
('G005', 'ARSYA', 'Laki-Laki', 'Kalijambe', '085764234654');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id_pembayaran` varchar(7) NOT NULL,
  `kd_transaksi` varchar(7) NOT NULL,
  `id_pelanggan` varchar(7) NOT NULL,
  `nm_plg` varchar(30) NOT NULL,
  `id_penjahit` varchar(7) NOT NULL,
  `nm_jahit` varchar(30) NOT NULL,
  `model_busana` varchar(50) NOT NULL,
  `tgl_ambil` date NOT NULL,
  `harga_busana` double NOT NULL,
  `jasa_jahit` double NOT NULL,
  `jml_bayar` double NOT NULL,
  `uang_bayar` double NOT NULL,
  `uang_kembali` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pembayaran`
--

INSERT INTO `pembayaran` (`id_pembayaran`, `kd_transaksi`, `id_pelanggan`, `nm_plg`, `id_penjahit`, `nm_jahit`, `model_busana`, `tgl_ambil`, `harga_busana`, `jasa_jahit`, `jml_bayar`, `uang_bayar`, `uang_kembali`) VALUES
('B001', 'TR001', 'G001', 'Kaka', 'P001', 'Rara', 'Celana Panjang', '2020-05-25', 30000, 20000, 50000, 60000, 10000),
('B002', 'TR002', 'G002', 'Nathan', 'P002', 'Laras', 'Kemeja Lengan Pendek', '2020-07-17', 80000, 30000, 110000, 110000, 0),
('B003', 'TR003', 'G003', 'Dina', 'P003', 'Mawar', 'Gamis', '2020-07-12', 100000, 35000, 135000, 140000, 5000),
('B004', 'TR004', 'G004', 'Raina', 'P004', 'Melati', 'Blus', '2020-07-20', 75000, 25000, 100000, 100000, 0),
('B005', 'TR005', 'G005', 'ARSYA', 'P004', 'Melati', 'Seragam Sekolah', '2020-07-23', 300000, 50000, 350000, 350000, 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjahit`
--

CREATE TABLE `penjahit` (
  `id_penjahit` char(7) NOT NULL,
  `nm_jahit` varchar(30) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(10) NOT NULL,
  `almt_jahit` varchar(50) NOT NULL,
  `no_telp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjahit`
--

INSERT INTO `penjahit` (`id_penjahit`, `nm_jahit`, `username`, `password`, `almt_jahit`, `no_telp`) VALUES
('P001', 'Rara', 'rara', 'rara', 'Sragen', '087980456547'),
('P002', 'Laras', 'laras', 'laras', 'Gemolong', '085234654123'),
('P003', 'Mawar', 'mawar', 'mawar', 'Sukododo', '085764234543'),
('P004', 'Melati', 'melati', 'melati', 'Sukoharjo', '085765345789'),
('P005', 'Reno', 'reno', 'reno', 'Sragen', '085345765234');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `kd_transaksi` char(7) NOT NULL,
  `id_pelanggan` varchar(7) NOT NULL,
  `nm_plg` varchar(30) NOT NULL,
  `id_penjahit` varchar(7) NOT NULL,
  `nm_jahit` varchar(30) NOT NULL,
  `model_busana` varchar(50) NOT NULL,
  `jenis_bahan` varchar(50) NOT NULL,
  `tgl_jahit` date NOT NULL,
  `tgl_ambil` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`kd_transaksi`, `id_pelanggan`, `nm_plg`, `id_penjahit`, `nm_jahit`, `model_busana`, `jenis_bahan`, `tgl_jahit`, `tgl_ambil`) VALUES
('TR001', 'G001', 'Kaka', 'P001', 'Rara', 'Celana Panjang', 'Drill', '2020-05-20', '2020-05-25'),
('TR002', 'G002', 'Nathan', 'P002', 'Laras', 'Kemeja Lengan Pendek', 'Batik', '2020-07-13', '2020-07-17'),
('TR003', 'G003', 'Dina', 'P003', 'Mawar', 'Gamis', 'Balotely', '2020-07-08', '2020-07-12'),
('TR004', 'G004', 'Raina', 'P004', 'Melati', 'Blus', 'Balotely', '2020-07-14', '2020-07-20'),
('TR005', 'G005', 'ARSYA', 'P004', 'Melati', 'Seragam Sekolah', 'Drill', '2020-07-08', '2020-07-23');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indexes for table `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id_pembayaran`),
  ADD KEY `kd_transaksi` (`kd_transaksi`);

--
-- Indexes for table `penjahit`
--
ALTER TABLE `penjahit`
  ADD PRIMARY KEY (`id_penjahit`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD KEY `id_pelanggan` (`id_pelanggan`),
  ADD KEY `id_penjahit` (`id_penjahit`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
