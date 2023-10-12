package apap.ti.silogistik2106751474.controller;

import apap.ti.silogistik2106751474.dto.BarangMapper;
import apap.ti.silogistik2106751474.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751474.model.*;
import apap.ti.silogistik2106751474.repository.BarangDb;
import apap.ti.silogistik2106751474.repository.GudangDb;
import apap.ti.silogistik2106751474.repository.KaryawanDb;
import apap.ti.silogistik2106751474.repository.PermintaanPengirimanDb;
import apap.ti.silogistik2106751474.service.BarangService;
import apap.ti.silogistik2106751474.service.GudangBarangService;
import apap.ti.silogistik2106751474.service.GudangService;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class BarangController {
    @Autowired
    private GudangDb gudangDb;

    @Autowired
    private BarangDb barangDb;

    @Autowired
    private PermintaanPengirimanDb permintaanPengirimanDb;

    @Autowired
    private KaryawanDb karyawanDb;

    @Autowired
    private BarangService barangService;

    @Autowired
    private GudangBarangService gudangBarangService;

    @Autowired
    private GudangService gudangService;

    @Autowired
    private BarangMapper barangMapper;

    @GetMapping("/")
    public String home(Model model) {
        List<Gudang> listGudang = gudangDb.findAll();
        int jumlahGudang = listGudang.size();
        model.addAttribute("jumlahGudang", jumlahGudang);

        List<Barang> listBarang = barangDb.findAll();
        int jumlahBarang = listBarang.size();
        model.addAttribute("jumlahBarang", jumlahBarang);

        List<PermintaanPengiriman> listPermintaanPengiriman = permintaanPengirimanDb.findAll();
        int jumlahPermintaanPengiriman = listPermintaanPengiriman.size();
        model.addAttribute("jumlahPermintaanPengiriman", jumlahPermintaanPengiriman);

        List<Karyawan> listKaryawan = karyawanDb.findAll();
        int jumlahKaryawan = listKaryawan.size();
        model.addAttribute("jumlahKaryawan", jumlahKaryawan);

        return "home";
    }

    @GetMapping("/barang")
    public String daftarBarang(Model model) {
        List<Barang> listBarang = barangDb.findAll();

        model.addAttribute("listBarang", listBarang);

        return "daftar-barang";
    }

    @GetMapping("/barang/tambah")
    public String formTambahBarang(Model model) {
        var barang = new CreateBarangRequestDTO();

        model.addAttribute(barang);

        Map<Integer, String> listTipeBarang = barangService.listTipeBarang();
        model.addAttribute("listTipeBarang", listTipeBarang);

        return "tambah-barang";
    }

    @PostMapping("/barang/tambah")
    public String tambahBarang(@Valid @ModelAttribute CreateBarangRequestDTO barangDTO, BindingResult bindingResult, Model model) {
        System.out.println("post ni bangg");

        if (bindingResult.hasErrors()) {
            List<ObjectError> err = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError r : err) {
                errorMessage.append(r.getDefaultMessage());
                errorMessage.append("\n");
            }
            model.addAttribute("errorMessage", errorMessage.toString());
            return "error-view";
        }

        String sku = barangService.generateSKU(barangDTO.getTipe_barang());

        Barang barang = barangMapper.createBarangRequestDTOToBarang(barangDTO);
        barang.setSku(sku);
        barangService.saveBarang(barang);

        Gudang gudang = new Gudang();
        gudang.setNama("");
        gudang.setAlamat_gudang("");
        gudangService.saveGudang(gudang);

        GudangBarang gudangBarang = new GudangBarang();
        gudangBarang.setStok(1);
        gudangBarang.setBarang(barang);
        gudangBarang.setGudang(gudang);
        gudangBarangService.saveGudangBarang(gudangBarang);

        model.addAttribute("sku", barang.getSku());
        model.addAttribute("barangBaru", barang);

        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang); // Simpan listBarang dalam model

        return "success-tambah-barang";
    }

    @GetMapping("/barang/{sku}")
    public String detailBarang(@PathVariable String sku, Model model) {
        Barang barang = barangService.findBySku(sku);

        List<GudangBarang> listGudangBarang = gudangBarangService.findByBarang(barang);

        model.addAttribute("barang", barang);
        model.addAttribute("listGudangBarang", listGudangBarang);

        return "detail-barang";
    }

    @GetMapping("/barang/{sku}/ubah")
    public String formUbahBarang(@PathVariable String sku, Model model) {
        Barang barang = barangService.findBySku(sku);

        model.addAttribute("barang", barang);

        Map<Integer, String> listTipeBarang = barangService.listTipeBarang();

        model.addAttribute("listTipeBarang", listTipeBarang);

        return "ubah-barang";
    }

    @PostMapping("/barang/{sku}/ubah")
    public String ubahBarang(@PathVariable String sku, @Valid @ModelAttribute UpdateBarangRequestDTO barangDTO, BindingResult bindingResult, Model model) {
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

        var barangFromDto = barangMapper.updateBarangRequestDTOToBarang(barangDTO);

        barangFromDto.setMerk(barangDTO.getMerk());
        barangFromDto.setHarga_barang(barangDTO.getHarga_barang());

        var barang = barangService.updateBarang(barangFromDto);

        model.addAttribute("sku", barang.getSku());

        return "success-update-barang";
    }

    @GetMapping("/permintaan-pengiriman")
    public String daftarPermintaanPengiriman(Model model) {
        return "daftar-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/idPermintaanPengiriman")
    public String detailPermintaanPengiriman(Model model) {
        return "detail-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/tambah")
    public String buatPermintaanPengiriman(Model model) {
        return "buat-permintaan-pengiriman";
    }

    @GetMapping("/permintaan-pengiriman/idPermintaanPengiriman/cancel")
    public String cancelPermintaanPengiriman(Model model) {
        return "cancel-permintaan";
    }
}
