void main() throws Exception {
    Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande = GestoreFile.Leggi_binarioDomande();
    Personaggio[] personaggi = GestoreFile.Leggi_binarioPersonaggi();
    for (int i =0; i<personaggi.length;i++){
        System.out.println(personaggi[i]);
    }
    System.out.println(GeneriDomande.get("Pelle").get("Ha la pelle chiara?"));
}