package com.example.as6pokemons

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.as6pokemons.Database.PokemonDatabase
import com.example.as6pokemons.Repository.RepositoryPokemons
import com.example.as6pokemons.ViewModel.PokemonViewModel
import com.example.as6pokemons.ViewModel.PokemonViewModelFactory
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

        // Crear DB → DAO → Repository → Factory
        val database = PokemonDatabase.getDatabase(this)
        val repository = RepositoryPokemons(database.pokemonDao())
        // MainActivity.kt
        val factory = PokemonViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)
            .get(PokemonViewModel::class.java)

        // ViewModel con factory (IMPORTANTE)
        viewModel = ViewModelProvider(this, factory)
            .get(PokemonViewModel::class.java)

        // Toolbar
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(setOf(R.id.pokedexFragment))
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Observer del título
        viewModel.title.observe(this) { newTitle ->
            binding.toolbar.title = newTitle
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}