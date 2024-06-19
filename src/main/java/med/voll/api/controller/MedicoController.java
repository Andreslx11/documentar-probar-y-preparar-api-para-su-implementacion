package med.voll.api.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    // Esta forma inyeccion de dependencia es de forma
    // dicdatica no recomendable , por que puede causar problemas
    // en en testing en pruebas unitarias
    @Autowired
    private MedicoRepository medicoRepository;

    /*
     se agrego la anotacion valid de la depencia validation para las validaciones
     para que todo sea valido

     */
    /*
    POr buenas praticas:
    * return el objeto creado
    * return codigo 201 created
    * return la URl   en un headcer donde encontrar al medico
       ejemplo: get http://localhost:8080/medicos/id

    * se reemplaza void por responseEntity

    * para obtener la url de forma dinamica spring boot nos ayuda con una
    clase  UriComponentsBuilder que va en los parametros

     */

    /*
     *Claro, vamos a explicar cada parte de esa línea de código:
    URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
    uriComponentsBuilder: Es una clase de Spring que permite construir URIs de manera programática.
    .path("/medicos/{id}"): Establece la ruta base de la URI, donde {id} es un parámetro que se
    *  reemplazará con el ID del médico.
    .buildAndExpand(medico.getId()): Reemplaza el parámetro {id} con el ID del médico recién creado.
    .toUri(): Convierte el objeto construido a una instancia de URI.

    En resumen, esta línea de código construye la URI completa del recurso médico recién creado, siguiendo
    *  el patrón /medicos/{id}.
    * return ResponseEntity.created(url).body(datosRespuestaMedico);
    ResponseEntity.created(url): Crea un objeto ResponseEntity con el código de estado HTTP 201 (Created)
    * y establece la URI del recurso creado en el header de la respuesta.
    .body(datosRespuestaMedico): Agrega el objeto DatosRespuestaMedico al cuerpo de la respuesta.



    En conjunto, estas líneas de código:
    Construyen la URI completa del recurso médico recién creado.
    Crean un objeto ResponseEntity con el código de estado HTTP 201 (Created), la URI del recurso en
    * el header y el objeto DatosRespuestaMedico en el cuerpo.
    Devuelven este ResponseEntity como resultado del método registrarMedico.

    De esta manera, el cliente que realiza la solicitud POST recibirá una respuesta con
    *  el código 201 (Created), la URI del recurso creado en el header y los datos del
    * médico en el cuerpo.¿Tienes alguna otra duda sobre esta implementación?
     */

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> regristrarMedico(
            @RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
            UriComponentsBuilder uriComponentsBuilder
    ) {
            /*
             daba error por el parametro que espera es Medico
               medicoRepository.save(datosRegistroMedico);
             */
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
            /*
            esta seria una forma para url pero no seria una buena pratica porque la url quedaria estactica
            y si cambia de servidor habria problemas
            URI url = URI.create("http://localhost:8080/medicos" + medico.getId());
             */

        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);

    }


     /* se comento porque le van hacer cambios para la paginacion
    // get request
    @GetMapping
    public List<DatosListadoMedico> listadoMedicos() {
        return medicoRepository.findAll().stream()
                .map(DatosListadoMedico::new).toList();
    }
     */

    /*
     se cambio de List a Page y Pageable es algo que nos retorna del frontend
     @PageableDefault()  es para modificar directamente los query params
     de aqui y no necesariamente en get http en url en insomnia
     */
    /*En aula 1.3 se hizo a  Page<DatosListadoMedico>  se le paso por ResponseEntity */

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 10) Pageable paginacion) {
        /*  se comento en la 5 etapa de Delet logico para listar los medicos activos
           return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new); */
        //return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new)) ;

    }

      /*
       para actualizar datos con el verbo put
       se debe crear un DTO propio para actualizar
       se busca al medico que se va actualizar los datos
       @Transactional  para que cierre la transacion y el medico actualice los datos , buscar
       infromacion de esta anotacion,  para cuando  JPA va  a mapear que cuando termine el metodo
       la transacion se va liberar
     */
    /*
     * De esta clase, por buena pratica http request se debe retornar el objecto que se actualizo
     * con el codigo
     *
     * se cambia el void por un ResponseEntity
     * */

    // ok es un codigo 200
    /*
    * Entendido, aquí una explicación breve:
    ResponseEntity.ok(): Crea una respuesta HTTP con código de estado 200 (OK).
    new DatosRespuestaMedico(...): Crea un nuevo objeto DTO DatosRespuestaMedico con los datos del médico.
    return: Devuelve la respuesta HTTP con el objeto DTO en el cuerpo.

    En resumen, ResponseEntity.ok(new DatosRespuestaMedico(...)) crea y devuelve una respuesta HTTP
    * con código 200, incluyendo un objeto DTO con los datos del médico.
    * */

    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosAtualizarMedico) {

        Medico medico = medicoRepository.getReferenceById(datosAtualizarMedico.id());
        medico.actualizarDatos(datosAtualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }


    /*  DELETE EN BASE DE DATOS
     SE COMENTO POR ES USAR UN METODO LOGICO PAR NO ELIMINAR LOS REGISTROS de los
     medicos si para lista los activos
     en la 5 etapa eliminar
      ese id en /id  es una variable de path
      */
     /*
    @PathVariable para decirle que esa varible es del path variable, para que identifique cual esa variable
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminaMedico(@PathVariable Long id){
        Medico  medico = medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
   }
*/

    /* DELETE LOGICO
     no va eliminar el registro de la base de datos si no que va cambiar el estado
     del medico de activo, por que en un futuro puede ser util saber que medicos ha trabajado
     en la clinica

     */

    /*
     * De este curso, se cambia el retorno void por ResponseEntity para retornar
     * al cliente un codigo http para informarlo
     *
     * noContent va setair el codigo en http pero se necesita construirlo*/

    /*
     * Bien, la línea return ResponseEntity.noContent().build(); se utiliza para devolver
     * una respuesta HTTP con un código de estado 204 (No Content) en un API REST.Esto se
     * usa cuando una operación, como la eliminación de un recurso, se ha completado
     * exitosamente, pero no hay contenido que devolver en la respuesta.En resumen,
     * ResponseEntity.noContent().build() crea una respuesta HTTP con el código de estado 204,
     *  indicando que la operación se realizó correctamente, pero sin incluir ningún cuerpo de
     * respuesta.*/
    /*
    * Entendido, aquí una explicación más detallada pero breve:
      ResponseEntity: Es una clase de Spring que permite personalizar la respuesta HTTP.
     .noContent(): Es un método estático que crea un ResponseEntity con el código de estado 204 (No Content).
     .build(): Construye y devuelve el objeto ResponseEntity con el código de estado 204.
      En resumen, ResponseEntity.noContent().build() crea y devuelve una respuesta HTTP con código
      de estado 204, indicando que la operación se completó exitosamente sin contenido que devolver.
      * */
    // noContent es un codigo 204 no Content

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminaMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }



    // para retornar los datos de un solo medico
    @GetMapping("/{id}")
    public ResponseEntity retornardatosMedico(@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }
}
