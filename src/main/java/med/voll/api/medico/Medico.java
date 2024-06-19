package med.voll.api.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.direccion.Direccion;




//esto es de lombok

// va usar el parametro id para las comparaciones entre medicos ,. es lombok tambien
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "medico")
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long   id;
    private  String nombre;
    private  String  email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    // en la clase Direccion se hace  la anotacion @Embeddable
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo= true;
        this.nombre= datosRegistroMedico.nombre();
        this.email= datosRegistroMedico.email();
        this.telefono= datosRegistroMedico.telefono();
        this.documento= datosRegistroMedico.documento();
        this.especialidad=datosRegistroMedico.especialidad();
        this.direccion= new Direccion(datosRegistroMedico.direccion());

    }

    // se crea en al cuarta etapa para actualizar los datos de un medico
    // por medio de request verbo Put de http
    // se crea el metodo actualizarDatos en clase direccion
    public void actualizarDatos(DatosActualizarMedico datosAtualizarMedico) {
        if(datosAtualizarMedico.nombre() != null){
            this.nombre= datosAtualizarMedico.nombre();
        }
        if(datosAtualizarMedico.documento() != null){
            this.documento= datosAtualizarMedico.documento();
        }
        if(datosAtualizarMedico.direccion() != null){
            this.direccion=direccion.actualizarDatos(datosAtualizarMedico.direccion());
        }



    }

    public void desactivarMedico() {
        this.activo= false;
    }
}
