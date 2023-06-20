package pt.ul.fc.css.democracia2.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.NonNull;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Entity
public class Votacao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp dataFecho;

  @NonNull @ManyToOne @JoinColumn private Tema tema;

  private int numVotosAtivosSIM;

  @ElementCollection(fetch = FetchType.EAGER)
  @JoinTable(name = "votos_publicos_delegados")
  private Map<Long, Integer> listaDelegados;

  private int numVotosAtivosNAO;

  private List<Long> listaCidadaosVotantes;

  @Enumerated(EnumType.STRING)
  private StatusVotacao estado;

  public Votacao() {
    // EMPTY
  }

  public Votacao(@NonNull Timestamp dataFecho, @NonNull Tema tema) {
    this.dataFecho = dataFecho;
    this.tema = tema;
    this.listaDelegados = new HashMap<>();
    listaCidadaosVotantes = new LinkedList<>();
    estado = StatusVotacao.ABERTA;
  }

  public int getTotalNumActiveVotes() {
    return numVotosAtivosNAO + numVotosAtivosSIM;
  }

  /**
   * Adiciona um delegado à lista correspondente, segundo o voto
   *
   * @requires voto == 0 || voto == 1
   */
  public boolean addVotoDelegado(Delegado delegado, int voto) {

    if (!this.listaDelegados.containsKey(delegado.getId())) {

      this.listaDelegados.put(delegado.getId(), voto);

      if (voto == 0) {
        numVotosAtivosNAO++;
      } else {
        numVotosAtivosSIM++;
      }

      return true;
    }

    return false;
  }

  /**
   * Adiciona um voto ao numero de votos ativos
   *
   * @requires voto == 0 || voto == 1
   */
  public boolean addVotoAtivo(Cidadao c, int voto) {

    if (!this.listaCidadaosVotantes.contains(c.getId())) {

      if (voto == 0) {
        numVotosAtivosNAO++;
      } else {
        numVotosAtivosSIM++;
      }

      this.listaCidadaosVotantes.add(c.getId());

      return true;
    }

    return false;
  }

  public void addVotosAtivos(int voto, int nrVotos) {
    if (voto == 0) {
      numVotosAtivosNAO += nrVotos;
    } else {
      numVotosAtivosSIM += nrVotos;
    }
  }

  public void aprovarVotacao() {
    this.estado = StatusVotacao.APROVADA;
  }

  public void rejeitarVotacao() {
    this.estado = StatusVotacao.REJEITADA;
  }

  public StatusVotacao getEstado() {
    return estado;
  }

  public Map<Long, Integer> getVotosPublicos() {
    return this.listaDelegados;
  }

  public List<Long> getListaCidadaosVotantes() {
    return listaCidadaosVotantes;
  }

  public void setListaCidadaosVotantes(List<Long> listaCidadaosVotantes) {
    this.listaCidadaosVotantes = listaCidadaosVotantes;
  }

  public long getId() {
    return id;
  }

  public Tema getTema() {
    return tema;
  }

  public Timestamp getDataFecho() {
    return dataFecho;
  }

  public int getNumVotosAtivosSIM() {
    return numVotosAtivosSIM;
  }

  public int getNumVotosAtivosNAO() {
    return numVotosAtivosNAO;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setDataFecho(Timestamp dataFecho) {
    this.dataFecho = dataFecho;
  }

  public void setTema(Tema tema) {
    this.tema = tema;
  }

  public void setNumVotosAtivosSIM(int numVotosAtivosSIM) {
    this.numVotosAtivosSIM = numVotosAtivosSIM;
  }

  public void setListaDelegados(Map<Long, Integer> listaDelegados) {
    this.listaDelegados = listaDelegados;
  }

  public Map<Long, Integer> getListaDelegados() {
    return this.listaDelegados;
  }

  public void setNumVotosAtivosNAO(int numVotosAtivosNAO) {
    this.numVotosAtivosNAO = numVotosAtivosNAO;
  }

  public void setEstado(StatusVotacao estado) {
    this.estado = estado;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataFecho, estado, id, tema);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Votacao)) return false;
    Votacao other = (Votacao) obj;
    return Objects.equals(dataFecho, other.dataFecho)
        && estado == other.estado
        && id == other.id
        && Objects.equals(tema, other.tema);
  }
}
