package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;

import java.util.List;
import java.util.UUID;

public interface GudangService {
    void saveGudang(Gudang gudang);
    List<Gudang> getAllGudang();
    Gudang getGudangById(Long id);
//    List<Gudang> searchGudangByBarang(String cariBarang);
    List<Gudang> findGudangByBarang(String barang);

}
