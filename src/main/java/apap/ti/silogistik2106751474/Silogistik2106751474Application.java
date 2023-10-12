package apap.ti.silogistik2106751474;

import apap.ti.silogistik2106751474.dto.GudangMapper;
import apap.ti.silogistik2106751474.dto.request.CreateBarangRequestDTO;
import apap.ti.silogistik2106751474.dto.request.CreateGudangRequestDTO;
import apap.ti.silogistik2106751474.service.GudangService;
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
	CommandLineRunner run(GudangService gudangService, GudangMapper gudangMapper){
		return args -> {
			var faker = new Faker(new Locale("in-ID"));

			var gudangDTO = new CreateGudangRequestDTO();
			var fakeNamaGudang = faker.company().name();
			var fakeAlamatGudang = faker.address().fullAddress();

			gudangDTO.setNama(fakeNamaGudang);
			gudangDTO.setAlamat_gudang(String.valueOf(fakeAlamatGudang));

			var gudang = gudangMapper.createGudangRequestDTOToGudang(gudangDTO);
			gudangService.saveGudang(gudang);
		};
	}

}
