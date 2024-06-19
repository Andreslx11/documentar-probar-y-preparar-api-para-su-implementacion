package med.voll.api.medico;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    // en la 5 etapa pra que retorne la pagina de medicos activos
    Page<Medico> findByActivoTrue(Pageable paginacion);
}
