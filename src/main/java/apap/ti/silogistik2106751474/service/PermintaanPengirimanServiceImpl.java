package apap.ti.silogistik2106751474.service;

import apap.ti.silogistik2106751474.model.*;
import apap.ti.silogistik2106751474.repository.PermintaanPengirimanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermintaanPengirimanServiceImpl implements PermintaanPengirimanService {
    @Autowired
    PermintaanPengirimanDb permintaanPengirimanDb;

    @Override
    public void savePermintaanPengiriman(PermintaanPengiriman permintaanPengiriman) {
        permintaanPengirimanDb.save(permintaanPengiriman);
    }

    @Override
    public List<PermintaanPengiriman> getAllPermintaanPengiriman(){
        return permintaanPengirimanDb.findAll();
    }

    @Override
    public Map<Integer, String> listJenisLayanan(){
        Map<Integer, String> jenisLayanan = new HashMap<>();

        jenisLayanan.put(1, "Same Day");
        jenisLayanan.put(2, "Kilat");
        jenisLayanan.put(3, "Reguler");
        jenisLayanan.put(4, "Hemat");

        return jenisLayanan;
    }

    @Override
    public String generateNomorPengiriman(int jumlahBarang, int jenisLayanan) {
        // Mengambil jumlah barang dalam format 2 digit terakhir
        String jumlahBarangStr = String.format("%02d", jumlahBarang % 100);

        // Mengambil jenis layanan
        String jenisLayananStr = "";
        switch (jenisLayanan) {
            case 1 -> jenisLayananStr = "SAM";
            case 2 -> jenisLayananStr = "KIL";
            case 3 -> jenisLayananStr = "REG";
            case 4 -> jenisLayananStr = "HEM";
        }

        // Mengambil delapan karakter terakhir dari waktu sekarang
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String waktuPembuatanStr = sdf.format(new Date());

        // Menggabungkan semua komponen
        return "REQ" + jumlahBarangStr + jenisLayananStr + waktuPembuatanStr;
    }

    @Override
    public PermintaanPengiriman findById(Long idPermintaanPengiriman) {
        for (PermintaanPengiriman pp : getAllPermintaanPengiriman()) {
            if (pp.getId().equals(idPermintaanPengiriman)) {
                return pp;
            }
        }
        return null;
    }

    @Override
    public void cancelPermintaanPengiriman(PermintaanPengiriman permintaanPengiriman){
        permintaanPengiriman.setIs_cancelled(true);
        permintaanPengirimanDb.save(permintaanPengiriman);
    }

    @Override
    public long calculateTimeDifference(PermintaanPengiriman permintaan) {
        long selisihWaktu = System.currentTimeMillis() - permintaan.getWaktu_permintaan().getTime();
        return selisihWaktu;
    }

    @Override
    public long getJamDalamSehari() {
        return 24 * 60 * 60 * 1000;
    }

}
