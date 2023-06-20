package pt.ul.fc.css.democracia2.dtos;

import org.springframework.stereotype.Component;

@Component
public class CidadaoDTO {

  private Long id;
  private String name;
  private int numCC;
  private boolean isCidadao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public boolean isCidadao() {
    return isCidadao;
  }

  public void setCidadao(boolean isCidadao) {
    this.isCidadao = isCidadao;
  }
}
