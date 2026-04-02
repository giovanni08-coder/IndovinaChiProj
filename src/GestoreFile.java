import java.io.*;
import java.util.Arrays;
import java.util.List;

public class GestoreFile implements Serializable {
    public static void Scrivi_binario(Albero albero) throws Exception {
        OutputStream Stream = new FileOutputStream("data.bin");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.bin"))) {
            oos.writeObject(albero); // serializza TUTTO l'albero
        }

        // ASCII values for "Hello"
        Stream.close();

    }


    public static Albero Leggi_binario() throws Exception {
        Albero alberostatico;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.bin"))) {
            alberostatico = (Albero) ois.readObject(); //oopure puoi mettere: inStream.readObject() ---> down-casting object
        }
        return alberostatico;

    }
}
