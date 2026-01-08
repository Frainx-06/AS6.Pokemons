package com.example.as6pokemons

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.databinding.ItemPokedexRecyclerviewBinding

//En el viewholder tenemos que hacer que le llegue la view, y luego que extedienda de viewholder para que funcione
class PokemonViewHolder (view: View): RecyclerView.ViewHolder(view){
    val binding = ItemPokedexRecyclerviewBinding.bind(view)

    //Esta funcion se llama para cada item del recyclerview, basicamente la que rellena los datos
    fun render(pokemonData: PokemonData){
        binding.tvPokemonId.text = "N.º ${pokemonData.id}"
        binding.imgPokemon.setImageResource(pokemonData.img)
        binding.tvPokemonNombre.text = pokemonData.name
    }

}