package med.voll.api.direccion;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// se hace por atributo direccion en la clase medico
// recordar que medico y pasientes necesitan una direccion
@Embeddable
//esto es de lombok
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Direccion {


    private String calle;
    private String numero;
    private String complemento;
    private String distrito;
    private String ciudad ;

    public Direccion(DatosDireccion direccion) {
        this.calle= direccion.calle();
        this.numero= direccion.numero();
        this.complemento= direccion.complemento();
        this.distrito= direccion.distrito();
        this.ciudad= direccion.ciudad();
    }

    // de la cuarta etapa para actualizar datos
    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.calle= direccion.calle();
        this.numero= direccion.numero();
        this.complemento= direccion.complemento();
        this.distrito= direccion.distrito();
        this.ciudad= direccion.ciudad();
        return this;
    }
}
