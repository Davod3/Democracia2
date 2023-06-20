package pt.ul.fc.css.democracia2.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.NonNull;

// Code adapted from https://www.baeldung.com/hibernate-inheritance
/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cidadao", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
public class Cidadao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @NonNull private String name;

  @Column(unique = true)
  private int numCC;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "mapa_tema_delegado",
      joinColumns = {@JoinColumn(name = "id_cidadao", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "id_delegado", referencedColumnName = "id")})
  @MapKeyJoinColumn(name = "id_tema")
  private Map<Tema, Delegado> delegados = new HashMap<>();

  private int tokenOAUTH; // Not implemented yet

  public Cidadao(@NonNull String name, int numCC) {
    this.name = name;
    this.numCC = numCC;
  }

  protected Cidadao() {
    // Does nothing
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getNumCC() {
    return numCC;
  }

  public void setNumCC(int numCC) {
    this.numCC = numCC;
  }

  public int getTokenOAUTH() {
    return tokenOAUTH;
  }

  public void setTokenOAUTH(int tokenOAUTH) {
    this.tokenOAUTH = tokenOAUTH;
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegados, id, name, numCC, tokenOAUTH);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Cidadao)) return false;
    Cidadao other = (Cidadao) obj;
    return Objects.equals(delegados, other.delegados)
        && Objects.equals(id, other.id)
        && Objects.equals(name, other.name)
        && numCC == other.numCC
        && tokenOAUTH == other.tokenOAUTH;
  }

  public Map<Tema, Delegado> getDelegados() {
    return delegados;
  }

  public void setDelegados(Map<Tema, Delegado> delegados) {
    this.delegados = delegados;
  }

  public void addTemaDelegado(Tema t, Delegado d) {

    if (this.delegados.containsKey(t)) {

      Delegado old = this.delegados.get(t);

      if (!old.equals(d)) {

        old.removeApoiante(t);
        d.addApoiante(t);
        this.delegados.put(t, d);
      }

    } else {

      d.addApoiante(t);
      this.delegados.put(t, d);
    }
  }
}
