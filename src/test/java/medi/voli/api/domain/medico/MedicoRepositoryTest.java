package medi.voli.api.domain.medico;

import medi.voli.api.domain.consulta.Consulta;
import medi.voli.api.domain.endereco.DadosEndereco;
import medi.voli.api.domain.paciente.DadosCadastroPaciente;
import medi.voli.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")// O spring reconhece o application-test como caminho de um banco de dados para teste
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)// Usamos o banco de dados real para fazer os testes
@DataJpaTest // Quando queremos testar um repository usamos essa anotação
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data") // Essa notação
        // pertence ao jUnit
        // e nos permite descrever em strings o que p metodo em quetsao esta fazendo
    void escolherMedicoAleatorioLivreNaDataCenario1() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou act
       var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,proximaSegundaAs10);
        //the ou assert
       assertThat(medicoLivre).isNull(); // Verifica se o metodo esta retornando o que ele deveria, ou seja, um null
    } // No final da execução de um metodo de test, TODOS os reistros no banco de dados sofre um rollback

    @Test
    @DisplayName("Deveria devolver medico quando ele estiver disponivel na data") // Essa notação
        // pertence ao jUnit
        // e nos permite descrever em strings o que p metodo em quetsao esta fazendo
    void escolherMedicoAleatorioLivreNaDataCenario2() {
        //given ou arrange

        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10,0);
        //when ou act

         var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = repository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA,proximaSegundaAs10);
        //the ou assert
        assertThat(medicoLivre).isEqualTo(medico); // Verifica se o metodo esta retornando o que ele deveria, ou seja, um null
    } // No final da execução de um metodo de test, TODOS os reistros no banco de dados sofre um rollback

    //Métodos private para usarmos em nossos testes

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedicos dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedicos(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}