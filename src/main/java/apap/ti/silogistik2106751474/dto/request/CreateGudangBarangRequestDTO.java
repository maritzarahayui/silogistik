package apap.ti.silogistik2106751474.dto.request;

import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Gudang;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateGudangBarangRequestDTO {
    private Long id;
    private Gudang gudang;
    private Barang barang;
    private Integer stok;
}
