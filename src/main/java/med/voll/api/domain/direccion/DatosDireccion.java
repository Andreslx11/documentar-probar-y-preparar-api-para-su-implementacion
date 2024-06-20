package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;

/*TAMBIEN SE HACEN LAS VALIDACIONES  CON LA dependencia validation atravez de anotaciones*/
public record DatosDireccion(

        @NotBlank
        String calle,

        @NotBlank
        String distrito,

        @NotBlank
        String ciudad,

        @NotBlank
        String numero,

        @NotBlank
        String complemento) {
}
