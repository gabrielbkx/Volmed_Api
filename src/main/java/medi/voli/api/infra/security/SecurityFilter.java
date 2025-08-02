package medi.voli.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import medi.voli.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Esse filtro será executado **uma única vez por requisição**
 * por isso estende a classe `OncePerRequestFilter`.
 * O Spring garante que esse filtro seja chamado para cada requisição recebida pela aplicação.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    /**
     * Esse e o metodo principal do filtro. Ele é chamado automaticamente
     * para cada requisição. Aqui você pode interceptar, validar, autenticar ou rejeitar a requisição.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {

        // Recupera o token JWT enviado no cabeçalho Authorization
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            var login = tokenService.getUsuarioDoToken(tokenJWT);
            var usuario = repository.findByLogin(login);
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Após a lógica do filtro, passa a requisição adiante para o próximo filtro ou controlador
        filterChain.doFilter(request, response);
    }

    /**
     * Esse metodo tenta recuperar o token JWT do cabeçalho Authorization da requisição.
     * Espera-se que o valor seja algo como: "Bearer <token>".
     */
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
