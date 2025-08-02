package medi.voli.api.domain.consulta.validacoes;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;
import medi.voli.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Component;


@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsultas {

    private PacienteRepository repository;

    public ValidadorPacienteAtivo(PacienteRepository repository) {
        this.repository = repository;
    }

    public void validar(DadosAgendamentoConsulta dados) {

        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());

        if (!pacienteEstaAtivo){
            throw new ValidacaoException("Uma consulta não pode ser agendada para um paciente excluído.");
        }
    }
}
