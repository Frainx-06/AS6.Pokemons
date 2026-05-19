package com.example.as6pokemons.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pokemon")
data class PokemonData(
    @PrimaryKey
    val id: Int?,
    val img: String,
    val name: String,
    val tipo: List<String>,
    val descripcion: String,
    var favorito: Boolean = false
) : Serializable