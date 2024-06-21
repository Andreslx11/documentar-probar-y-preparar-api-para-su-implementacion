package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;



/*IMPORTANTE
 anotación @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) se utiliza para:

Evitar que Spring Boot reemplace la configuración de base de datos existente en tu
 aplicación. Permitir el uso de una base de datos externa durante la ejecución de las pruebas
 unitarias. Esto es especialmente útil cuando quieres asegurarte de que tus pruebas unitarias
se ejecuten contra la misma configuración de base de datos que tu aplicación de
producción, lo que ayuda a garantizar la consistencia y la fiabilidad de tus pruebas.
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")// para que use el archivo application-test.propesties
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;



    /*
    El TestEntityManager es una clase proporcionada por Spring Boot para facilitar la realización de
     pruebas unitarias que involucran el acceso a la base de datos.En resumen:
    Es una implementación especial del EntityManager que se utiliza únicamente en el contexto de las pruebas.
    Permite crear, persistir y consultar entidades de la base de datos sin necesidad de tener una base de
     datos real configurada.
    Maneja automáticamente la creación y destrucción de la base de datos de prueba, evitando que las pruebas
     interfieran con la base de datos de producción.
    Simplifica la configuración de la base de datos de prueba, ya que Spring Boot se encarga de inyectarlo
     automáticamente en la clase de prueba.

    Esto permite que las pruebas unitarias que involucran el acceso a la base de datos se puedan ejecutar
    de manera rápida y confiable, sin depender de una base de datos externa.
     */
    /*
     El entity manager es el encargado de realizar el puente entre nuestra aplicación y la base de datos. Cuando
     nosotros utilizamos el entity manager en las aplicaciones, nosotros tenemos que instanciarlo a través de a
      través del Entity Manager Factory.

     Pero acá, Spring framework nos permite utilizar el TestEntityManager, que él se va a autoinstanciar
      únicamente para realizar las pruebas. Entonces solamente para ambientes de prueba, nosotros vamos a
      tener este TestEntityManager que es un gerenciador que cuando nosotros ejecutemos la prueba él va a
      estar activo.

     Él va a realizar todas las operaciones donde va a crear una instancia y va a hacer esa conexión con
     esa base de datos. Vamos a llamar a este EntityManager em y vamos a inyectarlo con la anotación
      @Autowired. Entonces ya tenemos el EntityManager funcionando.

     Estamos realizando una persistencia para esa consulta, donde recibimos un médico, un paciente y
     la fecha, que sería el próximo lunes. Estamos registrando también un médico, acá en médico voy
     a registrar un médico y los valores para ese médico serían el nombre, el email, el documento y
     la especialidad.
     */
    @Autowired
    private TestEntityManager em;


    // Fases de una prueba
    // give => dado un conjunto de valores, que le pasamos
    // when =>   cuando se realiza alguna acción, la acción que vamos testiar
    // then => debe suceder o vamos verificar





    //Escenario o caso uno
    @Test
    @DisplayName("deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFecha() {

        // Fase de prueba => given

        //El horario par nuestro metodo de prueba
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        // IMPORTANTE EN var medico Especialidad.CARDIOLOGIA  y en var medicolibre
        // Especialidad.CARDIOLOGIA  entonces en la base de datos solo vamos a tener
        // un nmedico con esa especialidad por eso en assertThat(medicoLibre).isNull();
        // nos debe dar null y asi pasar el test



        // registrar medico en base de datos
        var medico= registrarMedico("Jose","j@mail.com","123456",Especialidad.CARDIOLOGIA);

        // registrar paciente en base de datos
        var paciente= registrarPaciente("antonio","a@mail.com","654321");

        // registramos esa consulta entre medico y paciente anteriores
        registrarConsulta(medico,paciente,proximoLunes10H);



        //Fase de prueba =>  when
        // obrteniendo un medico libre debe dar null
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);



        /*
        Esa línea de código utiliza el método assertThat() de la librería de aserciones de JUnit
         para verificar si la variable medicoLibre es nula.Específicamente:
        assertThat(medicoLibre): Toma la variable medicoLibre y la pasa como argumento al método assertThat().
        .isNull(): Es un método de aserción que verifica si el valor pasado como argumento es nulo. En
         este caso, está verificando si medicoLibre es nulo.

         Si medicoLibre es nulo, la aserción pasará y la prueba será exitosa. Si medicoLibre no es nulo,
         la aserción fallará y la prueba fallará.Este tipo de aserción se utiliza para verificar que
         el método findActivoById() del repositorio de médicos retorna nulo cuando el médico no está
         disponible en el horario solicitado.
         */
        /*
        Para realizar las pruebas nosotros tenemos de JUnit las diferentes clases estáticas
         de las clases de assertion. Esas clases nos permiten hacer diferentes comparaciones y retorna verdadero o falso.

         Eso en caso de que sea verdadero, de que se cumpla esa condición, esa assertion, la prueba pasa efectivamente,
         en caso de que no se cumpla esa prueba que estamos realizando con los assert, él va a fallar. Nosotros vamos a
          utilizar, vamos a probar que ese médico libre, sea nulo.

         Entonces nosotros vamos a importar del paquete de assertion, todo lo que sería la
         librería que nos va a permitir hacer las diferentes comparaciones, entonces aquí estamos
         comparando ese valor de médico que estamos trayendo de la base de datos utilizando el repositorio, y vamos a
         verificar si ese valor es nulo.

        Para verificar si es nulo, nosotros primero tenemos que registrar un médico, tenemos que
         registrar un paciente y tenemos que registrar una consulta, nosotros vamos a realizar toda
         la operación donde registramos un médico, registramos un paciente y vamos a crear una consulta
          con ese médico y con ese paciente para que ella, luego de que se encuentre en la base de datos
           cuando realice la búsqueda, se va a encontrar de que ese médico no se encuentra disponible y nos
           va a retornar nulo.
         */

        //Fase de prueba => then
        // verificar si ese valor es null
        assertThat(medicoLibre).isNull();
    }




    @Test
    @DisplayName("deberia retornar un medico cuando realice la consulta en la base de datos  en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {

        //Fase de prueba =>  given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico=registrarMedico("Jose","j@mail.com","123456",Especialidad.CARDIOLOGIA);


        //Fase de prueba =>  when
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);


        //Fase de prueba =>  then
        assertThat(medicoLibre).isEqualTo(medico);
    }




  // va registrar en la base datos
    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(null, medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }



    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }

        private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "61999999999",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );

//
//    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
//        em.persist(new Consulta(null, medico, paciente, fecha, null));
//    }
//
//
//
//    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
//        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
//        em.persist(medico);
//        return medico;
//    }
//
//
//
//    private Paciente registrarPaciente(String nombre, String email, String documento) {
//        var paciente = new Paciente(datosPaciente(nombre, email, documento));
//        em.persist(paciente);
//        return paciente;
//    }
//
//
//
//    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
//        return new DatosRegistroMedico(
//                nombre,
//                email,
//                "61999999999",
//                documento,
//                especialidad,
//                datosDireccion()
//        );
//    }
//
//
//
//    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
//        return new DatosRegistroPaciente(
//                nombre,
//                email,
//                "61999999999",
//                documento,
//                datosDireccion()
//        );
//    }
//
//    private DatosDireccion datosDireccion() {
//        return new DatosDireccion(
//                " loca",
//                "azul",
//                "acapulpo",
//                "321",
//                "12"
//        );
//    }

}



}