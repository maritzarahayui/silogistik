package apap.ti.silogistik2106751474.repository;

import apap.ti.silogistik2106751474.model.Gudang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GudangDb extends JpaRepository<Gudang, Long> {
    @Query("SELECT g FROM Gudang g WHERE g.nama LIKE %:barang% OR g.alamat_gudang LIKE %:barang%")
    List<Gudang> cariBarang(@Param("barang") String barang);
}
