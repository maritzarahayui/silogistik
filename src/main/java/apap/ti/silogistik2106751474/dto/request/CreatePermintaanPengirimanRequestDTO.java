package apap.ti.silogistik2106751474.dto.request;

import apap.ti.silogistik2106751474.model.GudangBarang;
import apap.ti.silogistik2106751474.model.Karyawan;
import apap.ti.silogistik2106751474.model.PermintaanPengirimanBarang;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreatePermintaanPengirimanRequestDTO {
    private Long id;
    private String nomor_pengiriman;
    private Boolean is_cancelled;
    private String nama_penerima;
    private String alamat_penerima;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tanggal_pengiriman;
    private Integer biaya_pengiriman;
    private Integer jenis_layanan;
    private Date waktu_permintaan;
    private Karyawan karyawan;
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang;
}
