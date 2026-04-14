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
Personaggio[] VerificaRisposta(Personaggio MiopersonaggioRandom,Personaggio[] MieiPersonaggi,String scelta,String GenereRandom,String DomandaRandom,Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande){
    Personaggio[] CopiaMieiPersonaggi = MieiPersonaggi.clone();
    if (scelta.equals("si")) {
        for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
            if (!(MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i]))) {
                CopiaMieiPersonaggi[i] = null;
            }
        }
    } else if (scelta.equals("no")) {
        for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
            if (MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i])) {
                CopiaMieiPersonaggi[i] = null;
            }
        }

    } else {
        throw new IllegalArgumentException("Non puoi inserire questa risposta");
    }
    List<Personaggio> lista = new ArrayList<>(Arrays.asList(CopiaMieiPersonaggi));
    for (int i=0;i<lista.size();i++){
        if (lista.get(i)==null) {
            lista.remove(i);
            i--;
        }

    }
    if (!lista.contains(MiopersonaggioRandom)){
        throw new IllegalArgumentException("HAI MENTITO");
    }
    else {
        MieiPersonaggi = lista.toArray(new Personaggio[0]);
        if (scelta.equals("si")){
            MieiGeneriDomande.remove(GenereRandom);
        }
        if (scelta.equals("no")){
            if (GenereRandom.equals("Capelli")){
                MieiGeneriDomande.get("Capelli").remove(DomandaRandom);
            }
            else {
                MieiGeneriDomande.remove(GenereRandom);
            }
        }
    }
    return MieiPersonaggi;
}
void main() throws Exception {
    Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande = GestoreFile.Leggi_binarioDomande();
    Random r = new Random();
    Personaggio[] MieiPersonaggi = GestoreFile.Leggi_binarioPersonaggi();
    Pannello pannello = new Pannello(MieiPersonaggi);
    System.out.println("Scegli il tuo personaggio dalla finestra");
    Personaggio MioPersonaggio = pannello.aspettaScelta();
    //Personaggio[] PersonaggiBot = GestoreFile.Leggi_binarioPersonaggi();
    //Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomandeBot = GestoreFile.Leggi_binarioDomande();

    System.out.println("Il tuo personaggio è: " + MioPersonaggio);

    while (MieiPersonaggi.length > 1) {
        boolean errore = true;
        EliminazioneDomandeInutili(MieiGeneriDomande, MieiPersonaggi);
        System.out.println(Arrays.asList(MieiPersonaggi));
        System.out.println(MieiGeneriDomande.keySet());
        List<String> ChiaviGeneri = new ArrayList<>(MieiGeneriDomande.keySet());
        String GenereRandom = ChiaviGeneri.get(r.nextInt(ChiaviGeneri.size()));
        List<String> ChiaviDomande = new ArrayList<>(MieiGeneriDomande.get(GenereRandom).keySet());
        String DomandaRandom = ChiaviDomande.get(r.nextInt(ChiaviDomande.size()));


        String scelta = pannello.aspettaRisposta(DomandaRandom);
        scelta = scelta.toLowerCase();
        while (errore) {
            try {
                MieiPersonaggi = VerificaRisposta(MioPersonaggio, MieiPersonaggi, scelta, GenereRandom, DomandaRandom, MieiGeneriDomande);
                ArrayList<Personaggio> eliminati = new ArrayList<>();
                for (Personaggio p : MieiPersonaggi) {
                    boolean trovato = false;
                    for (Personaggio nuovo : MieiPersonaggi) {
                        if (p.equals(nuovo)) trovato = true;
                    }
                    if (!trovato) eliminati.add(p);
                }
                pannello.eliminaPersonaggi(eliminati);
                errore = false;
            } catch (Exception e) {
                System.out.println("HAI MENTITO, DIMMI LA VERITAAAAAAAA");
                scelta = pannello.aspettaRisposta("RIPROVA, E RISPONDI BENE: ");
            }
        }
    }
    if (MioPersonaggio == MieiPersonaggi[0]) {
        pannello.mostraRisultato(true, MioPersonaggio, MieiPersonaggi[0]);
    } else {
        pannello.mostraRisultato(false, MioPersonaggio, MieiPersonaggi[0]);
    }
}
