package apap.ti.silogistik2106751474.dto;

import apap.ti.silogistik2106751474.dto.request.CreateKaryawanRequestDTO;
import apap.ti.silogistik2106751474.model.Karyawan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KaryawanMapper {
    Karyawan createKaryawanRequestDTOToKaryawan(CreateKaryawanRequestDTO createKaryawanRequestDTO);
}
