package com.example.as6pokemons.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.as6pokemons.Database.PokemonDatabase
import com.example.as6pokemons.Repository.RepositoryPokemons


class PokemonViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {

            // Instancia de la base de datos
            val database = PokemonDatabase.getDatabase(context)

            // Repositorio
            val repository = RepositoryPokemons(database.pokemonDao(), context.applicationContext)

            @Suppress("UNCHECKED_CAST")
            return PokemonViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
