package com.example.as6pokemons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.as6pokemons.Adapter.PokemonAdapterRecyclerView
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.Repository.RepositoryPokemons
import com.example.as6pokemons.ViewModel.PokemonViewModel
import com.example.as6pokemons.databinding.FragmentFavoritosPokemonBinding

class FavoritosPokemonFragment : Fragment() {
    private var _binding: FragmentFavoritosPokemonBinding? = null
    private val binding get() = _binding!!
    private val repositorio = RepositoryPokemons()
    private lateinit var viewModel: PokemonViewModel
    private lateinit var adapter: PokemonAdapterRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout para este fragment
        _binding = FragmentFavoritosPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Metemos el mismo viewmodel
        viewModel = ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)

        val listaVaciaFav = mutableListOf<PokemonData>()
        adapter = PokemonAdapterRecyclerView(requireContext(), listaVaciaFav, viewModel, R.id.action_favoritosPokemonFragment_to_fragmentDetallesPokemons)
        binding.favPokedexRecyclerView.adapter = adapter
        binding.favPokedexRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.pokemonFavoritos.observe(viewLifecycleOwner) { favoritos ->
            adapter.establecerLista(favoritos)
        }

        viewModel.obtenerPokemons()
        eventoEliminarPokemon(view)
        viewModel.updateTitle("Favoritos")

        binding.barraBusquedaFav.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Método que se ejecuta cuando el usuario le da a intro
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            // Método que se ejecuta cada vez que el usuario escribe una letra en el searchView
            override fun onQueryTextChange(texto: String?): Boolean {
                viewModel.buscarFavoritosPorNombre(texto ?: "")
                return true
            }
        })


    }

    private fun eventoEliminarPokemon(view: View) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, // No permitimos mover elementos (drag)
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // Deslizar izquierda o derecha
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // No necesitamos implementar el movimiento
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val pokemonEliminado = repositorio.getPosicionPokemon(position)

                    if (position != RecyclerView.NO_POSITION) {
                        // Pedimos al ViewModel que elimine el animal de esa posición
                        viewModel.eliminarPokemon(position)
                    }
                }
            }

        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.favPokedexRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
