package com.example.letsrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
  //  MenuItem fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController= Navigation.findNavController(this,R.id.mainActivity_navHostfragment);
        NavigationUI.setupActionBarWithNavController(this,navController);

        //Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        //fav = menu.findItem(R.id.menu_profile);
        return true;
    }
   public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
           navController.navigateUp();
           return true;
     }
        else{

                return NavigationUI.onNavDestinationSelected(item, navController);

        }
   }

//Switching between fragments from the navBar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    NavigationUI.onNavDestinationSelected(item,navController);

                    return true;
                }
            };

}


