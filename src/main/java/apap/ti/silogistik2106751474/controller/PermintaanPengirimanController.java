package apap.ti.silogistik2106751474.controller;

import apap.ti.silogistik2106751474.dto.BarangMapper;
import apap.ti.silogistik2106751474.dto.PermintaanPengirimanMapper;
import apap.ti.silogistik2106751474.dto.request.CreatePermintaanPengirimanRequestDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

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
        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanService.getAllPermintaanPengiriman();

        listPermintaanPengiriman.sort(Comparator.comparing(PermintaanPengiriman::getWaktu_permintaan).reversed());

        model.addAttribute("listPermintaanPengiriman", listPermintaanPengiriman);

        model.addAttribute("activePage", "PermintaanPengiriman");
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
        model.addAttribute("listBarang", listBarang);

        var permintaan_pengiriman = new CreatePermintaanPengirimanRequestDTO();
        model.addAttribute("permintaan_pengiriman", permintaan_pengiriman);

        model.addAttribute("activePage", "PermintaanPengiriman");
        return "buat-permintaan-pengiriman";
    }

    @PostMapping(value = "/permintaan-pengiriman/tambah", params = {"addRow"})
    public String addRowBarang(@Valid @ModelAttribute CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            List<ObjectError> err = bindingResult.getAllErrors();

            StringBuilder errorMessage = new StringBuilder();

            for(ObjectError r : err){
                errorMessage.append(r.getDefaultMessage());
                errorMessage.append("\n");
            }

            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

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

        model.addAttribute("activePage", "PermintaanPengiriman");
        return "buat-permintaan-pengiriman";
    }

    @PostMapping("/permintaan-pengiriman/tambah")
    public String postTambahPermintaanPengiriman(@Valid @ModelAttribute CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO,
                                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            List<ObjectError> err = bindingResult.getAllErrors();

            StringBuilder errorMessage = new StringBuilder();

            for(ObjectError r : err){
                errorMessage.append(r.getDefaultMessage());
                errorMessage.append("\n");
            }

            model.addAttribute("errorMessage", errorMessage);
            return "error-view";
        }

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

        model.addAttribute("activePage", "PermintaanPengiriman");
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

        Map<Integer, String> listJenisLayanan = permintaanPengirimanService.listJenisLayanan();
        model.addAttribute("listJenisLayanan", listJenisLayanan);

        var biaya_pengiriman = permintaan.getBiaya_pengiriman();
        model.addAttribute("biaya_pengiriman", biaya_pengiriman);

        List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = permintaan.getListPermintaanPengirimanBarang();
        model.addAttribute("listPermintaanPengirimanBarang", listPermintaanPengirimanBarang);

        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang);

        model.addAttribute("is_canceled", permintaan.getIs_cancelled());

        long selisihWaktu = permintaanPengirimanService.calculateTimeDifference(permintaan);
        long jamDalamSehari = permintaanPengirimanService.getJamDalamSehari();
        model.addAttribute("selisihWaktu", selisihWaktu);
        model.addAttribute("jamDalamSehari", jamDalamSehari);

        model.addAttribute("activePage", "PermintaanPengiriman");
        return "detail-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/{idPermintaanPengiriman}/cancel")
    public String cancelPermintaanPengiriman(@PathVariable Long idPermintaanPengiriman, Model model) {
        var permintaan = permintaanPengirimanService.findById(idPermintaanPengiriman);
        model.addAttribute("permintaan", permintaan);

        long selisihWaktu = permintaanPengirimanService.calculateTimeDifference(permintaan);
        long jamDalamSehari = permintaanPengirimanService.getJamDalamSehari();

        if (selisihWaktu <= jamDalamSehari){
            permintaanPengirimanService.cancelPermintaanPengiriman(permintaan);
        }

        model.addAttribute("nomor_pengiriman", permintaan.getNomor_pengiriman());

        model.addAttribute("activePage", "PermintaanPengiriman");
        return "cancel-permintaan";
    }
}
