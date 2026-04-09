void main() throws Exception {
    Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande = GestoreFile.Leggi_binarioDomande();
    Personaggio[] personaggi = GestoreFile.Leggi_binarioPersonaggi();


    List<String> ChiaviGeneri = new ArrayList<>(GeneriDomande.keySet());
    System.out.println(ChiaviGeneri);

    Random r = new Random();

    String GenereRandom = ChiaviGeneri.get(r.nextInt(ChiaviGeneri.size()));
    List<String> ChiaviDomande =new ArrayList<>(GeneriDomande.get(GenereRandom).keySet());


    System.out.println(ChiaviDomande);
    String DomandaRandom = ChiaviDomande.get(r.nextInt(ChiaviDomande.size()));


    String scelta = IO.readln(DomandaRandom);
    scelta = scelta.toLowerCase();

    if (scelta.equals("si")) {
        System.out.println(GeneriDomande.get(GenereRandom).get(DomandaRandom));
        for (int i = 0; i < personaggi.length; i++) {
            if (!(GeneriDomande.get(GenereRandom).get(DomandaRandom).contains(personaggi[i]))) {
                personaggi[i] = null;
            }
        }
        GeneriDomande.remove(GenereRandom);
    }
    else if (scelta.equals("no")){
        for (int i=0; i<personaggi.length;i++){
            if (GeneriDomande.get(GenereRandom).get(DomandaRandom).contains(personaggi[i])){
                personaggi[i]=null;
            }
        }

    }
    else {
        throw new IllegalArgumentException("Non puoi inserire questa risposta");
    }

    System.out.println(Arrays.asList(personaggi));
    System.out.println(GeneriDomande.keySet());
}