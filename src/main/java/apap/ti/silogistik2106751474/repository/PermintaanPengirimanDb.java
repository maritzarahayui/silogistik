package apap.ti.silogistik2106751474.repository;

import apap.ti.silogistik2106751474.model.PermintaanPengiriman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermintaanPengirimanDb extends JpaRepository<PermintaanPengiriman, Long> {
    List<PermintaanPengiriman> findAll();
    Optional<PermintaanPengiriman> findById(Long id);

    @Query("SELECT p FROM PermintaanPengiriman p WHERE p.is_cancelled = false")
    List<PermintaanPengiriman> findByIsCancelledFalse();
}
