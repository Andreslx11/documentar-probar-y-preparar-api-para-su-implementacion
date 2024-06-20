package med.voll.api.domain.consulta;


import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

// clase para las reglas de neegocio como los horios

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;


    /*
     Entonces, lo primero que se nos viene a la mente es asignar un atributo del tipo de horarioDeAnticipación
     y colocar la anotación @Autowired que sería privado, horarioDeAnticipación, colocar el nombre del atributo
     y colocar la anotación @Autowired.

     Ahí nosotros tendríamos que hacer eso con cada una de las clases y a la hora que nosotros deseemos
     modificarlo, tendríamos que eliminar el atributo y la clase. Entonces nuevamente estamos violando
     el principio de modificación de open close. Entonces, lo que nosotros vamos a crear es una especie
     similar al Design Pattern Strategy.

     Vamos a crear un atributo que Spring nos permite utilizar, que es nNosotros vamos a crear una lista
     que va a recibir un elemento del tipo ValidadorDeConsulta. Esa lista la vamos a llamar
     validadores. Entonces, de esta forma, nosotros simplemente tenemos una lista, una lista que está
     recibiendo una interfaz.

     Pero cuando yo coloco acá la anotación @Autowired, Spring automáticamente sabe que todos los
     elementos que estén implementando la interfaz ValidadorDeConsultas van a ser inyectados dentro
     de esta lista y van a encontrarse disponibles.

     De esa forma cuando se agreguen nuevas validaciones o se elimine alguna de esas validaciones,
      nosotros no tendríamos que intervenir dentro de la clase de servicio, sino simplemente
       eliminar esa clase y Spring automáticamente sabe que ya esa clase no existe dentro de la lista.

     Entonces, nosotros podríamos agregar 100 clases, eliminar 15 clases y no tendríamos que llevar
      un registro de cuáles clases tenemos que alterar dentro de mi clase de servicio. Entonces, ya
       esta clase se encuentra cerrada para modificación y nos permite crear extensiones para esos
       validadores.

     Otro principio que nosotros estamos creando es el principio de la S, de single
     responsabilty. Nosotros tenemos una única responsabilidad para la clase de servicio,
     que es revisar, recibir ese parámetro y realizar esa consulta y cada una de esas clases tiene
     una única función que es recibir el parámetro y validar.

    */

    // cuando se inyecten el constructor spring va saber que todos los
    // elementos que esten implementando la interface  ValidadorDeConsultar
    // va ser inyectados dentro de esta lista
    @Autowired
    List<ValidadorDeConsultas> validadores;



    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){

        // aqui la var es de tipo Optional y debe ser de tipo paciente para eso .get()
        //  var paciente = pacienteRepository.findById(datos.idPaciente());

        //verificar si el id paciente existe en Base de datos
        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            // exception creada personalizada
            throw new ValidacionDeIntegridad("este id para el paciente no fue encontrado");
        }


        //verificar si el id medico existe en Base de datos
        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("este id para el medico no fue encontrado");
        }

        // validaciones
        validadores.forEach(v-> v.validar(datos));

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        /* se comento porque se debe implementar un metodo por si en la consulta
         no pasan id para medico, escoger uno de la base datos aleatorio */
        // var medico = medicoRepository.findById((datos.idMedico())).get();


        /* Si sino se pasa el idMedico, sino la especialidad en el Json enviado por el cliente
         y si no hay medicos con esa especialidad devolera null lo que causara un error
         porque el campo de abajo de consulta pide un medico no puede ser null,  se debe
         tratar ese error, para eso el if() de adelante */

        var medico = seleccionarMedico(datos);


        if(medico == null){
            throw new ValidacionDeIntegridad("no existen medicos disponibles para este horario y especialidad");
        }

//        var consulta = new Consulta(null,medico,paciente,datos.fecha());
        var consulta = new Consulta(medico,paciente,datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }




    //cancelar consutla
//    public void cancelar(DatosCancelamientoConsulta datos){
//        if(!consultaRepository.existsById(datos.idConsulta())){
//            throw new ValidacionDeIntegridad("Id de la consulta informado no existe");
//        }
//
//        validadoresCancelamiento.forEach(v -> v.validar(datos));
//
//        var consulta =  consultaRepository-getReferenceById(datos.idConsulta());
//        consulta.cancelar(datos.motivo());
//
//    }







    // seleccionar medico aleatorio si en la consulta no pasa el id y si el medico
    // no existe, tener encuenta la expecilidad
    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()  !=  null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if(datos.especialidad() == null){
            throw new ValidacionDeIntegridad("debe seleccionarse una especialidad para el medico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}