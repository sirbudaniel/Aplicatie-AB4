
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa bazei de date care are un HashMap cu numele tarii si obiectul Tara
 * corespunzator
 *
 * @author daniel
 */
public class Database {

    private LinkedHashMap<String, Tara> tari = null;

    Database() {
        this.tari = new LinkedHashMap<>();
    }

    /**
     * Verifica existenta tarii cu numele s in HashMap, daca exista se intoarce
     * obiectul corespunzator tarii pentru a putea fi folosit, altfel null
     *
     * @param s numele
     * @return obiectul cautat sau null
     */
    public Tara Exista(String s) {
        if (tari.containsKey(s)) {
            return tari.get(s);
        }
        return null;
    }

    /**
     * Adauga un hotel in baza de date folosind linia citita de la tastatura. Se
     * cauta daca exista deja in baza de date tara, regiunea sau
     * locatia(orasul), in caz afirmativ se introduce hotelul in tara, regiunea,
     * orasul corespunzator, altfel se creeaza o tara, o regiune sau un oras
     * nou, sau o combinatie de ele pentru a introduce un hotel a carui locatie
     * nu a mai existat pana in acel moment in baza de date
     *
     * @param tokens linia citita dupa ce a fost parsata in tokeni(dupa virgula)
     */
    public void addEntry(String[] tokens) {

        Tara tara;
        if ((tara = this.Exista(tokens[2])) == null) {
            tara = new Tara(tokens[2]);
        }

        Regiune reg;
        if ((reg = tara.Exista(tokens[3])) == null) {
            reg = new Regiune(tokens[3]);
        }

        Locatie loc = null;
        if (tokens[0].compareTo("Oras") == 0) {
            if ((loc = reg.Exista(tokens[4])) == null) {
                loc = new Oras(tokens[4]);
            }
        } else { //adaugare alte tipuri (sate, comune) cu else if

        }

        Hotel h;
        if ((h = loc.Exista(tokens[1])) == null) {
            h = new Hotel(tokens[1]);
        }

        h.addPeriod(tokens);
        loc.addHotel(tokens[1], h);
        reg.addLocatie(tokens[4], loc);
        tara.addRegiune(tokens[3], reg);
        tari.put(tokens[2], tara);
    }

    /**
     * Metoda care realizeaza opertia de tip 1, adica afisarea tuturor
     * informatiilor despre o anumita locatie. Se determina locatia pe baza
     * numelor primite ca parametru si se apeleaza metoda de afisare pentru
     * locatia respectiva
     *
     * @param t numele tarii in care se cauta
     * @param r numele regiunii in care se cauta
     * @param l numele locatiei(orasul) in care se cauta
     */
    public void show(String t, String r, String l) {
        Tara tara;
        if ((tara = this.Exista(t)) == null) {
            System.out.println("Nu exista tara in baza de date");
            return;
        }
        Regiune reg;
        if ((reg = tara.Exista(r)) == null) {
            System.out.println("Nu exista regiunea in baza de date");
            return;
        }

        Locatie loc;
        if ((loc = reg.Exista(l)) == null) {
            System.out.println("Nu exista localitatea in baza de date");
            return;
        }
        loc.show();
        System.out.println("");
    }

    /**
     * Metoda pentru realizarea operatiei de tip 2, de cautare a top5 locatii
     * pentru o anumita perioada. Dupa prelucrarea string-urilor care contin
     * data de inceput si final a sejurului se apeleaza metoda de cautare top5
     * pentru tara specificata. In final in functie de numarul de rezultate
     * intors in PriorityQueue unde s-au inserat hotelurile care corespundeau
     * din punct de vedere al datelor in ordine crescatoare dupa pret, se
     * afiseaza primele 5 hoteluri, sau toate hotelurile care se potrivesc daca
     * sunt mai putine decat 5(nu s-au gasit 5).
     *
     * @param t numele tarii in care se cauta
     * @param r numele regiunii in care se cauta sau null daca nu se specifica
     * regiunea
     * @param l numele localitatii in care se cauta sau null daca nu se
     * specifica localitatea
     * @param d1 data de inceput a sejurului cautat
     * @param d2 data de final a sejurului cautat
     */
    public void top5(String t, String r, String l, String d1, String d2) {
        PriorityQueue<InfoActiv> queue = new PriorityQueue<>(100, new InfoComparator());
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null, date2 = null;
        try {
            date1 = dateformat.parse(d1);
            date2 = dateformat.parse(d2);
        } catch (ParseException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        tari.get(t).top5(queue, r, l, date1, date2);

        if (queue.isEmpty()) {
            System.out.println("Nu exista oferte in perioada specificata");
        } else {
            for (int i = 1; i <= 5; i++) {
                if (!queue.isEmpty()) {
                    InfoActiv x = queue.poll();
                    System.out.println("Locul " + i + ": " + x.getLoc() + " cu pretul de " + x.getPret() + " RON pe intreg sejurul");
                }
            }
        }
        System.out.println("");
    }

    /**
     * Metoda pentru realizarea operatiei de tip 3. Se creeaza un PriorityQueue
     * in care se vor insera crescator dupa pretul total locatiile cu
     * activitatea ceruta, in final locatia cu pretul cel mai mic pentru acea
     * activitate se va afla prima in PriorityQueue. Se parcurge HashMap-ul si
     * se apeleaza metoda de cautare pentru toate tarile.
     *
     * @param activitate activitatea cautata
     */
    public void searchBest(String activitate) {
        PriorityQueue<InfoActiv> queue = new PriorityQueue<>(100, new InfoComparator());
        for (Map.Entry<String, Tara> entry : tari.entrySet()) {
            entry.getValue().searchBest(queue, activitate);
        }
        System.out.println("Cea mai buna locatie pentru activitatea " + activitate + " este: " + queue.peek().getLoc()
                + " la pretul de " + queue.peek().getPret() + " RON pe zi\n");
    }
}
