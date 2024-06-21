package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetalleConsulta;
import med.voll.api.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



// LO QUE VAMOS A TESTEAR
/*LO QUE VAMOS A TESTEAR
  Entonces, ahora el siguiente componente que tenemos que probar serían
  los controladores, y para eso nosotros vamos a seleccionar la consulta,
  el controlador de las consultas, específicamente el método
  agendarConsulta. Entonces nosotros vamos a verificar que a la hora de
  recibir los valores de la petición, cuáles son los estados que estamos
  retornando y que los valores que nosotros estemos recibiendo de esa
  requisición sean correctos.
 */


//@SpringBootTest
/*  @SpringBootTest
   En la parte interior nosotros habíamos trabajado con @DataJpaTest, que es la anotación,
   que nos permite trabajar con accesos a la base de datos y consultas. Acá vamos a trabajar
   con @SpringBootTest que es una anotación que nos permite trabajar con todos los
   componentes dentro del contexto de Spring, entonces acá nosotros podemos utilizar
   repositorios, los servicios y los controladores.
 */

//@AutoConfigureMockMvc
/*
        @AutoConfigureMockMvc es una anotación de Spring Boot que configura automáticamente
        el entorno necesario para utilizar MockMvc en las pruebas.Principales funciones:
        Configura MockMvc para simular solicitudes HTTP a los controladores.
        Inyecta el objeto MockMvc en la clase de prueba.
        Prepara el contexto de Spring para que los controladores puedan ser probados.

        En resumen, @AutoConfigureMockMvc simplifica la configuración necesaria para
         utilizar MockMvc en las pruebas unitarias de controladores en Spring Boot.
 */


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters //hace  todas las configuraciones relacionadas al parametro JacksonTester
class ConsultaControllerTest {

    // DESAFIO
    /*
     Nosotros ya con esto finalizamos lo que serían las pruebas, está faltando únicamente
     la prueba 404. Recordando que el estado 404 es cuando nosotros hacemos una petición
     en la base de datos buscando un valor y esa página no se encuentra o no existe.

     Entonces, una forma de hacerlo es buscando alguna de esas consultas con el ID
     incorrecto que va a retornar el estado 404, entonces lo dejo como desafío




     Claro, te explico lo que se menciona sobre la prueba del estado 404:El estado
     HTTP 404 significa "Not Found", es decir, que se está intentando acceder a un
     recurso que no existe en el servidor.En el contexto de la aplicación de Spring
     Boot que se está probando, un escenario donde se podría obtener un 404 sería
     cuando se intenta buscar una consulta en la base de datos utilizando un ID que
     no existe.El instructor menciona que la prueba del estado 404 se deja como un
     desafío. Esto significa que el estudiante debe implementar esta prueba por su
     cuenta.Una forma de hacerlo sería:
    Crear un método de prueba que simule una solicitud a un endpoint que busca una
    consulta por un ID que no existe.
    Configurar el mock del servicio AgendaDeConsultaService para que, cuando se
    llame al método que busca la consulta por ID, devuelva un valor nulo o lance
    una excepción que indique que el recurso no fue encontrado.
    Verificar que la respuesta de la aplicación tenga un estado HTTP 404.

    De esta manera, se estaría probando el comportamiento de la aplicación cuando
    se intenta acceder a un recurso que no existe en la base de datos, lo cual es
    un escenario importante de cubrir en las pruebas unitarias.El instructor deja
    esto como un desafío para que el estudiante pueda practicar y aplicar los
    conceptos aprendidos en las pruebas anteriores.
     */





    // lo que se va testear hacer
    /*
    Ahora nosotros tenemos que verificar los diferentes estados de que nosotros podemos
    retornar cuando realizamos una petición.

    Entonces, cuando nosotros realizamos una requisición, tenemos el estado 400 en caso
    de que los valores sean inválidos, el estado 404 en el caso de que el usuario no
     haya sido encontrado, el estado 403 cuando la autorización no ha sido pasada que
     es cuando nosotros no pasamos el token y el estado 200, que es cuando el usuario
     ha sido encontrado exitosamente.

    Entonces yo voy a trabajar con el primer escenario, y para indicar cuál va a ser ese
      escenario voy a colocar acá escenario 1 y vamos a colocar la anotación @DisplayName
     una descripción de ese método. Entonces “Debería retornar estado http 400 cuando los
     datos ingresados sean inválidos”.
     */


