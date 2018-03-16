
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Clasa Tara contine numele tarii si un HashMap cu toate numele regiunilor si
 * obiectele corespunzatoare fiecarei regiuni
 * @author daniel
 */
public class Tara {

    private final String name;
    private LinkedHashMap<String, Regiune> regiuni = null;

    Tara(String name) {
        this.name = name;
        this.regiuni = new LinkedHashMap<>();
    }

    /**
     * Adauga regiune in HashMap
     *
     * @param s numele regiunii
     * @param r regiunea
     */
    public void addRegiune(String s, Regiune r) {
        this.regiuni.put(s, r);
    }

    /**
     * Verifica existenta regiunii cu numele s in HashMap, daca exista se
     * intoarce obiectul corespunzator regiunii pentru a putea fi folosit,
     * altfel null
     *
     * @param s numele
     * @return obiectul cautat sau null
     */
    public Regiune Exista(String s) {
        if (regiuni.containsKey(s)) {
            return regiuni.get(s);
        }
        return null;
    }

    /**
     * Metoda care realizeaza operatia de cautare pentru top5 pe toate regiunile
     * din HashMap daca nu se specifica explicit una, sau doar pe cea
     * specificata daca parametrul de intrare r(regiunea) nu este null
     *
     * @param queue coada de prioritati care va contine raspunsul final
     * @param r regiunea
     * @param l locatia
     * @param date1 data de inceput a sejurului cautat
     * @param date2 data de final a sejurului cautat
     */
    public void top5(PriorityQueue<InfoActiv> queue, String r, String l, Date date1, Date date2) {
        if (r == null) {
            for (Map.Entry<String, Regiune> entry : regiuni.entrySet()) {
                entry.getValue().top5(queue, l, date1, date2);
            }
        } else {
            regiuni.get(r).top5(queue, l, date1, date2);
        }
    }

    /**
     * Metoda care realizeaza cautarea celei mai bune oferte pentru o activitate
     * actualizand coada de prioritati prin cautarea pe toate regiunile salvate
     * in HashMap
     *
     * @param queue coada de prioritati care va contine raspunsul final
     * @param activitate activitatea
     */
    public void searchBest(PriorityQueue<InfoActiv> queue, String activitate) {
        for (Map.Entry<String, Regiune> entry : regiuni.entrySet()) {
            entry.getValue().searchBest(queue, activitate);
        }
    }

}
