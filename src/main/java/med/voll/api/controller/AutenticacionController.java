package med.voll.api.controller;


import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAutenticacionUsuario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.DatosJWTToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    /*
    Esta clase para que se disapare se inicie el proceso de autenticacion
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /*
    Esta clase para que se disapare se inicie el proceso de autenticacion
     */
    @Autowired
    private TokenService tokenService;

    /*
   IMPORTANTE spring va pedir un AuthenticationManager por eso se crea
   el metodo en infra security que lo de
    */
    /*
    Cuarta clase importante se refactoriza
    token => authToken (aunthencation token)
    se agrega => var JWTtoken = tokenService.generarToken();
    se quita build() a ok() para construir la respuesta y se
    le pasa como parametro a oK() JWTtoken

     */
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        // cuarta clase se cambio
        //    authenticationManager.authenticate(authToken);
        var usuarioAutenticado = authenticationManager.authenticate(authToken);


        /* se agrego en la cuarta clase esto viene ath0 de la clase TokenService
         getPrincipal() para obtener el objeto en esta caso de tipo Usauario
         que el parametro que se le paso generarToken

         se caste ya que usuarioAutenticado devuelve un Objeto tipo Objeto generico
         y el parametro espera un objeto tipo Usuario se caste, que el objeto
         devuelto de usuarioAutenticado.getPrincipal() es un objeto (Usuario)
         de nuestra clase */
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

}
