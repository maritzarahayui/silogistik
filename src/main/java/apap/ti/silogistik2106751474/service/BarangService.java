package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Barang;

import java.util.List;
import java.util.Map;

public interface BarangService {
    void saveBarang(Barang barang);
    List<Barang> getAllBarang();
    List<Barang> getAllBarangAscending();
    Map<Integer, String> listTipeBarang();
    int count();
    String generateSKU(int tipeBarang);
    Barang findBySku(String sku);
    Barang updateBarang(Barang barang);
    int totalStok(Barang barang);
}
