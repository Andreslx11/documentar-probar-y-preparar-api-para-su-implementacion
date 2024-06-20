package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/*@RestControllerAdvice // tratando error
Atua como un proxy para todos nuestros controller, para intersetar atrapar
las llamadas a caso suceda algun tipo de expcecion

 */


@RestControllerAdvice
public class TratadorDeErrores {


      /*
  ResponseEntity porque en nuestro controller es quien trata las respuestas
  codigos http, los wraper (encasulda) y devuleve a nuestro cliente, entonces como hace
  eso nos sirve para tratar los errores
   */


    /* Para tratar el error cuando nuestro cliente busque un elemento
    que no se encuentra en nuestra base datos y no devuelva un error 500
    que es de servidor sino un error 404 nopt found que error del cliente
    por buscar algo que no existe
    @ExceptionHandler es como los metodos del controller para indicarle
    que expecion debe ser capturada
     */

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }


    /*
    Para cuando nuestro cliente cometa el error de registrar un objeto
    de forma incompleta, para que sea mas amigable con el cliente y no devuelva
    el array normas que no es facil de entender.

    como argumento se pasa la expcecion porque es la que tiene la informacion
    de campos faltan o estan null

    PARA poder personalizar la lista de errores sea mas clara se debe
    crear un DTO , como solo va ser usado a este nivel se crea aqui
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        //   var errores = e.getFieldErrors();
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }



    // atrapa las expceciones ValidacionDeIntegridad que son de la regla de negocio
    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity errorHandlerValidacionesIntegridad(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }



    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }




//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    private record DatosErrorValidacion(String campo, String error){

        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

}