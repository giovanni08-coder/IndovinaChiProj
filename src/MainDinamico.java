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
        throw new IllegalArgumentException("HAI MENTITO, BUGIARDO, DAMMMI LA RISPOSTA GIUSTA");
    }
    else {
        //lista.removeIf(e -> e == null);
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
Personaggio[] VerificaRispostaBot(Personaggio MiopersonaggioRandom,Personaggio[] MieiPersonaggi,String GenereRandom,String DomandaRandom,Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande){
    boolean si;
    Personaggio[] CopiaMieiPersonaggi = MieiPersonaggi.clone();
    if(MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MiopersonaggioRandom)){
        for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
            if (!(MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i]))) {
                CopiaMieiPersonaggi[i] = null;
            }
        }
        System.out.println("si");
        si = true;
    }
    else if (!(MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MiopersonaggioRandom))) {
        for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
            if (MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i])) {
                CopiaMieiPersonaggi[i] = null;
            }
        }
        System.out.println("no");
        si=false;
    } else {
        throw new IllegalArgumentException("Il tuo personaggio non esiste");
    }
    List<Personaggio> lista = new ArrayList<>(Arrays.asList(CopiaMieiPersonaggi));
    for (int i=0;i<lista.size();i++){
        if (lista.get(i)==null) {
            lista.remove(i);
            i--;
        }
    }
    MieiPersonaggi = lista.toArray(new Personaggio[0]);
    if (si){
        MieiGeneriDomande.remove(GenereRandom);
    }
    if (!(si)){
        if (GenereRandom.equals("Capelli")){
            MieiGeneriDomande.get("Capelli").remove(DomandaRandom);
        }
        else {
            MieiGeneriDomande.remove(GenereRandom);
        }
    }
    return MieiPersonaggi;
}
String TrovaGenere(Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande,String domanda){
    String risultato= null;
    List<String> GeneriPresenti = new ArrayList<>(MieiGeneriDomande.keySet());
    boolean trovato = true;
    int i = 0;
    while (trovato || i<GeneriPresenti.size()) {
        if (MieiGeneriDomande.get(GeneriPresenti.get(i)).containsKey(domanda)) {
            risultato = GeneriPresenti.get(i);
        }
        if (risultato!=null){
            trovato=false;
        }
        i++;
    }
    return risultato;
}

