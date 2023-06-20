package pt.ul.fc.css.democracia2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Entity
public class Tema {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String tipo;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_tema")
  private Tema pai;

  protected Tema() {
    // Does nothing
  }

  public Tema(String tipo, Tema pai) {
    this.tipo = tipo;
    this.pai = pai;
  }

  public Tema(String tipo) {
    this.tipo = tipo;
  }

  public Long getId() {
    return id;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public Tema getPai() {
    return pai;
  }

  public void setPai(Tema pai) {
    this.pai = pai;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, pai, tipo);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Tema)) return false;
    Tema other = (Tema) obj;
    return Objects.equals(id, other.id)
        && Objects.equals(pai, other.pai)
        && Objects.equals(tipo, other.tipo);
  }
  
  @Override
	public String toString() {
		return this.getTipo();
	}
}
