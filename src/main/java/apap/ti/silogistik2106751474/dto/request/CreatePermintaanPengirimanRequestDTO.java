package apap.ti.silogistik2106751474.dto.request;

import apap.ti.silogistik2106751474.model.Karyawan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

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
    private Date tanggal_pengiriman;
    private Integer biaya_pengiriman;
    private Integer jenis_layanan;
    private Date waktu_permintaan;
    private Karyawan karyawan;
}
