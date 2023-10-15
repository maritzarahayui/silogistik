package apap.ti.silogistik2106751474.controller;

import apap.ti.silogistik2106751474.dto.GudangBarangMapper;
import apap.ti.silogistik2106751474.dto.GudangMapper;
import apap.ti.silogistik2106751474.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import apap.ti.silogistik2106751474.repository.GudangDb;
import apap.ti.silogistik2106751474.service.BarangService;
import apap.ti.silogistik2106751474.service.GudangBarangService;
import apap.ti.silogistik2106751474.service.GudangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class GudangController {
    @Autowired
    private GudangDb gudangDb;

    @Autowired
    private GudangService gudangService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private GudangBarangService gudangBarangService;

    @Autowired
    private GudangBarangMapper gudangBarangMapper;

    @Autowired
    private GudangMapper gudangMapper;

    @GetMapping("/gudang")
    public String daftarGudang(Model model) {
        List<Gudang> listGudang = gudangService.getAllGudang();
        model.addAttribute("listGudang", listGudang);

        model.addAttribute("activePage", "Gudang");
        return "daftar-gudang";
    }

    @GetMapping("/gudang/{idGudang}")
    public String detailGudang(@PathVariable Long idGudang, Model model) {
        Gudang gudang = gudangService.getGudangById(idGudang);
        model.addAttribute("gudang", gudang);

        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang);

        Map<String, Integer> totalStokGudang = new HashMap<>();
        for (Barang brg : listBarang){
            int total = barangService.totalStok(brg);
            totalStokGudang.put(brg.getSku(), total);
        }

        model.addAttribute("totalStokGudang", totalStokGudang);

//        System.out.println("HASHSHAAHSJHSJSHKJAHDKHDAHASJ list GUDBAR ");

        model.addAttribute("activePage", "Gudang");
        return "detail-gudang";
    }

    @GetMapping("/gudang/cari-barang")
    public String cariBarang(@RequestParam(value = "sku", required = false) String sku, Model model) {
        List<GudangBarang> listGudangBarang;

        if (sku == null) {
            listGudangBarang = new ArrayList<>();
        } else {
            var barang = barangService.findBySku(sku);
            if (barang != null) {
                model.addAttribute("barang", barang);
                listGudangBarang = barang.getListGudangBarang();
            } else {
                listGudangBarang = new ArrayList<>();
            }
        }

        List<Barang> listBarang = barangService.getAllBarangAscending();
        model.addAttribute("listBarang", listBarang);

        model.addAttribute("listGudangBarang", listGudangBarang);

        model.addAttribute("activePage", "Gudang");
        return "cari-barang";
    }

    @GetMapping("/gudang/{idGudang}/restock-barang")
    public String restockGudang(@PathVariable("idGudang") Long idGudang, Model model) {
        var gudang = gudangService.getGudangById(idGudang);

        var gudangFromDto = gudangMapper.gudangToUpdateGudangRequestDTO(gudang);
        model.addAttribute("gudangDTO", gudangFromDto);

        model.addAttribute("listGudangBarang", gudangFromDto.getListGudangBarang());

        List<Barang> listBarang = barangService.getAllBarangAscending();
        model.addAttribute("listBarang", listBarang);
//        System.out.println("updategudangdto list GUDBAR ");

        model.addAttribute("activePage", "Gudang");
        return "restock-gudang";
    }

    @PostMapping(value = "/gudang/{idGudang}/restock-barang", params = {"addRow"})
    public String addRowBarang(@PathVariable("idGudang") Long idGudang, @ModelAttribute UpdateGudangRequestDTO updateGudangRequestDTO,
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

        if (updateGudangRequestDTO.getListGudangBarang() == null || updateGudangRequestDTO.getListGudangBarang().size() == 0) {
            updateGudangRequestDTO.setListGudangBarang(new ArrayList<>());
        }

        updateGudangRequestDTO.getListGudangBarang().add(new GudangBarang());

        model.addAttribute("gudangDTO", updateGudangRequestDTO);
        model.addAttribute("listGudangBarang", updateGudangRequestDTO.getListGudangBarang());

        List<Barang> listBarang = barangService.getAllBarangAscending();
        model.addAttribute("listBarang", listBarang);

        model.addAttribute("activePage", "Gudang");
        return "restock-gudang";
    }

    @PostMapping("/gudang/{idGudang}/restock-barang")
    public String postRestockGudang(@Valid @ModelAttribute UpdateGudangRequestDTO updateGudangRequestDTO,
                                    @PathVariable Long idGudang, BindingResult bindingResult, Model model) {
//        System.out.println("updategudangdto " + updateGudangRequestDTO);
//        System.out.println("updategudangdto list GUDBAR " + updateGudangRequestDTO.getListGudangBarang());

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

        var gudang = gudangMapper.updateGudangRequestDTOToGudang(updateGudangRequestDTO);

        if (gudang.getListGudangBarang() != null){
            for (GudangBarang gb : gudang.getListGudangBarang()){
                gb.setGudang(gudang);
            }
        }

        gudangService.saveGudang(gudang);

        Map<String, Integer> totalStokGudang = new HashMap<>();
        List<Barang> listBarang = barangService.getAllBarang();

        for (Barang brg : listBarang){
            int total = barangService.totalStok(brg);
            totalStokGudang.put(brg.getSku(), total);
        }

        model.addAttribute("gudangDTO", updateGudangRequestDTO);
        model.addAttribute("totalStokGudang", totalStokGudang);

//        System.out.println("updategudangdto " + updateGudangRequestDTO);
//        System.out.println("updategudangdto list GUDBAR " + updateGudangRequestDTO.getListGudangBarang());

        model.addAttribute("activePage", "Gudang");
        return "success-restock-gudang";
    }
}