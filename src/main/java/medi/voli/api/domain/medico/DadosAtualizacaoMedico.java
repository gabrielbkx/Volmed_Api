package medi.voli.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import medi.voli.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
