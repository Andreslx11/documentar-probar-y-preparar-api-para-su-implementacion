package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoConsulta(

        Long id,

        @NotNull
        Long idConsulta


) {
}
