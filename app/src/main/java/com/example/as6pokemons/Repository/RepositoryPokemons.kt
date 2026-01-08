package com.example.as6pokemons.Repository

import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.R

class RepositoryPokemons {

    private val listaPokemons: MutableList<PokemonData> = mutableListOf()

    init {
        listaPokemons.addAll(
            listOf(
                PokemonData(
                    img = R.drawable.p_001,
                    id = 1,
                    name = "Bulbasaur",
                    tipo = mutableListOf("Planta", "Veneno"),
                    descripcion = "Una rara semilla fue plantada en su espalda al nacer. La planta brota y crece con este Pokémon."
                ),
                PokemonData(
                    img = R.drawable.p_002,
                    id = 2,
                    name = "Ivysaur",
                    tipo = mutableListOf("Planta", "Veneno"),
                    descripcion = "Cuando el bulbo de su espalda crece, parece no poder ponerse de pie sobre las patas traseras."
                ),
                PokemonData(
                    img = R.drawable.p_003,
                    id = 3,
                    name = "Venusaur",
                    tipo = mutableListOf("Planta", "Veneno"),
                    descripcion = "La planta florece cuando absorbe energía solar. Permanece en movimiento para buscar la luz del sol."
                ),
                PokemonData(
                    img = R.drawable.p_004,
                    id = 4,
                    name = "Charmander",
                    tipo = mutableListOf("Fuego"),
                    descripcion = "Prefiere las cosas calientes. Dicen que cuando llueve, el vapor sale de la punta de su cola."
                ),
                PokemonData(
                    img = R.drawable.p_005,
                    id = 5,
                    name = "Charmeleon",
                    tipo = mutableListOf("Fuego"),
                    descripcion = "Tiene una naturaleza agresiva. Ataca constantemente a sus enemigos con su cola ardiente."
                ),
                PokemonData(
                    img = R.drawable.p_006,
                    id = 6,
                    name = "Charizard",
                    tipo = mutableListOf("Fuego", "Volador"),
                    descripcion = "Escupe fuego tan caliente que funde cualquier cosa. Sus alas le permiten volar alto."
                ),
                PokemonData(
                    img = R.drawable.p_007,
                    id = 7,
                    name = "Squirtle",
                    tipo = mutableListOf("Agua"),
                    descripcion = "Cuando retrae su largo cuello en el caparazón, dispara agua a una presión increíble."
                ),
                PokemonData(
                    img = R.drawable.p_008,
                    id = 8,
                    name = "Wartortle",
                    tipo = mutableListOf("Agua"),
                    descripcion = "Se le considera un símbolo de longevidad. Vive miles de años."
                ),
                PokemonData(
                    img = R.drawable.p_009,
                    id = 9,
                    name = "Blastoise",
                    tipo = mutableListOf("Agua"),
                    descripcion = "Aplasta a sus enemigos usando el peso de su cuerpo y potentes chorros de agua."
                ),
                PokemonData(
                    img = R.drawable.p_010,
                    id = 10,
                    name = "Caterpie",
                    tipo = mutableListOf("Bicho"),
                    descripcion = "Para protegerse, despide un hedor horrible por las antenas de su cabeza."
                ),
                PokemonData(
                    img = R.drawable.p_011,
                    id = 11,
                    name = "Metapod",
                    tipo = mutableListOf("Bicho"),
                    descripcion = "Está preparando su cuerpo dentro del caparazón duro para evolucionar."
                ),
                PokemonData(
                    img = R.drawable.p_012,
                    id = 12,
                    name = "Butterfree",
                    tipo = mutableListOf("Bicho", "Volador"),
                    descripcion = "Le encanta recolectar miel. Puede localizar flores a kilómetros de distancia."
                ),
                PokemonData(
                    img = R.drawable.p_013,
                    id = 13,
                    name = "Weedle",
                    tipo = mutableListOf("Bicho", "Veneno"),
                    descripcion = "Tiene un aguijón venenoso en la cabeza. Se alimenta de hojas en los bosques."
                ),
                PokemonData(
                    img = R.drawable.p_014,
                    id = 14,
                    name = "Kakuna",
                    tipo = mutableListOf("Bicho", "Veneno"),
                    descripcion = "Permanece casi inmóvil mientras se prepara para evolucionar."
                ),
                PokemonData(
                    img = R.drawable.p_015,
                    id = 15,
                    name = "Beedrill",
                    tipo = mutableListOf("Bicho", "Veneno"),
                    descripcion = "Tiene tres aguijones venenosos en las patas delanteras y la cola."
                ),
                PokemonData(
                    img = R.drawable.p_016,
                    id = 16,
                    name = "Pidgey",
                    tipo = mutableListOf("Normal", "Volador"),
                    descripcion = "Tiene un sentido de la orientación muy desarrollado. Regresa siempre a su nido."
                ),
                PokemonData(
                    img = R.drawable.p_017,
                    id = 17,
                    name = "Pidgeotto",
                    tipo = mutableListOf("Normal", "Volador"),
                    descripcion = "Protege su territorio ferozmente. Ataca a intrusos sin piedad."
                ),
                PokemonData(
                    img = R.drawable.p_018,
                    id = 18,
                    name = "Pidgeot",
                    tipo = mutableListOf("Normal", "Volador"),
                    descripcion = "Vuela a velocidad Mach 2 cuando caza. Sus alas generan fuertes ráfagas de viento."
                ),
                PokemonData(
                    img = R.drawable.p_019,
                    id = 19,
                    name = "Rattata",
                    tipo = mutableListOf("Normal"),
                    descripcion = "Es muy cauteloso. Si muerde algo, no lo suelta fácilmente."
                ),
                PokemonData(
                    img = R.drawable.p_020,
                    id = 20,
                    name = "Raticate",
                    tipo = mutableListOf("Normal"),
                    descripcion = "Sus grandes colmillos crecen constantemente, por lo que roe objetos duros."
                )
            )
        )
    }


    //Coge el pokemon en una posicion concreta
    fun getPosicionPokemon(position: Int): PokemonData? {
        return if (position in listaPokemons.indices) {
            listaPokemons[position]
        } else {
            null
        }
    }

    //Elimina el pokemon de la lista
    fun eliminarPokemon(pokemon : PokemonData){
        listaPokemons.remove(pokemon)
    }

    fun actualizarPokemon(pokemonData: PokemonData){
        // Recuperamos la posición previa para volver a colocarlo en el mismo sitio
        val posicion = listaPokemons.indexOf(pokemonData)
        // Colocamos el animal modificado en el mismo sitio
        listaPokemons[posicion] = pokemonData
    }

    // Devuelve solo los animales cuyo nombre empieza por el texto indicado
    fun getPokemonPorNombre(texto: String): List<PokemonData> {
        val resultado = mutableListOf<PokemonData>()
        for (a in listaPokemons) {
            if (a.name.lowercase().startsWith(texto.lowercase())) {
                resultado.add(a)
            }
        }
        return resultado
    }

    fun getListaPokemons(): MutableList<PokemonData> = listaPokemons
}
