package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(

        @NotNull
        Long idPaciente,

        Long idMedico,

        @NotNull
        @Future // para indicar que la fecha sea una fecha posterior a la fecha actual
        LocalDateTime fecha,


        // no se usa la anotacion   @NotNull por que no devolveria la expecion al cliente
        // por se trata con la excepcion creada en AgendarConsultarService
        Especialidad especialidad ) {

}
