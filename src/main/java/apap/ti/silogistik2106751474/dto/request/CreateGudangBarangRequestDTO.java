package apap.ti.silogistik2106751474.dto.request;

import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Gudang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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
