package pt.ul.fc.css.democracia2.handlers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.entities.Cidadao;
import pt.ul.fc.css.democracia2.entities.Delegado;
import pt.ul.fc.css.democracia2.entities.Tema;
import pt.ul.fc.css.democracia2.entities.Votacao;
import pt.ul.fc.css.democracia2.repositories.CidadaoRepository;
import pt.ul.fc.css.democracia2.repositories.VotacaoRepository;

/*
 *
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 *
 */
@Component
public class VotarPropostaHandler {

  private VotacaoRepository votRepo;
  private CidadaoRepository cidRepo;
  private Votacao votacaoCorrente;
  private Cidadao cidadaoCorrente;

  public VotarPropostaHandler(VotacaoRepository votRepo, CidadaoRepository cidRepo) {
    this.votRepo = votRepo;
    this.cidRepo = cidRepo;
  }

  /**
   * Sets the current cidadao to a given cidadao
   *
   * @param c The given cidadao
   */
  public int registerCidadao(int numCC) {
    Cidadao c = cidRepo.findByNumCC(numCC);
    if (c == null) {
      return -1;
    }
    this.cidadaoCorrente = c;
    return 1;
  }

  /**
   * Returns a list of currently active votacoes
   *
   * @return The list of votacoes
   */
  public List<Votacao> getListaVotacoes() {
    ListarVotacoesHandler h = new ListarVotacoesHandler(this.votRepo);
    return h.listarVotacoesCorrentes();
  }

  /**
   * Sets a given votacao as current
   *
   * @requires Given votacao must be in the list returned by getListaVotacoes().
   * @param v The given votacao id
   * @return An integer representing the default vote of the citizen's delegado, should it exist. (0
   *     == NO, 1 == YES, -1 == NON-EXISTANT)
   */
  public int selectVotacao(Long votId) {
    Optional<Votacao> optionalV = votRepo.findById(votId);
    Votacao v = null;
    if (optionalV.isPresent()) {
      v = optionalV.get();
    } else {
      return -1;
    }
    if (!v.getListaCidadaosVotantes().contains(this.cidadaoCorrente.getId())) {

      this.votacaoCorrente = v;

      return defaultVote();
    } else {
      return -1;
    }
  }

  /**
   * Sets the vote of current votacao to a given option associated to current cidadao
   *
   * @param option The given option
   * @requires Current cidadao must be set using registerCidadao() and current votacao must be set
   *     using selectVotacao().
   * @return true if the operation was successful
   */
  public boolean votar(int option) {

    return this.votRepo.votarEmVotacao(option, this.votacaoCorrente.getId(), cidadaoCorrente);
  }

  /**
   * Checks if the is a default vote and returns it.
   *
   * @return The default vote if it exists, -1 otherwise
   */
  public int defaultVote() {

    Tema t = this.votacaoCorrente.getTema();
    Map<Tema, Delegado> delegados = this.cidadaoCorrente.getDelegados();

    while (!delegados.containsKey(t)) {

      if (t.getPai() != null) {
        t = t.getPai();
      } else {
        return -1;
      }
    }

    Delegado d = delegados.get(t);

    Map<Long, Integer> votosPublicos = this.votacaoCorrente.getVotosPublicos();

    if (votosPublicos.containsKey(d.getId())) {
      return votosPublicos.get(d.getId());
    }

    return -1;
  }
}
