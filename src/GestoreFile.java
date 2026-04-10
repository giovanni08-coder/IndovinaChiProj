import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GestoreFile implements Serializable {

    public static void Scrivi_binarioAlbero(Albero albero) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.bin"))) {
            oos.writeObject(albero);
        }
    }


    public static Albero Leggi_binarioAlbero() throws Exception {
        Albero alberostatico;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.bin"))) {
            alberostatico = (Albero) ois.readObject();
            return alberostatico;

        }
    }

    public static void Scrivi_binarioPersonaggi(Personaggio[] personaggi) throws Exception {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personaggi.bin"))) {
            oos.writeObject(personaggi);
        }
    }
    

    public static Personaggio[] Leggi_binarioPersonaggi() throws Exception {
        Personaggio[] personaggi;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personaggi.bin"))) {
            personaggi = (Personaggio[]) ois.readObject(); //oopure puoi mettere: inStream.readObject() ---> down-casting object
        }
        return personaggi;

    }
    public static void Scrivi_binariDomande(Map<String, Map<String, ArrayList<Personaggio>>> domande) throws Exception {
        OutputStream Stream = new FileOutputStream("domande.bin");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("domande.bin"))) {
            oos.writeObject(domande); // serializza TUTTA la lista

            // ASCII values for "Hello"
            Stream.close();

        }
    }


    public static  Map<String, Map<String, ArrayList<Personaggio>>> Leggi_binarioDomande() throws Exception {
        Map<String, Map<String, ArrayList<Personaggio>>> domande;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("domande.bin"))) {
            domande = (Map<String, Map<String, ArrayList<Personaggio>>>) ois.readObject(); //oopure puoi mettere: inStream.readObject() ---> down-casting object
        }
        return domande;

    }
}
