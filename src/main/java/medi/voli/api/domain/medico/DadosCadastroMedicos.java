package medi.voli.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import medi.voli.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedicos(@NotBlank(message = "O campo nome não pode estar vazio")
                                   String nome,
                                   @NotBlank
                                   @Email
                                   String email,
                                   @NotBlank
                                   String telefone,
                                   @Pattern(regexp = "\\d{4,6}")
                                   String crm,
                                   @NotNull
                                   Especialidade especialidade,
                                   @NotNull
                                   @Valid //Valida o outro objeto(classe)
                                   DadosEndereco endereco) {
}