    // para simular una peticion http
    /*
    MockMvc es una herramienta proporcionada por Spring Boot que permite simular y probar controladores web sin
     necesidad de levantar un servidor web completo.

     En el contexto del curso de Spring Boot 3, el instructor está utilizando MockMvc para realizar pruebas
     unitarias de los controladores de la aplicación. Esto permite verificar el comportamiento de los controladores
     de manera aislada, sin tener que interactuar con otros componentes como los servicios o repositorios.

   Algunas de las principales características y usos de MockMvc son:

   Simulación de solicitudes HTTP: MockMvc permite enviar solicitudes HTTP (GET, POST, PUT, DELETE, etc.)
   a los controladores de la aplicación, como si fueran solicitudes reales.

   Verificación de respuestas: Después de enviar la solicitud, MockMvc permite verificar el estado HTTP,
   los encabezados y el contenido de la respuesta devuelta por el controlador.

    Aislamiento de pruebas: Al utilizar MockMvc, las pruebas de los controladores se mantienen aisladas,
    sin depender de otros componentes como la base de datos o servicios externos.

    Facilidad de configuración: MockMvc se configura automáticamente cuando se utiliza la anotación
    @AutoConfigureMockMvc en la clase de prueba, lo que simplifica la configuración.

    En el caso específico del curso, el instructor está utilizando MockMvc para probar el
    comportamiento del controlador de consultas, específicamente el método agendarConsulta. Esto
    le permite verificar que el controlador esté devolviendo los códigos de estado
    HTTP correctos (como 400 para datos inválidos) sin tener que interactuar con otros
    componentes de la aplicación.

   Espero que esta explicación general sobre MockMvc te ayude a entender mejor su uso en el
   contexto de las pruebas de controladores en el curso de Spring Boot 3. Si tienes más
   preguntas, con gusto intentaré responderlas.
     */
    @Autowired
    private MockMvc mvc;

    // toma un objeto del tipo java y lo va convertir en json
    // es para convertir el obejeto a json para simular elo envio desde el cliente
    /*

    @Autowired:
    Esta es una anotación de Spring que se utiliza para inyectar automáticamente una
    dependencia en un campo o método de una clase.
    En este caso, se está inyectando el objeto que será utilizado para probar la
     serialización y deserialización de la clase DatosAgendarConsulta.


    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;:
    JacksonTester<DatosAgendarConsulta> es una clase proporcionada por la biblioteca
     de pruebas de Spring, que permite probar la serialización y deserialización de
      objetos JSON utilizando la biblioteca Jackson.
    agendarConsultaJacksonTester es el nombre del campo que recibirá el objeto
     inyectado por Spring.
    Este objeto será utilizado para verificar que los datos de la clase
     DatosAgendarConsulta se serialicen y deserialicen correctamente en formato JSON.



      En resumen, esta línea de código está inyectando un objeto JacksonTester
       que será utilizado para probar la serialización y deserialización de
        la clase DatosAgendarConsulta en el contexto de las pruebas del
      controlador de consultas.Esto es útil porque permite verificar
        que los datos que se envían y reciben en las solicitudes HTTP
       a través del controlador se manejen correctamente en formato
       JSON, sin tener que preocuparse por los detalles de implementación
       de la serialización y deserialización.Si tienes alguna otra duda
       sobre esta línea de código o sobre el uso de JacksonTester en
      las pruebas de Spring Boot, no dudes en preguntar.
     */
    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    // para convertit el json que vamos a recibir simulado a un objeto java
    @Autowired
    private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;

