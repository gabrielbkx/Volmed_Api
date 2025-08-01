package medi.voli.api.domain.consulta.validacoes;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsultas {

    public void validar(DadosAgendamentoConsulta dados){

        var dataConsuylta = dados.dataHoraConsulta();
        var agora = LocalDateTime.now();
        var diferencaEmMinutos = Duration.between(dataConsuylta, agora).toMinutes();

        if (diferencaEmMinutos < 30) {
            throw new ValidacaoException("A Consulta deve ser agendada com antecedência mínima de 30 minutos.");
        }

    }
}
