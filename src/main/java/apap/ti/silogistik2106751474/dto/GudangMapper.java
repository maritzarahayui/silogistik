package apap.ti.silogistik2106751474.dto;

import apap.ti.silogistik2106751474.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.UpdateGudangRequestDTO;
import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Gudang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GudangMapper {
    Gudang createGudangRequestDTOToGudang(CreateGudangRequestDTO createGudangRequestDTO);

//    @Mapping(target = "nama", source = "nama")
//    @Mapping(target = "alamat_gudang", source = "alamat_gudang")
//    Gudang createGudangRequestDTOToGudang(CreateGudangRequestDTO createGudangRequestDTO);

    Gudang updateGudangRequestDTOToGudang(UpdateGudangRequestDTO updateGudangRequestDTO);
    UpdateGudangRequestDTO gudangToUpdateGudangRequestDTO(Gudang gudang);
}
