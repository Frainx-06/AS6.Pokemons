package com.example.as6pokemons

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.ViewModel.PokemonViewModel
import com.example.as6pokemons.databinding.FragmentDetallesPokemonsBinding

class FragmentDetallesPokemons : Fragment() {

    private var _binding: FragmentDetallesPokemonsBinding? = null
    private val binding get() = _binding!!
    private var pokemon: PokemonData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var viewModel: PokemonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallesPokemonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Obtenemos el ViewModel compartido (misma instancia que en PokedexFragment)
        viewModel = ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)

        viewModel.pokemonSeleccionado.observe(viewLifecycleOwner) { pokemonData ->
            pokemonData?.let {
                binding.imgDetallePokemon.setImageResource(it.img)
                binding.tvDetalleId.setText("N.º ${it.id}")
                binding.tvDetalleNombre.setText(it.name)
                binding.tvDetalleDescripcion.setText(it.descripcion)
                viewModel.updateTitle("Detalles de ${it.name}")

                mostrarTipos(it.tipo)

            } ?: run {
                // En caso de error, mostrar mensaje y volver atrás
                Toast.makeText(
                    requireContext(),
                    "No se pudo cargar el detalle del pokemon",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().onBackPressed()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun mostrarTipos(tipos: List<String>) {
        binding.tiposContainer.removeAllViews()

        for (tipo in tipos) {
            val tipoTextView = TextView(requireContext()).apply {
                text = tipo
                setPadding(24, 12, 24, 12)
                setTextColor(Color.WHITE)
                setBackgroundColor(Color.parseColor("#FF6200EE"))
            }

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            tipoTextView.layoutParams = params

            binding.tiposContainer.addView(tipoTextView)
        }
    }


}
