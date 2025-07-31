package medi.voli.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        @NonNull
        Long idMedico,
        @NotNull
        Long idPaciente,
        @Future // Anotação que define que a data TEM QUE SER futura
        LocalDateTime dataHoraConsulta) {
}
