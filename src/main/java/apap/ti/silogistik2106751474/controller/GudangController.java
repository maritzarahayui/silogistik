package apap.ti.silogistik2106751474.controller;

import apap.ti.silogistik2106751474.dto.GudangBarangMapper;
import apap.ti.silogistik2106751474.dto.GudangMapper;
import apap.ti.silogistik2106751474.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import apap.ti.silogistik2106751474.repository.GudangDb;
import apap.ti.silogistik2106751474.service.BarangService;
import apap.ti.silogistik2106751474.service.GudangBarangService;
import apap.ti.silogistik2106751474.service.GudangService;
import io.micrometer.common.util.StringUtils;
import io.netty.util.internal.StringUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        return "daftar-gudang";
    }

    @GetMapping("/gudang/{idGudang}")
    public String detailGudang(@PathVariable Long idGudang, Model model) {
        Gudang gudang = gudangService.getGudangById(idGudang);
        model.addAttribute("gudang", gudang);

//        System.out.println("WOIII");
//        System.out.println("WOIII " + gudang.getListGudangBarang());
//        if (gudang.getListGudangBarang() != null) {
//            for (GudangBarang gdg : gudang.getListGudangBarang()) {
//                System.out.println("((cntrller) isi list gdg brg " + gdg.getBarang().getMerk());
//            }
//        }

        return "detail-gudang";
    }

    @GetMapping("/gudang/cari-barang")
    public String cariBarang(@RequestParam(name = "cariBarang", required = false) String cariBarang, Model model) {
        List<Gudang> listGudang;

        if (StringUtils.isNotBlank(cariBarang)){
            listGudang = gudangService.searchGudangByBarang(cariBarang);
        } else {
            listGudang = gudangService.getAllGudang();
        }

        model.addAttribute("listGudang", listGudang);

        return "cari-barang";
    }

    @GetMapping("/gudang/{idGudang}/restock-barang")
    public String restockGudang(@PathVariable("idGudang") Long id, Model model) {
        var gudang = gudangService.getGudangById(id);

        var gudangFromDto = gudangMapper.gudangToUpdateGudangRequestDTO(gudang);
        model.addAttribute("gudangDTO", gudangFromDto);

        model.addAttribute("listGudangBarang", gudangFromDto.getListGudangBarang());

        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang);

        return "restock-gudang";
    }

    @PostMapping("/gudang/{id}/restock-barang")
    public String postRestockGudang(@ModelAttribute UpdateGudangRequestDTO updateGudangRequestDTO, Model model) {
        var gudang = gudangMapper.updateGudangRequestDTOToGudang(updateGudangRequestDTO);

        if (gudang.getListGudangBarang() != null){
            for (GudangBarang gb : gudang.getListGudangBarang()){
                gb.setGudang(gudang);
            }
        }

        gudangService.saveGudang(gudang);

        model.addAttribute("gudangDTO", updateGudangRequestDTO);

//        List<Barang> listBarang = barangService.getAllBarang();
//        model.addAttribute("listBarang", listBarang);

        return "success-restock-gudang";
//        return "detail-barang";
    }

    @PostMapping(value = "/gudang/{idGudang}/restock-barang", params = {"addRow"})
    public String addRowBarang(@ModelAttribute UpdateGudangRequestDTO updateGudangRequestDTO, Model model) {
        if (updateGudangRequestDTO.getListGudangBarang() == null || updateGudangRequestDTO.getListGudangBarang().size() == 0) {
            updateGudangRequestDTO.setListGudangBarang(new ArrayList<>());
        }

        updateGudangRequestDTO.getListGudangBarang().add(new GudangBarang());

        model.addAttribute("gudangDTO", updateGudangRequestDTO);
        model.addAttribute("listGudangBarang", updateGudangRequestDTO.getListGudangBarang());

        List<Barang> listBarang = barangService.getAllBarang();
        model.addAttribute("listBarang", listBarang);

        return "restock-gudang";
    }
}
