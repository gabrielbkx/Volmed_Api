package medi.voli.api.domain.consulta.validacoes;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsultas {

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.dataHoraConsulta();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoExpediente = dataConsulta.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoExpediente){
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clínica. ");
        }
    }
}