    // para simular la clase AgendaDeConsultaService  y no vaya directamente a la clase
    // y asi llendo a la base datos y asi generando el error y que no tiene que ver con test
    //
    /*
      Entendido, seré más breve en mis explicaciones.@MockBean es una anotación de
      Spring Boot que permite crear y configurar mocks de componentes de la
      aplicación para usarlos en pruebas unitarias.Principales usos:
      Crear mocks de servicios, repositorios, etc.
      Configurar el comportamiento de los mocks (respuestas, excepciones, etc.)
      Inyectar automáticamente los mocks en los componentes que los necesiten.
      Reutilizar los mocks en múltiples pruebas.

      En el curso de Spring Boot 3, el instructor probablemente usa @MockBean
      para simular dependencias en las pruebas de los controladores, sin
      tener que depender de la implementación real de otros componentes.¿Necesitas
      que profundice más en algún aspecto en particular?
     */
    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;




    /*
    [05:31] Entonces hay dos estrategias para nosotros realizar las pruebas para este controlador,
     una donde nosotros inyectamos un servidor y realizamos una requisición de verdad dentro de
     la aplicación, para eso tendríamos que utilizar la anotación @RestAndPlay donde vamos a tener
      un servidor creando una petición en nuestra aplicación.

    [05:55] El controlador va a recibir esa petición, va a realizar la búsqueda en el repositorio,
     va a realizar las validaciones con el componente de service y luego va a retornar el estado
     en caso de que sea encontrada o en caso de que los datos sean inválidos. Esa es una primera
      estrategia. La segunda estrategia es donde nosotros vamos a simular que se realizó la
      petición y nos vamos a enfocar únicamente en este componente, ignorando el resto de los componentes.

    [06:28] Entonces nosotros vamos a simular esa petición, no vamos a crear un servidor sino
    que es vamos a simularlo y vamos a ignorar el restante de los componentes que serían los
     repositorios, los servicios o cualquier otro componente, y vamos a ver el Estado que
      retorna cuando realizamos la petición con esos datos.

    [06:47] Entonces como nosotros vamos a hacer una simulación, vamos a hacer un mock,
     vamos a inyectar dentro de la clase de prueba el atributo MockMvc que lo vamos a
      llamar mvc. Para inyectarlo dentro de nuestra clase de prueba tenemos que recordar
       que hay que usar la anotación @Autowired y junto con esta, tenemos que colocar
        la anotación @AutoConfigureMockMvc.

    [07:17] De esa forma, esta anotación se encarga de configurar todos los componentes
     necesarios para realizar una simulación de una petición para ese controlador. Ahora,
      lo siguiente que nosotros tenemos que hacer es utilizar ese MockMvc para realizar
       una petición, o sea, la petición que nosotros estamos probando es la petición
        del tipo post.

    [07:44] Ahora la URL para ese post sería URL “consultas”. Vamos a ver la dirección
     de acceso, consultas. Y tenemos que retornar una respuesta. Entonces, si vemos acá,
      nosotros tenemos el dato que le estamos pasando, sería la ruta que sería post,
       un post consulta, vamos a realizar una petición del tipo post en la dirección
        consulta y tenemos que retornar cuando given, dado este valor, cuando generemos
         esa petición.

    [08:31] Entonces, hacer, aquí vamos a colocar //then. Ese then va a ser un una
    verificación assertThat. ¿Entonces, qué es lo que necesitamos verificar? Tenemos
     que verificar que el estado, la respuesta que estamos recibiendo de esta requisición,
      de esta petición, sea el estado 400.

    [09:02] Entonces acá yo tengo que guardar esta petición dentro de una variable que la
    voy a llamar response o respuesta. Acá aún me está generando un error, tenemos que
    importar los valores. Y el siguiente error es que nosotros, como nosotros estamos
    tratando las excepciones, tenemos que agregar un throw dentro de la firma de nuestro método.

    [09:28] Agregamos un throw. De esa forma queda corregido el error, la excepción que se
    estaba presentando. Y ahora vamos a colocar, vamos a importar, vamos a verificar que
     ese response, vamos a obtener el estado y que sea igual, isEqual. Vamos a colocar
     getStatus. Vamos a importar acá.

    [09:55] Tenemos que importar la librería y vamos a verificar que el estatus HttpStatus
    sea igual a BAD_REQUEST. Tenemos que obtener el valor y cerramos la verificación. Entonces
    acá vamos a colocar given, dados unos valores iniciales, y when. Nuestro when sería cuando
     se realiza la petición, entonces nosotros en esta parte estamos realizando esos dos primeros pasos.

    [10:36] Vamos a dar los valores iniciales que sería cuál es el tipo de consulta que
     vamos a hacer, el tipo de requisición y cuál va a ser la ruta. Y cuándo sería cuando
      realicemos la petición. Entonces, en ese caso tenemos que confirmar que el estado de
      la respuesta sea igual al estado 400, que es BAD_REQUEST.

    [11:00] Entonces vamos a ejecutar esta aplicación y ver qué obtenemos como retorno. Entonces,
     una vez que ya se cargaron los datos, vemos que el resultado esperado no, no fue el
      deseado. Nosotros estábamos esperando un estado 400. Esa prueba falló, acá cada indica, el
      símbolo amarillo indica que la prueba falló y dice que el estado esperado era el estado 400,
      pero recibimos el estado 403.

    [11:36] Entonces, recordando que nosotros teníamos que enviar el token antes de realizar
     cualquier petición, entonces nosotros tenemos que también simular el envío de ese token
      dentro de nuestra petición. Entonces para eso nosotros vamos a utilizar la anotación
       @WithMockUser y tenemos que importar la librería.

    [11:58] En este caso la librería no se encuentra, entonces cuando nosotros no
    encontramos la librería dentro de las dependencias podemos utilizar la herramienta de
     agregar la dependencia interna, queremos una herramienta que nos permite buscar
     dependencia de Maven o podemos simplemente ir a un navegador como Firefox o Google,
      buscar la dependencia para esa anotación, copiarla.

    [12:24] En este caso yo ya tengo esa anotación copiada. Copié mis dependencias y las
     voy a colocar en el archivo pom, donde está el resto de las dependencias, vamos a
      actualizar mis dependencias acá, con esa nueva dependencia. Vamos a revisar en
      Maven que se haya cargado efectivamente, entonces acá tengo la nueva dependencia
       sprint security para la parte de prueba.

    [12:51] Ahora, en mi clase de prueba para el controlador, yo voy a conseguir
     importar la clase @WithMockUser. Ahora vamos a ejecutar esa aplicación
      nuevamente, vemos que se modificó el resultado. Esta vez no fue un resultado
       inválido, nosotros tuvimos un resultado positivo para esa prueba. Los iconos
        se encuentran en verde indicando que el resultado esperado, que era el estado
         400, nosotros recibimos el resultado esperado que fue el estado 400.

    [13:31] Entonces, en la siguiente parte, nosotros vamos a probar lo que sería
     el estado 404, que es el caso en el que nosotros recibimos los valores, pero
      ese usuario o ese ese médico no se encuentra registrado dentro de la base
       de datos.
     */

