package medi.voli.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Define que esta classe é um componente de serviço gerenciado pelo Spring
@Service
public class AutenticacaoService implements UserDetailsService {

    // Injeta automaticamente uma instância de UsuarioRepository no atributo "repository"
    @Autowired
    private UsuarioRepository repository;

    // Este metodo e sobrescrito da interface UserDetailsService, usada pelo Spring Security
    // Ele é chamado automaticamente durante o processo de autenticação
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca no banco de dados um usuário com o login fornecido
        // A entidade Usuario deve implementar UserDetails
        return repository.findByLogin(username);
    }
}

