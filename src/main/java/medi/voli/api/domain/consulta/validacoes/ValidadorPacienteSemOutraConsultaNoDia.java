package medi.voli.api.domain.consulta.validacoes;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.consulta.ConsultaRepository;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;


@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoDeConsultas {

    private ConsultaRepository repository;

    public ValidadorPacienteSemOutraConsultaNoDia(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {

        var primeiroHorario = dados.dataHoraConsulta().withHour(7);
        var segundoHorario = dados.dataHoraConsulta().withHour(18);

        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(),
                primeiroHorario,segundoHorario);

        if (pacientePossuiOutraConsultaNoDia){
            throw new ValidacaoException("Paciente ja possui uma consulta agendada nesse dia");

        }
    }
}
