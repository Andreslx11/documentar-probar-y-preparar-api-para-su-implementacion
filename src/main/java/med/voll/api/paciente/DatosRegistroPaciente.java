package med.voll.api.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.direccion.DatosDireccion;

public record DatosRegistroPaciente(


        @NotBlank
        String nombre,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefono,

        @NotBlank
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
        String documento,

        @NotNull
        @Valid
        DatosDireccion direccion


) {


}

//    {
//            "nombre": "Andres Muñoz",
//            "email": "andres.muñoz@voll.med",
//            "telefono": "1235",
//            "documento": "1234",
//            "direccion": {
//            "calle": "calle 86 #",
//            "distrito": "distrito 40",
//            "ciudad": "pasto",
//            "numero": "70",
//            "complemento": "19"
//            }
//            }
