package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
 @Component
    para que sea reconocida por spring al momento que la escanie
    es un componente no un servicio ni una configuracion

    Es una clase y bueno, si yo quiero que esta clase sea reconocida por Spring al momento
    en que la escanee, entonces yo debo usar una anotación. Y ahora la pregunta es para ti.
    ¿Qué anotación debería usar? Porque esto no es un servicio, no es un repositorio y tampoco
    es un Controller. Aquí la notación que tendría más sentido component.

   Component es el estereotipo más genérico de Spring para definir simplemente un
   componente de Spring. Spring precisa hacer el escaneo en la clase para incluirlo en su
   contexto. Service, repository y controller son estereotipos basados en componente,
   explicándolo en otras formas.

    En Spring todo podría ser Component. Tú podrías nombrar Component una clase de servicio,
    todo eso, pero para fines de implementar la lectura el código, para un programador
    Spring divide componente en varios estereotipos para especificar Okay, es un componente
    pero un tipo en específico.

    Esto ya es un poco más sobre Springs Corp. cómo funciona pero es muy bueno que lo sepan,
    dado que nuestro filtro no es un servicio ni un repositorio ni nada, vamos a dejarlo como
    Component.
 */





@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    // para forzar sesion de los requests como post obtener lista medico
    @Autowired
    private UsuarioRepository usuarioRepository;



// IMPORTANTE lo comente por necesito refactorizarlo para forzar
// el login del usuario por cada request
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        //System.out.println("El filtro es siendo llamado");
//
//        // obtener el token, llega por defecto en el header Authorization
//        var token = request.getHeader("Authorization");
//        System.out.println("este token paso por SecurityFilter " + token);
//
//     /*     se cambio la logica por si el token venia null dada error entonces nunca se iba logear el
//           usuario debe loguearse autenticarse y luego el token para autorizacion del verbos metodos
//           if (token == null || token == "") {
//
//            throw new RuntimeException("El token enviado no es valido");
//        }*/
//        if (token != null) {
//            System.out.println("validamos que el token no es null");
//            token = token.replace("Bearer ", "");
//            System.out.println(token);
//            System.out.println(tokenService.getSubject(token));  //verificar que ese subject ese usuario si este logeado en mi sistema
//
//
//        }
//        filterChain.doFilter(request, response);
//    }






    // refactorizado codigo de arriba
    /*cuando se menciona "forzar el inicio de sesión o sesión", se está refiriendo
    a la autenticación del usuario en el contexto de seguridad de Spring. */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del header
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token); // extract username
            if (nombreUsuario != null) {
                // Token es valido / para forzar el sesion por cada request
                /*debería ser capaz de retornar mi usuario por login y hacer el
                 llamado al método para que inicie sesión en este momento */
                var usuario = usuarioRepository.findByLogin(nombreUsuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); // Forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
