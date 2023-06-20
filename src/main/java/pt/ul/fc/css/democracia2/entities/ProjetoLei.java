package pt.ul.fc.css.democracia2.entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.File;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Entity
public class ProjetoLei {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NonNull private String titulo;

  @NonNull private String descricao;

  @NonNull @Embedded private FilePDF pdf;

  @NonNull
  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp dataValidade;

  @NonNull @ManyToOne @JoinColumn private Tema tema;

  @NonNull @ManyToOne @JoinColumn private Delegado delegadoProponente;

  @NonNull
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinColumn
  private List<Cidadao> listaApoiantes;

  @NonNull
  @Enumerated(EnumType.STRING)
  private StatusProjLei estado;

  public ProjetoLei() {}

  public ProjetoLei(
      String titulo,
      String descricao,
      File pdf2,
      Timestamp dataValidade,
      Tema tema,
      Delegado delegadoProponente) {
    this.pdf = new FilePDF(pdf2);
    this.titulo = titulo;
    this.descricao = descricao;
    this.dataValidade = dataValidade;
    this.tema = tema;
    this.delegadoProponente = delegadoProponente;
    this.listaApoiantes = new LinkedList<>();
    listaApoiantes.add(delegadoProponente);
    this.estado = StatusProjLei.ABERTO;
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getDescricao() {
    return descricao;
  }

  public FilePDF getPDF() {
    Hibernate.initialize(pdf);
    return pdf;
  }

  public Timestamp getDataValidade() {
    return dataValidade;
  }

  public Tema getTema() {
    return tema;
  }

  public Delegado getDelegadoProponente() {
    return delegadoProponente;
  }

  public List<Cidadao> getListaApoiantes() {
    return listaApoiantes;
  }

  public boolean addApoiante(Cidadao cidadao) {
    if (estado.equals(StatusProjLei.ABERTO) && !this.listaApoiantes.contains(cidadao)) {
      listaApoiantes.add(cidadao);
      return true;
    }

    return false;
  }

  public StatusProjLei getEstado() {
    return estado;
  }

  public void fecharProjeto() {
    this.estado = StatusProjLei.FECHADO;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataValidade, delegadoProponente, descricao, estado, id, pdf, tema, titulo);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof ProjetoLei)) return false;
    ProjetoLei other = (ProjetoLei) obj;
    return Objects.equals(dataValidade, other.dataValidade)
        && Objects.equals(delegadoProponente, other.delegadoProponente)
        && Objects.equals(descricao, other.descricao)
        && estado == other.estado
        && Objects.equals(id, other.id)
        && Objects.equals(pdf, other.pdf)
        && Objects.equals(tema, other.tema)
        && Objects.equals(titulo, other.titulo);
  }

  @Override
  public String toString() {
    return "ProjetoLei [id="
        + id
        + ", titulo="
        + titulo
        + ", descricao="
        + descricao
        + ", pdf="
        + pdf
        + ", dataValidade="
        + dataValidade
        + ", tema="
        + tema
        + ", delegadoProponente="
        + delegadoProponente
        + ", listaApoiantes="
        + listaApoiantes
        + ", estado="
        + estado
        + "]";
  }
}
