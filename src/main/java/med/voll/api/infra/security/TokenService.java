package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    /* para consumir la variable que escribimos en properties (vaiable de entorno),
      se usa la anotaccion @Value  y se pasa la propiedad del propertiespara que obtenga
      el valor esa variable */
    @Value("${api.security.secret}")
    private String apiSecret;


    // recordar imporetante el metodo debe ser llamado en AuntenticacionController

    public String generarToken(Usuario usuario) {
        // codigo fue copiado del repositorio de json web token ath0
        // se modico cosas
        try {
            // se comento por el instructor prefirio usar un metodo mas sencillo
            //Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }





    /* para obtener usuario, ese getSuject es haciendo referencia a withSubjet
    del metodo generar toekn donde se obtiene el usuario
     */

    /* se copia el codigo JWT del repositorio de Auth0
     para verificar token, se cambio como el tipo de algoritmo y se
      paso por parametro el apiSecret

       el Issur se coloco voll med */
    // El metodo debe ser llamdo en SecurityFilter
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(100).toInstant(ZoneOffset.of("-05:00"));
    }

}
