package apap.ti.silogistik2106751474.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateBarangRequestDTO {
    private String sku;
    private int tipe_barang;
    private String merk;
    private int harga_barang;
}
