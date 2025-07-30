package medi.voli.api.domain.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosListagemPaciente(

        Long id,
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,
        @Pattern(regexp = "\\d{11}")
        String cpf) {

      public DadosListagemPaciente(Paciente paciente){
              this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
      }
}
