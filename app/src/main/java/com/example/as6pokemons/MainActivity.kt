package com.example.as6pokemons

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.as6pokemons.ViewModel.PokemonViewModel
import com.example.as6pokemons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: PokemonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializamos el viewmodel IMPORTANTE, por lo que sea
        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        //Cogemos la toolbar
        setSupportActionBar(binding.toolbar)

        //Esto son las direcciones que tenemos en el nav_graph para que funcionen los botones
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        //Declaro en esta linea cual es el fragment principal
        appBarConfiguration = AppBarConfiguration(setOf(R.id.pokedexFragment))
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        //Implementamos livedata para que modifique el titulo
        viewModel.title.observe(this) { newTitle ->
            binding.toolbar.title = newTitle
        }

    }

    //Cargamos el menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Los menus de dentro de option menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}