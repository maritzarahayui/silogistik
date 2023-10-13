package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.Karyawan;

import java.util.List;

public interface KaryawanService {
    void saveKaryawan(Karyawan karyawan);
    List<Karyawan> getAllKaryawan();
}
