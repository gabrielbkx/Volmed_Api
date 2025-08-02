package medi.voli.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import medi.voli.api.domain.consulta.AgendaDeConsultas;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("consultas")
public class ConsultaController {

    private AgendaDeConsultas agenda;

    public ConsultaController(AgendaDeConsultas agenda) {
        this.agenda = agenda;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Object> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
       var dto =  agenda.agendar(dados);
       return ResponseEntity.ok(dto);
    }

}