void main() throws Exception {
    Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande = GestoreFile.Leggi_binarioDomande();
    Random r = new Random();
    Personaggio[] MieiPersonaggi = GestoreFile.Leggi_binarioPersonaggi();
    Personaggio MioPersonaggio = MieiPersonaggi[r.nextInt(MieiPersonaggi.length)];

    int sceltaGiocatore = 0;
    Personaggio[] PersonaggiBot = GestoreFile.Leggi_binarioPersonaggi();
    Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomandeBot = GestoreFile.Leggi_binarioDomande();
    Personaggio PersonaggioRandomBot = PersonaggiBot[r.nextInt(PersonaggiBot.length)];

    System.out.println("Il tuo personaggio è: " + MioPersonaggio);

    while (MieiPersonaggi.length > 1 && PersonaggiBot.length>1) {
        boolean errore = true;
        EliminazioneDomandeInutili(MieiGeneriDomande, MieiPersonaggi);
        EliminazioneDomandeInutili(GeneriDomandeBot,PersonaggiBot);
        //Stampa Personaggi
        System.out.println(Arrays.asList(MieiPersonaggi));
        //Stampa generi domande

        //Generi Domande  che può fare il bot al giocatore
        List<String> ChiaviGeneri = new ArrayList<>(MieiGeneriDomande.keySet());
        String GenereRandom = ChiaviGeneri.get(r.nextInt(ChiaviGeneri.size()));

        //Domande che può fare il bot al giocatore
        List<String> ChiaviDomande = new ArrayList<>(MieiGeneriDomande.get(GenereRandom).keySet());
        String DomandaRandom = ChiaviDomande.get(r.nextInt(ChiaviDomande.size()));

        //Generi Domande  che può fare il giocatore al bot
        List<String> ChiaviGeneriBot = new ArrayList<>(GeneriDomandeBot.keySet());
        List<String> GeneriRandomBot = new ArrayList<>();
        String GenereRandomBot;
        List<String> CopiaGeneri = new ArrayList<>(ChiaviGeneriBot);
        while (GeneriRandomBot.size()<4 && (!CopiaGeneri.isEmpty())) {
            GenereRandomBot = CopiaGeneri.get(r.nextInt(CopiaGeneri.size()));
            GeneriRandomBot.add(GenereRandomBot);
            CopiaGeneri.remove(GenereRandomBot);
        };
        //Domande che può fare il giocatore al bot
        List<String> DomandeBot = new ArrayList<>();
        for (int i=0; i<GeneriRandomBot.size();i++){
            List<String> DomandePerGenere = new ArrayList<>(GeneriDomandeBot.get(GeneriRandomBot.get(i)).keySet());
            DomandeBot.add(DomandePerGenere.get(r.nextInt(DomandePerGenere.size())));
        }

        String scelta = IO.readln(DomandaRandom);
        scelta = scelta.toLowerCase();

        while (errore) {
            try {
                MieiPersonaggi = VerificaRisposta(MioPersonaggio, MieiPersonaggi, scelta, GenereRandom, DomandaRandom, MieiGeneriDomande);
                errore = false;
            } catch (Exception e) {
                System.out.println("HAI MENTITO, DIMMI LA VERITAAAAAAAA");
                scelta = IO.readln("RIPROVA, E RISPONDI BENE: ");
            }
        }
        System.out.println(Arrays.asList(PersonaggiBot));
        errore=true;
        while (errore) {
            try {
                sceltaGiocatore = Integer.parseInt(IO.readln("Di queste domande:" + DomandeBot + " quale vuoi fare all'altro giocatore (dai 1...n): "));
                if (sceltaGiocatore>DomandeBot.size() || sceltaGiocatore <= 0 ){
                    throw new IllegalArgumentException("Scegli la domanda no una a caso che non esiste");
                }
                errore=false;
            } catch (Exception e) {
                System.out.println("Non scrivere corbellerie scegli la domanda che vuoi fare pls ");
            }
        }
        PersonaggiBot = VerificaRispostaBot(PersonaggioRandomBot,PersonaggiBot,TrovaGenere(GeneriDomandeBot,DomandeBot.get(sceltaGiocatore-1)),DomandeBot.get(sceltaGiocatore-1),GeneriDomandeBot);
        errore = true;
        if (PersonaggiBot.length<=6 && PersonaggiBot.length>1) {
            System.out.println(Arrays.asList(PersonaggiBot));
            String siNo = "";
            while (errore)
                try {
                    siNo = IO.readln("Vuoi indovinare Il personaggio");
                    siNo = siNo.trim().toLowerCase();
                    if (!(siNo.equals("si") || siNo.equals("no"))) {
                        throw new IllegalArgumentException("Non puoi inserire questa risposta");
                    }
                    errore = false;
                } catch (Exception e) {
                    System.out.println("Non puoi mettere questa risposta");
                }
            errore = true;
            if (siNo.equals("si")){
                List<String> NomiPersonaggi = new ArrayList<>();
                for (int i=0;i<PersonaggiBot.length;i++){
                    NomiPersonaggi.add(PersonaggiBot[i].getNome());
                }
                System.out.println(NomiPersonaggi);
                String IndovinaPersonaggio="";
                while (errore) {
                    try {
                        IndovinaPersonaggio = IO.readln("Indovina il nome di un personaggio");
                        IndovinaPersonaggio = IndovinaPersonaggio.substring(0,1).toUpperCase() + IndovinaPersonaggio.substring(1).toLowerCase();
                        System.out.println(IndovinaPersonaggio);
                        if (!NomiPersonaggi.contains(IndovinaPersonaggio)) {
                            throw new IllegalArgumentException("Cosa hai scritto?");
                        }
                        errore=false;
                    } catch (Exception e) {
                        System.out.println("o il nome del tuo personaggio non è scritto bene o non esiste");
                    }
                }
                if (PersonaggioRandomBot.getNome().equals(IndovinaPersonaggio)){
                    System.out.println("Hai indovinato il personaggio è: " + PersonaggioRandomBot);
                    List<Personaggio> lista = new ArrayList<>(Arrays.asList(PersonaggiBot));
                    lista.clear();
                    PersonaggiBot = lista.toArray(new Personaggio[0]);
                }else {
                    System.out.println("Mi dispiace " + IndovinaPersonaggio + " non è il personaggio del tuo avversario");
                    List<Personaggio> lista = new ArrayList<>(Arrays.asList(PersonaggiBot));
                    boolean trovato = true;
                    int i=0;
                    while (trovato){
                        if (lista.get(i).getNome().equals(IndovinaPersonaggio)){
                            lista.remove(i);
                            trovato=false;
                        }
                        i++;
                    }
                    PersonaggiBot = lista.toArray(new Personaggio[0]);

                }
            }
        }
    }
    if (MieiPersonaggi.length==1){
        System.out.println("Il tuo personaggio è " + MieiPersonaggi[0]);
    }
    if (PersonaggiBot.length==1){
        System.out.println("Hai indovinato, il personaggio del tuo avversario è" + PersonaggioRandomBot);
    }
}