package med.voll.api.controller;

import jakarta.validation.Valid;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/*
estas anotaciones lo que indica es que son componentes gerenciados por Spring.
 ¿Qué significa eso? Que ya no tenemos que usar la palabra new ConsultaController
  para poder hacer uso de esa clase, sino que, como ya no tenemos que realizar
  el uso de la palabra new, Spring automáticamente él instancia esa clase y la
  deja disponible para nuestro uso.

 */

/*
@RestController  esta compuesta por @Controller y @ResponseBody
podemos usar @RestController  o las dos anoataciones juntas @Controller y @ResponseBody
 */
@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    //para la inyeccion de depencias por contructor
    @Autowired
    private AgendaDeConsultaService service;

    //metodo para agendar las consultas
    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos) throws ValidacionDeIntegridad {
        //System.out.println(datos);
        var response = service.agendar(datos);

        return ResponseEntity.ok(response);
    }
}