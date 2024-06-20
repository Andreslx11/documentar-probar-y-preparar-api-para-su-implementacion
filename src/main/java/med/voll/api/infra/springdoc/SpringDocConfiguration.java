package med.voll.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SpringDocConfiguration {


    /*
     esta clase, al importar el bean nos va a retornar un elemento del tipo OpenAI, con
     un componente que va a recibir una llave de identificación llamado bearer-key, que
     es el que vamos a pasar a cada uno de nuestros controladores indicándole cuál va a
      ser el esquema de seguridad.

     El esquema de seguridad es del tipo bearer de Json Web Token. Si nosotros revisamos
      en el Postman, nosotros tenemos acá diferentes tipos de autorizaciones y la que
      nosotros estamos utilizando es bearer Json web token. Entonces, acá nosotros
      colocamos el token y luego de eso nosotros conseguimos hacer las consultas de
      los endpoints.

     Adicional, el método bean ejecuta de forma automática ese método, pero para que
     esta clase se encuentre en el contexto, tenemos que colocar la anotación
     @Configuration indicando que esta clase también va a ser autoinstanciada
     en el contexto de Spring framework.
     */

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                .addSecuritySchemes("bearer-key",
new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }







    /*  MUY IMPORTANTE
      @SecurityRequirement(name = "bearer-key")
     Ya tenemos el mensaje. Ahora en la documentación de Springdoc nos indica que otro
     de los elementos que tenemos que hacer es agregarle la anotación requerimientos
     de seguridad en cada uno de los controladores que van a solicitar ese token.

      Entonces vamos a ir a los controladores. En el controlador de autenticación,
      nosotros no lo necesitamos colocar, ya que nosotros le damos acceso a la
      autenticación a través de las configuraciones de seguridad. Entonces los
      controladores que necesitan acceso a ese token son las consultas, el controlador
      de médicos y el controlador de pacientes.

     */












    @Bean
    public void message(){
        System.out.println("bearer is working");
    }
}
