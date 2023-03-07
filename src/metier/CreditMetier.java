package metier;

import dao.IDao;

import lombok.*;
import model.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Data @AllArgsConstructor @NoArgsConstructor
@Service("metier")
public class CreditMetier implements ICreditMetier{
    @Autowired
    @Qualifier("dao")
    IDao<Credit,Long> creditDao;
    @Override
    public Credit calculer_Mensualite(Long id) throws Exception{
        var credit = creditDao.trouverParID(id);

        if (credit == null)
        {
            throw new Exception("L'id du credit est incorrecte :: [Credit non trouve]");
        }
        else {
            double  Wa_taux         = credit.getWa_taux_Mensuel();
                    Wa_taux         = Wa_taux/1200;
            double  Wa_capitale     = credit.getWa_capitale_Emprunt();
            int     Wa_nbr_mois     = credit.getWa_nombre_Mois();

            double  Wa_mensualite   = (Wa_capitale * Wa_taux) / (1 - (Math.pow((1 + Wa_taux), -1 * Wa_nbr_mois)));
                    Wa_mensualite   = Math.round(Wa_mensualite*100)/100;

                   credit.setWa_mensualite(Wa_mensualite);

            return credit;
        }
    }
}
