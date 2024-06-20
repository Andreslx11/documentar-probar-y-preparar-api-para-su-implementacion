package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*  Regla de negocio:
    No permitir programar citas con médicos inactivos en el sistema;
 */
@Service
public class MedicoActivo implements ValidadorDeConsultas{

    // inyeccion de dependencia
    @Autowired
    private MedicoRepository repository;

    public void validar(DatosAgendarConsulta datos) {
        if(datos.idMedico() == null){
            return;
        }
        var medicoActivo=repository.findActivoById(datos.idMedico());
        if(!medicoActivo){
            throw new ValidationException("No se puede permitir agendar citas con medicos inactivos en el sistema");
        }
    }
}
