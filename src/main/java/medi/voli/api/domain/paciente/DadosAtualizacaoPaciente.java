package medi.voli.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import medi.voli.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoPaciente(@NotNull
                                       Long id,
                                       String nome,
                                       String telefone,
                                       DadosEndereco endereco) {
}
