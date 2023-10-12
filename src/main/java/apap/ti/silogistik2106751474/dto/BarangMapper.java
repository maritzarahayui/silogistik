package apap.ti.silogistik2106751474.dto;

import apap.ti.silogistik2106751474.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751474.model.Barang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BarangMapper {
    Barang createBarangRequestDTOToBarang(CreateBarangRequestDTO createBarangRequestDTO);
    Barang updateBarangRequestDTOToBarang(UpdateBarangRequestDTO updateBarangRequestDTO);
    UpdateBarangRequestDTO barangToUpdateBarangRequestDTO(Barang barang);
}
