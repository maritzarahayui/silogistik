package apap.ti.silogistik2106751474.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "permintaan_pengiriman")
public class PermintaanPengiriman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(max = 16)
    @Column(name = "nomor_pengiriman", nullable = false)
    private String nomor_pengiriman;

    @Column(name = "is_cancelled")
    private Boolean is_cancelled;

    @NotNull
    @Column(name = "nama_penerima", nullable = false)
    private String nama_penerima;

    @NotNull
    @Column(name = "alamat_penerima", nullable = false)
    private String alamat_penerima;

    @NotNull
    @Column(name = "tanggal_pengiriman", nullable = false)
    private Date tanggal_pengiriman;

    @NotNull
    @Column(name = "biaya_pengiriman", nullable = false)
    private Integer biaya_pengiriman;

    @NotNull
    @Column(name = "jenis_layanan", nullable = false)
    private Integer jenis_layanan;

    @NotNull
    @Column(name = "waktu_permintaan", nullable = false)
    private Date waktu_permintaan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_karyawan", referencedColumnName = "id")
    private Karyawan karyawan;

    @OneToMany(mappedBy = "permintaanPengiriman", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = new ArrayList<>();
}
