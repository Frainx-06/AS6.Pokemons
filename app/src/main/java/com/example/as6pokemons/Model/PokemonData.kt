package com.example.as6pokemons.Model

import java.io.Serializable

data class PokemonData(
    val img: Int,
    val id: Int,
    val name: String,
    val tipo: List<String>,
    val descripcion: String,
    var favorito: Boolean = false
) : Serializable