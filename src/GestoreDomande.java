import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GestoreDomande implements Serializable {
    public static void Scrivi_binario(Map<String, Map<String, ArrayList<Personaggio>>> domande) throws Exception {
        OutputStream Stream = new FileOutputStream("domande.bin");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("domande.bin"))) {
            oos.writeObject(domande); // serializza TUTTA la lista

            // ASCII values for "Hello"
            Stream.close();

        }
    }


    public static  Map<String, Map<String, ArrayList<Personaggio>>> Leggi_binario() throws Exception {
        Map<String, Map<String, ArrayList<Personaggio>>> domande;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("domande.bin"))) {
            domande = (Map<String, Map<String, ArrayList<Personaggio>>>) ois.readObject(); //oopure puoi mettere: inStream.readObject() ---> down-casting object
        }
        return domande;

    }
}
