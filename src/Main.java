
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */


public class Main {
    
    /**
     * 
     * In main se deschide si se citeste linie cu linie din fisierul cu baza de date
     * si pentru fiecare linie se apeleaza metoda addEntry care adauga hotelul in baza
     * de date conform ierarhiei.
     * Apoi se citeste cate o linie de la stdin prin care se cere sa se introduca o 
     * anumita comanda pentru program prin intermediul cifrelor, dupa care se cer 
     * parametrii corespunzatori comenzii si in functie de aceasta se afiseaza la 
     * stdout mesajul corespunzator
     * 
     * @param args =  argumentele liniei de comanda 
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        Database db = new Database();
        Scanner scan = new Scanner(System.in);
        int command1 = -1, command2;
        String line;
        String[] tokens;
        String tara, regiune, localitate, activitate, perioada1, perioada2;
        BufferedReader in;

        try {
            in = new BufferedReader(new FileReader(args[0]));

            while ((line = in.readLine()) != null) {
                tokens = line.split(",");
                db.addEntry(tokens);
            }

            while (command1 != 0) {
                System.out.println("Introduceti o optiune: \n\t\"1\" pentru informatii "
                        + "despre o locatie\n\t\"2\" pentru top 5 locatii pentru o "
                        + "anumita perioada\n\t\"3\" pentru cea mai buna oferta pentru"
                        + " o activitate timp de 10 zile\n\t\"0\" pentru a iesi");

                try {
                    command1 = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Trebuie introdusa o cifra");
                    break;
                }
                switch (command1) {
                    case (1):
                        System.out.print("Introduceti tara:");
                        while ((tara = scan.nextLine()).compareTo("") == 0);
                        System.out.print("Introduceti regiunea:");
                        while ((regiune = scan.nextLine()).compareTo("") == 0);
                        System.out.print("Introduceti localitatea:");
                        while ((localitate = scan.nextLine()).compareTo("") == 0);
                        db.show(tara, regiune, localitate);
                        break;
                    case (2):
                        System.out.println("Introduceti tipul de cautare:\n\t1 pentru"
                                + " a cauta intr-o tara\n\t2 pentru a cauta intr-o regiune"
                                + "\n\t3 pentru a cauta intr-un oras \n\t\"0\" pentru a iesi");
                        try {
                            command2 = scan.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Trebuie introdusa o cifra");
                            command1 = 0;
                            break;
                        }

                        switch (command2) {
                            case (1):
                                System.out.print("Introduceti tara:");
                                while ((tara = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti data de inceput a sejurului in format DD/MM/YYYY:");
                                while ((perioada1 = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti data de terminare a sejurului in format DD/MM/YYYY:");
                                while ((perioada2 = scan.nextLine()).compareTo("") == 0);
                                db.top5(tara, null, null, perioada1, perioada2);
                                break;
                            case (2):
                                System.out.print("Introduceti tara:");
                                while ((tara = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti regiunea:");
                                while ((regiune = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti data de inceput a sejurului in format DD/MM/YYYY:");
                                while ((perioada1 = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti data de terminare a sejurului in format DD/MM/YYYY:");
                                while ((perioada2 = scan.nextLine()).compareTo("") == 0);
                                db.top5(tara, regiune, null, perioada1, perioada2);
                                break;
                            case (3):
                                System.out.print("Introduceti tara:");
                                while ((tara = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti regiunea:");
                                while ((regiune = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti localitatea:");
                                while ((localitate = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti data de inceput a sejurului in format DD/MM/YYYY:");
                                while ((perioada1 = scan.nextLine()).compareTo("") == 0);
                                System.out.print("Introduceti data de terminare a sejurului in format DD/MM/YYYY:");
                                while ((perioada2 = scan.nextLine()).compareTo("") == 0);
                                db.top5(tara, regiune, localitate, perioada1, perioada2);
                                break;
                            default:
                                break;
                        }
                        break;
                    case (3):
                        System.out.print("Introduceti activitatea:");
                        while ((activitate = scan.nextLine()).compareTo("") == 0);
                        db.searchBest(activitate);
                        break;
                    case (0):
                        break;
                    default:
                        System.out.println("Comanda incorecta");
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
