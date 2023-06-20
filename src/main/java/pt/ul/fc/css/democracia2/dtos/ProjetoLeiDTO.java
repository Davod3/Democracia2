package pt.ul.fc.css.democracia2.dtos;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.entities.StatusProjLei;

@Component
public class ProjetoLeiDTO {

  private Long id;
  private String titulo;
  private String descricao;
  private FilePDFDTO pdf;
  private String dataValidade;
  private String tema;
  private String delegadoProponente;
  private StatusProjLei estado;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public FilePDFDTO getPdf() {
    return pdf;
  }

  public void setPdf(FilePDFDTO pdf) {
    this.pdf = pdf;
  }

  public String getDataValidade() {
    return dataValidade;
  }

  public void setDataValidade(String dataValidade) {
    this.dataValidade = dataValidade;
  }

  public String getTema() {
    return tema;
  }

  public void setTema(String tema) {
    this.tema = tema;
  }

  public String getDelegadoProponente() {
    return delegadoProponente;
  }

  public void setDelegadoProponente(String delegadoProponente) {
    this.delegadoProponente = delegadoProponente;
  }

  public StatusProjLei getEstado() {
    return estado;
  }

  public void setEstado(StatusProjLei estado) {
    this.estado = estado;
  }
}
