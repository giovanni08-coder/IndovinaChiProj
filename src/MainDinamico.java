void main() throws Exception {
    Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande = GestoreFile.Leggi_binarioDomande();
    Personaggio[] personaggi = GestoreFile.Leggi_binarioPersonaggi();
    System.out.println(GeneriDomande.get("capelli").get("Ha i capelli biondi?"));
    System.out.println(GeneriDomande.get("capelli").get("Ha i capelli biondi?").get(0).equals(personaggi[0]));
}