    // PORQUE FALLO EL PRIMER test dio error 403 y tenia que ser 400, fue por que falto el toke para eso @WithMockUser
    /*
        Entonces vamos a ejecutar esta aplicación y ver qué obtenemos como retorno. Entonces,
        una vez que ya se cargaron los datos, vemos que el resultado esperado no, no fue el
        deseado. Nosotros estábamos esperando un estado 400. Esa prueba falló, acá cada indica,
        el símbolo amarillo indica que la prueba falló y dice que el estado esperado era el
        estado 400, pero recibimos el estado 403.

       Entonces, recordando que nosotros teníamos que enviar el token antes de realizar cualquier
       petición, entonces nosotros tenemos que también simular el envío de ese token dentro de
       nuestra petición. Entonces para eso nosotros vamos a utilizar la anotación @WithMockUser
       y tenemos que importar la librería.

      En este caso la librería no se encuentra, entonces cuando nosotros no encontramos la
      librería dentro de las dependencias podemos utilizar la herramienta de agregar la
      dependencia interna, queremos una herramienta que nos permite buscar dependencia
      de Maven o podemos simplemente ir a un navegador como Firefox o Google, buscar
      la dependencia para esa anotación, copiarla.

      En este caso yo ya tengo esa anotación copiada. Copié mis dependencias y las voy
      a colocar en el archivo pom, donde está el resto de las dependencias, vamos a
      actualizar mis dependencias acá, con esa nueva dependencia. Vamos a revisar
      en Maven que se haya cargado efectivamente, entonces acá tengo la nueva
      dependencia sprint security para la parte de prueba.

     Ahora, en mi clase de prueba para el controlador, yo voy a conseguir importar la clase @WithMockUser. Ahora vamos a ejecutar esa aplicación nuevamente, vemos que se modificó el resultado. Esta vez no fue un resultado inválido, nosotros tuvimos un resultado positivo para esa prueba. Los iconos se encuentran en verde indicando que el resultado esperado, que era el estado 400, nosotros recibimos el resultado esperado que fue el estado 400.
     */

