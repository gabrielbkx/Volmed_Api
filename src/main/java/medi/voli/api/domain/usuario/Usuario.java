package medi.voli.api.domain.usuario;

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

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails { // Implementa UserDetails para integrar com Spring Security

    // Campo que será a chave primária da tabela e gerado automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Login do usuário, será usado como username
    private String login;

    // Senha do usuário, deve ser armazenada criptografada (ex: BCrypt)
    private String senha;

    // Retorna as permissões do usuário para o Spring Security
    // Aqui, está dando o papel "ROLE_USER" para todos os usuários
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Retorna a senha (criptografada) para o Spring Security validar
    @Override
    public String getPassword() {
        return senha;
    }

    // Retorna o nome do usuário para o Spring Security
    @Override
    public String getUsername() {
        return login;
    }

    // Métodos abaixo indicam se a conta está ativa, não expirada e não bloqueada
    // Aqui está usando o comportamento padrão (true), mas você pode customizar

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled(); // padrão true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired(); // padrão true
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked(); // padrão true
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired(); // padrão true
    }
}
