void main() throws Exception {
    //DONNE
    Albero albero = new Albero(new Nodo("è donna?"));
    albero.AggiungiNodo(new Nodo("Ha i capelli biondi?"),albero.getRoot(),true);

    albero.AggiungiNodo(new Nodo("Ha gli occhiali"),albero.getRoot().getNododx(),true); // == albero.root.destra.destra
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,true,false,false,false,true,false,"Immagini/Giocatori/20.png","Sarah")),albero.getRoot().getNododx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,false,false,false,false,false,false,false,false,"Immagini/Giocatori/1.png","Anita")),albero.getRoot().getNododx().getNododx(),false);

    albero.AggiungiNodo(new Nodo("Ha gli orecchini"),albero.getRoot().getNododx(),false);// == albero.root.destra.sinistra

    albero.AggiungiNodo(new Nodo("è Giovane?"),albero.getRoot().getNododx().getNodosx(),true); // == albero.root.destra.sinistra.destra
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIANCHI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,true,false,false,false,false,false,"Immagini/Giocatori/2.png","Anne")),albero.getRoot().getNododx().getNodosx().getNododx(),false);

    albero.AggiungiNodo(new Nodo("Ha il capello?"),albero.getRoot().getNododx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,false,false,true,false,true,false,"Immagini/Giocatori/15.png","Marta")),albero.getRoot().getNododx().getNodosx().getNododx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,false,false,false,false,false,false,"Immagini/Giocatori/13.png","Katrin")),albero.getRoot().getNododx().getNodosx().getNododx().getNododx(),false);

    albero.AggiungiNodo(new Nodo("ha i capelli rossi?"),albero.getRoot().getNododx().getNodosx(),false); // == root.destra.sinistra.sinistra
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.ROSSI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,false,true,false,false,false,"Immagini/Giocatori/11.png","Isabelle")),albero.getRoot().getNododx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo("è castana?"),albero.getRoot().getNododx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,false,false,true,false,false,false,"Immagini/Giocatori/4.png","Carmen")),albero.getRoot().getNododx().getNodosx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,true,false,true,false,false,false,"Immagini/Giocatori/21.png","Sophie")),albero.getRoot().getNododx().getNodosx().getNodosx().getNodosx(),false);

    //UOMO
    albero.AggiungiNodo(new Nodo("è calvo?"),albero.getRoot(),false);

    //BAFFI
    albero.AggiungiNodo(new Nodo("Hai baffi"),albero.getRoot().getNodosx(),true);
    albero.AggiungiNodo(new Nodo("Ha il pizetto"),albero.getRoot().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.SCURA,true,false,false,false,true,false,false,false,true,"Immagini/Giocatori/19.png","Roger")),albero.getRoot().getNodosx().getNododx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,true,false,true,true,true,false,false,true,"Immagini/Giocatori/5.png","Charles")),albero.getRoot().getNodosx().getNododx().getNododx(),false);
    albero.AggiungiNodo(new Nodo("Ha le sopracciglia scure"),albero.getRoot().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,false,false,true,true,false,false,false,true,"Immagini/Giocatori/6.png","Daniel")),albero.getRoot().getNodosx().getNododx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo("Ha la barba"),albero.getRoot().getNodosx().getNododx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,true,false,true,"Immagini/Giocatori/18.png","Philippe")),albero.getRoot().getNodosx().getNododx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,true,"Immagini/Giocatori/10.png","Herman")),albero.getRoot().getNodosx().getNododx().getNodosx().getNodosx(),false);

    //CAPPELLO
    albero.AggiungiNodo(new Nodo("Ha il capello"),albero.getRoot().getNodosx(),false);

    albero.AggiungiNodo(new Nodo("Blu?"),albero.getRoot().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,true,false,"Immagini/Giocatori/7.png","Eric")),albero.getRoot().getNodosx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo("Giallo?"),albero.getRoot().getNodosx().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,false,true,false,false,true,false,"Immagini/Giocatori/3.png","Bernard")),albero.getRoot().getNodosx().getNodosx().getNododx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,false,"Immagini/Giocatori/8.png","Frank")),albero.getRoot().getNodosx().getNodosx().getNododx().getNodosx(),false);

    albero.AggiungiNodo(new Nodo("è Biondo"),albero.getRoot().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo("ha i capelli bianchi?"),albero.getRoot().getNodosx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo("Ha gli occhiali?"),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIANCHI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,true,false,false,true,true,false,false,false,false,"Immagini/Giocatori/17.png","Paul")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIANCHI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,false,"Immagini/Giocatori/24.png","Victor")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo("Capelli Scuri?"),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.ROSSI,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,true,false,false,true,false,false,false,false,"Immagini/Giocatori/22.png","Stephen")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo("Ha la pelle scura"),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,true,false,false,true,true,false,false,false,"Immagini/Giocatori/23.png","Theo")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,false,true,false,false,false,false,"Immagini/Giocatori/16.png","Max")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNodosx().getNododx(),false);

    albero.AggiungiNodo(new Nodo("Ha gli occhi azzurri?"),albero.getRoot().getNodosx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,false,"Immagini/Giocatori/12.png","Joe")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo("ha solo i baffi?"),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,true,false,false,true,false,false,false,false,"Immagini/Giocatori/9.png","Hans")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,true,false,false,false,true,false,false,false,false,"Immagini/Giocatori/14.png","Lucas")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx().getNodosx(),false);
    GestoreFile.Scrivi_binarioAlbero(albero);

    Personaggio[] personaggios = new Personaggio[24];
    Personaggio p1 = new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,false,false,false,false,false,false,false,false,"Immagini/Giocatori/1.png","Anita");
    Personaggio p2 = new Personaggio(ColoriCapelli.BIANCHI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,true,false,false,false,false,false,"Immagini/Giocatori/2.png","Anne");
    Personaggio p3 = new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,false,true,false,false,true,false,"Immagini/Giocatori/3.png","Bernard");
    Personaggio p4 = new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,false,false,true,false,false,false,"Immagini/Giocatori/4.png","Carmen");
    Personaggio p5 = new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,true,false,true,true,false,false,false,true,"Immagini/Giocatori/5.png","Charles");
    Personaggio p6 = new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,false,false,true,true,false,false,false,true,"Immagini/Giocatori/6.png","Daniel");
    Personaggio p7 = new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,false,"Immagini/Giocatori/7.png","Eric");
    Personaggio p8 = new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,true,false,"Immagini/Giocatori/8.png","Frank");
    Personaggio p9 = new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,true,false,false,true,false,false,false,false,"Immagini/Giocatori/9.png","Hans");
    Personaggio p10 = new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,true,"Immagini/Giocatori/10.png","Herman");
    Personaggio p11 = new Personaggio(ColoriCapelli.ROSSI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,false,true,false,false,false,"Immagini/Giocatori/11.png","Isabelle");
    Personaggio p12 = new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,false,"Immagini/Giocatori/12.png","Joe");
    Personaggio p13 = new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,false,false,false,false,false,false,"Immagini/Giocatori/13.png","Katrin");
    Personaggio p14 = new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,true,false,false,false,true,false,false,false,false,"Immagini/Giocatori/14.png","Lucas");
    Personaggio p15 = new Personaggio(ColoriCapelli.CASTANI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,false,false,false,false,true,false,"Immagini/Giocatori/15.png","Marta");
    Personaggio p16 = new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,false,true,false,true,false,false,"Immagini/Giocatori/16.png","Max");
    Personaggio p17 = new Personaggio(ColoriCapelli.BIANCHI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,true,false,false,true,true,false,false,false,false,"Immagini/Giocatori/17.png","Paul");
    Personaggio p18 = new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,true,false,true,"Immagini/Giocatori/18.png","Philippe");
    Personaggio p19 = new Personaggio(ColoriCapelli.NESSUNO,ColoriOcchi.MARRONI, ColorePelle.SCURA,true,false,false,false,true,false,false,false,true,"Immagini/Giocatori/19.png","Roger");
    Personaggio p20 = new Personaggio(ColoriCapelli.BIONDI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,true,true,false,false,false,true,false,"Immagini/Giocatori/20.png","Sarah");
    Personaggio p21 = new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.SCURA,false,false,false,true,false,true,false,false,false,"Immagini/Giocatori/21.png","Sophie");
    Personaggio p22 = new Personaggio(ColoriCapelli.ROSSI,ColoriOcchi.AZZURRI, ColorePelle.CHIARA,false,true,false,false,true,false,false,false,false,"Immagini/Giocatori/22.png","Stephen");
    Personaggio p23 = new Personaggio(ColoriCapelli.NERI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,true,false,false,true,false,false,false,false,"Immagini/Giocatori/23.png","Theo");
    Personaggio p24 = new Personaggio(ColoriCapelli.BIANCHI,ColoriOcchi.MARRONI, ColorePelle.CHIARA,false,false,false,false,true,false,false,false,false,"Immagini/Giocatori/24.png","Victor");
    personaggios[0] = p1;
    personaggios[1] = p2;
    personaggios[2] = p3;
    personaggios[3] = p4;
    personaggios[4] = p5;
    personaggios[5] = p6;
    personaggios[6] = p7;
    personaggios[7] = p8;
    personaggios[8] = p9;
    personaggios[9] = p10;
    personaggios[10] = p11;
    personaggios[11] = p12;
    personaggios[12] = p13;
    personaggios[13] = p14;
    personaggios[14] = p15;
    personaggios[15] = p16;
    personaggios[16] = p17;
    personaggios[17] = p18;
    personaggios[18] = p19;
    personaggios[19] = p20;
    personaggios[20] = p21;
    personaggios[21] = p22;
    personaggios[22] = p23;
    personaggios[23] = p24;
    GestoreFile.Scrivi_binarioPersonaggi(personaggios);

    Personaggio[] personaggi = GestoreFile.Leggi_binarioPersonaggi();
    Map<String, Map<String, ArrayList<Personaggio>>> genere = new HashMap<>();

