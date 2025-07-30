package medi.voli.api.domain.paciente;

import medi.voli.api.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente(Long id,
                                      String nome,
                                      String email,
                                      Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente){

        this(paciente.getId(), paciente.getNome(),paciente.getEmail(),paciente.getEndereco());
    }
}
