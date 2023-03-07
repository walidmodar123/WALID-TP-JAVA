package model;


import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class Credit {


    private Long Wa_id;
    private Double Wa_capitale_Emprunt;
    private Integer Wa_nombre_Mois;
    private Double Wa_taux_Mensuel;
    private String Wa_nom_Demandeur;
    private Double Wa_mensualite;

    @Override
    public String toString(){
        var creditStr = "==================================================================== \n";
            creditStr+= "=> Crédit n°                  : " +getWa_id()+ "                        \n";
            creditStr+= "=> Nom du demandeur de crédit : " + getWa_nom_Demandeur() + "           \n";
            creditStr+= "-------------------------------------------------------------------- \n";
            creditStr+= "=> Capitale Emprunté          : " + getWa_capitale_Emprunt() + "        \n";
            creditStr+= "=> Nombre de mois             : " + getWa_nombre_Mois() + "             \n";
            creditStr+= "=> Taux mensuel               : " + getWa_taux_Mensuel() + "            \n" ;
            creditStr+= "-------------------------------------------------------------------- \n";
            creditStr+= "=> Mensualité                 : "
                    + (getWa_mensualite() == 0.0 ? "NON-CALCULE":getWa_mensualite()+ " DH/mois")+"\n";
            creditStr+= "==================================================================== \n";

        return creditStr;
    }
}
