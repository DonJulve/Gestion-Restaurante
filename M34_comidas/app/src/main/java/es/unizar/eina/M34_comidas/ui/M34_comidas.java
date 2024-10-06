package es.unizar.eina.M34_comidas.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import es.unizar.eina.M34_comidas.R;

/** Pantalla principal de la aplicación M34_comidas */
public class M34_comidas extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem platos, pedidos, tests;
    Controlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewer);

        platos = findViewById(R.id.tab_platos);
        pedidos = findViewById(R.id.tab_pedidos);
        tests = findViewById(R.id.tab_test);

        controlador = new Controlador(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(controlador);
        Log.d("onCreate", "llega a inicializar");
        tabLayout.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2){
                    controlador.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}