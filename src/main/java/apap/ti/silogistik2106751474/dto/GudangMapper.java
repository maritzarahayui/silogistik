package apap.ti.silogistik2106751474.dto;

import apap.ti.silogistik2106751474.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751474.model.Gudang;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GudangMapper {
    Gudang createGudangRequestDTOToGudang(CreateGudangRequestDTO createGudangRequestDTO);
    Gudang updateGudangRequestDTOToGudang(UpdateGudangRequestDTO updateGudangRequestDTO);
    UpdateGudangRequestDTO gudangToUpdateGudangRequestDTO(Gudang gudang);
}
