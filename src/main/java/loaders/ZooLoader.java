package loaders;

import animals.Zwierze;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

public class ZooLoader {
    private String sciezka;
    private ObservableList<Zwierze> zwierzes = FXCollections.observableArrayList();

    public ZooLoader()
    {
        super();
    }




    public ObservableList<Zwierze> wczytajPlik(String sciezka) {
        this.sciezka = sciezka;
        this.zwierzes.clear();
        Scanner in = null;
        try {

            in = new Scanner(new File(sciezka));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = "";

        while(in.hasNext())
        {
            line = in.nextLine();
            try {
                String[] wiersz = line.split(",");
                Zwierze z = new Zwierze(wiersz[0]);
                z.setSiersc(toBoolean(wiersz[1]));
                z.setPiora(toBoolean(wiersz[2]));
                z.setJaja(toBoolean(wiersz[3]));
                z.setMleko(toBoolean(wiersz[4]));
                z.setLatajacy(toBoolean(wiersz[5]));
                z.setWodny(toBoolean(wiersz[6]));

                z.setDrapieznik(toBoolean(wiersz[7]));
                z.setUzebiony(toBoolean(wiersz[8]));
                z.setKregoslup(toBoolean(wiersz[9]));
                z.setOddycha(toBoolean(wiersz[10]));
                z.setJadowity(toBoolean(wiersz[11]));
                z.setPletwy(toBoolean(wiersz[12]));
                z.setNogi(Integer.parseInt(wiersz[13]));
                z.setOgon(toBoolean(wiersz[14]));
                z.setDomowy(toBoolean(wiersz[15]));
                z.setRozmiarKota(toBoolean(wiersz[16]));
                z.setTyp(Integer.parseInt(wiersz[17]));
                zwierzes.add(z);
            }catch (Exception e)
            {
                //
            }
        }

        return zwierzes;
    }

    private Boolean toBoolean(String t)
    {
        if(t.equals("0"))
            return false;
        else
        return true;
    }


}
