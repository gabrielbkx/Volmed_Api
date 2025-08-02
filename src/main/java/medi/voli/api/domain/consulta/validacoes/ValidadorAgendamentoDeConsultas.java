package medi.voli.api.domain.consulta.validacoes;

import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsultas {

    void validar(DadosAgendamentoConsulta dados);
}
