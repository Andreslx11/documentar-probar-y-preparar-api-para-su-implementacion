package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Recordatorio que tenemos que hacer una relaccion entre la entidad consulta
    // y la tabla de medico y paciente por eso usamos    @JoinColumn(name = "medico_di")

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;

//    @Column(name = "motivo_cancelamento")
//    @Enumerated (EnumType.STRING)
//    private MotivoCancelaniento motivoCancelaniento;



    public Consulta(Medico medico, Paciente paciente, LocalDateTime fecha){
            this.medico = medico;
            this.paciente = paciente;
             this.fecha = fecha;

           }


//public void cancelar(MotivoCancelaniento motivo) {
//    this.motivoCancelaniento = motivo;


}