     //se tiene que añadir la dependencia de  spring-security-test   para usar @WithMockUser
    /*
       Entonces, recordando que nosotros teníamos que enviar el token antes de realizar cualquier
       petición, entonces nosotros tenemos que también simular el envío de ese token dentro de
       nuestra petición. Entonces para eso nosotros vamos a utilizar la anotación @WithMockUser
       y tenemos que importar la librería.
     */

    @Test
    @DisplayName("deberia retornar estado http 400 cuando los datos ingresados sean invalidos")
    @WithMockUser
    void agendarEscenario1() throws Exception {
        //given //when
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        //then
        // verificar que el estado de esta requision sea del estado 400, BAD_REQUEST es igual al estado 400
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }




    /*
    Estamos enviando una fecha que va a ser una hora después de la hora actual en la que
     estamos realizando este procedimiento, estamos colocando el parámetro de la especialidad,
     estamos colocando la especialidad dentro de un parámetro y estamos generando una
     respuesta o una aplicación simulada.

     Entonces esa aplicación va a enviar los datos que se encuentran en un archivo del
     tipo DatosAgendarConsulta, que son transformados a través de JacksonTester. Él va a
     retornar una respuesta y vamos a validar esa respuesta con el primer assert, la primera verificación. Tenemos que verificar que esa respuesta sea igual al estado 200.

     Por último, tenemos que verificar el cuerpo del JSON que estamos retornando, para eso
     tenemos que escribir cuál es el cuerpo del JSON que deberíamos recibir. Sería
     JsonEsperado, y vamos a transformar ese elemento de tipo Java, un elemento del tipo JSON para poder compararlo con el JSON que estamos retornando en la respuesta de la aplicación simulada.

     Entonces vamos a ejecutar este test a ver cuál es el resultado. Ahora, una vez que ya
     ha cargado la segunda prueba realizada, nosotros nos encontramos con un error. Ese error
     indica que no fue encontrado el JacksonTester para esta prueba. Entonces nosotros tenemos
     que colocar una anotación adicional a la parte superior de la clase, que es la anotación
     @AutoConfigureJsonTesters.

     La anotación se encargará de hacer todas las configuraciones para esta clase de prueba
     relacionadas al parámetro JacksonTester que es el encargado de realizar esas
     transformaciones de elementos de tipo Java a tipo JSON. Ahora si ejecutamos
     nuevamente nuestra aplicación. Entonces, una vez que cargó este segundo intento de
     prueba, vemos que ya no entregó un error de compilación, sino que simplemente falló
     la prueba.

     Entonces vamos a ver qué indica. Acá indica lo siguiente, está diciendo que estábamos
     esperando un estado de 200, pero recibimos el estado 400, recordando que el estado 400
     es que las informaciones que fueron pasadas son inválidas. ¿Por qué ocurre esto?

     Cuando nosotros estamos enviando el JSON dentro de nuestra aplicación simulada, él
     está realizando todo el proceso dentro de lo que sería la base de datos real, entonces
     nosotros estamos tomando este JSON y lo estamos enviando en la clase controlador,
     que es la que recibe los archivos de las API externas.

     De allí lo estamos enviando a la clase de servicio, que a su vez lo envía la clase
     de repositorio para hacer una búsqueda en la base de datos y guardarlo. Luego, por
     último, va a retornar los valores que nosotros deseamos, que en este caso serían
     los detalles de la consulta con el ID de esa consulta que fue agendada.

     Entonces, para eso nosotros tenemos que simular esa clase de servicio para indicarle
     a la aplicación que no tiene que hacer una búsqueda dentro de la base de datos real,
     sino le vamos a indicar directamente cuál va a ser el retorno.

     Entonces acá vamos a inyectar un nuevo parámetro, que va a ser AgendaDeConsultaService
     y lo vamos a anotar con la anotación @MockBean siendo referencia de que vamos a simular
     esa clase de servicio.

     Ahora, dentro de mi método de prueba voy a utilizar una comparación que es
     agendaDeConsultaService, y vamos a colocar una agenda. Entonces cuando se realice el
     asentamiento de esta consulta que sería esta de acá, DatosAgendarConsulta, acá podemos
     colocar esta o podemos colocar any, indicando que puede ser cualquier petición.

     Entonces, cuando enviemos cualquier petición o esta que estamos colocando acá, nosotros
     tenemos que retornar lo siguiente, tenemos que retornar DatosDetalleConsulta. Entonces
     ahora como estamos utilizando DatosDetalleConsulta, podríamos colocarla dentro de una
     variable que se llame datos y pasar acá simplemente el parámetro datos.

     Entonces, cuando se intente agendar cualquier JSON, nosotros vamos a retornar este
     parámetro. Entonces, acá vamos a reemplazar nuevo DatosDetalleConsulta por la variable
     datos. Vamos a intentar ejecutarlo de nuevo, para ver cuál es el resultado.
     */

