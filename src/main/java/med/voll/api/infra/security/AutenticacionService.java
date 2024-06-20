package med.voll.api.infra.security;

import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/*
@Service para indicarle a indicarle spring escanea esta clase
por que es un servicio de esta aplicación
 */


@Service
public class AutenticacionService implements UserDetailsService {

    // @Autowired no es la mejor forma de inyectar dependecias, cuasa problemas con el testing
    @Autowired
    private UsuarioRepository usuarioRepository;


    /*
        Aqui este metodo de la interface nos esta pidiendo de forma vamos ha cagar ese
        usuario y de donde, para eso se crea un metodo en nuestro repository UsuarioRepository
         */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }
}
