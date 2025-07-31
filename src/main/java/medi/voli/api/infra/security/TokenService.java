package medi.voli.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import medi.voli.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service // Marca a classe como um componente do Spring do tipo "Service" (camada de serviço)
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret; // Injeta o valor do segredo da aplicação, definido no application.properties ou .yml

    // Gera um token JWT com base no usuário autenticado
    public String gerarToken(Usuario usuario) {
        System.out.println(secret); // Apenas para debug (pode ser removido em produção)

        try {
            // Define o algoritmo de assinatura HMAC com a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);

            // Cria o token JWT com informações do emissor, assunto e tempo de expiração
            return JWT.create()
                    .withIssuer("Api voll med") // Define quem está emitindo o token
                    .withSubject(usuario.getLogin()) // Define o "dono" do token (quem está autenticado)
                    .withExpiresAt(dataExpiracao()) // Define quando o token expira
                    .sign(algoritmo); // Assina o token com o algoritmo definido
        } catch (JWTCreationException exception){
            // Caso ocorra algum erro na criação do token, lança uma exceção
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getUsuarioDoToken(String tokenJWT){
        DecodedJWT decodedJWT;
        try {
            var algoritmo = Algorithm.HMAC256(secret);;
            return JWT.require(algoritmo)
                    .withIssuer("Api voll med")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado", exception);
        }
    }

    // Metodo que define o tempo de expiração do token como 2 horas a partir do momento atual
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2)
                .toInstant(ZoneOffset.of("-03:00")); // Define o fuso horário de -3h (Brasil)
    }
}
