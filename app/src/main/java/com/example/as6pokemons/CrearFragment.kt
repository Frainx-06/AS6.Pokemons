package com.example.as6pokemons

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.ViewModel.PokemonViewModel
import com.example.as6pokemons.databinding.FragmentCrearBinding
import kotlinx.coroutines.launch

class CrearFragment : Fragment() {
    private var _binding: FragmentCrearBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PokemonViewModel
    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                requireContext().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // Some gallery providers do not offer persistable permissions.
            }

            selectedImageUri = uri
            binding.image.setImageURI(uri)
            binding.fotoPokemon.error = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PokemonViewModel::class.java)
        viewModel.updateTitle("Crear Pokemon")

        binding.image.setOnClickListener {
            abrirSelectorImagen()
        }

        binding.seleccionarFotoButton.setOnClickListener {
            abrirSelectorImagen()
        }

        binding.guardarButton.setOnClickListener {
            guardarPokemon()
        }
    }

    private fun abrirSelectorImagen() {
        imagePickerLauncher.launch(arrayOf("image/*"))
    }

    private fun guardarPokemon() {
        val nombrePokemon = binding.nombrePokemon.editText?.text.toString().trim()
        val tipoPokemonTexto = binding.tipoPokemon.editText?.text.toString().trim()
        val descripcionPokemon = binding.descripcionPokemon.editText?.text.toString().trim()
        val fotoPokemon = selectedImageUri

        binding.nombrePokemon.error = null
        binding.tipoPokemon.error = null
        binding.descripcionPokemon.error = null
        binding.fotoPokemon.error = null

        var formularioValido = true

        if (nombrePokemon.isBlank()) {
            binding.nombrePokemon.error = "Introduce un nombre"
            formularioValido = false
        }

        if (tipoPokemonTexto.isBlank()) {
            binding.tipoPokemon.error = "Introduce al menos un tipo"
            formularioValido = false
        }

        if (descripcionPokemon.isBlank()) {
            binding.descripcionPokemon.error = "Introduce una descripcion"
            formularioValido = false
        }

        if (fotoPokemon == null) {
            binding.fotoPokemon.error = "Selecciona una foto"
            formularioValido = false
        }

        if (!formularioValido || fotoPokemon == null) {
            return
        }

        val tipoPokemonLista = tipoPokemonTexto
            .split(',', ';')
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        lifecycleScope.launch {
            binding.guardarButton.isEnabled = false

            try {
                val pokemonData = PokemonData(
                    id = viewModel.getNextId(),
                    img = fotoPokemon.toString(),
                    name = nombrePokemon,
                    tipo = tipoPokemonLista,
                    descripcion = descripcionPokemon,
                    favorito = false
                )

                viewModel.agregarPokemon(pokemonData)
                findNavController().navigate(R.id.action_crearFragment_to_pokedexFragment)
            } catch (exception: Exception) {
                binding.guardarButton.isEnabled = true
                Toast.makeText(
                    requireContext(),
                    "No se pudo guardar el Pokemon",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
