package pt.ul.fc.css.democracia2;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.ProjetoLei;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.handlers.FecharProjsLeiHandler;
import pt.ul.fc.css.democracia2.handlers.FecharVotacaoHandler;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;
import pt.ul.fc.css.democracia2.repositories.ProjetoLeiRepository;
import pt.ul.fc.css.democracia2.repositories.TemaRepository;
import pt.ul.fc.css.democracia2.repositories.VotacaoRepository;

@SpringBootApplication
@EnableScheduling
public class Democracy2 {

  @Autowired CidadaoRepository cidRepo;
  @Autowired DelegadoRepository delRepo;
  @Autowired VotacaoRepository votRepo;
  @Autowired TemaRepository temaRepo;
  @Autowired ProjetoLeiRepository projRepo;
  @Autowired static FecharProjsLeiHandler fecharProjsLeiHandler;
  @Autowired static FecharVotacaoHandler fecharVotacaoHandler;

  private static final Logger log = LoggerFactory.getLogger(Democracy2.class);

  public static void main(String[] args) {
    SpringApplication.run(Democracy2.class, args);
  }

  @Scheduled(fixedRate = 10000)
  public static void fecharProjsLei() {
    System.out.println("Fechar Projetos Lei");
    fecharProjsLeiHandler.fecharProjsLei();
  }

  @Scheduled(fixedRate = 10000)
  public static void fecharVotacoes() {
    System.out.println("Fechar Votacoes");
    fecharVotacaoHandler.fecharVotacoes();
  }

  @Bean
  public CommandLineRunner demo() {
    return (args) -> {
      fecharProjsLeiHandler = new FecharProjsLeiHandler(projRepo);
      fecharProjsLei();

      fecharVotacaoHandler = new FecharVotacaoHandler(votRepo, delRepo);
      fecharVotacoes();

      System.out.println("APP STARTED!!");

      Timestamp dateToday = new Timestamp(System.currentTimeMillis());
      LocalDate tomorrow = LocalDate.now().plusDays(1);
      Timestamp dateTomorrow = Timestamp.valueOf(tomorrow.atStartOfDay());

      Cidadao cid1 = new Cidadao("Eduardo", 12345);
      Cidadao cid2 = new Cidadao("Francisco", 123456);
      Cidadao cid3 = new Cidadao("Joana", 123457);
      Cidadao cid4 = new Cidadao("Maria", 123458);
      Cidadao cid5 = new Cidadao("Miguel", 123459);
      cidRepo.save(cid1);
      cidRepo.save(cid2);
      cidRepo.save(cid3);
      cidRepo.save(cid4);
      cidRepo.save(cid5);

      Delegado del1 = new Delegado("Rute", 123456789);
      Delegado del2 = new Delegado("Tomás", 123436789);
      Delegado del3 = new Delegado("David", 123446789);
      delRepo.save(del1);
      delRepo.save(del2);
      delRepo.save(del3);

      Tema t1 = new Tema("Justiça");
      Tema t2 = new Tema("Saúde");
      Tema t3 = new Tema("Educação");
      Tema t4 = new Tema("Economia");
      Tema t5 = new Tema("Política");
      temaRepo.save(t1);
      temaRepo.save(t2);
      temaRepo.save(t3);
      temaRepo.save(t4);
      temaRepo.save(t5);

      Tema st1 = new Tema("Crime", t1);
      Tema st2 = new Tema("Sistema Prisional", t1);
      Tema st3 = new Tema("Saúde pública", t2);
      Tema st4 = new Tema("Saúde privada", t2);
      Tema st5 = new Tema("Tecnologia na educação", t3);
      Tema st6 = new Tema("Financiamento na educação", t3);
      Tema st7 = new Tema("Emprego", t4);
      Tema st8 = new Tema("Comércio", t4);
      Tema st9 = new Tema("Sistemas eleitorais", t5);
      Tema st10 = new Tema("Relações internacionais", t5);
      temaRepo.save(st1);
      temaRepo.save(st2);
      temaRepo.save(st3);
      temaRepo.save(st4);
      temaRepo.save(st5);
      temaRepo.save(st6);
      temaRepo.save(st7);
      temaRepo.save(st8);
      temaRepo.save(st9);
      temaRepo.save(st10);

      Votacao vot1 = new Votacao(dateToday, t1);
      Votacao vot2 = new Votacao(dateToday, t2);
      Votacao vot3 = new Votacao(dateToday, t3);
      Votacao vot4 = new Votacao(dateTomorrow, t4);
      Votacao vot5 = new Votacao(dateTomorrow, t5);
      votRepo.save(vot1);
      votRepo.save(vot2);
      votRepo.save(vot3);
      votRepo.save(vot4);
      votRepo.save(vot5);

      File pdf = new File("PDF_for_test.pdf");

      ProjetoLei proj1 = new ProjetoLei("Title1", "Description1", pdf, dateToday, t1, del1);
      ProjetoLei proj2 = new ProjetoLei("Title2", "Description2", pdf, dateToday, t2, del2);
      ProjetoLei proj3 = new ProjetoLei("Title3", "Description3", pdf, dateToday, t3, del3);
      ProjetoLei proj4 = new ProjetoLei("Title4", "Description4", pdf, dateTomorrow, t4, del1);
      ProjetoLei proj5 = new ProjetoLei("Title5", "Description5", pdf, dateTomorrow, t5, del2);
      projRepo.save(proj1);
      projRepo.save(proj2);
      projRepo.save(proj3);
      projRepo.save(proj4);
      projRepo.save(proj5);
    };
  }
}
