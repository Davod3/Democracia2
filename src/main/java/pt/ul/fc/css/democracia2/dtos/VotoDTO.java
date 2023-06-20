package pt.ul.fc.css.democracia2.dtos;

import org.springframework.stereotype.Component;

@Component
public class VotoDTO {

  private int voto;

  public int getVoto() {
    return voto;
  }

  public void setVoto(int voto) {
    this.voto = voto;
  }
}
