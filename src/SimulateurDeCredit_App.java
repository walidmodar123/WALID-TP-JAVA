import dao.IDao;
import dao.CreditDao;
import lombok.var;
import metier.CreditMetier;
import metier.ICreditMetier;
import model.Credit;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import presentation.CreditControleur;
import presentation.ICreditControleur;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Scanner;

public class SimulateurDeCredit_App {
    static Scanner clavier = new Scanner(System.in);
    static ICreditControleur creditControleur;

    private static boolean estUnNombre(String input){
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e ){
            return false;
        }
    }

    public static void test1(){
        // instanciation des différents compsants de l'application
        var dao = new CreditDao();
        var metier = new CreditMetier();
        var controleur = new CreditControleur();
        // injection des dépendances
        metier.setCreditDao(dao);
        controleur.setCreditMetier(metier);
        // tester
        String rep = "";
        do {
            System.out.println("=> [Test 1] calcule de Mensualité de cédit <= \n");
            try {
                String input = "";
                while (true){
                    System.out.println("=> Entrez l'id du crédit : ");
                    input = clavier.nextLine();
                    if (estUnNombre(input)) break;
                    System.err.println("Entrée non valide !!!");
                }
                long id = Long.parseLong(input);
                controleur.afficher_Mensualite(id);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Voulez vous quittez (oui/non) ? ");
            rep = clavier.nextLine();
        }while (!rep.equalsIgnoreCase("oui"));
        System.out.println("Au revoir ^_^");
    }
    public static void test2() throws Exception {

        String daoClass;
        String serviceClass;
        String controllerClass;

        Properties properties = new Properties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream("config.properties");

        if (propertiesFile == null) throw new Exception("fichier config introuvable");
        else {
            try {
                properties.load(propertiesFile);
                daoClass        = properties.getProperty("DAO");
                serviceClass    = properties.getProperty("SERVICE");
                controllerClass = properties.getProperty("CONTROLLER");
                propertiesFile.close();
            }
            catch (IOException e){
                throw new Exception("Problème de chargement des propriétés du fichier config");
            }
            finally {
                properties.clear();
            }
        }
        try {
            Class cDao          = Class.forName(daoClass);
            Class cMetier       = Class.forName(serviceClass);
            Class cController   = Class.forName(controllerClass);

            var dao = (IDao<Credit, Long>) cDao.getDeclaredConstructor().newInstance();
            var metier = (ICreditMetier) cMetier.getDeclaredConstructor().newInstance();
            creditControleur    = (ICreditControleur) cController.getDeclaredConstructor().newInstance();

            Method setDao       = cMetier.getMethod("setCreditDao", IDao.class);
            setDao.invoke(metier,dao);

            Method setMetier    = cController.getMethod("setCreditMetier", ICreditMetier.class);
            setMetier.invoke(creditControleur,metier);

            creditControleur.afficher_Mensualite(1L);
        }
        catch (Exception e ){
            e.printStackTrace();
        }
    }
    public static void test3() throws Exception{
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-ioc.xml");
            creditControleur = (ICreditControleur) context.getBean("controleur");
            creditControleur.afficher_Mensualite(1L);
    }

    public static void test4() throws Exception{
        ApplicationContext context = new AnnotationConfigApplicationContext("dao","metier","presentation");
        creditControleur = (ICreditControleur) context.getBean(ICreditControleur.class);
        creditControleur.afficher_Mensualite(1L);
    }

    public static void main(String[] args) throws Exception {
        test2();
    }
}