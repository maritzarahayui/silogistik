package apap.ti.silogistik2106751474.dto.request;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateKaryawanRequestDTO {
    private Long id;
    private String nama;
    private Integer jenis_kelamin;
    private Date tanggal_lahir;
}
