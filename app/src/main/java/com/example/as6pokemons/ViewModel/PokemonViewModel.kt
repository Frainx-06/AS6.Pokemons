package com.example.as6pokemons.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.Repository.RepositoryPokemons

class PokemonViewModel : ViewModel (){
    //Referencia del repostirio de pokemons
    private val repositorio = RepositoryPokemons()
    val pokemonSeleccionado = MutableLiveData<PokemonData>()
    //Este va a ser el livedata para los fav
    val pokemonFavoritos = MutableLiveData<List<PokemonData>>()

    //El livedata que saca la lista de pokemons
    val pokemons: MutableLiveData<List<PokemonData>> = MutableLiveData()

    fun obtenerPokemons() {
        //Recupera los animales del repositorio y los mete
        pokemons.value = repositorio.getListaPokemons()
        actualizarFavoritos()
    }

    //Selecciona un pokemon para que saque luego los detalles en el segundo fragmet
    fun seleccionarPokemon(pokemonData: PokemonData){
        pokemonSeleccionado.value = pokemonData
    }

    // Elimina un animal del repositorio y actualiza el LiveData
    fun eliminarPokemon(position: Int) {
        // Obtenemos la lista actual del LiveData
        val listaActual = pokemons.value

        // Comprobamos que la lista existe y que la posición es válida
        if (listaActual != null && position in listaActual.indices) {
            // Recuperamos el animal que queremos eliminar
            val eliminado = listaActual[position]

            // Lo eliminamos del repositorio (fuente de datos)
            repositorio.eliminarPokemon(eliminado)

            // Actualizamos el LiveData con la nueva lista
            pokemons.value = repositorio.getListaPokemons()
        }
    }

    fun actualizarPokemon(pokemonData: PokemonData) {
        //Actualizamos el repositorio y a la livedata de la lista de favs
        repositorio.actualizarPokemon(pokemonData)
        pokemons.value = repositorio.getListaPokemons()
        actualizarFavoritos()
    }

    fun buscarPokemonPorNombre(texto: String) {
        pokemons.value = repositorio.getPokemonPorNombre(texto)
    }

    //Actualizamos los pokemons solo a los favoritos
    fun actualizarFavoritos() {
        // Filtra los Pokémon que están marcados como favoritos y actualiza el LiveData
        val listaActual = pokemons.value ?: emptyList()
        pokemonFavoritos.value = listaActual.filter { it.favorito }
    }


    //Actualizar los nombres
    private val titulo = MutableLiveData<String>("Inicio")
    val title: LiveData<String> get() = titulo

    // Funcion para actualizar el titulo de la toolbar
    fun updateTitle(newTitle: String) {
        titulo.value = newTitle
    }

    fun buscarFavoritosPorNombre(texto: String) {
        val listaFavs = pokemons.value?.filter { it.favorito } ?: emptyList()
        pokemonFavoritos.value = if (texto.isBlank()) {
            listaFavs
        } else {
            listaFavs.filter { it.name.contains(texto, ignoreCase = true) }
        }
    }


}