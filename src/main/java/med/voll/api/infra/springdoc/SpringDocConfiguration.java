package med.voll.api.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


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


    // SE COMENTO PORQUE SE AGREGO MAS PERSONALIZADO ABAJO
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                .addSecuritySchemes("bearer-key",
//new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
//    }


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("API Voll.med")
                        .description("API  Rest de  la aplicación Voll.med, que contiene las funcionalidades de  CRUD  de  médicos y pacientes,  así  como  programación  y  cancelación de consultas .")
                        .contact(new Contact()
                                .name("Equipo   Backend")
                                .email("backend@voll.med"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://voll.med/api/licencia")))
                .tags(
                        List.of(
                                new Tag().name("Autenticacion").description("Endpoints relacionados con la autenticación"),
                                new Tag().name("Consultas").description("Endpoints relacionados con las consultas"),
                                new Tag().name("Médicos").description("Endpoints relacionados con los médicos"),
                                new Tag().name("Pacientes").description("Endpoints relacionados con los pacientes")
                        )
                );

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
    public void message() {
        System.out.println("bearer is working");
    }


}