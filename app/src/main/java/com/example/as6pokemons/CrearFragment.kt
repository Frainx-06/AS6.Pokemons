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

    //Launcher para abrir la galeria y seleccionar la foto del pokemon
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                //Guardamos permiso para poder volver a leer la imagen mas adelante
                requireContext().contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                //Algunas galerias no dan permiso persistente, pero no cortamos la app por eso
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

    //Funcion para validar los campos y guardar el pokemon en room
    private fun guardarPokemon() {
        val nombrePokemon = binding.nombrePokemon.editText?.text.toString().trim()
        val tipoPokemonTexto = binding.tipoPokemon.editText?.text.toString().trim()
        val descripcionPokemon = binding.descripcionPokemon.editText?.text.toString().trim()
        val fotoPokemon = selectedImageUri

        //En caso de que salte algún error metemos un null para que no pete
        binding.nombrePokemon.error = null
        binding.tipoPokemon.error = null
        binding.descripcionPokemon.error = null
        binding.fotoPokemon.error = null

        var formularioValido = true

        //Comprobamos cada campo para mostrar el error justo donde toca
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

        //Separamos los tipos por comas o punto y coma para que se pase bien a la room y pueda convertilo con los converter
        val tipoPokemonLista = tipoPokemonTexto
            .split(',', ';')
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        lifecycleScope.launch {
            //Desactivamos el boton para evitar guardar dos veces seguidas
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
