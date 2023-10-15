package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.PermintaanPengiriman;

import java.util.List;
import java.util.Map;

public interface PermintaanPengirimanService {
    Map<Integer, String> listJenisLayanan();
    List<PermintaanPengiriman> getAllPermintaanPengiriman();
    void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman);
    String generateNomorPengiriman(int jumlahBarang, int jenisLayanan);
    PermintaanPengiriman findById(Long id);
    void cancelPermintaanPengiriman(PermintaanPengiriman permintaanPengiriman);
    long calculateTimeDifference(PermintaanPengiriman permintaan);
    long getJamDalamSehari();
}
