package medi.voli.api.domain.consulta.validacoes;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.consulta.ConsultaRepository;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;


@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsultas {

    private ConsultaRepository repository;

    public ValidadorMedicoComOutraConsultaNoMesmoHorario(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {

        var medicoPossuiConsultaNOMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(),
                dados.dataHoraConsulta());

        if (medicoPossuiConsultaNOMesmoHorario){
            throw new ValidacaoException("Esse médico ja possui outra consulta agendada para esse mesmo dia e horário.");
        }
    }
}
