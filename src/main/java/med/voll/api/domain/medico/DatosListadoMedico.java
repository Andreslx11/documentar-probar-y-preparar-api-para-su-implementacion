package med.voll.api.domain.medico;

public record DatosListadoMedico(

        Long id,

        String nombre,

        String especialidad,

        String documento,

        String email) {


    // en la cuarta etapa para actualizar medicos con verbo Put en htttp
    // se le le añadio el atrbituo id  a este DO
    public DatosListadoMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getEspecialidad().toString(), medico.getDocumento(), medico.getEmail());
    }
}


