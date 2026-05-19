package com.example.as6pokemons.Repository

import androidx.room.*
import com.example.as6pokemons.Model.PokemonData

@Dao
interface PokemonDao {

    // Obtener todos los Pokémon
    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemons(): List<PokemonData>

    // Obtener un Pokémon por ID
    @Query("SELECT * FROM pokemon WHERE id = :id LIMIT 1")
    suspend fun getPokemonById(id: Int): PokemonData?

    // Insertar uno o varios Pokémon
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(vararg pokemons: PokemonData)

    // Insertar lista
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonsList(pokemons: List<PokemonData>)

    // Eliminar un Pokémon
    @Delete
    suspend fun deletePokemon(pokemon: PokemonData)

    // Actualizar un Pokémon
    @Update
    suspend fun updatePokemon(pokemon: PokemonData)

    @Query("SELECT MAX(id) FROM pokemon")
    suspend fun getIdPokemon(): Int?

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun getPokemonCount(): Int

    // Buscar por nombre (empieza por texto)
    @Query("SELECT * FROM pokemon WHERE LOWER(name) LIKE LOWER(:texto || '%')")
    suspend fun getPokemonPorNombre(texto: String): List<PokemonData>
}
