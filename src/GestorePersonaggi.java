import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorePersonaggi implements Serializable {
    public static void Scrivi_binario(Personaggio[] personaggi) throws Exception {
        OutputStream Stream = new FileOutputStream("personaggi.bin");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personaggi.bin"))) {
            oos.writeObject(personaggi); // serializza TUTTA la lista

            // ASCII values for "Hello"
            Stream.close();

        }
    }


    public static Personaggio[] Leggi_binario() throws Exception {
        Personaggio[] personaggi;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personaggi.bin"))) {
            personaggi = (Personaggio[]) ois.readObject(); //oopure puoi mettere: inStream.readObject() ---> down-casting object
        }
        return personaggi;

    }
}
