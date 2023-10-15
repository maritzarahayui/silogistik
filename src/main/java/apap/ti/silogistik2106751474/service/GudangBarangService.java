package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;

import java.util.List;

public interface GudangBarangService {
    void saveGudangBarang(GudangBarang gudangBarang);
    List<GudangBarang> getAllGudangBarang();
    List<GudangBarang> findByBarang(Barang barang);
    GudangBarang findById(Long id);
    GudangBarang updateGudangBarang(GudangBarang gudangBarang);
    GudangBarang findGudangBarangByGudangAndBarang(Gudang gudang, Barang barang);
}
