package pt.ul.fc.css.democracia2;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.handlers.ConsultarProjsLeiHandler;
import pt.ul.fc.css.democracia2.handlers.EscolherDelegadoHandler;
import pt.ul.fc.css.democracia2.handlers.ListarVotacoesHandler;
import pt.ul.fc.css.democracia2.handlers.VotarPropostaHandler;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;
import pt.ul.fc.css.democracia2.repositories.ProjetoLeiRepository;
import pt.ul.fc.css.democracia2.repositories.TemaRepository;
import pt.ul.fc.css.democracia2.repositories.VotacaoRepository;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class RestTests {

  private final MediaType MEDIA_TYPE_JSON_UTF8 =
      new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

  private static final File PDF = new File("PDF_for_test.pdf");

  @Autowired CidadaoRepository cidRepo;
  @Autowired DelegadoRepository delRepo;
  @Autowired VotacaoRepository votRepo;
  @Autowired TemaRepository temaRepo;
  @Autowired ProjetoLeiRepository projRepo;

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper mapper;

  /** Basic empty test to verify if the application loads */
  @Test
  @Order(0)
  void contextLoads() {
    System.out.println(" \n REST TESTS \n");
    cleanRepos();
  }

  @Test
  @Order(1)
  void testListarProjsLei() throws Exception {

    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    Delegado del = new Delegado("Rute", 123456789);
    delRepo.save(del);

    ProjetoLei proj1 = new ProjetoLei("Title1", "Description1", PDF, date, t, del);
    ProjetoLei proj2 = new ProjetoLei("Title2", "Description2", PDF, date, t, del);
    ProjetoLei proj3 = new ProjetoLei("Title3", "Description3", PDF, date, t, del);
    ProjetoLei proj4 = new ProjetoLei("Title4", "Description4", PDF, date, t, del);
    ProjetoLei proj5 = new ProjetoLei("Title5", "Description5", PDF, date, t, del);
    projRepo.save(proj1);
    projRepo.save(proj2);
    projRepo.save(proj3);
    projRepo.save(proj4);
    projRepo.save(proj5);

    mockMvc
        .perform(get("/api/projetosLei"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Title1")))
        .andExpect(content().string(containsString("Title2")))
        .andExpect(content().string(containsString("Title3")))
        .andExpect(content().string(containsString("Title4")))
        .andExpect(content().string(containsString("Title5")))
        .andExpect(content().string(containsString("Description1")))
        .andExpect(content().string(containsString("Description2")))
        .andExpect(content().string(containsString("Description3")))
        .andExpect(content().string(containsString("Description4")))
        .andExpect(content().string(containsString("Description5")))
        .andExpect(content().string(containsString("Crime")))
        .andExpect(content().string(containsString("Rute")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5));
    cleanRepos();

    mockMvc
        .perform(get("/api/projetosLei"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
  }

  @Test
  @Order(2)
  void testConsultarProjetolei() throws Exception {
    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    Delegado del = new Delegado("Rute", 123456789);
    delRepo.save(del);

    mockMvc.perform(get("/api/projetosLei/1")).andExpect(status().isNotFound());

    ProjetoLei proj = new ProjetoLei("Title1", "Description1", PDF, date, t, del);
    projRepo.save(proj);
    mockMvc
        .perform(get("/api/projetosLei/" + proj.getId().toString()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Title1")))
        .andExpect(content().string(containsString("Description1")))
        .andExpect(content().string(containsString("PDF_for_test.pdf")))
        .andExpect(content().string(containsString("Rute")))
        .andExpect(content().string(containsString("ABERTO")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(8));
    cleanRepos();
  }

  @Test
  @Order(3)
  void testApoiarProjetoLei() throws Exception {
    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    Delegado del = new Delegado("Rute", 123456789);
    delRepo.save(del);

    Cidadao c = new Cidadao("Maria", 987654321);
    cidRepo.save(c);

    mockMvc
        .perform(
            post("/api/projetosLei/1/apoiar/" + c.getNumCC())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MEDIA_TYPE_JSON_UTF8)
                .content("{\"numCC\":\"987654321\"}"))
        .andExpect(status().isNotFound());

    ProjetoLei proj = new ProjetoLei("Title1", "Description1", PDF, date, t, del);
    projRepo.save(proj);

    mockMvc
        .perform(
            post("/api/projetosLei/" + proj.getId().toString() + "/apoiar/" + c.getNumCC())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MEDIA_TYPE_JSON_UTF8)
                .content("{\"numCC\":\"987654321\"}"))
        .andExpect(status().isOk());
    cleanRepos();
  }

  @Test
  @Order(4)
  void testListarVotacoes() throws Exception {

    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    mockMvc
        .perform(get("/api/votacoes"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

    Votacao vot1 = new Votacao(date, t);
    Votacao vot2 = new Votacao(date, t);
    Votacao vot3 = new Votacao(date, t);
    Votacao vot4 = new Votacao(date, t);
    Votacao vot5 = new Votacao(date, t);
    votRepo.save(vot1);
    votRepo.save(vot2);
    votRepo.save(vot3);
    votRepo.save(vot4);
    votRepo.save(vot5);

    mockMvc
        .perform(get("/api/votacoes"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5));

    cleanRepos();
  }

  @Test
  @Order(5)
  void testObterVotoDefault() throws Exception {
    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    Votacao vot = new Votacao(date, t);
    votRepo.save(vot);

    mockMvc
        .perform(get("/api/votacao/" + vot.getId() + "/defaultVote/0"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("-1")));

    Delegado del = new Delegado("Rute", 123456789);
    delRepo.save(del);

    Cidadao c = new Cidadao("Luis", 12345);
    cidRepo.save(c);

    mockMvc
        .perform(get("/api/votacao/" + vot.getId() + "/defaultVote/" + c.getNumCC()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("-1")));

    EscolherDelegadoHandler escDelHandler = new EscolherDelegadoHandler(delRepo, cidRepo, temaRepo);
    escDelHandler.setCidadaoCorrente(c.getNumCC());
    escDelHandler.escolheDelegado(del, t);

    mockMvc
        .perform(get("/api/votacao/" + vot.getId() + "/defaultVote/" + c.getNumCC()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("-1")));

    VotarPropostaHandler votarPropostaDelegado = new VotarPropostaHandler(votRepo, cidRepo);
    votarPropostaDelegado.registerCidadao(del.getNumCC());
    votarPropostaDelegado.selectVotacao(votarPropostaDelegado.getListaVotacoes().get(0).getId());
    votarPropostaDelegado.votar(0);

    mockMvc
        .perform(get("/api/votacao/" + vot.getId() + "/defaultVote/" + c.getNumCC()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("0")));

    cleanRepos();
  }

  @Test
  @Order(6)
  void testVotar() throws Exception {
    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    Votacao vot = new Votacao(date, t);
    votRepo.save(vot);

    mockMvc
        .perform(
            post("/api/votacao/" + vot.getId() + "/vote/0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MEDIA_TYPE_JSON_UTF8)
                .content("{\"voto\":\"1\"}"))
        .andExpect(result -> CoreMatchers.not(status().isOk()));
    Cidadao c = new Cidadao("Luis", 12345);
    cidRepo.save(c);

    mockMvc
        .perform(
            post("/api/votacao/" + vot.getId() + "/vote/" + c.getNumCC())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MEDIA_TYPE_JSON_UTF8)
                .content("{\"voto\":\"1\"}"))
        .andExpect(status().isOk());

    cleanRepos();
  }

  @Test
  @Order(7)
  void fecharProjetosLei() throws Exception {

    Timestamp date = new Timestamp(System.currentTimeMillis());
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    Timestamp dateTomorrow = Timestamp.valueOf(tomorrow.atStartOfDay());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    Delegado del = new Delegado("Rute", 123456789);
    delRepo.save(del);

    ProjetoLei proj1 = new ProjetoLei("Title1", "Description1", PDF, date, t, del);
    ProjetoLei proj2 = new ProjetoLei("Title2", "Description2", PDF, date, t, del);
    ProjetoLei proj3 = new ProjetoLei("Title3", "Description3", PDF, date, t, del);
    ProjetoLei proj4 = new ProjetoLei("Title4", "Description4", PDF, dateTomorrow, t, del);
    ProjetoLei proj5 = new ProjetoLei("Title5", "Description5", PDF, dateTomorrow, t, del);
    projRepo.save(proj1);
    projRepo.save(proj2);
    projRepo.save(proj3);
    projRepo.save(proj4);
    projRepo.save(proj5);

    ConsultarProjsLeiHandler handler = new ConsultarProjsLeiHandler(projRepo);
    List<ProjetoLei> lista1 = handler.listarProjsLei();
    Democracy2.fecharProjsLei();
    assertEquals(lista1.size(), 5);
    List<ProjetoLei> lista2 = handler.listarProjsLei();
    assertEquals(lista2.size(), 2);
    cleanRepos();
  }

  @Test
  @Order(8)
  void fecharVotacoes() throws Exception {

    Timestamp date = new Timestamp(System.currentTimeMillis());
    LocalDate tomorrow = LocalDate.now().plusDays(1);
    Timestamp dateTomorrow = Timestamp.valueOf(tomorrow.atStartOfDay());

    Tema t = new Tema("Crime");
    temaRepo.save(t);

    Votacao vot1 = new Votacao(date, t);
    Votacao vot2 = new Votacao(date, t);
    Votacao vot3 = new Votacao(date, t);
    Votacao vot4 = new Votacao(dateTomorrow, t);
    Votacao vot5 = new Votacao(dateTomorrow, t);
    votRepo.save(vot1);
    votRepo.save(vot2);
    votRepo.save(vot3);
    votRepo.save(vot4);
    votRepo.save(vot5);

    ListarVotacoesHandler handler = new ListarVotacoesHandler(votRepo);
    List<Votacao> lista1 = handler.listarVotacoesCorrentes();
    Democracy2.fecharVotacoes();
    assertEquals(lista1.size(), 5);
    List<Votacao> lista2 = handler.listarVotacoesCorrentes();
    assertEquals(lista2.size(), 2);
    cleanRepos();
  }

  /** Deletes all the entries in all the repos */
  private void cleanRepos() {
    projRepo.deleteAll();
    votRepo.deleteAll();
    cidRepo.deleteAll();
    delRepo.deleteAll();
    temaRepo.deleteAll();
  }
}
