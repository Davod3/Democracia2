package pt.ul.fc.css.democracia2.dtos;

import org.springframework.stereotype.Component;
import pt.ul.fc.css.democracia2.entities.Tema;

/**
 * @author André Dias - 55314
 * @author David Pereira - 56361
 * @author Miguel Cut - 56339
 */
@Component
public class TemaDTO {

  private String id;
  private String tipo;
  private Tema pai;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
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
}
