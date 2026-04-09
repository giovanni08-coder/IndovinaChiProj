void main() throws Exception {
    Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande = GestoreFile.Leggi_binarioDomande();
    Personaggio[] personaggi = GestoreFile.Leggi_binarioPersonaggi();

    List<String> ChiaviGeneri = new ArrayList<>(GeneriDomande.keySet());
    System.out.println(ChiaviGeneri);

    Random r = new Random();

    List<String> ChiaviDomande =new ArrayList<>(GeneriDomande.get(ChiaviGeneri.get(r.nextInt(ChiaviGeneri.size()))).keySet());
    System.out.println(ChiaviDomande);

    System.out.println(ChiaviDomande.get(r.nextInt(ChiaviDomande.size())));

}