package animals;

import animals.typ.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Rodzaj {
    private StringProperty nazwa = new SimpleStringProperty();
    private IntegerProperty typ = new SimpleIntegerProperty();

    public Rodzaj()
    {

    }

    public Rodzaj(String nazwa, int typ)
    {
        this();
        setNazwa(nazwa);
        setTyp(typ);
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


    public int getTyp() {
        return typ.get();
    }

    public IntegerProperty typProperty() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ.set(typ);
    }

    public static Rodzaj getKlase(int typ)
    {
        switch (typ)
        {
            case 1: return new Ssak("Ssak",1);
            case 2: return new Ptak("Ptak", 2);
            case 3: return new Gad("Gad",3);
            case 4: return new Ryba("Ryba",4);
            case 5: return new Plaz("Plaz",5);
            case 6: return new Owad("Owad",6);
            case 7: return new Mieczak("Mieczak",7);
        }

        return null;
    }

}
