package com.example.as6pokemons.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.PokemonViewHolder
import com.example.as6pokemons.R
import com.example.as6pokemons.ViewModel.PokemonViewModel


//Para crear la clase necesito meterle el recyclerview.adapter y con el <> le tengo que meter el layout de los items que vaya a meter
class PokemonAdapterRecyclerView(
    context: Context,
    val pokemonList: MutableList<PokemonData>,
    private val viewModel: PokemonViewModel,
    private val listaId: Int
) : RecyclerView.Adapter <PokemonViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    //Le pasamos el layout con el que lo podra modificar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.item_pokedex_recyclerview, parent, false))
    }

    //Rellena los item que tiene el recyclerview
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        //Rellenamos los datos mediante un metodo del viewHolder
        holder.render(pokemon)

        //Metemos el evento de click en un item para llevarlo a Detalles Pokemon
        holder.itemView.setOnClickListener {
            //Enviamos la info a traver del viewModel en vede del bundle que pasamos por el navigation
            viewModel.seleccionarPokemon(pokemon)

            val navController = Navigation.findNavController(holder.itemView)
            navController.navigate(listaId)
        }

        // Cuando se cargue el RecyclerView, actualizamos el icono según el estado del animal
        establecerIconoFav(pokemon, holder)

        // Listener para el evento sobre el icono de favorito
        holder.binding.iconFavorito.setOnClickListener {
            marcarFavorito(pokemon, holder)
        }

    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }


    //Funciones aparte
    //Funcion para meter al livedata la lista del repository
    fun establecerLista(nuevaLista: List<PokemonData>) {
        pokemonList.clear()
        pokemonList.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    //Funcion para marcar el fav
    fun marcarFavorito(pokemonData: PokemonData, holder: PokemonViewHolder) {
        if (pokemonData.favorito) {
            pokemonData.favorito = false
        } else {
            pokemonData.favorito = true
        }

        establecerIconoFav(pokemonData, holder)

        viewModel.actualizarPokemon(pokemonData)
    }

    //Funcion para establecer el icono de fav
    fun establecerIconoFav(pokemonData: PokemonData, holder: PokemonViewHolder){
        if (pokemonData.favorito) {
            holder.binding.iconFavorito.setImageResource(R.drawable.favorito)
        } else {
            holder.binding.iconFavorito.setImageResource(R.drawable.favoritonorelleno)
        }
    }

}