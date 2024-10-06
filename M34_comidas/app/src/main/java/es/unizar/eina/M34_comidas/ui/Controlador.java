package es.unizar.eina.M34_comidas.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import es.unizar.eina.M34_comidas.ui.pedidos.Pedidos;
import es.unizar.eina.M34_comidas.ui.platos.Platos;
import es.unizar.eina.M34_comidas.ui.test.UnitTests;

public class Controlador extends FragmentPagerAdapter {
    int numTabs;

     public Controlador(@NonNull FragmentManager fm, int behavior){
        super(fm, behavior);
        this.numTabs = behavior;
     }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new Platos();
            case 1:
                return new Pedidos();
            case 2:
                return new UnitTests();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
