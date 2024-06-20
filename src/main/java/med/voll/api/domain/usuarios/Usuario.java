package med.voll.api.domain.usuarios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/*
Al implementar al interface UserDetails nuestro Usuario ya un objeto de tipo UserDtails
tenerlo en cuenta para el UsuarioRepository que ya no se le pasa Usuario y Long sino
UserDatils y Long
 */


@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String clave;


    /*
    Authorities significa el roll que va tener nuestro usuario en nuestra app
    si no tiene lo va bloquear, para eso se return una lista de
    SimpleGrantedAuthority("ROLE_USER") usuarioo basico
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // la implementacion donde indicamos cual es nuestro campo password clave
    @Override
    public String getPassword() {
        return clave;
    }

    // la implementacion donde indicamos cual es nuestro campo login
    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
