package animals;

import javafx.beans.property.*;

public class Zwierze {
    private StringProperty nazwa = new SimpleStringProperty();
    private BooleanProperty siersc = new SimpleBooleanProperty();
    private BooleanProperty piora = new SimpleBooleanProperty();
    private BooleanProperty jaja = new SimpleBooleanProperty();
    private BooleanProperty mleko = new SimpleBooleanProperty();
    private BooleanProperty latajacy = new SimpleBooleanProperty();
    private BooleanProperty wodny = new SimpleBooleanProperty();
    private BooleanProperty drapieznik = new SimpleBooleanProperty();
    private BooleanProperty uzebiony = new SimpleBooleanProperty();
    private BooleanProperty kregoslup = new SimpleBooleanProperty();
    private BooleanProperty oddycha = new SimpleBooleanProperty();
    private BooleanProperty jadowity = new SimpleBooleanProperty();
    private BooleanProperty pletwy = new SimpleBooleanProperty();
    private IntegerProperty nogi = new SimpleIntegerProperty();  //(set of values: {0,2,4,5,6,8})
    private BooleanProperty ogon = new SimpleBooleanProperty();
    private BooleanProperty domowy = new SimpleBooleanProperty();
    private BooleanProperty rozmiarKota = new SimpleBooleanProperty();
    private IntegerProperty typ = new SimpleIntegerProperty(); //1..7
    private Rodzaj rodzaj = null;


    public Zwierze()
    {

    }

    public Zwierze(String nazwa)
    {
        this();
        this.nazwa.setValue(nazwa);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public StringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public boolean isSiersc() {
        return siersc.get();
    }

    public BooleanProperty sierscProperty() {
        return siersc;
    }

    public void setSiersc(boolean siersc) {
        this.siersc.set(siersc);
    }

    public boolean isPiora() {
        return piora.get();
    }

    public BooleanProperty pioraProperty() {
        return piora;
    }

    public void setPiora(boolean piora) {
        this.piora.set(piora);
    }

    public boolean isJaja() {
        return jaja.get();
    }

    public BooleanProperty jajaProperty() {
        return jaja;
    }

    public void setJaja(boolean jaja) {
        this.jaja.set(jaja);
    }

    public boolean isMleko() {
        return mleko.get();
    }

    public BooleanProperty mlekoProperty() {
        return mleko;
    }

    public void setMleko(boolean mleko) {
        this.mleko.set(mleko);
    }

    public boolean isLatajacy() {
        return latajacy.get();
    }

    public BooleanProperty latajacyProperty() {
        return latajacy;
    }

    public void setLatajacy(boolean latajacy) {
        this.latajacy.set(latajacy);
    }

    public boolean isWodny() {
        return wodny.get();
    }

    public BooleanProperty wodnyProperty() {
        return wodny;
    }

    public void setWodny(boolean wodny) {
        this.wodny.set(wodny);
    }

    public boolean isDrapieznik() {
        return drapieznik.get();
    }

    public BooleanProperty drapieznikProperty() {
        return drapieznik;
    }

    public void setDrapieznik(boolean drapieznik) {
        this.drapieznik.set(drapieznik);
    }

    public boolean isUzebiony() {
        return uzebiony.get();
    }

    public BooleanProperty uzebionyProperty() {
        return uzebiony;
    }

    public void setUzebiony(boolean uzebiony) {
        this.uzebiony.set(uzebiony);
    }

    public boolean isKregoslup() {
        return kregoslup.get();
    }

    public BooleanProperty kregoslupProperty() {
        return kregoslup;
    }

    public void setKregoslup(boolean kregoslup) {
        this.kregoslup.set(kregoslup);
    }

    public boolean isOddycha() {
        return oddycha.get();
    }

    public BooleanProperty oddychaProperty() {
        return oddycha;
    }

    public void setOddycha(boolean oddycha) {
        this.oddycha.set(oddycha);
    }

    public boolean isJadowity() {
        return jadowity.get();
    }

    public BooleanProperty jadowityProperty() {
        return jadowity;
    }

    public void setJadowity(boolean jadowity) {
        this.jadowity.set(jadowity);
    }

    public boolean isPletwy() {
        return pletwy.get();
    }

    public BooleanProperty pletwyProperty() {
        return pletwy;
    }

    public void setPletwy(boolean pletwy) {
        this.pletwy.set(pletwy);
    }

    public int getNogi() {
        return nogi.get();
    }

    public IntegerProperty nogiProperty() {
        return nogi;
    }

    public void setNogi(int nogi) {
        this.nogi.set(nogi);
    }

    public boolean isOgon() {
        return ogon.get();
    }

    public BooleanProperty ogonProperty() {
        return ogon;
    }

    public void setOgon(boolean ogon) {
        this.ogon.set(ogon);
    }

    public boolean isDomowy() {
        return domowy.get();
    }

    public BooleanProperty domowyProperty() {
        return domowy;
    }

    public void setDomowy(boolean domowy) {
        this.domowy.set(domowy);
    }

    public boolean isRozmiarKota() {
        return rozmiarKota.get();
    }

    public BooleanProperty rozmiarKotaProperty() {
        return rozmiarKota;
    }

    public void setRozmiarKota(boolean rozmiarKota) {
        this.rozmiarKota.set(rozmiarKota);
    }

    public int getTyp() {
        return typ.get();
    }

    public IntegerProperty typProperty() {
        return typ;
    }

    public void setTyp(int typ) {

        this.typ.set(typ);
        setRodzaj(Rodzaj.getKlase(typ));
    }


    public Rodzaj getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(Rodzaj rodzaj) {
        this.rodzaj = rodzaj;
    }

    @Override
    public String toString() {
        return "Zwierze{" +
                "nazwa=" + nazwa +
                ", siersc=" + siersc +
                ", piora=" + piora +
                ", jaja=" + jaja +
                ", mleko=" + mleko +
                ", latajacy=" + latajacy +
                ", wodny=" + wodny +
                ", drapieznik=" + drapieznik +
                ", uzebiony=" + uzebiony +
                ", kregoslup=" + kregoslup +
                ", oddycha=" + oddycha +
                ", jadowity=" + jadowity +
                ", pletwy=" + pletwy +
                ", nogi=" + nogi +
                ", ogon=" + ogon +
                ", domowy=" + domowy +
                ", rozmiarKota=" + rozmiarKota +
                ", typ=" + typ +
                '}';
    }
}
