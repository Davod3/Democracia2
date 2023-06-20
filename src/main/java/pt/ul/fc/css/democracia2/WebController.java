package pt.ul.fc.css.democracia2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

  @RequestMapping("/")
  public String getIndex(Model model) {
    return "index";
  }
}
