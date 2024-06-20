package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

/*  Para la regla de negocio
    El horario de atención de la clínica es de lunes a sábado,
    de 07:00 a 19:00 horas; */

@Component
public class HorarioFuncionamiento implements ValidadorDeConsultas{

    // validar que no sea domingo y este en horario atencion de la clinica
    public void validar(DatosAgendarConsulta datos) {

        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

        var antesdDeApertura=datos.fecha().getHour()<7;
        var despuesDeCierre=datos.fecha().getHour()>18;

        if(domingo || antesdDeApertura || despuesDeCierre){
            throw  new ValidationException("El horario de atención de la clínica es de" +
                    " lunes a sábado, de 07:00 a 19:00 horas");
        }
    }



}
