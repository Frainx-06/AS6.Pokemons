package com.example.as6pokemons

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.as6pokemons.Adapter.PokemonAdapterRecyclerView
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.Repository.RepositoryPokemons
import com.example.as6pokemons.ViewModel.PokemonViewModel
import com.example.as6pokemons.databinding.FragmentPokedexBinding

class PokedexFragment : Fragment() {

    private var _binding: FragmentPokedexBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: PokemonAdapterRecyclerView
    private lateinit var viewModel: PokemonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View {
        // Aquí inicializamos el binding
        _binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Metemos el viewmodel
        viewModel = ViewModelProvider (requireActivity()).get(PokemonViewModel::class.java)

        //Configuramos el adapter y el recycleview como tal
        val listaVacia = mutableListOf<PokemonData>()
        adapter = PokemonAdapterRecyclerView(requireContext(), listaVacia, viewModel,R.id.action_pokedexFragment_to_fragmentDetallesPokemons)
        binding.pokedexRecyclerView.adapter = adapter
        binding.pokedexRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.pokemons.observe(viewLifecycleOwner) { lista ->
            // Si hay cambios, actualizamos la lista del adaptador del RecyclerView
            adapter.establecerLista(lista)
        }

        binding.fabMain.setOnClickListener {
            findNavController().navigate(R.id.action_pokedexFragment_to_crearFragment)
        }

        viewModel.obtenerPokemons()
        eventoEliminarPokemon(view)
        viewModel.updateTitle("Inicio")

        binding.barraBusqueda.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Método que se ejecuta cuando el usuario le da a intro
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            // Método que se ejecuta cada vez que el usuario escribe una letra en el searchView
            override fun onQueryTextChange(texto: String?): Boolean {
                texto?.let { viewModel.buscarPokemonPorNombre(it) }
                return true
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Limpiamos el binding para evitar leaks
        _binding = null
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
                        // Pedimos al ViewModel que elimine el animal de esa posición
                        viewModel.eliminarPokemon(position)
                    }

            }

        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.pokedexRecyclerView)
    }
}
