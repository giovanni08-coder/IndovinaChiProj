void EliminazioneDomandeInutili(Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomande, Personaggio[] personaggi) {
    int conta;
    boolean caratteristicaPresente;
    for (String genere : new ArrayList<>(GeneriDomande.keySet())) {
        Map<String, ArrayList<Personaggio>> domande = GeneriDomande.get(genere);
        for (String domanda : new ArrayList<>(domande.keySet())) {
            conta = 0;
            caratteristicaPresente = false;
            for (Personaggio p : personaggi) {
                if (domande.get(domanda).contains(p)) {
                    caratteristicaPresente = true;
                    break;
                }
            }
            for (Personaggio pers : personaggi) {
                if (domande.get(domanda).contains(pers)) {
                    conta++;
                }
            }
            if (!caratteristicaPresente) {
                domande.remove(domanda);
            }
            if (caratteristicaPresente && conta == personaggi.length) {
                domande.remove(domanda);
            }
        }
        if (domande.isEmpty())
            GeneriDomande.remove(genere);
    }
}

Personaggio[] VerificaRisposta(Personaggio MiopersonaggioRandom, Personaggio[] MieiPersonaggi, String scelta, String GenereRandom, String DomandaRandom, Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande) {
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
    for (int i = 0; i < lista.size(); i++) {
        if (lista.get(i) == null) { lista.remove(i); i--; }
    }
    if (!lista.contains(MiopersonaggioRandom)) {
        throw new IllegalArgumentException("HAI MENTITO, BUGIARDO, DAMMMI LA RISPOSTA GIUSTA");
    } else {
        MieiPersonaggi = lista.toArray(new Personaggio[0]);
        if (scelta.equals("si")) {
            MieiGeneriDomande.remove(GenereRandom);
        }
        if (scelta.equals("no")) {
            if (GenereRandom.equals("Capelli")) {
                MieiGeneriDomande.get("Capelli").remove(DomandaRandom);
            } else {
                MieiGeneriDomande.remove(GenereRandom);
            }
        }
    }
    return MieiPersonaggi;
}

Personaggio[] VerificaRispostaBot(Personaggio MiopersonaggioRandom, Personaggio[] MieiPersonaggi, String GenereRandom, String DomandaRandom, Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande) {
    boolean si;
    Personaggio[] CopiaMieiPersonaggi = MieiPersonaggi.clone();
    if (MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MiopersonaggioRandom)) {
        for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
            if (!(MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i]))) {
                CopiaMieiPersonaggi[i] = null;
            }
        }
        System.out.println("si");
        si = true;
    } else if (!(MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MiopersonaggioRandom))) {
        for (int i = 0; i < CopiaMieiPersonaggi.length; i++) {
            if (MieiGeneriDomande.get(GenereRandom).get(DomandaRandom).contains(MieiPersonaggi[i])) {
                CopiaMieiPersonaggi[i] = null;
            }
        }
        System.out.println("no");
        si = false;
    } else {
        throw new IllegalArgumentException("Il tuo personaggio non esiste");
    }
    List<Personaggio> lista = new ArrayList<>(Arrays.asList(CopiaMieiPersonaggi));
    for (int i = 0; i < lista.size(); i++) {
        if (lista.get(i) == null) { lista.remove(i); i--; }
    }
    MieiPersonaggi = lista.toArray(new Personaggio[0]);
    if (si) {
        MieiGeneriDomande.remove(GenereRandom);
    }
    if (!si) {
        if (GenereRandom.equals("Capelli")) {
            MieiGeneriDomande.get("Capelli").remove(DomandaRandom);
        } else {
            MieiGeneriDomande.remove(GenereRandom);
        }
    }
    return MieiPersonaggi;
}

String TrovaGenere(Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande, String domanda) {
    String risultato = null;
    List<String> GeneriPresenti = new ArrayList<>(MieiGeneriDomande.keySet());
    boolean trovato = true;
    int i = 0;
    while (trovato || i < GeneriPresenti.size()) {
        if (MieiGeneriDomande.get(GeneriPresenti.get(i)).containsKey(domanda)) {
            risultato = GeneriPresenti.get(i);
        }
        if (risultato != null) { trovato = false; }
        i++;
    }
    return risultato;
}

