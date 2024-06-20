package med.voll.api.infra.errores;




/*  tenemos la clase throwable y tenemos la clase RunTimeException. La diferencia entre una
     y otra es que la clase throwable responde ante errores y excepciones, y RunTimeException
     solo responde ante excepciones.

     Cuando nosotros usamos el Throwable, tenemos que agregar dentro del método un
     throw. Entonces, ahora, acá en este constructor, déjame mostrar ese throwable. Entonces,
     si coloco acá, voy a enviar ese mensaje a la clase madre utilizando la palabra super. Y
     acá vamos a ver lo que estoy diciendo del throwable./

*/
public class ValidacionDeIntegridad extends RuntimeException {


    public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
