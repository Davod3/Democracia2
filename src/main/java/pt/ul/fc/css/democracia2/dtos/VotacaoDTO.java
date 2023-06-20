package pt.ul.fc.css.democracia2.dtos;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import pt.ul.fc.css.democracia2.entities.StatusVotacao;
import pt.ul.fc.css.democracia2.entities.Tema;

@Component
public class VotacaoDTO {

  private long id;
  private Timestamp dataFecho;
  private Tema tema;
  private int numVotosAtivosSIM;
  private Map<Long, Integer> listaDelegados;
  private int numVotosAtivosNAO;
  private List<Long> listaCidadaosVotantes;
  private StatusVotacao estado;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  
  public Timestamp getDataFecho() {
    return dataFecho;
  }


  public void setDataFecho(Timestamp dataFecho) {
    this.dataFecho = dataFecho;
  }

  public Tema getTema() {
    return tema;
  }

  public void setTema(Tema tema) {
    this.tema = tema;
  }

  public int getNumVotosAtivosSIM() {
    return numVotosAtivosSIM;
  }

  public void setNumVotosAtivosSIM(int numVotosAtivosSIM) {
    this.numVotosAtivosSIM = numVotosAtivosSIM;
  }

  public Map<Long, Integer> getListaDelegados() {
    return listaDelegados;
  }

  public void setListaDelegados(Map<Long, Integer> listaDelegados) {
    this.listaDelegados = listaDelegados;
  }

  public int getNumVotosAtivosNAO() {
    return numVotosAtivosNAO;
  }

  public void setNumVotosAtivosNAO(int numVotosAtivosNAO) {
    this.numVotosAtivosNAO = numVotosAtivosNAO;
  }

  public List<Long> getListaCidadaosVotantes() {
    return listaCidadaosVotantes;
  }

  public void setListaCidadaosVotantes(List<Long> listaCidadaosVotantes) {
    this.listaCidadaosVotantes = listaCidadaosVotantes;
  }

  public StatusVotacao getEstado() {
    return estado;
  }

  public void setEstado(StatusVotacao estado) {
    this.estado = estado;
  }
}
