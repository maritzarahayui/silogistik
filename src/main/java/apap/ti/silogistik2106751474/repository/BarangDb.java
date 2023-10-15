package apap.ti.silogistik2106751474.repository;

import apap.ti.silogistik2106751474.model.Barang;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface BarangDb extends JpaRepository<Barang, String> {
    List<Barang> findAll();
    Barang findBySku(String sku);
}
