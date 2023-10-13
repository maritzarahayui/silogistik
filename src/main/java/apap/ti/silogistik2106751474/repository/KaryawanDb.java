package apap.ti.silogistik2106751474.repository;

import apap.ti.silogistik2106751474.model.Barang;
import apap.ti.silogistik2106751474.model.Karyawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KaryawanDb extends JpaRepository<Karyawan, Long> {
}
