package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/* Regla de negocio
   No permitir agendar citas con pacientes inactivos en el sistema
 */

@Component
public class PacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository repository;

    public void validar(DatosAgendarConsulta datos){
        if(datos.idPaciente() ==null ){
            return;
        }

        /* Una @Query
         búsqueda particular donde no vamos a traer todos los elementos del paciente,
         sino únicamente el parámetro activo, y el parámetro activo es buscando por el ID
         */

        var pacienteActivo=repository.findActivoById(datos.idPaciente());

        if(!pacienteActivo){
            throw new ValidationException("No se puede permitir agendar citas con pacientes inactivos en el sistema");
        }
    }
}
