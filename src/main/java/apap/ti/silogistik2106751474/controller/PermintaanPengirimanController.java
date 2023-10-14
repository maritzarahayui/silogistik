package apap.ti.silogistik2106751474.controller;

import apap.ti.silogistik2106751474.dto.BarangMapper;
import apap.ti.silogistik2106751474.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751474.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751474.model.*;
import apap.ti.silogistik2106751474.repository.PermintaanPengirimanDb;
import apap.ti.silogistik2106751474.service.BarangService;
import apap.ti.silogistik2106751474.service.GudangService;
import apap.ti.silogistik2106751474.service.KaryawanService;
import apap.ti.silogistik2106751474.service.PermintaanPengirimanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
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
    private GudangService gudangService;

    @Autowired
    private PermintaanPengirimanService permintaanPengirimanService;

    @Autowired
    private PermintaanPengirimanMapper permintaanPengirimanMapper;

    @Autowired
    private BarangMapper barangMapper;

    @GetMapping("/permintaan-pengiriman")
    public String daftarPermintaanPengiriman(Model model) {
        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanDb.findByIsCancelledFalse();

        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);

        return "daftar-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/tambah")
    public String buatPermintaanPengiriman(@Valid @ModelAttribute CreatePermintaanPengirimanRequestDTO
           createPermintaanPengirimanRequestDTO, Model model) {
        List<Karyawan> listKaryawan = karyawanService.getAllKaryawan();
        model.addAttribute("listKaryawan", listKaryawan);

        Map<Integer, String> listJenisLayanan = permintaanPengirimanService.listJenisLayanan();
        model.addAttribute("listJenisLayanan", listJenisLayanan);

        List<Barang> listBarang = barangService.getAllBarang();
//        model.addAttribute("listBarang", listBarang);
        model.addAttribute("listBarang", listBarang);

        var permintaan_pengiriman = new CreatePermintaanPengirimanRequestDTO();

        model.addAttribute("permintaan_pengiriman", permintaan_pengiriman);

        return "buat-permintaan-pengiriman";
    }

    @PostMapping(value = "/permintaan-pengiriman/tambah", params = {"addRow"})
    public String addRowBarang(@Valid @ModelAttribute CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO, Model model) {
        System.out.println("INI ADD ROWWWWWW");

        if (createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang() == null ||
                createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang().size() == 0) {
            createPermintaanPengirimanRequestDTO.setListPermintaanPengirimanBarang(new ArrayList<>());
        }

        createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang().add(new PermintaanPengirimanBarang());

        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang);
        model.addAttribute("permintaan_pengiriman", createPermintaanPengirimanRequestDTO);
        model.addAttribute("listPermintaanPengirimanBarang", createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang());
        model.addAttribute("listKaryawan", karyawanService.getAllKaryawan());
        model.addAttribute("listJenisLayanan", permintaanPengirimanService.listJenisLayanan());

        return "buat-permintaan-pengiriman";
    }

    @PostMapping("/permintaan-pengiriman/tambah")
    public String postTambahPermintaanPengiriman(@Valid @ModelAttribute CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO,
                                    Model model) {

        System.out.println("skrg post beneran masya allah tabarakallah");

//        int jumlahBarang = createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang().size();

        System.out.println("biaya " + createPermintaanPengirimanRequestDTO.getBiaya_pengiriman());
        System.out.println("nomor " + createPermintaanPengirimanRequestDTO.getNomor_pengiriman());

        int jumlahBarang = 0;
        if (createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang() != null) {
            for (PermintaanPengirimanBarang ppb : createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang()) {
                jumlahBarang += ppb.getKuantitas_pesanan();
            }
        }

        int jenisLayanan = createPermintaanPengirimanRequestDTO.getJenis_layanan();

        String nomorPengiriman = permintaanPengirimanService.generateNomorPengiriman(jumlahBarang, jenisLayanan);

        var permintaan = permintaanPengirimanMapper.createPermintaanPengirimanRequestDTOToPermintaanPengiriman(createPermintaanPengirimanRequestDTO);

        if (permintaan.getListPermintaanPengirimanBarang() != null){
            for (PermintaanPengirimanBarang ppb : permintaan.getListPermintaanPengirimanBarang()){
                ppb.setPermintaanPengiriman(permintaan);
            }
        }

        permintaan.setIs_cancelled(false);
        permintaan.setNomor_pengiriman(nomorPengiriman);
        permintaan.setWaktu_permintaan(new Date());

        permintaanPengirimanService.savePermintaanPengiriman(permintaan);
        model.addAttribute("permintaanPengirimanBarangDTO", createPermintaanPengirimanRequestDTO);

        model.addAttribute("nomor_pengiriman", nomorPengiriman);

        System.out.println("permintaanPengirimanBarangDTO " + createPermintaanPengirimanRequestDTO);
        System.out.println("permintaanPengirimanBarangDTO list ppb " + createPermintaanPengirimanRequestDTO.getListPermintaanPengirimanBarang());

//        return "daftar-permintaan-pengiriman";
        return "success-tambah-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}")
    public String detailPermintaanPengiriman(@PathVariable Long idPermintaanPengiriman, Model model) {
        var permintaan = permintaanPengirimanService.findById(idPermintaanPengiriman);
        model.addAttribute("permintaan", permintaan);

        var nomor_pengiriman = permintaan.getNomor_pengiriman();
        model.addAttribute("nomor_pengiriman", nomor_pengiriman);

        var waktu_permintaan = permintaan.getWaktu_permintaan();
        model.addAttribute("waktu_permintaan", waktu_permintaan);

        var karyawan = permintaan.getKaryawan();
        model.addAttribute("karyawan", karyawan);

        var tanggal_pengiriman = permintaan.getTanggal_pengiriman();
        model.addAttribute("tanggal_pengiriman", tanggal_pengiriman);

        var nama_penerima = permintaan.getNama_penerima();
        model.addAttribute("nama_penerima", nama_penerima);

        var alamat_penerima = permintaan.getAlamat_penerima();
        model.addAttribute("alamat_penerima", alamat_penerima);

//        int id_layanan = permintaan.getJenis_layanan();
//        var jenis_layanan = permintaanPengirimanService.listJenisLayanan().get(id_layanan);
        Map<Integer, String> listJenisLayanan = permintaanPengirimanService.listJenisLayanan();
        model.addAttribute("listJenisLayanan", listJenisLayanan);

        var biaya_pengiriman = permintaan.getBiaya_pengiriman();
        model.addAttribute("biaya_pengiriman", biaya_pengiriman);

        List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = permintaan.getListPermintaanPengirimanBarang();
        model.addAttribute("listPermintaanPengirimanBarang", listPermintaanPengirimanBarang);

//        for (PermintaanPengirimanBarang p : listPermintaanPengirimanBarang ){
//            System.out.println(p);
//        }

        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang);

        return "detail-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}/cancel")
    public String cancelPermintaanPengiriman(@PathVariable Long idPermintaanPengiriman, Model model) {
        var permintaan = permintaanPengirimanService.findById(idPermintaanPengiriman);
//        model.addAttribute("permintaan", permintaan);

        long selisihWaktu = System.currentTimeMillis() - permintaan.getWaktu_permintaan().getTime();
        long jamDalamSehari = 24 * 60 * 60 * 1000; // Satu hari dalam milidetik

        if (selisihWaktu <= jamDalamSehari){
            permintaanPengirimanService.cancelPermintaanPengiriman(permintaan);

//            permintaan.setIs_cancelled(true);
//            permintaanPengirimanService.savePermintaanPengiriman(permintaan);

//            model.addAttribute("listPermintaanPengiriman", permintaanPengirimanService.findNonCancelledPermintaanPengiriman());
        }

//        model.addAttribute("message", "Permintaan pengiriman tidak dapat dibatalkan.");
        return "cancel-permintaan";
    }
}
