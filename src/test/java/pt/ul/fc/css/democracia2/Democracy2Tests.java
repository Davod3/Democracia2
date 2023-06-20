package pt.ul.fc.css.democracia2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.StatusProjLei;
import pt.ul.fc.css.democracia2.entities.StatusVotacao;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.handlers.ApoiarProjLeiHandler;
import pt.ul.fc.css.democracia2.handlers.ApresentarProjLeiHandler;
import pt.ul.fc.css.democracia2.handlers.ConsultarProjsLeiHandler;
import pt.ul.fc.css.democracia2.handlers.EscolherDelegadoHandler;
import pt.ul.fc.css.democracia2.handlers.FecharProjsLeiHandler;
import pt.ul.fc.css.democracia2.handlers.FecharVotacaoHandler;
import pt.ul.fc.css.democracia2.handlers.ListarVotacoesHandler;
import pt.ul.fc.css.democracia2.handlers.VotarPropostaHandler;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;
import pt.ul.fc.css.democracia2.repositories.ProjetoLeiRepository;
import pt.ul.fc.css.democracia2.repositories.TemaRepository;
import pt.ul.fc.css.democracia2.repositories.VotacaoRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class Democracy2Tests {

  @Autowired CidadaoRepository cidRepo;
  @Autowired DelegadoRepository delRepo;
  @Autowired VotacaoRepository votRepo;
  @Autowired TemaRepository temaRepo;
  @Autowired ProjetoLeiRepository projRepo;

  private static final File PDF = new File("PDF_for_test.pdf");

  /** Basic empty test to verify if the application loads */
  @Test
  @Order(0)
  void contextLoads() {
    cleanRepos();
  }

  /** Verify if a Cidadao gets created properly */
  @Test
  @Order(1)
  void test_persist_fetch_Cidadao() {

    Cidadao cid = new Cidadao("Eduardo", 12345);

    cidRepo.save(cid);

    Cidadao fetched = cidRepo.findById(cid.getId()).get();

    assertEquals(cid, fetched);

    cidRepo.deleteAll();
  }

  /** Verify if a Cidadao gets deleted properly */
  @Test
  @Order(2)
  void test_delete_Cidadao() {

    Cidadao cid = new Cidadao("Ricardo", 123456);

    cidRepo.save(cid);

    cidRepo.delete(cid);

    assertTrue(cidRepo.findById(cid.getId()).isEmpty());

    cidRepo.deleteAll();
  }

  /** Verify if a Delegado gets created properly */
  @Test
  @Order(3)
  void test_persist_fetch_Delegado() {

    Delegado del = new Delegado("Tomás", 123457);

    delRepo.save(del);

    Delegado fetch = delRepo.findById(del.getId()).get();

    assertEquals(del, fetch);

    delRepo.deleteAll();
  }

  /** Verify if a Delegado gets deleted properly */
  @Test
  @Order(4)
  void test_delete_Delegado() {

    Delegado del = new Delegado("Rute", 123450);

    delRepo.save(del);

    delRepo.delete(del);

    assertTrue(delRepo.findById(del.getId()).isEmpty());

    delRepo.deleteAll();
  }

  /** Verify if a Votacao gets created properly */
  @Test
  @Order(5)
  void test_persist_fetch_Votacao() {

    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Educação");

    temaRepo.save(t);

    Votacao vot = new Votacao(date, t);

    votRepo.save(vot);

    Votacao fetched = votRepo.findById(vot.getId()).get();

    assertEquals(vot, fetched);

    votRepo.deleteAll();
  }

  /** Verify if a Votacao gets deleted properly */
  @Test
  @Order(6)
  void test_delete_Votacao() {

    Timestamp date = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Educação");

    temaRepo.save(t);

    Votacao vot = new Votacao(date, t);

    votRepo.save(vot);

    votRepo.delete(vot);

    assertTrue(votRepo.findById(vot.getId()).isEmpty());

    votRepo.deleteAll();
  }

  /** Verify if a Tema gets created properly */
  @Test
  @Order(9)
  void test_persist_fetch_Tema() {

    Tema t = new Tema("Saúde");

    temaRepo.save(t);

    Tema fetched = temaRepo.findById(t.getId()).get();

    assertEquals(t, fetched);

    temaRepo.deleteAll();
  }

  /** Verify if a Tema gets deleted properly */
  @Test
  @Order(10)
  void test_delete_Tema() {

    Tema t = new Tema("Educação");

    temaRepo.save(t);

    temaRepo.delete(t);

    assertTrue(temaRepo.findById(t.getId()).isEmpty());

    temaRepo.deleteAll();
  }

  /** Verify if a child Tema gets created properly */
  @Test
  @Order(11)
  void test_persist_fetch_SubTema() {

    Tema t = new Tema("Justiça");
    Tema st = new Tema("Direitos das vítimas e dos acusados", t);
    temaRepo.save(t);

    temaRepo.save(st);

    Tema fetchSub = temaRepo.findById(st.getId()).get();

    assertEquals(st, fetchSub);
    assertEquals(t, fetchSub.getPai());

    temaRepo.deleteAll();
  }

  /** Verify if a ProjetoLei gets created properly */
  @Test
  @Order(12)
  void test_persist_fetch_ProjetoLei() {

    Timestamp date = new Timestamp(System.currentTimeMillis());

    Delegado del = new Delegado("Rute", 123456789);

    delRepo.save(del);

    Tema t = new Tema("Justiça");

    temaRepo.save(t);

    ProjetoLei proj = new ProjetoLei("Title", "Description", PDF, date, t, del);

    projRepo.save(proj);

    ProjetoLei fetched = projRepo.findById(proj.getId()).get();

    assertEquals(proj, fetched);

    cleanRepos();
  }

  /** Verify if a ProjetoLei gets deleted properly */
  @Test
  @Order(13)
  void test_delete_ProjetoLei() {

    Timestamp date = new Timestamp(System.currentTimeMillis());

    Delegado del = new Delegado("Rute", 12345678);

    delRepo.save(del);

    Tema t = new Tema("Justiça");

    temaRepo.save(t);

    ProjetoLei proj = new ProjetoLei("Title", "Description", PDF, date, t, del);

    projRepo.save(proj);

    projRepo.delete(proj);

    assertTrue(projRepo.findById(proj.getId()).isEmpty());

    cleanRepos();
  }

  /** Verify if ListarVotacaoHanlder works properly */
  @Test
  @Order(14)
  void test_list_votacao() {

    Timestamp date = new Timestamp(System.currentTimeMillis());
    Timestamp date2 = new Timestamp(System.currentTimeMillis());

    Tema t = new Tema("Saúde");

    temaRepo.save(t);

    Votacao v1 = new Votacao(date, t);
    Votacao v2 = new Votacao(date2, t);
    Votacao v3 = new Votacao(date, t);
    Votacao v4 = new Votacao(date2, t);
    Votacao v5 = new Votacao(date, t);
    votRepo.save(v1);
    votRepo.save(v2);
    votRepo.save(v3);
    votRepo.save(v4);
    votRepo.save(v5);

    ListarVotacoesHandler listVotHandler = new ListarVotacoesHandler(votRepo);
    List<Votacao> testList = listVotHandler.listarVotacoesCorrentes();

    assertEquals(testList.get(0), v1);
    assertEquals(testList.get(1), v2);
    assertEquals(testList.get(2), v3);
    assertEquals(testList.get(3), v4);
    assertEquals(testList.get(4), v5);

    cleanRepos();
  }

  /** Verify if ApresentaProjLeirHanlder works properly */
  @Test
  @Order(15)
  void test_apresentar_proj_lei() {

    Tema newT = new Tema("teste");

    temaRepo.save(newT);

    int numCC = 123450;

    Delegado del = new Delegado("Rute", 123450);

    delRepo.save(del);

    ApresentarProjLeiHandler h = new ApresentarProjLeiHandler(delRepo, projRepo, temaRepo);

    h.setDelegadoCorrente(numCC);

    List<Tema> temas = h.getTemas();

    Tema t = temas.get(0);

    Timestamp date = new Timestamp(System.currentTimeMillis());

    h.apresentarProjLei("Recupera", "Reabilitação Física", PDF, date, t);

    // Check if sucessfull

    List<ProjetoLei> testProj = projRepo.findByStatus(StatusProjLei.ABERTO);

    assertEquals(testProj.get(0).getDelegadoProponente(), del);

    cleanRepos();
  }

  /** Verify if ConsultarProjLeiHanlder works properly */
  @Test
  @Order(16)
  void test_consultar_proj_lei() {

    Timestamp date = new Timestamp(System.currentTimeMillis());
    Delegado del = new Delegado("Rute", 123459);
    Delegado del2 = new Delegado("João", 67890);

    delRepo.save(del);
    delRepo.save(del2);

    Tema t = new Tema("Justiça");
    Tema t2 = new Tema("Saúde");

    temaRepo.save(t);
    temaRepo.save(t2);

    ProjetoLei proj = new ProjetoLei("Title", "Description", PDF, date, t, del);
    ProjetoLei proj2 = new ProjetoLei("Title2", "Description2", PDF, date, t2, del);

    projRepo.save(proj);
    projRepo.save(proj2);

    ConsultarProjsLeiHandler consultProjLeiHandler = new ConsultarProjsLeiHandler(projRepo);
    List<ProjetoLei> test_list = consultProjLeiHandler.listarProjsLei();

    assertTrue(test_list.contains(proj));
    assertTrue(test_list.contains(proj2));
    ProjetoLei proj3 = new ProjetoLei("Title", "Description", PDF, date, t, del);

    proj3.fecharProjeto();
    projRepo.save(proj3);
    List<ProjetoLei> test_list2 = consultProjLeiHandler.listarProjsLei();

    assertEquals(2, test_list2.size());

    assertEquals(
        proj.toString(), consultProjLeiHandler.consultarProjLei(proj.getId()).get().toString());

    cleanRepos();
  }

  /** Verify if EscolherDelegadoHanlder works properly */
  @Test
  @Order(17)
  void test_escolher_delegado() {

    Delegado del = new Delegado("Eduardo", 45678);

    delRepo.save(del);

    Tema newT = new Tema("Teste");

    temaRepo.save(newT);

    int numCC = 1234567;

    Cidadao cid = new Cidadao("José", numCC);

    cidRepo.save(cid);

    EscolherDelegadoHandler h = new EscolherDelegadoHandler(delRepo, cidRepo, temaRepo);

    h.setCidadaoCorrente(numCC);

    List<Delegado> delegados = h.getDelegados();

    List<Tema> temas = h.getTemas();

    Delegado d = delegados.get(0);
    Tema t = temas.get(0);

    h.escolheDelegado(d, t);

    // Check if sucessfull

    Cidadao fetchedCid = cidRepo.findById(cid.getId()).get();
    Delegado fetchedDel = delRepo.findById(d.getId()).get();

    Map<Tema, Delegado> testDelegados = fetchedCid.getDelegados();
    Map<Tema, Integer> testApoiantes = fetchedDel.getNumApoiantes();

    assertEquals(fetchedDel, testDelegados.get(t));
    assertEquals(1, testApoiantes.get(t));

    cleanRepos();
  }

  /** Verify if a ApoiarProjLeiHanlder works properly */
  @Test
  @Order(18)
  void test_apoiar_proj_lei_normal() {

    createTestProj();

    int numCC = 4321;

    Cidadao cid = new Cidadao("Ricardo", numCC);

    cidRepo.save(cid);

    ApoiarProjLeiHandler handler = new ApoiarProjLeiHandler(cidRepo, projRepo, votRepo);

    handler.setCidadaoCorrente(numCC);

    List<ProjetoLei> projs = handler.getProjsLei();

    ProjetoLei p = projs.get(0);

    handler.apoiarProjLei(p);

    ProjetoLei fetched = projRepo.findById(p.getId()).get();

    assertTrue(fetched.getListaApoiantes().contains(cid));

    cleanRepos();
  }

  /**
   * Verify if ApoiarProjLeiHandler works properly when supporting a ProjetoLei past the support
   * thershold, making it a Votacao
   */
  @Test
  @Order(19)
  void test_apoiar_proj_lei_votacao() {

    createTestProj();

    long nVotacoes = votRepo.count();

    int numCC = 43210;

    Cidadao cid = new Cidadao("Manuel", numCC);

    cidRepo.save(cid);

    ApoiarProjLeiHandler handler = new ApoiarProjLeiHandler(cidRepo, projRepo, votRepo);

    handler.setCidadaoCorrente(numCC);

    List<ProjetoLei> projs = handler.getProjsLei();

    ProjetoLei p = projs.get(0);

    long id = handler.apoiarProjLeiTeste(p);

    // Check if procedure done correctly

    assertEquals(nVotacoes + 1, votRepo.count());

    ProjetoLei fetched = projRepo.findById(p.getId()).get();

    assertEquals(StatusProjLei.FECHADO, fetched.getEstado());

    Votacao v = votRepo.findById(id).get();

    Map<Long, Integer> result = v.getVotosPublicos();

    assertEquals(1, result.get(p.getDelegadoProponente().getId()));

    assertEquals(1, v.getNumVotosAtivosSIM());

    cleanRepos();
  }

  /**
   * Verify if ApoiarProjLeiHandler works properly, not allowing a Cidadao to support a ProjetoLei
   * twice
   */
  @Test
  @Order(20)
  void test_apoiar_proj_lei_twice() {

    createTestProj();

    int numCC = 4321;

    Cidadao cid = new Cidadao("Ricardo", numCC);

    cidRepo.save(cid);

    ApoiarProjLeiHandler handler = new ApoiarProjLeiHandler(cidRepo, projRepo, votRepo);

    handler.setCidadaoCorrente(numCC);

    List<ProjetoLei> projs = handler.getProjsLei();

    ProjetoLei p = projs.get(0);

    handler.apoiarProjLei(p);

    ProjetoLei fetched = projRepo.findById(p.getId()).get();

    assertTrue(fetched.getListaApoiantes().contains(cid));

    ApoiarProjLeiHandler handler2 = new ApoiarProjLeiHandler(cidRepo, projRepo, votRepo);

    handler2.setCidadaoCorrente(numCC);

    List<ProjetoLei> projs2 = handler.getProjsLei();

    ProjetoLei p2 = projs2.get(0);

    handler.apoiarProjLei(p2);

    int previousLen = fetched.getListaApoiantes().size();

    fetched = projRepo.findById(p2.getId()).get();

    assertEquals(previousLen, fetched.getListaApoiantes().size());

    cleanRepos();
  }

  /** Verify if FecharProjsLei works properly */
  @Test
  @Order(21)
  void test_fechar_proj_lei() {

    createTestProj();

    FecharProjsLeiHandler hf = new FecharProjsLeiHandler(projRepo);

    hf.fecharProjsLei();

    assertTrue(projRepo.findByStatus(StatusProjLei.ABERTO).isEmpty());
    assertTrue(!projRepo.findByStatus(StatusProjLei.FECHADO).isEmpty());

    cleanRepos();
  }

  /**
   * Verify if ListarVotacaoHanlder works properly, closing a Votacao as approved when expected to
   * be
   */
  @Test
  @Order(22)
  void test_fechar_votacao_aprovada() {

    // Criar cidadaos
    Cidadao cid1 = new Cidadao("Eduarda", 123344);
    Cidadao cid2 = new Cidadao("Maria", 123432);
    Cidadao cid3 = new Cidadao("Jose", 123623);
    Cidadao cid4 = new Cidadao("Andre", 321645);

    cidRepo.save(cid1);
    cidRepo.save(cid2);
    cidRepo.save(cid3);
    cidRepo.save(cid4);

    // Criar tema
    Tema t = new Tema("Justiça");
    temaRepo.save(t);

    // Criar delegado
    Delegado del = new Delegado("Maria", 123513);
    delRepo.save(del);

    // Criar votacao
    Timestamp date = new Timestamp(System.currentTimeMillis());
    Votacao v = new Votacao(date, t);

    votRepo.save(v);

    Votacao v2 = votRepo.findById(v.getId()).get();

    // Adicionar delegado com o seu voto
    v2.addVotoDelegado(del, 1);

    // Associar votos dos cidadaos ao delegado
    cid1.addTemaDelegado(t, del);
    cid2.addTemaDelegado(t, del);
    cid3.addTemaDelegado(t, del);
    v2.addVotoAtivo(cid4, 0);

    delRepo.save(del);

    votRepo.save(v2);

    // Fechar votacao

    FecharVotacaoHandler h = new FecharVotacaoHandler(votRepo, delRepo);

    h.fecharVotacoes();

    Optional<Votacao> v1 = votRepo.findById(v.getId());

    assertEquals(StatusVotacao.APROVADA, v1.get().getEstado());
    assertEquals(4, v1.get().getNumVotosAtivosSIM());
    assertEquals(1, v1.get().getNumVotosAtivosNAO());

    cleanRepos();
  }

  /**
   * Verify if VotarPropostaHandler works properly when there is only a Cidadao voting, without a
   * Delegado
   */
  @Test
  @Order(23)
  void test_add_voto_cidadao_sem_delegado() {

    createTestVotacao();

    Cidadao c = new Cidadao("Marco", 45321);

    cidRepo.save(c);

    VotarPropostaHandler h = new VotarPropostaHandler(votRepo, cidRepo);

    h.registerCidadao(c.getNumCC());

    List<Votacao> lv = h.getListaVotacoes();

    Votacao v = lv.get(0);

    assertEquals(StatusVotacao.ABERTA, v.getEstado());

    int defaultVote = h.selectVotacao(v.getId());

    assertEquals(defaultVote, -1);

    h.votar(1);

    Votacao fetched = votRepo.findById(v.getId()).get();

    assertTrue(fetched.getListaCidadaosVotantes().contains(c.getId()));

    assertTrue(fetched.getNumVotosAtivosSIM() >= 1);

    cleanRepos();
  }

  /**
   * Verify if VotarPropostaHandler works properly when there is only a Delegado voting, without a
   * Delegado assigned to the first one
   */
  @Test
  @Order(24)
  void test_add_voto_delegado_sem_delegado() {

    createTestVotacao();

    Delegado d = new Delegado("Gina", 432167);

    delRepo.save(d);

    VotarPropostaHandler h = new VotarPropostaHandler(votRepo, cidRepo);

    h.registerCidadao(d.getNumCC());

    List<Votacao> lv = h.getListaVotacoes();

    Votacao v = lv.get(0);

    assertEquals(StatusVotacao.ABERTA, v.getEstado());

    int defaultVote = h.selectVotacao(v.getId());

    assertEquals(defaultVote, -1);

    h.votar(1);

    Votacao fetched = votRepo.findById(v.getId()).get();

    assertFalse(fetched.getListaCidadaosVotantes().contains(d.getId()));

    assertTrue(fetched.getVotosPublicos().containsKey(d.getId()));

    assertEquals(1, fetched.getVotosPublicos().get(d.getId()));

    Votacao n = new Votacao(new Timestamp(System.currentTimeMillis()), v.getTema());

    votRepo.save(n);

    cleanRepos();
  }

  /**
   * Verify if VotarPropostaHandler works properly when there is a Cidadao voting with a Delegado
   * assigned
   */
  @Test
  @Order(25)
  void test_add_voto_cidadao_com_delegado() {

    // votRepo.deleteAll();

    Votacao v = createTestVotacao();

    Cidadao c = new Cidadao("Tiago", 6784310);

    cidRepo.save(c);

    Delegado d = new Delegado("Maria", 945321);

    delRepo.save(d);

    EscolherDelegadoHandler escolherDelegado =
        new EscolherDelegadoHandler(delRepo, cidRepo, temaRepo);
    escolherDelegado.setCidadaoCorrente(c.getNumCC());
    escolherDelegado.getDelegados();
    escolherDelegado.getTemas();
    escolherDelegado.escolheDelegado(d, v.getTema());

    Cidadao fetch = cidRepo.findById(c.getId()).get();

    int votoDelegado = 0; // NO

    VotarPropostaHandler votarPropostaDelegado = new VotarPropostaHandler(votRepo, cidRepo);
    votarPropostaDelegado.registerCidadao(d.getNumCC());
    votarPropostaDelegado.selectVotacao(votarPropostaDelegado.getListaVotacoes().get(0).getId());
    votarPropostaDelegado.votar(votoDelegado);

    VotarPropostaHandler votarPropostaCidadao = new VotarPropostaHandler(votRepo, cidRepo);
    votarPropostaCidadao.registerCidadao(fetch.getNumCC());
    int defaultVote =
        votarPropostaCidadao.selectVotacao(votarPropostaDelegado.getListaVotacoes().get(0).getId());

    assertEquals(votoDelegado, defaultVote);

    FecharVotacaoHandler fh = new FecharVotacaoHandler(votRepo, delRepo);
    fh.fecharVotacoes();

    assertEquals(StatusVotacao.REJEITADA, votRepo.findById(v.getId()).get().getEstado());

    cleanRepos();
  }

  /**
   * Verify if ListarVotacaoHanlder works properly, closing a Votacao as rejected when expected to
   * be
   */
  @Test
  @Order(26)
  void test_fechar_votacao_rejeitada_cidadaos() {
    votRepo.deleteAll();

    // Criar cidadaos
    Cidadao cid1 = new Cidadao("Joao", 1873944);
    Cidadao cid2 = new Cidadao("Maria", 1233492);
    Cidadao cid3 = new Cidadao("Jose", 1235643);
    Cidadao cid4 = new Cidadao("Andre", 3212625);

    cidRepo.saveAndFlush(cid1);
    cidRepo.saveAndFlush(cid2);
    cidRepo.saveAndFlush(cid3);
    cidRepo.saveAndFlush(cid4);

    // Criar tema
    Tema t = new Tema("Justiça");
    temaRepo.save(t);

    // Criar votacao
    Timestamp date = new Timestamp(System.currentTimeMillis());
    Votacao v = new Votacao(date, t);

    votRepo.save(v);

    VotarPropostaHandler hv = new VotarPropostaHandler(votRepo, cidRepo);

    hv.registerCidadao(cid1.getNumCC());

    List<Votacao> v1 = hv.getListaVotacoes();

    hv.selectVotacao(v1.get(0).getId());

    hv.votar(1);

    VotarPropostaHandler hv2 = new VotarPropostaHandler(votRepo, cidRepo);

    hv2.registerCidadao(cid2.getNumCC());

    List<Votacao> v2 = hv.getListaVotacoes();

    hv2.selectVotacao(v2.get(0).getId());

    hv2.votar(0);

    VotarPropostaHandler hv3 = new VotarPropostaHandler(votRepo, cidRepo);

    hv3.registerCidadao(cid3.getNumCC());

    List<Votacao> v3 = hv.getListaVotacoes();

    hv3.selectVotacao(v3.get(0).getId());

    hv3.votar(0);

    VotarPropostaHandler hv4 = new VotarPropostaHandler(votRepo, cidRepo);

    hv4.registerCidadao(cid4.getNumCC());

    List<Votacao> v4 = hv.getListaVotacoes();

    hv4.selectVotacao(v4.get(0).getId());

    hv4.votar(0);

    // Fechar votacao
    FecharVotacaoHandler h = new FecharVotacaoHandler(votRepo, delRepo);

    h.fecharVotacoes();

    Optional<Votacao> v7 = votRepo.findById(v.getId());

    assertEquals(StatusVotacao.REJEITADA, v7.get().getEstado());
    assertEquals(3, v7.get().getNumVotosAtivosNAO());
    assertEquals(1, v7.get().getNumVotosAtivosSIM());

    cleanRepos();
  }

  /**
   * Verify if VotarPropostaHandler works properly when there is a Delegado voting with a Delegado
   * assigned
   */
  @Test
  @Order(27)
  void test_add_voto_delegado_com_delegado() {

    createTestVotacao();

    Delegado d = new Delegado("Gina", 432167);

    delRepo.save(d);

    Delegado d2 = new Delegado("Mariana", 431367);

    delRepo.save(d2);

    VotarPropostaHandler h = new VotarPropostaHandler(votRepo, cidRepo);

    h.registerCidadao(d.getNumCC());

    List<Votacao> lv = h.getListaVotacoes();

    Votacao v = lv.get(0);

    assertEquals(StatusVotacao.ABERTA, v.getEstado());

    int defaultVote = h.selectVotacao(v.getId());

    assertEquals(defaultVote, -1);

    h.votar(1);

    EscolherDelegadoHandler he = new EscolherDelegadoHandler(delRepo, cidRepo, temaRepo);

    he.setCidadaoCorrente(d2.getNumCC());

    he.escolheDelegado(he.getDelegados().get(0), he.getTemas().get(0));

    Delegado dCorrente = delRepo.findById(d2.getId()).get();

    VotarPropostaHandler h2 = new VotarPropostaHandler(votRepo, cidRepo);

    h2.registerCidadao(dCorrente.getNumCC());

    List<Votacao> lv2 = h.getListaVotacoes();

    Votacao v2 = lv2.get(0);

    assertEquals(StatusVotacao.ABERTA, v.getEstado());

    int defaultVote2 = h2.selectVotacao(v2.getId());

    assertEquals(1, defaultVote2);

    Votacao fetched = votRepo.findById(v.getId()).get();

    assertFalse(fetched.getListaCidadaosVotantes().contains(d.getId()));

    assertTrue(fetched.getVotosPublicos().containsKey(d.getId()));

    assertEquals(1, fetched.getVotosPublicos().get(d.getId()));

    Votacao n = new Votacao(new Timestamp(System.currentTimeMillis()), v.getTema());

    votRepo.save(n);

    cleanRepos();
  }

  /**
   * Verify if VotarPropostaHandler works properly when there are child Temas and Delegados involved
   */
  @Test
  @Order(28)
  void test_votacao_com_subtema() {
    createTestVotacaoWithSubTheme();

    Delegado del = new Delegado("Andre", 55555);
    Cidadao c1 = new Cidadao("Miguel", 2332433);
    Cidadao c2 = new Cidadao("David", 1232);

    delRepo.save(del);
    cidRepo.save(c1);
    cidRepo.save(c2);

    Tema t = temaRepo.findAll().get(1);
    Tema subtema = temaRepo.findAll().get(0);

    EscolherDelegadoHandler esdHandler = new EscolherDelegadoHandler(delRepo, cidRepo, temaRepo);
    esdHandler.setCidadaoCorrente(c1.getNumCC());
    esdHandler.escolheDelegado(del, t);

    Delegado fetched = delRepo.findById(del.getId()).get();

    assertEquals(1, fetched.getNumApoiantes().get(t));
    assertEquals(null, fetched.getNumApoiantes().get(subtema));

    VotarPropostaHandler vpHandler1 = new VotarPropostaHandler(votRepo, cidRepo);

    vpHandler1.registerCidadao(fetched.getNumCC());
    vpHandler1.selectVotacao(votRepo.findAll().get(0).getId());
    vpHandler1.votar(1);

    VotarPropostaHandler vpHandler2 = new VotarPropostaHandler(votRepo, cidRepo);

    vpHandler2.registerCidadao(c2.getNumCC());
    vpHandler2.selectVotacao(votRepo.findAll().get(0).getId());
    vpHandler2.votar(0);

    FecharVotacaoHandler fcvHandler = new FecharVotacaoHandler(votRepo, delRepo);
    fcvHandler.fecharVotacoes();

    Votacao v = votRepo.findAll().get(0);

    assertEquals(StatusVotacao.APROVADA, v.getEstado());
    assertEquals(1, v.getNumVotosAtivosNAO());
    assertEquals(2, v.getNumVotosAtivosSIM());

    cleanRepos();
  }

  /** Creates and adds a ProjetoLei with a Delegado and a Tema associated for testing */
  private void createTestProj() {

    Delegado d = new Delegado("Jeremias", 12345678);
    delRepo.save(d);

    Tema t = new Tema("Teste");
    temaRepo.save(t);

    Timestamp date = new Timestamp(System.currentTimeMillis());
    ApresentarProjLeiHandler h = new ApresentarProjLeiHandler(delRepo, projRepo, temaRepo);
    h.setDelegadoCorrente(d.getNumCC());
    h.apresentarProjLei("Recupera", "Reabilitação Física", PDF, date, h.getTemas().get(0));
  }

  /** Creates and adds a Votacao for testing */
  private Votacao createTestVotacao() {

    Timestamp date = new Timestamp(System.currentTimeMillis());
    Tema t = new Tema("Teste");
    temaRepo.save(t);

    Votacao v = new Votacao(date, t);
    votRepo.save(v);

    return v;
  }

  /** Creates and adds a Votacao with a child Tema associated for testing */
  private void createTestVotacaoWithSubTheme() {
    Timestamp date = new Timestamp(System.currentTimeMillis());
    Tema t = new Tema("Teste");
    Tema subtema = new Tema("Subteste", t);
    temaRepo.save(t);
    temaRepo.save(subtema);

    Votacao v = new Votacao(date, subtema);
    votRepo.save(v);
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