int gioca() throws Exception {
    Random r = new Random();

    Personaggio[] MieiPersonaggi = GestoreFile.Leggi_binarioPersonaggi();
    Map<String, Map<String, ArrayList<Personaggio>>> MieiGeneriDomande = GestoreFile.Leggi_binarioDomande();

    Personaggio[] PersonaggiBot = GestoreFile.Leggi_binarioPersonaggi();
    Map<String, Map<String, ArrayList<Personaggio>>> GeneriDomandeBot = GestoreFile.Leggi_binarioDomande();
    Personaggio PersonaggioRandomBot = PersonaggiBot[r.nextInt(PersonaggiBot.length)];

    Pannello pannello = new Pannello(MieiPersonaggi);
    System.out.println("Scegli il tuo personaggio dalla finestra");
    Personaggio MioPersonaggio = pannello.aspettaScelta();
    System.out.println("Il tuo personaggio è: " + MioPersonaggio);

    while (MieiPersonaggi.length > 1 && PersonaggiBot.length > 1) {

        EliminazioneDomandeInutili(MieiGeneriDomande, MieiPersonaggi);
        EliminazioneDomandeInutili(GeneriDomandeBot, PersonaggiBot);
        System.out.println(Arrays.asList(MieiPersonaggi));

        List<String> ChiaviGeneri = new ArrayList<>(MieiGeneriDomande.keySet());
        String GenereRandom = ChiaviGeneri.get(r.nextInt(ChiaviGeneri.size()));
        List<String> ChiaviDomande = new ArrayList<>(MieiGeneriDomande.get(GenereRandom).keySet());
        String DomandaRandom = ChiaviDomande.get(r.nextInt(ChiaviDomande.size()));

        String scelta = pannello.aspettaRisposta(DomandaRandom);
        scelta = scelta.toLowerCase();

        boolean errore = true;
        while (errore) {
            try {
                Personaggio[] vecchi = MieiPersonaggi.clone();
                MieiPersonaggi = VerificaRisposta(MioPersonaggio, MieiPersonaggi, scelta, GenereRandom, DomandaRandom, MieiGeneriDomande);
                ArrayList<Personaggio> eliminati = new ArrayList<>();
                for (Personaggio p : vecchi) {
                    boolean ancora = false;
                    for (Personaggio nuovo : MieiPersonaggi) {
                        if (p.equals(nuovo)) { ancora = true; break; }
                    }
                    if (!ancora) eliminati.add(p);
                }
                pannello.eliminaPersonaggi(eliminati);
                errore = false;
            } catch (Exception e) {
                System.out.println("HAI MENTITO, DIMMI LA VERITAAAAAAAA");
                scelta = pannello.aspettaRisposta("HAI MENTITO! Rispondi di nuovo: " + DomandaRandom);
            }
        }

        System.out.println(Arrays.asList(PersonaggiBot));
        List<String> CopiaGeneri = new ArrayList<>(GeneriDomandeBot.keySet());
        List<String> GeneriRandomBot = new ArrayList<>();
        while (GeneriRandomBot.size() < 4 && !CopiaGeneri.isEmpty()) {
            String g = CopiaGeneri.get(r.nextInt(CopiaGeneri.size()));
            GeneriRandomBot.add(g);
            CopiaGeneri.remove(g);
        }
        List<String> DomandeBot = new ArrayList<>();
        for (int i = 0; i < GeneriRandomBot.size(); i++) {
            List<String> dp = new ArrayList<>(GeneriDomandeBot.get(GeneriRandomBot.get(i)).keySet());
            DomandeBot.add(dp.get(r.nextInt(dp.size())));
        }

        int sceltaGiocatore = pannello.aspettaSceltaDomanda(DomandeBot);
        PersonaggiBot = VerificaRispostaBot(PersonaggioRandomBot, PersonaggiBot, TrovaGenere(GeneriDomandeBot, DomandeBot.get(sceltaGiocatore - 1)), DomandeBot.get(sceltaGiocatore - 1), GeneriDomandeBot);

        if (PersonaggiBot.length <= 6 && PersonaggiBot.length > 1) {
            System.out.println(Arrays.asList(PersonaggiBot));

            String siNo = pannello.aspettaRisposta("Vuoi indovinare il personaggio del bot?");
            siNo = siNo.trim().toLowerCase();

            if (siNo.equals("si")) {
                List<String> NomiPersonaggi = new ArrayList<>();
                for (int i = 0; i < PersonaggiBot.length; i++) {
                    NomiPersonaggi.add(PersonaggiBot[i].getNome());
                }

                String IndovinaPersonaggio = pannello.aspettaSceltaNome(NomiPersonaggi);
                IndovinaPersonaggio = IndovinaPersonaggio.substring(0, 1).toUpperCase() + IndovinaPersonaggio.substring(1).toLowerCase();
                System.out.println("Tentativo: " + IndovinaPersonaggio);

                if (PersonaggioRandomBot.getNome().equals(IndovinaPersonaggio)) {
                    System.out.println("Hai indovinato! Il personaggio è: " + PersonaggioRandomBot);
                    PersonaggiBot = new Personaggio[0];   // termina il loop
                } else {
                    System.out.println("Mi dispiace, " + IndovinaPersonaggio + " non è il personaggio del bot");
                    List<Personaggio> lista = new ArrayList<>(Arrays.asList(PersonaggiBot));
                    boolean trovato = true;
                    int i = 0;
                    while (trovato && i < lista.size()) {
                        if (lista.get(i).getNome().equals(IndovinaPersonaggio)) {
                            lista.remove(i);
                            trovato = false;
                        }
                        i++;
                    }
                    PersonaggiBot = lista.toArray(new Personaggio[0]);
                }
            }
        }
    }

    boolean vittoria;
    Personaggio personaggioMostrato;

    if (MieiPersonaggi.length == 1 && PersonaggiBot.length > 0) {
        vittoria = false;
        personaggioMostrato = MieiPersonaggi[0];
        System.out.println("Il bot ha indovinato: sei " + MieiPersonaggi[0]);
    } else {
        vittoria = true;
        personaggioMostrato = PersonaggioRandomBot;
        System.out.println("Hai indovinato! Il personaggio del bot era: " + PersonaggioRandomBot);
    }

    int sceltaFinale = pannello.mostraRisultato(vittoria, MioPersonaggio, personaggioMostrato);
    pannello.dispose();
    return sceltaFinale;
}

void main() throws Exception {
    boolean continua = true;

    while (continua) {
        MenuPrincipale menu = new MenuPrincipale();
        int sceltaMenu = menu.aspettaSceltaMenu();

        if (sceltaMenu == MenuPrincipale.SCELTA_ESCI) {
            System.exit(0);
        }

        if (sceltaMenu == MenuPrincipale.SCELTA_GIOCA) {
            int risultatoPartita = gioca();
            if (risultatoPartita == Pannello.RISULTATO_ESCI) {
                continua = false;
                System.exit(0);
            }
        }
    }
}