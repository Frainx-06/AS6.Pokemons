package com.example.as6pokemons.ViewModel

import androidx.lifecycle.*
import com.example.as6pokemons.Data.DefaultPokemons
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.Repository.RepositoryPokemons
import kotlinx.coroutines.launch

class PokemonViewModel(private val repositorio: RepositoryPokemons) : ViewModel() {
    val pokemonSeleccionado = MutableLiveData<PokemonData>()
    val pokemonFavoritos = MutableLiveData<List<PokemonData>>()
    val pokemons: MutableLiveData<List<PokemonData>> = MutableLiveData()

    //Obtener todos los pokemons
    fun obtenerPokemons() {
        viewModelScope.launch {
            var lista = repositorio.getListaPokemons()
            if (lista.isEmpty()) {
                repositorio.insertarPokemons(DefaultPokemons.list)
                lista = repositorio.getListaPokemons()
            }
            pokemons.value = lista
            actualizarFavoritos()
        }
    }

    //  Seleccionar pokemon
    fun seleccionarPokemon(pokemonData: PokemonData) {
        pokemonSeleccionado.value = pokemonData
    }

    //  Eliminar pokemon
    fun eliminarPokemon(position: Int) {
        viewModelScope.launch {
            val listaActual = pokemons.value

            if (listaActual != null && position in listaActual.indices) {
                val eliminado = listaActual[position]

                repositorio.eliminarPokemon(eliminado)

                val nuevaLista = repositorio.getListaPokemons()
                pokemons.value = nuevaLista
                actualizarFavoritos()
            }
        }
    }

    //  Actualizar pokemon
    fun actualizarPokemon(pokemonData: PokemonData) {
        viewModelScope.launch {
            repositorio.actualizarPokemon(pokemonData)

            val nuevaLista = repositorio.getListaPokemons()
            pokemons.value = nuevaLista
            actualizarFavoritos()
        }
    }

    //  Buscar por nombre
    fun buscarPokemonPorNombre(texto: String) {
        viewModelScope.launch {
            val resultado = repositorio.getPokemonPorNombre(texto)
            pokemons.value = resultado
        }
    }

    //  Filtrar favoritos
    fun actualizarFavoritos() {
        val listaActual = pokemons.value ?: emptyList()
        pokemonFavoritos.value = listaActual.filter { it.favorito }
    }

    //  Título toolbar
    private val titulo = MutableLiveData("Inicio")
    val title: LiveData<String> get() = titulo

    fun updateTitle(newTitle: String) {
        titulo.value = newTitle
    }

    suspend fun getNextId(): Int {
        val max = repositorio.getIdMax() ?: 0
        return max + 1
    }

    suspend fun agregarPokemon(pokemonData: PokemonData) {
        repositorio.insertarPokemon(pokemonData)

        val nuevaLista = repositorio.getListaPokemons()
        pokemons.value = nuevaLista
        actualizarFavoritos()
    }


    //  Buscar dentro de favoritos
    fun buscarFavoritosPorNombre(texto: String) {
        val listaFavs = pokemons.value?.filter { it.favorito } ?: emptyList()

        pokemonFavoritos.value = if (texto.isBlank()) {
            listaFavs
        } else {
            listaFavs.filter { it.name.contains(texto, ignoreCase = true) }
        }
    }

}
