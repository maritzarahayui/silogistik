package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import apap.ti.silogistik2106751474.repository.BarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BarangServiceImpl implements BarangService {
    @Autowired
    BarangDb barangDb;

    @Override
    public void saveBarang(Barang barang) {
        barangDb.save(barang);
    }

    @Override
    public List<Barang> getAllBarangAscending(){
        List<Barang> allBarang = barangDb.findAll();

        return allBarang.stream()
                .sorted(Comparator.comparing(Barang::getMerk)).toList();
    }

    @Override
    public List<Barang> getAllBarang(){
//        return barangDb.findAll();
        return barangDb.findAll()
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Barang findBySku(String sku) {
        for (Barang barang : getAllBarang()) {
            if (barang.getSku().equals(sku)) {
                return barang;
            }
        }
        return null;
    }

    @Override
    public Map<Integer, String> listTipeBarang(){
        Map<Integer, String> tipeBarang = new HashMap<>();

        tipeBarang.put(1, "Produk Elektronik");
        tipeBarang.put(2, "Pakaian & Aksesoris");
        tipeBarang.put(3, "Makanan & Minuman");
        tipeBarang.put(4, "Kosmetik");
        tipeBarang.put(5, "Perlengkapan Rumah");

        return tipeBarang;
    }

    @Override
    public int count(){
        List<Barang> listBarang = getAllBarang();
        return listBarang.size();
    }

    @Override
    public String generateSKU(int tipeBarang) {
        StringBuilder generatedSku = new StringBuilder();

        switch (tipeBarang) {
            case 1 -> generatedSku.append("ELEC");
            case 2 -> generatedSku.append("CLOT");
            case 3 -> generatedSku.append("FOOD");
            case 4 -> generatedSku.append("COSM");
            case 5 -> generatedSku.append("TOOL");
        }

        String sku = String.format("%03d", count() + 1);

        return generatedSku + sku;
    }

    @Override
    public Barang updateBarang(Barang barangFromDto){
        Barang barang = findBySku(barangFromDto.getSku());
        if (barang != null){
            barang.setMerk(barangFromDto.getMerk());
            barang.setHarga_barang(barangFromDto.getHarga_barang());
            barangDb.save(barang);
        }
        return barang;
    }

    @Override
    public int totalStok(Barang barang){
        int total = 0;

        for (GudangBarang gudbar : barang.getListGudangBarang()){
            total += gudbar.getStok();
        }

        return total;
    }

}
