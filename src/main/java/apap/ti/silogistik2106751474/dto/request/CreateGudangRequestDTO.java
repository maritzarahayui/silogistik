package apap.ti.silogistik2106751474.dto.request;

import apap.ti.silogistik2106751474.model.GudangBarang;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateGudangRequestDTO {
    private Long id;
    private String nama;
    private String alamat_gudang;
    private List<GudangBarang> listGudangBarang;
}
