package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Karyawan;
import apap.ti.silogistik2106751474.repository.KaryawanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaryawanServiceImpl implements KaryawanService {
    @Autowired
    KaryawanDb karyawanDb;

    @Override
    public void saveKaryawan(Karyawan karyawan) {
        karyawanDb.save(karyawan);
    }

    @Override
    public List<Karyawan> getAllKaryawan() {
        return karyawanDb.findAll();
    }
}
