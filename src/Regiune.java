
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Clasa Regiune contine numele regiunii si un HashMap cu toate numele
 * localitatilor si obiectele corespunzatoare fiecarei localitati
 *
 * @author daniel
 */
public class Regiune {

    private final String name;
    private LinkedHashMap<String, Locatie> locatii = null;

    Regiune(String name) {
        this.name = name;
        this.locatii = new LinkedHashMap<>();
    }

    /**
     * Adauga localitate in HashMap
     *
     * @param s numele localitatii
     * @param loc localitatea
     */
    public void addLocatie(String s, Locatie loc) {
        this.locatii.put(s, loc);
    }

    /**
     * Verifica existenta locatiei cu numele s in HashMap, daca exista se
     * intoarce obiectul corespunzator locatiei pentru a putea fi folosit,
     * altfel null
     *
     * @param s numele
     * @return obiectul cautat sau null
     */
    public Locatie Exista(String s) {
        if (locatii.containsKey(s)) {
            return locatii.get(s);
        }
        return null;
    }

    /**
     * Metoda care realizeaza operatia de cautare pentru top5 pe toate localitatile
     * din HashMap daca nu se specifica explicit una, sau doar pe cea
     * specificata daca parametrul de intrare l(localitatea) nu este null
     *
     * @param queue coada de prioritati care va contine raspunsul final
     * @param l locatia
     * @param date1 data de inceput a sejurului cautat
     * @param date2 data de final a sejurului cautat
     */
    public void top5(PriorityQueue<InfoActiv> queue, String l, Date date1, Date date2) {
        if (l == null) {
            for (Map.Entry<String, Locatie> entry : locatii.entrySet()) {
                entry.getValue().top5(queue, date1, date2);
            }
        } else {
            locatii.get(l).top5(queue, date1, date2);
        }
    }

    /**
     * Metoda care realizeaza cautarea celei mai bune oferte pentru o activitate
     * actualizand coada de prioritati prin cautarea pe toate localitatile salvate
     * in HashMap
     *
     * @param queue coada de prioritati care va contine raspunsul final
     * @param activitate activitatea
     */
    public void searchBest(PriorityQueue<InfoActiv> queue, String activitate) {
        for (Map.Entry<String, Locatie> entry : locatii.entrySet()) {
            entry.getValue().searchBest(queue, activitate);
        }
    }
}
