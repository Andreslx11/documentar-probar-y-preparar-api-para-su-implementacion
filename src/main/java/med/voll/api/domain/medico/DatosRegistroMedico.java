package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.voll.api.domain.direccion.DatosDireccion;

/*  SE HACEN LAS VALIDACIONES ATRAVEZ DE LA DEPENDENCIA VALIDATIOS atravez de anotaciones
ESTO ES LA DEPENDECIA VALITADION QUE SE AGREGO esta "NotNull que espara
que no llegue null , y con @NotBlank es para que no llegue ni nulo ni en blanco*/

public record DatosRegistroMedico(


        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 0, max = 15)
        String telefono,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}") // es para que solo sean numeros de 4 a 6 digitos
        String documento,

        @NotNull
        Especialidad especialidad,

        @NotNull  // este notnull por como es un opbjeto nunca va llegar NotBlank va llegar null
        @Valid
        DatosDireccion direccion) {
}
