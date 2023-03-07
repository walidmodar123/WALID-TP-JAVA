package metier;

import model.Credit;

public interface ICreditMetier {
    Credit calculer_Mensualite(Long id) throws Exception;
}
