package medi.voli.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long idConsilta, Long idMedico, Long idPaciente, LocalDateTime dataHoraConsulta) {
}
