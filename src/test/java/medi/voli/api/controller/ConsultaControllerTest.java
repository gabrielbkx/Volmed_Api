package medi.voli.api.controller;

import medi.voli.api.domain.consulta.AgendaDeConsultas;
import medi.voli.api.domain.consulta.DadosAgendamentoConsulta;
import medi.voli.api.domain.consulta.DadosDetalhamentoConsulta;
import medi.voli.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureJsonTesters // Sem essa notação o spring nao carrega o JacksonTester para testar o json
@AutoConfigureMockMvc // Sem essa anotração nao conseguimos injetar o mockmvc abaixo
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc; // Simula requisições HTTP

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean // Sem essa notação nosso agendar_Cenario2 vai tentar acessar o repositorio
    // que esta dentro de AgendaDeConsultas e vai gerar um erro no teste
    private AgendaDeConsultas agendaDeConsultas;


    @Test
    @DisplayName("Deveria devolver código http 400 quando informações estão inválidas")
    @WithMockUser // Faz o spring considerar que tem um usuario logado para poder fazer a requisição
    void agendar_Cenario1() throws Exception {

       var response =  mvc.perform(post("/consultas"))
                .andReturn().getResponse();

       assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deveria devolver código http 200 quando informações estão válidas")
    @WithMockUser // Faz o spring considerar que tem um usuario logado para poder fazer a requisição
    void agendar_Cenario2() throws Exception {

        var data = LocalDateTime.now().plusDays(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null,2l,5l,data);

       when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        var response =  mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON) // diz wue o corpo vai ser um json
                        .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(2l,5l,especialidade,data)).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamento).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // me da uma string e verifica se é iual ao json
    }


}