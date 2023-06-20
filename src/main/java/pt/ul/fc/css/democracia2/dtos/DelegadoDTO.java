package pt.ul.fc.css.democracia2.dtos;

import org.springframework.stereotype.Component;

@Component
public class DelegadoDTO {

  private Long id;
  private String name;
  private int numCC;

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
}
