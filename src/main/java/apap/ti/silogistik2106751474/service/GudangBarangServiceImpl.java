package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import apap.ti.silogistik2106751474.repository.GudangBarangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GudangBarangServiceImpl implements GudangBarangService {
    @Autowired
    GudangBarangDb gudangBarangDb;

    @Override
    public void saveGudangBarang(GudangBarang gudangBarang) {
        gudangBarangDb.save(gudangBarang);
    }

    @Override
    public List<GudangBarang> getAllGudangBarang(){
        return gudangBarangDb.findAll();
    }

    @Override
    public List<GudangBarang> findByBarang(Barang barang){
        return gudangBarangDb.findByBarang(barang);
    }

    @Override
    public GudangBarang findById(Long id) {
        for (GudangBarang gb : getAllGudangBarang()) {
            if (gb.getId().equals(id)) {
                return gb;
            }
        }
        return null;
    }

    @Override
    public GudangBarang updateGudangBarang(GudangBarang gudangBarang){
        GudangBarang gudbar = findById(gudangBarang.getId());
        if (gudbar != null){
            gudbar.setStok(gudangBarang.getStok());
            gudangBarangDb.save(gudbar);
        }
        return gudbar;
    }
}
