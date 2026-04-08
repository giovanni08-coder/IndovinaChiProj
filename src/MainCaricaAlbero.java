void main() throws Exception {
    //DONNE
    Albero albero = new Albero(new Nodo("è donna?"));
    albero.AggiungiNodo(new Nodo("Ha i capelli biondi?"),albero.getRoot(),true);

    albero.AggiungiNodo(new Nodo("Ha gli occhiali"),albero.getRoot().getNododx(),true); // == albero.root.destra.destra
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Donna","Bionda","Occhiali"),"Immagini/Giocatori/20.png","Sarah")),albero.getRoot().getNododx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Donna","Bionda"),"Immagini/Giocatori/1.png","Anita")),albero.getRoot().getNododx().getNododx(),false);

    albero.AggiungiNodo(new Nodo("Ha gli orecchini"),albero.getRoot().getNododx(),false);// == albero.root.destra.sinistra

    albero.AggiungiNodo(new Nodo("è Giovane?"),albero.getRoot().getNododx().getNodosx(),true); // == albero.root.destra.sinistra.destra
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Donna","Orecchini","Anziana","Capelli Bianchi"),"Immagini/Giocatori/2.png","Anne")),albero.getRoot().getNododx().getNodosx().getNododx(),false);

    albero.AggiungiNodo(new Nodo("Ha il capello?"),albero.getRoot().getNododx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Castana","Capello","Giovane","Orecchini"),"Immagini/Giocatori/15.png","Marta")),albero.getRoot().getNododx().getNodosx().getNododx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Castana","Giovane","Orecchini"),"Immagini/Giocatori/15.png","Katrin")),albero.getRoot().getNododx().getNodosx().getNododx().getNododx(),false);

    albero.AggiungiNodo(new Nodo("ha i capelli rossi?"),albero.getRoot().getNododx().getNodosx(),false); // == root.destra.sinistra.sinistra
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("donna","rossa"),"Immagini/Giocatori/11.png","Isabelle")),albero.getRoot().getNododx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo("è castana?"),albero.getRoot().getNododx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Donna","Castana"),"Immagini/Giocatori/4.png","Carmen")),albero.getRoot().getNododx().getNodosx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Donna","Capelli neri"),"Immagini/Giocatori/21.png","Sophie")),albero.getRoot().getNododx().getNodosx().getNodosx().getNodosx(),false);

    //UOMO
    albero.AggiungiNodo(new Nodo("è calvo?"),albero.getRoot(),false);

    //BAFFI
    albero.AggiungiNodo(new Nodo("Hai baffi"),albero.getRoot().getNodosx(),true);
    albero.AggiungiNodo(new Nodo("Ha il pizetto"),albero.getRoot().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Calvo","Pizzetto"),"Immagini/Giocatori/19.png","Roger")),albero.getRoot().getNodosx().getNododx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Calvo","Occhiali"),"Immagini/Giocatori/5.png","Charles")),albero.getRoot().getNodosx().getNododx().getNododx(),false);
    albero.AggiungiNodo(new Nodo("Ha le sopracciglia scure"),albero.getRoot().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Uomo","Calvo","Sopracciglia scure"),"Immagini/Giocatori/6.png","Daniel")),albero.getRoot().getNodosx().getNododx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo("Ha la barba"),albero.getRoot().getNodosx().getNododx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Calvo","Barba Rossa"),"Immagini/Giocatori/10.png","Philippe")),albero.getRoot().getNodosx().getNododx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Calvo","sopracciglia rosse"),"Immagini/Giocatori/10.png","Herman")),albero.getRoot().getNodosx().getNododx().getNodosx().getNodosx(),false);

    //CAPPELLO
    albero.AggiungiNodo(new Nodo("Ha il capello"),albero.getRoot().getNodosx(),false);

    albero.AggiungiNodo(new Nodo("Blu?"),albero.getRoot().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Capello blu"),"Immagini/Giocatori/7.png","Eric")),albero.getRoot().getNodosx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo("Giallo?"),albero.getRoot().getNodosx().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Cappello giallo"),"Immagini/Giocatori/3.png","Bernard")),albero.getRoot().getNodosx().getNodosx().getNododx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Cappello multicolore"),"Immagini/Giocatori/8.png","Frank")),albero.getRoot().getNodosx().getNodosx().getNododx().getNodosx(),false);

    albero.AggiungiNodo(new Nodo("è Biondo"),albero.getRoot().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo("ha i capelli bianchi?"),albero.getRoot().getNodosx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo("Ha gli occhiali?"),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("capelli bianchi","occhiali"),"Immagini/Giocatori/17.png","Paul")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Capelli Bianchi"),"Immagini/Giocatori/24.png","Victor")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo("Capelli Scuri?"),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Capelli Bianchi"),"Immagini/Giocatori/22.png","Stephen")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNodosx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("capelli Rossi","Baffi"),"Immagini/Giocatori/23.png","Theo")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNodosx().getNodosx(),true);

    albero.AggiungiNodo(new Nodo("Ha gli occhi azzurri?"),albero.getRoot().getNodosx().getNodosx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Occhi Azzurri","Biondo"),"Immagini/Giocatori/12.png","Joe")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx(),true);
    albero.AggiungiNodo(new Nodo("ha solo i baffi?"),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx(),false);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Baffi","Biondo"),"Immagini/Giocatori/9.png","Hans")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx().getNodosx(),true);
    albero.AggiungiNodo(new Nodo(new Personaggio(Arrays.asList("Piezetto","Biondo"),"Immagini/Giocatori/14.png","Lucas")),albero.getRoot().getNodosx().getNodosx().getNodosx().getNododx().getNodosx(),false);
    GestoreFile.Scrivi_binario(albero);
}