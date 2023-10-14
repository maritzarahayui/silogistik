package apap.ti.silogistik2106751474.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "barang")
public class Barang {
    @Id
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @NotNull
    @Column(name = "tipe_barang", nullable = false)
    private int tipe_barang;

    @NotNull
    @Column(name = "merk", nullable = false)
    private String merk;

    @NotNull
    @Column(name = "harga_barang", nullable = false)
    private int harga_barang;

    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GudangBarang> listGudangBarang = new ArrayList<>();

    @OneToMany(mappedBy = "barang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PermintaanPengirimanBarang> listPermintaanPengirimanBarang = new ArrayList<>();
}
