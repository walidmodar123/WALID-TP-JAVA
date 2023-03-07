package presentation;
import lombok.*;
import metier.CreditMetier;
import metier.ICreditMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Data @AllArgsConstructor @NoArgsConstructor
@Controller
public class CreditControleur implements ICreditControleur{
    @Autowired
    @Qualifier("metier")
    ICreditMetier creditMetier;
    @Override
    public void afficher_Mensualite(Long id) throws Exception {
        var creditAvecMensualite = creditMetier.calculer_Mensualite(id);
        System.out.println(creditAvecMensualite);
    }
}
