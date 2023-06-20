package pt.ul.fc.css.democracia2.handlers;

import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.repositories.DelegadoRepository;
import pt.ul.fc.css.democracia2.repositories.VotacaoRepository;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *     <p>Handler to close votacao which have passed the close date threshold, assigning the needed
 *     votes by Cidadao who have not voted
 */
public class FecharVotacaoHandler {

  private VotacaoRepository votRepo;
  private DelegadoRepository delRepo;

  public FecharVotacaoHandler(VotacaoRepository votRepo, DelegadoRepository delRepo) {
    this.votRepo = votRepo;
    this.delRepo = delRepo;
  }

  /** Close all votacao which have passed the close date threshold */
  @Transactional
  public void fecharVotacoes() {
    ListarVotacoesHandler hl = new ListarVotacoesHandler(votRepo);
    List<Votacao> votAbertas = hl.listarVotacoesCorrentes();
    for (Votacao v : votAbertas) {
      fecharVotacao(v);
    }
  }

  /**
   * Close a given Votacao, assigning the needed missing votes
   *
   * @param v: Votacao
   * @requires v != null
   */
  @Transactional
  public void fecharVotacao(Votacao v) {
    Timestamp now = new Timestamp(System.currentTimeMillis());
    if (v.getDataFecho().before(now)) {
      contarVotos(v);

      classificarVotacao(v);
      // Salvar votacao
      votRepo.save(v);
    }
  }

  /**
   * Counts all the votes of a given Votacao, including the passive votes by Cidadao who have not
   * voted but have assigned Delegado
   *
   * @param v: Votacao
   * @requires v != null
   */
  public void contarVotos(Votacao v) {
    int nrVotosPassivosSim = 0;
    int nrVotosPassivosNao = 0;

    Map<Long, Integer> votosPublicos = v.getVotosPublicos();
    // Atribuir votos
    for (Entry<Long, Integer> entry : votosPublicos.entrySet()) {
      Delegado d = delRepo.findById(entry.getKey()).get();
      Integer voto = entry.getValue();
      Map<Tema, Integer> nrApoiantes = d.getNumApoiantes();
      Tema tema = v.getTema();

      if (nrApoiantes.containsKey(tema)) {
        if (voto == 0) {
          nrVotosPassivosNao += nrApoiantes.get(tema);
        } else {
          nrVotosPassivosSim += nrApoiantes.get(tema);
        }
      } else {
        while (!nrApoiantes.containsKey(tema)) {
          if (tema.getPai() != null) {
            tema = tema.getPai();
          } else {
            break;
          }
          if (voto == 0) {
            nrVotosPassivosNao += nrApoiantes.get(tema);
          } else {
            nrVotosPassivosSim += nrApoiantes.get(tema);
          }
        }
      }
    }
    v.addVotosAtivos(0, nrVotosPassivosNao);
    v.addVotosAtivos(1, nrVotosPassivosSim);
  }

  /**
   * Sets the result of a given Votacao, either APROVADO or REJEITADO
   *
   * @param v: Votacao
   * @requires v != null
   */
  public void classificarVotacao(Votacao v) {
    // Se mais de metade responder sim APROVAR
    if ((v.getNumVotosAtivosSIM()) > v.getNumVotosAtivosNAO()) {
      v.aprovarVotacao();
    } else {
      // Caso contrário REJEITAR
      v.rejeitarVotacao();
    }
  }
}