// Crea prima la mappa interna
    Map<String, ArrayList<Personaggio>> mappaCapelli = new HashMap<>();
    mappaCapelli.put("Ha i capelli rossi?", new ArrayList<>(Arrays.asList(personaggi[10], personaggi[21])));
    mappaCapelli.put("Ha i capelli neri?",new ArrayList<>(Arrays.asList(personaggi[15], personaggi[20],personaggi[22])));
    mappaCapelli.put("Ha i capelli biondi?",new ArrayList<>(Arrays.asList(personaggi[0],personaggi[8],personaggi[11],personaggi[13],personaggi[19])));
    mappaCapelli.put("Ha i capelli castani?",new ArrayList<>(Arrays.asList(personaggi[2],personaggi[3],personaggi[12],personaggi[14])));
    mappaCapelli.put("Ha i capelli bianchi?",new ArrayList<>(Arrays.asList(personaggi[1],personaggi[16],personaggi[23])));
    mappaCapelli.put("è calvo?",new ArrayList<>(Arrays.asList(personaggi[4],personaggi[5],personaggi[9],personaggi[17],personaggi[18])));

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
    mappaOrecchini.put("Ha gli orecchini?",new ArrayList<>(Arrays.asList(personaggi[1],personaggi[12],personaggi[14],personaggi[19])));

    Map<String,ArrayList<Personaggio>> mappaOcchiali = new HashMap<>();
    mappaOcchiali.put("Ha gli occhiali?",new ArrayList<>(Arrays.asList(personaggi[1],personaggi[4],personaggi[5],personaggi[16],personaggi[19],personaggi[20])));

    Map<String,ArrayList<Personaggio>> mappaBarba = new HashMap<>();
    mappaBarba.put("Ha il pizzetto o la barba?",new ArrayList<>(Arrays.asList(personaggi[13],personaggi[16],personaggi[18],personaggi[15],personaggi[17])));
    mappaBarba.put("Ha i baffi?", new ArrayList<>(Arrays.asList(personaggi[4],personaggi[8],personaggi[21],personaggi[22])));

    Map<String,ArrayList<Personaggio>> mappaCappello = new HashMap<>();
    mappaCappello.put("Ha il cappello?", new ArrayList<>(Arrays.asList(personaggi[2],personaggi[6],personaggi[7],personaggi[14],personaggi[19])));

    // Poi inseriscila nella mappa esterna
    genere.put("Capelli", mappaCapelli);
    genere.put("Occhi",mappaOcchi);
    genere.put("Pelle",mappaPelle);
    genere.put("Sesso",mappaSesso);
    genere.put("Orecchini",mappaOrecchini);
    genere.put("Occhiali",mappaOcchiali);
    genere.put("Barba",mappaBarba);
    genere.put("Cappello",mappaCappello);
    GestoreFile.Scrivi_binariDomande(genere);
}