package medi.voli.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import medi.voli.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long idConsulta, Long idMedico, Long idPaciente,
                                        @JsonFormat( pattern = "dd/MM/yyyy HH:mm") // Define o padrao brasileiro de datas la no front ou mobile
                                        LocalDateTime dataHoraConsulta) {

    public DadosDetalhamentoConsulta(Consulta consulta) {

        this(consulta.getId(),consulta.getMedico().getId(),consulta.getPaciente().getId(),consulta.getData());
    }
}
