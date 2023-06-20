package democracia2.javafx.services;

public class ProjectDTO {

  public Long id;
  public String titulo;
  public String descricao;
  public FileDTO pdf;
  public String dataValidade;
  public String tema;
  public String delegadoProponente;
  public String estado;

  public ProjectDTO(
      Long id,
      String titulo,
      String descricao,
      FileDTO pdf,
      String dataValidade,
      String tema,
      String delegadoProponente,
      String estado) {

    this.id = id;
    this.titulo = titulo;
    this.descricao = descricao;
    this.pdf = pdf;
    this.dataValidade = dataValidade;
    this.tema = tema;
    this.delegadoProponente = delegadoProponente;
    this.estado = estado;
  }
}
