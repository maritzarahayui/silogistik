package apap.ti.silogistik2106751474.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService {
    @Override
    public Map<Integer, String> listJenisLayanan(){
        Map<Integer, String> jenisLayanan = new HashMap<>();

        jenisLayanan.put(1, "Same Day");
        jenisLayanan.put(2, "Kilat");
        jenisLayanan.put(3, "Reguler");
        jenisLayanan.put(4, "Hemat");

        return jenisLayanan;
    }
}
