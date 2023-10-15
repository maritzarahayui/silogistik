package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import apap.ti.silogistik2106751474.repository.GudangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GudangServiceImpl implements GudangService {
    @Autowired
    GudangDb gudangDb;

    @Override
    public void saveGudang(Gudang gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public List<Gudang> getAllGudang(){
        return gudangDb.findAll();
    }

    @Override
    public Gudang getGudangById(Long kodeGudang){
        for (Gudang gudang : getAllGudang()){
            if(gudang.getId().equals(kodeGudang)){
                return gudang;
            }
        }
        return null;
    }

    @Override
    public List<Gudang> findGudangByBarang(String barang) {
        return gudangDb.findGudangByBarang(barang);
    }

    @Override
    public void checkAndProcessDuplicateData(UpdateGudangRequestDTO gudangDTO, Gudang gudang) {
        for (GudangBarang gudangBarangDTO : gudangDTO.getListGudangBarang()) {
            boolean found = false; // Menandakan apakah barang sudah ada di gudang

            for (GudangBarang gudangBarang : gudang.getListGudangBarang()) {
                if (gudangBarangDTO.getBarang().getSku().equals(gudangBarang.getBarang().getSku())) {
                    // Barang sudah ada di gudang, update stok
                    int existingStok = gudangBarang.getStok();
                    int newStok = gudangBarangDTO.getStok();
                    gudangBarang.setStok(existingStok + newStok);
                    found = true;
                    break; // Keluar dari loop karena barang sudah ditemukan
                }
            }

            if (!found) {
                // Barang belum ada di gudang, tambahkan ke list
                gudangBarangDTO.setGudang(gudang);
                gudang.getListGudangBarang().add(gudangBarangDTO);
            }
        }
    }

}