    @Test
    @DisplayName("deberia retornar estado http 200 cuando los datos ingresados son validos")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        //given
        // es para cuando haga la consulta sea una hora despues a la actual, reglas de negocio
        var fecha = LocalDateTime.now().plusHours(1);

        var especialidad = Especialidad.CARDIOLOGIA;

        // DatosDetalleConsulta es el record, eso seria para serializar el json a obejto java
        var datos = new DatosDetalleConsulta(null,2l,5l,fecha);

        // when

        // el when de abajo es un metodo static de mokito
        /*
        when(...): Este es un método estático de la clase Mockito que se utiliza para
        configurar el comportamiento esperado de un método de un objeto mock.

        agendaDeConsultaService.agendar(any()): Esto es la llamada al método agendar()
        del objeto mock agendaDeConsultaService. El método any() de Mockito se utiliza
        como un matcher, lo que significa que se aceptará cualquier argumento para este
        método.

        .thenReturn(datos): Esta es la acción que se define para cuando se llame al
        método agendar() del objeto mock. Cuando se llame a este método con cualquier
        argumento, se devolverá el objeto datos.

        En resumen, esta línea de código está configurando el comportamiento
        simulado del método agendar() del servicio AgendaDeConsultaService. Cuando
        se llame a este método con cualquier argumento, se devolverá el objeto datos.

        Esto se utiliza en las pruebas unitarias para aislar el componente que se
        está probando (en este caso, el controlador) de las dependencias externas
        (en este caso, el servicio).
         */
        // en lugar de any() se le podia pasar el new DatosAgendarConsulta(2l,5l,fecha, especialidad
        // pero no se hace para que reciba cualquiera peticion y  new DatosAgendarConsulta(2l,5l,fecha, especialidad
        // se guarda en una variable y pasa como parametro a thenReturn(datos), entonces cuando se intente agendar
        // cualquier json va retornar ese parametro
        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);




        /*
        var response = mvc.perform(...): Esto ejecuta una solicitud simulada utilizando
        el objeto mvc (MockMvc) y almacena la respuesta en la variable response.

         post("/consultas"): Especifica que se realizará una solicitud HTTP POST a la ruta
         "/consultas".

        .contentType(MediaType.APPLICATION_JSON): Establece el tipo de contenido de la
         solicitud como "application/json".

        .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l,5l,fecha,
         especialidad)).getJson()): Utiliza el objeto agendarConsultaJacksonTester para
         convertir un objeto DatosAgendarConsulta a formato JSON y establece ese JSON
         como el contenido de la solicitud.

        new DatosAgendarConsulta(2l,5l,fecha, especialidad): Crea un nuevo objeto
        DatosAgendarConsulta con los valores proporcionados.
        agendarConsultaJacksonTester.write(...): Convierte el objeto DatosAgendarConsulta
        a formato JSON.
        .getJson(): Obtiene el JSON generado.
        .andReturn().getResponse(): Obtiene la respuesta de la solicitud simulada y la
        almacena en la variable response.

        En resumen, este código realiza una solicitud POST simulada a "/consultas" con
        un objeto DatosAgendarConsulta convertido a JSON como contenido, y luego
        almacena la respuesta en la variable response para su posterior verificación.

         La razón por la que se utilizan los ID con la letra "l" al final, como "2l" y
         "5l", es para indicar que se trata de valores de tipo "long" en Java.

                 */

        /* IMPORTATE aqui en .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l,5l,fecha,
         especialidad)).getJson())  va dar error codigo 400 error cliente informacion invalidad, por que
         Entonces vamos a ver qué indica. Acá indica lo siguiente, está diciendo que estábamos esperando un estado
         de 200, pero recibimos el estado 400, recordando que el estado 400 es que las informaciones que fueron
         pasadas son inválidas. ¿Por qué ocurre esto?

        Cuando nosotros estamos enviando el JSON dentro de nuestra aplicación simulada, él está realizando
        todo el proceso dentro de lo que sería la base de datos real, entonces nosotros estamos tomando
        este JSON y lo estamos enviando en la clase controlador, que es la que recibe los archivos de
        las API externas.

        De allí lo estamos enviando a la clase de servicio, que a su vez lo envía la clase de repositorio
        para hacer una búsqueda en la base de datos y guardarlo. Luego, por último, va a retornar los
        valores que nosotros deseamos, que en este caso serían los detalles de la consulta con el ID
        de esa consulta que fue agendada.

        Entonces, para eso nosotros tenemos que simular esa clase de servicio para indicarle
        a la aplicación que no tiene que hacer una búsqueda dentro de la base de datos real,
        sino le vamos a indicar directamente cuál va a ser el retorno.

        Entonces acá vamos a inyectar un nuevo parámetro, que va a ser AgendaDeConsultaService
        y lo vamos a anotar con la anotación @MockBean siendo referencia de que vamos a
        simular esa clase de servicio.

        Ahora, dentro de mi método de prueba voy a utilizar una comparación que es
        agendaDeConsultaService, y vamos a colocar una agenda. Entonces cuando se realice
        el asentamiento de esta consulta que sería esta de acá, DatosAgendarConsulta, acá
        podemos colocar esta o podemos colocar any, indicando que puede ser cualquier petición.

        Entonces, cuando enviemos cualquier petición o esta que estamos colocando acá,
        nosotros tenemos que retornar lo siguiente, tenemos que retornar
        DatosDetalleConsulta. Entonces ahora como estamos utilizando DatosDetalleConsulta,
        podríamos colocarla dentro de una variable que se llame datos y pasar acá
        simplemente el parámetro datos.

        Entonces, cuando se intente agendar cualquier JSON, nosotros vamos a
        retornar este parámetro. Entonces, acá vamos a reemplazar nuevo DatosDetalleConsulta
        por la variable datos. Vamos a intentar ejecutarlo de nuevo, para ver cuál es el
        resultado.
        */
        var response = mvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l,5l,fecha, especialidad)).getJson())
        ).andReturn().getResponse();

        //then
        // el estado esperado es ok es 200
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalleConsultaJacksonTester.write(datos).getJson();

        // verificar que el json sea el esperado
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

}