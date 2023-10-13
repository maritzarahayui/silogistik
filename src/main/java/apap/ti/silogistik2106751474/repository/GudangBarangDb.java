package apap.ti.silogistik2106751474.repository;

import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.GudangBarang;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface GudangBarangDb extends JpaRepository<GudangBarang, Long> {
    List<GudangBarang> findAll();
    Optional<GudangBarang> findById(Long id);
    List<GudangBarang> findByBarang(Barang barang);
}
