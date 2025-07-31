package medi.voli.api.domain.consulta;

import medi.voli.api.domain.ValidacaoException;
import medi.voli.api.domain.medico.Medico;
import medi.voli.api.domain.medico.MedicoRepository;
import medi.voli.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    private ConsultaRepository consultaRepository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;

    // COnstrutor feito manuelmanete para a injeção de dependencias
    public AgendaDeConsultas(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    //metodo para o agendamento de consultas
    public void agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe");
        }
        var medico = escolherMedico(dados);// invoca ou o objeto medico pelo id ou um medico aleatório pela
        // especialidade

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var consulta = new Consulta(null,medico,paciente,dados.dataHoraConsulta());
        consultaRepository.save(consulta);
    }

    //Este metodo retorna um médico aleatório de uma especialidade x
    // caso o id do médico vindo na requisição de agendamento seja null
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() != null){
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for selecionado.");
        }
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(),dados.dataHoraConsulta());
    }

}
