package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
@Configuration esta anotacion es para que spring lo cargue de primero porque es una configuracion
para el servicio.
@EnableWebSecurity habalita modulo web security para esta class de configuracion,
para indicarle que este metodo securityFilterChain esta siendo utilizado para sobre escribir el
comportamiento de autenticacion que queremos
 */

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;




     /*
    para evitar suplatacion de identidad
    CSRF (Cross-Site Request Forgery) es un tipo de ataque web en el que un usuario malicioso
    puede engañar a un usuario legítimo para que realice acciones no deseadas en una aplicación
    web a la que el usuario está autenticado.

    csrf() esta disable() por que estamos usando stateless usando token y ya nos protege cuantra
    ataques CSRF (Cross-Site Request Forgery), csrf() se usa para statefull,


    La anotación @Bean se utiliza para indicar que un método de una clase genera un objeto que
     debe ser administrado por el contenedor de Spring. Esto significa que Spring se encargará
     de crear e inyectar ese objeto en donde sea necesario.
     */

//QUEDO OBSOLETO EN LA VERSION SPRING BOOT 3.0.0 pero me funciona
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.csrf(csrf -> csrf.disable())
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();
//    }



/* asi es antes de la version de spring 3.1
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().build();
    }

*/




// .requestMatchers(HttpMethod.GET, "/medicos").authenticated()

// IMPORTANTE la nueva forma
    /*
    IMPORTANTE

    // cuando se menciona "forzar el inicio de sesión o sesión", se está refiriendo a la
     //autenticación del usuario en el contexto de seguridad de Spring.


     EL PROBLEMA que cuando hacia en insomnia un post obtner lista medicos y nos daba un
     404 forbidden y en la terminal intellij no salia, era porque no esta pasando por nuestro
     filter doFilterInternal  de la class SecurityFilter donde validamos el token sino que pasaba
     primero por esta clase metodo  securityFilterChain, para eso se coloco
         .and()
          .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

     para que antes de este filtro de spring haga primero el nuestro securityFilter
     y UsernamePasswordAuthenticationFilter.class Esto es porque este tipo de filtro
     lo que va a hacer es validar que en efecto el usuario que está iniciando la sesión
     existe y que ya está autenticado SERIA STATEFULL, EL PROBLEMA LO NUESTRO ES STATELES
     entonces debemos forzar el login del usuario por cada request toca refatorizar
     en doFilterInternal

     despues de la refactorizacion en doFilterInternal , en esta clase despues
     para los ndemas request el usuario ya va estar autentificado

      .anyRequest()
       .authenticated()

     */


   // .requestMatchers(HttpMethod.GET, "/medicos").authenticated()
    // IMPORTANTE la nueva forma
    /*
    IMPORTANTE

    // cuando se menciona "forzar el inicio de sesión o sesión", se está refiriendo a la
     //autenticación del usuario en el contexto de seguridad de Spring.


     EL PROBLEMA que cuando hacia en insomnia un post obtner lista medicos y nos daba un
     404 forbidden y en la terminal intellij no salia, era porque no esta pasando por nuestro
     filter doFilterInternal  de la class SecurityFilter donde validamos el token sino que pasaba
     primero por esta clase metodo  securityFilterChain, para eso se coloco
         .and()
          .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

     para que antes de este filtro de spring haga primero el nuestro securityFilter
     y UsernamePasswordAuthenticationFilter.class Esto es porque este tipo de filtro
     lo que va a hacer es validar que en efecto el usuario que está iniciando la sesión
     existe y que ya está autenticado SERIA STATEFULL, EL PROBLEMA LO NUESTRO ES STATELES
     entonces debemos forzar el login del usuario por cada request toca refatorizar
     en doFilterInternal

     despues de la refactorizacion en doFilterInternal , en esta clase despues
     para los ndemas request el usuario ya va estar autentificado

      .anyRequest()
       .authenticated()

     */
//   @Bean
//   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       return http.csrf().disable()
//               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//le indicamos el tipo se seccion
//               .and().authorizeHttpRequests()
//               .requestMatchers(HttpMethod.POST, "/login").permitAll()
//               .anyRequest()
//               .authenticated()
//               .and()
//               .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//               .build();
//   }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    // Es para inyectar dependencia en AutenticacionController para que spring
    // lo pueda encontrar
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // passwordEncoder= codificador de contraseñascodificador de contraseñas
    /*
    BCryptPasswordEncoder es una implementación de la interfaz PasswordEncoder
    en Spring Security, que usa hash BCrypt
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }









/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Le indicamos a Spring el tipo de sesion
                .and().authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    */





}
