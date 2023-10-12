package apap.ti.silogistik2106751474.repository;

import apap.ti.silogistik2106751474.model.PermintaanPengiriman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermintaanPengirimanDb extends JpaRepository<PermintaanPengiriman, Long> {

}
