package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalladoPaciente> registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                            UriComponentsBuilder uriComponentsBuilder){
        var paciente = new Paciente(datosRegistroPaciente);
        pacienteRepository.save(paciente);
        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
//       return ResponseEntity.created(uri).body(pacienteRepository.save(new Paciente(datosRegistroPaciente)));
        return ResponseEntity.created(uri).body(new DatosDetalladoPaciente(paciente));

    }




     @GetMapping
     public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(page=0,size=10, sort= {"nombre"}) Pageable paginacion) {
        var page =  pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new);
        return ResponseEntity.ok(page);

     }


    @PutMapping
    @Transactional
    public ResponseEntity<DatosDetalladoPaciente> atualizarPaciente(@RequestBody @Valid DatosActualizacionPaciente datos) {
        var paciente = pacienteRepository.getReferenceById(datos.id());
        paciente.atualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetalladoPaciente(paciente));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity removerPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inactivar();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity retornarDatosPaciente(@PathVariable Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalladoPaciente(paciente));
    }
}
