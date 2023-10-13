package apap.ti.silogistik2106751474;

import apap.ti.silogistik2106751474.dto.GudangMapper;
import apap.ti.silogistik2106751474.dto.KaryawanMapper;
import apap.ti.silogistik2106751474.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.CreateKaryawanRequestDTO;
import apap.ti.silogistik2106751474.service.GudangService;
import apap.ti.silogistik2106751474.service.KaryawanService;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class Silogistik2106751474Application {

	public static void main(String[] args) {
		SpringApplication.run(Silogistik2106751474Application.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(GudangService gudangService, GudangMapper gudangMapper, KaryawanService karyawanService, KaryawanMapper karyawanMapper){
		return args -> {
			var faker = new Faker(new Locale("in-ID"));

			var gudangDTO = new CreateGudangRequestDTO();
			var fakeNamaGudang = faker.company().name();
			var fakeAlamatGudang = faker.address().fullAddress();

			gudangDTO.setNama(fakeNamaGudang);
			gudangDTO.setAlamat_gudang(String.valueOf(fakeAlamatGudang));

			var gudang = gudangMapper.createGudangRequestDTOToGudang(gudangDTO);
			gudangService.saveGudang(gudang);

			var karyawanDTO = new CreateKaryawanRequestDTO();

			karyawanDTO.setJenis_kelamin(1);
			karyawanDTO.setNama(faker.name().fullName());
			karyawanDTO.setTanggal_lahir(faker.date().birthday(18, 55));

			var karyawan = karyawanMapper.createKaryawanRequestDTOToKaryawan(karyawanDTO);
			karyawanService.saveKaryawan(karyawan);
		};
	}

}
