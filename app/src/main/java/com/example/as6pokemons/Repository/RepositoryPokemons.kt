package com.example.as6pokemons.Repository

import com.example.as6pokemons.Model.PokemonData

class RepositoryPokemons(private val pokemonDao: PokemonDao) {

    suspend fun getListaPokemons(): List<PokemonData> {
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
}
