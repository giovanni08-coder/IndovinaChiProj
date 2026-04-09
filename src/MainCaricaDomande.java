void main(String[] args) throws Exception {
    Personaggio[] personaggi = GestorePersonaggi.Leggi_binario();
    Map<String, Map<String, ArrayList<Personaggio>>> genere = new HashMap<>();

// Crea prima la mappa interna
    Map<String, ArrayList<Personaggio>> mappaCapelli = new HashMap<>();
    mappaCapelli.put("Ha i capelli rossi?", new ArrayList<>(Arrays.asList(personaggi[10], personaggi[21])));
    mappaCapelli.put("Ha i capelli neri?",new ArrayList<>(Arrays.asList(personaggi[15], personaggi[20],personaggi[22])));
    mappaCapelli.put("Ha i capelli biondi?",new ArrayList<>(Arrays.asList(personaggi[0],personaggi[8],personaggi[11],personaggi[13],personaggi[19])));
    mappaCapelli.put("Ha i capelli castani?",new ArrayList<>(Arrays.asList(personaggi[2],personaggi[3],personaggi[12],personaggi[14])));
    mappaCapelli.put("Ha i capelli bianchi?",new ArrayList<>(Arrays.asList(personaggi[1],personaggi[16],personaggi[23])));

    Map<String,ArrayList<Personaggio>> mappaCalvo = new HashMap<>();
    mappaCalvo.put("è calvo",new ArrayList<>(Arrays.asList(personaggi[4],personaggi[5],personaggi[9],personaggi[17],personaggi[18])));

    Map<String,ArrayList<Personaggio>> mappaOcchi = new HashMap<>();
    mappaOcchi.put("Ha gli occhi scuri?",new ArrayList<>(Arrays.asList(personaggi[1],personaggi[2],personaggi[3],personaggi[4],personaggi[6],personaggi[7],personaggi[8],personaggi[9],personaggi[10],personaggi[12],personaggi[13],personaggi[14],personaggi[15],personaggi[16],personaggi[17],personaggi[18],personaggi[19],personaggi[20],personaggi[22],personaggi[23])));
    mappaOcchi.put("Ha gli occhi chiari?",new ArrayList<>(Arrays.asList(personaggi[0],personaggi[5],personaggi[11],personaggi[21])));

    Map<String,ArrayList<Personaggio>> mappaPelle = new HashMap<>();
    mappaPelle.put("Ha la pelle chiara?",new ArrayList<>(Arrays.asList(personaggi[0],personaggi[1],personaggi[4],personaggi[5],personaggi[6],personaggi[7],personaggi[8],personaggi[9],personaggi[10],personaggi[11],personaggi[12],personaggi[13],personaggi[14],personaggi[16],personaggi[17],personaggi[19],personaggi[21],personaggi[22],personaggi[23])));
    mappaPelle.put("Ha la pelle scura?",new ArrayList<>(Arrays.asList(personaggi[2],personaggi[3],personaggi[15],personaggi[18],personaggi[20])));

    Map<String,ArrayList<Personaggio>> mappaSesso = new HashMap<>();
    mappaSesso.put("è donna?", new ArrayList<>(Arrays.asList(personaggi[0],personaggi[1],personaggi[3],personaggi[10],personaggi[12],personaggi[14],personaggi[19],personaggi[20])));
    mappaSesso.put("è un uomo?",new ArrayList<>(Arrays.asList(personaggi[2],personaggi[4],personaggi[5],personaggi[6],personaggi[7],personaggi[8],personaggi[9],personaggi[11],personaggi[13],personaggi[15],personaggi[16],personaggi[17],personaggi[18],personaggi[21],personaggi[22],personaggi[23])));

    Map<String,ArrayList<Personaggio>> mappaOrecchini = new HashMap<>();
    mappaOrecchini.put("Ha gli orecchini",new ArrayList<>(Arrays.asList(personaggi[1],personaggi[12],personaggi[14],personaggi[19])));

    Map<String,ArrayList<Personaggio>> mappaOcchiali = new HashMap<>();
    mappaOcchiali.put("Ha gli occhiali",new ArrayList<>(Arrays.asList(personaggi[1],personaggi[4],personaggi[5],personaggi[16],personaggi[19],personaggi[20])));

    Map<String,ArrayList<Personaggio>> mappaBarba = new HashMap<>();
    mappaBarba.put("Ha il pizzetto o la barba",new ArrayList<>(Arrays.asList(personaggi[13],personaggi[16],personaggi[18],personaggi[15],personaggi[17])));
    mappaBarba.put("Ha i baffi", new ArrayList<>(Arrays.asList(personaggi[4],personaggi[8],personaggi[21],personaggi[22])));

    Map<String,ArrayList<Personaggio>> mappaCappello = new HashMap<>();
    mappaCappello.put("Ha il cappello", new ArrayList<>(Arrays.asList(personaggi[2],personaggi[6],personaggi[7],personaggi[14],personaggi[19])));

    Map<String,ArrayList<Personaggio>> mappaCapelliLunghi = new HashMap<>();
    mappaCapelliLunghi.put("Ha i capelli lunghi",new ArrayList<>(Arrays.asList(personaggi[4],personaggi[10],personaggi[20])));

    // Poi inseriscila nella mappa esterna
    genere.put("capelli", mappaCapelli);
    genere.put("Calvo",mappaCalvo);
    genere.put("occhi",mappaOcchi);
    genere.put("Pelle",mappaPelle);
    genere.put("Sesso",mappaSesso);
    genere.put("Orecchini",mappaOrecchini);
    genere.put("Occhiali",mappaOcchiali);
    genere.put("Barba",mappaBarba);
    genere.put("Capello",mappaCappello);
    genere.put("capelli lunghi", mappaCapelliLunghi);
GestoreDomande.Scrivi_binario(genere);
}
