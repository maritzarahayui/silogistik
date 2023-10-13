package apap.ti.silogistik2106751474.dto;

import apap.ti.silogistik2106751474.dto.request.CreatePermintaanPengirimanRequestDTO;
import apap.ti.silogistik2106751474.model.PermintaanPengiriman;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermintaanPengirimanMapper {
    PermintaanPengiriman createPermintaanPengirimanRequestDTOToPermintaanPengiriman(
            CreatePermintaanPengirimanRequestDTO createPermintaanPengirimanRequestDTO);
}
