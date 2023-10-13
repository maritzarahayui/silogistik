package apap.ti.silogistik2106751474.controller;

import apap.ti.silogistik2106751474.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Karyawan;
import apap.ti.silogistik2106751474.model.PermintaanPengiriman;
import apap.ti.silogistik2106751474.repository.PermintaanPengirimanDb;
import apap.ti.silogistik2106751474.service.BarangService;
import apap.ti.silogistik2106751474.service.KaryawanService;
import apap.ti.silogistik2106751474.service.PermintaanPengirimanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class PermintaanPengirimanController {
    @Autowired
    private PermintaanPengirimanDb permintaanPengirimanDb;

    @Autowired
    private BarangService barangService;

    @Autowired
    private KaryawanService karyawanService;

    @Autowired
    private PermintaanPengirimanService permintaanPengirimanService;

    @GetMapping("/permintaan-pengiriman")
    public String daftarPermintaanPengiriman(Model model) {
        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanDb.findAll();

        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);

        return "daftar-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/tambah")
    public String buatPermintaanPengiriman(Model model) {
        List<Barang> listBarang = barangService.getAllBarang();
        List<Karyawan> listKaryawan = karyawanService.getAllKaryawan();

        var permintaan_pengiriman = new CreatePermintaanPengirimanRequestDTO();

        model.addAttribute("permintaan_pengiriman", permintaan_pengiriman);
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listKaryawan", listKaryawan);

        Map<Integer, String> listJenisLayanan = permintaanPengirimanService.listJenisLayanan();
        model.addAttribute("listTipeBarang", listJenisLayanan);

        return "buat-permintaan-pengiriman";
    }
}
