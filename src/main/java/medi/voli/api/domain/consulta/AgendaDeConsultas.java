package medi.voli.api.domain.consulta;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsultas;
import medi.voli.api.domain.medico.Medico;
import medi.voli.api.domain.medico.MedicoRepository;
import medi.voli.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AgendaDeConsultas {

    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;
    private List<ValidadorAgendamentoDeConsultas> validadores; // O spring cria uma lista com
    // todas as classes que implementam essa interface

    // COnstrutor feito manuelmanete para a injeção de dependencias
    public AgendaDeConsultas(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
                             PacienteRepository pacienteRepository,List<ValidadorAgendamentoDeConsultas> validadores) {

        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    //metodo para o agendamento de consultas
    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe");
        }

        validadores.forEach(v -> v.validar(dados));// percorre a lista de validadores

        var medico = escolherMedico(dados);// invoca ou o objeto medico pelo id ou um medico aleatório pela
        // especialidade
        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null,medico,paciente,dados.dataHoraConsulta());
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    //Este metodo retorna um médico aleatório de uma especialidade x
    // caso o id do médico vindo na requisição de agendamento seja null
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for selecionado.");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(),dados.dataHoraConsulta());
    }

}
