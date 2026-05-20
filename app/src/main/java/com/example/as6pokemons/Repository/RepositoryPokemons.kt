package com.example.as6pokemons.Repository

import android.content.Context
import com.example.as6pokemons.Api.PokeApiClient
import com.example.as6pokemons.Data.DefaultPokemons
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.Network.NetworkUtils

class RepositoryPokemons(
    private val pokemonDao: PokemonDao,
    private val context: Context,
    private val apiClient: PokeApiClient = PokeApiClient()
) {

    //Funcion principal para obtener pokemons: si hay internet sincroniza con la API
    suspend fun getListaPokemons(): List<PokemonData> {
        if (NetworkUtils.hasInternet(context)) {
            sincronizarDesdeApi()
        }

        return pokemonDao.getAllPokemons()
    }

    //Funcion para leer solo Room, sin volver a consultar la API
    suspend fun getListaPokemonsLocal(): List<PokemonData> {
        return pokemonDao.getAllPokemons()
    }

    suspend fun getPokemonPorId(id: Int): PokemonData? {
        return pokemonDao.getPokemonById(id)
    }

    suspend fun eliminarPokemon(pokemon: PokemonData) {
        pokemonDao.deletePokemon(pokemon)
    }

    suspend fun actualizarPokemon(pokemon: PokemonData) {
        pokemonDao.updatePokemon(pokemon)
    }

    suspend fun getIdMax(): Int? {
        return pokemonDao.getIdPokemon()
    }

    suspend fun getPokemonCount(): Int {
        return pokemonDao.getPokemonCount()
    }

    suspend fun insertarPokemons(pokemons: List<PokemonData>) {
        pokemonDao.insertPokemonsList(pokemons)
    }

    suspend fun getPokemonPorNombre(texto: String): List<PokemonData> {
        return pokemonDao.getPokemonPorNombre(texto)
    }

    suspend fun insertarPokemon(pokemon: PokemonData) {
        pokemonDao.insertPokemons(pokemon)
    }

    //Funcion para actualizar Room con los datos de la API cuando hay internet
    private suspend fun sincronizarDesdeApi() {
        val listaLocal = pokemonDao.getAllPokemons()
        //Guardamos los favoritos actuales para no perderlos al refrescar desde internet
        val favoritosPorId = listaLocal.associate { it.id to it.favorito }
        //Guardamos tambien los pokemons creados por el usuario para que no desaparezcan
        val pokemonsCreados = listaLocal.filter { pokemon ->
            val id = pokemon.id ?: return@filter true
            id > API_LIMIT
        }

        //Si falla la API, usamos los default solo si la base de datos esta vacia
        val listaApi = try {
            apiClient.obtenerPrimerosPokemons(API_LIMIT)
        } catch (_: Exception) {
            if (listaLocal.isEmpty()) DefaultPokemons.list else return
        }

        //Mezclamos los datos nuevos con los favoritos y los pokemons creados
        val listaSincronizada = listaApi.map { pokemonApi ->
            pokemonApi.copy(favorito = favoritosPorId[pokemonApi.id] ?: false)
        } + pokemonsCreados

        pokemonDao.insertPokemonsList(listaSincronizada)
    }

    private companion object {
        const val API_LIMIT = 20
    }
}
