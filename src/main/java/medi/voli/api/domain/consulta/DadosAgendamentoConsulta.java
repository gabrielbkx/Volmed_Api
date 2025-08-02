package medi.voli.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import medi.voli.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        Especialidade especialidade,
        @Future // Anotação que define que a data TEM QUE SER futura
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataHoraConsulta) {

}
