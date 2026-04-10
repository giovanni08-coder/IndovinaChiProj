void EliminazioneDomandeInutili(Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande,Personaggio[] personaggi){
    int conta;
    boolean caratteristicaPresente;
    for (String genere : new ArrayList<>(GeneriDomande.keySet())) {
        Map<String, ArrayList<Personaggio>> domande = GeneriDomande.get(genere);
        for(String domanda: new ArrayList<>(domande.keySet())){
            conta=0;
            caratteristicaPresente=false;
            for (Personaggio p: personaggi){
                if (domande.get(domanda).contains(p)){
                    caratteristicaPresente=true;
                    break;
                }
            }
            for (Personaggio pers: personaggi){
                if (domande.get(domanda).contains(pers)){
                    conta++;
                }
            }
            if (!caratteristicaPresente){
                domande.remove(domanda);
            }
            if (caratteristicaPresente && conta == personaggi.length){
                domande.remove(domanda);
            }
        }
        if (domande.isEmpty())
            GeneriDomande.remove(genere);
    }
}
void main() throws Exception {
    Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande = GestoreFile.Leggi_binarioDomande();
    Personaggio[] personaggi = GestoreFile.Leggi_binarioPersonaggi();

    Random r = new Random();
    while (personaggi.length > 1) {
        EliminazioneDomandeInutili(GeneriDomande,personaggi);
        System.out.println(Arrays.asList(personaggi));
        System.out.println(GeneriDomande.keySet());
        List<String> ChiaviGeneri = new ArrayList<>(GeneriDomande.keySet());
        String GenereRandom = ChiaviGeneri.get(r.nextInt(ChiaviGeneri.size()));
        List<String> ChiaviDomande = new ArrayList<>(GeneriDomande.get(GenereRandom).keySet());


        String DomandaRandom = ChiaviDomande.get(r.nextInt(ChiaviDomande.size()));


        String scelta = IO.readln(DomandaRandom);
        scelta = scelta.toLowerCase();

        if (scelta.equals("si")) {
            for (int i = 0; i < personaggi.length; i++) {
                if (!(GeneriDomande.get(GenereRandom).get(DomandaRandom).contains(personaggi[i]))) {
                    personaggi[i] = null;
                }
            }
            GeneriDomande.remove(GenereRandom);
        } else if (scelta.equals("no")) {
            for (int i = 0; i < personaggi.length; i++) {
                if (GeneriDomande.get(GenereRandom).get(DomandaRandom).contains(personaggi[i])) {
                    personaggi[i] = null;
                }
            }
            if (GenereRandom.equals("Capelli")){
                GeneriDomande.get("Capelli").remove(DomandaRandom);
            }
            else {
                GeneriDomande.remove(GenereRandom);
            }

        } else {
            throw new IllegalArgumentException("Non puoi inserire questa risposta");
        }
        List<Personaggio> lista = new ArrayList<>(Arrays.asList(personaggi));
        for (int i=0;i<lista.size();i++){
            if (lista.get(i)==null) {
                lista.remove(i);
                i--;
            }

        }
        //lista.removeIf(e -> e == null);
        personaggi = lista.toArray(new Personaggio[0]);

        //System.out.println(ChiaviGeneri);
    }
    if (personaggi.length>0){
        System.out.println("Il tuo personaggio è " + personaggi[0]);
    }
    else {
        System.out.println("Il tuo personaggio non è attualmente presente nel gioco. HAI BARATO \uD83D\uDE21 \uD83D\uDE21");
    }
}