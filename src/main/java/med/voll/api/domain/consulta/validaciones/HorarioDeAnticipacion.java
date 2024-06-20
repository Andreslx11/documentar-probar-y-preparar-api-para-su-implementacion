package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;



/* Regla de negocio

Las consultas deben programarse con al menos 30 minutos de anticipación
*/

@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{

    public void validar(DatosAgendarConsulta datos) {

        var ahora = LocalDateTime.now();
        var horaDeConsulta= datos.fecha();


        /*
        .toMinutes(): Convierte esa diferencia de tiempo a minutos. Es decir,
        obtiene el valor numérico de la diferencia en minutos.
         */
        var diferenciaDe30Minutos= Duration.between(ahora,horaDeConsulta).toMinutes()<30;
        if(diferenciaDe30Minutos){
            throw new ValidationException("Las consultas deben programarse con al menos 30 minutos de anticipación");
        }
    }
}
