package apap.ti.silogistik2106751474.dto.request;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateGudangRequestDTO extends CreateGudangRequestDTO{
    @Id
    private Long id;
}
