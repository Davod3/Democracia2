package pt.ul.fc.css.democracia2.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.lang.NonNull;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Entity
@DiscriminatorValue("2")
public final class Delegado extends Cidadao {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @ElementCollection(fetch = FetchType.EAGER)
  @JoinColumn
  private Map<Tema, Integer> numApoiantes = new HashMap<>();

  public Delegado(@NonNull String name, int numCC) {

    super(name, numCC);
  }

  protected Delegado() {
    super();
  }

  public Map<Tema, Integer> getNumApoiantes() {
    return numApoiantes;
  }

  public void setNumApoiantes(Map<Tema, Integer> numApoiantes) {
    this.numApoiantes = numApoiantes;
  }

  public void addApoiante(Tema t) {

    if (this.numApoiantes.containsKey(t)) {
      int current = numApoiantes.get(t);
      numApoiantes.put(t, current + 1);
    } else {
      numApoiantes.put(t, 1);
    }
  }

  public void removeApoiante(Tema t) {

    if (this.numApoiantes.containsKey(t)) {
      int current = numApoiantes.get(t);

      if (current >= 1) {

        numApoiantes.put(t, current - 1);
      }
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + Objects.hash(id, numApoiantes);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (!(obj instanceof Delegado)) return false;
    Delegado other = (Delegado) obj;
    return Objects.equals(id, other.id) && Objects.equals(numApoiantes, other.numApoiantes);
  }
  
  @Override
  public String toString() {
    return this.getName();
  }
}
