package medi.voli.api.domain.consulta.validacoes;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.consulta.ConsultaRepository;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario {

    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {

        var medicoPossuiConsultaNOMesmoHorario = repository.existsByMedicoIdAndDataHora(dados.idMedico(),
                dados.dataHoraConsulta());

        if (medicoPossuiConsultaNOMesmoHorario){
            throw new ValidacaoException("Esse médico ja possui outra consulta agendada para esse mesmo dia e horário.");
        }
    }
}
