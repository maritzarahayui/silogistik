package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import apap.ti.silogistik2106751474.repository.GudangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public List<Gudang> searchGudangByBarang(String barang) {
        return gudangDb.cariBarang(barang);
    }

//    @Override
//    public Gudang updateGudang(Gudang gudang){
//        Gudang gd = getGudangById(gudang.getId());
//        if (gd != null){
//            gd.setListGudangBarang(gd);
//            gudangDb.save(gd);
//        }
//        return gd;
//    }
}
