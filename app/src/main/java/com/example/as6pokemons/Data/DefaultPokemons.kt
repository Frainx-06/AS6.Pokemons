package com.example.as6pokemons.Data

import com.example.as6pokemons.Model.PokemonData

object DefaultPokemons {

    //Lista de pokemons iniciales por si no hay datos descargados o no hay internet
    val list = listOf(
        PokemonData(
            img = "p_001",
            id = 1,
            name = "Bulbasaur",
            tipo = listOf("Planta", "Veneno"),
            descripcion = "Una rara semilla fue plantada en su espalda al nacer. La planta brota y crece con este Pokemon."
        ),
        PokemonData(
            img = "p_002",
            id = 2,
            name = "Ivysaur",
            tipo = listOf("Planta", "Veneno"),
            descripcion = "Cuando el bulbo de su espalda crece, parece no poder ponerse de pie sobre las patas traseras."
        ),
        PokemonData(
            img = "p_003",
            id = 3,
            name = "Venusaur",
            tipo = listOf("Planta", "Veneno"),
            descripcion = "La planta florece cuando absorbe energia solar."
        ),
        PokemonData(
            img = "p_004",
            id = 4,
            name = "Charmander",
            tipo = listOf("Fuego"),
            descripcion = "Prefiere las cosas calientes."
        ),
        PokemonData(
            img = "p_005",
            id = 5,
            name = "Charmeleon",
            tipo = listOf("Fuego"),
            descripcion = "Tiene una naturaleza agresiva."
        ),
        PokemonData(
            img = "p_006",
            id = 6,
            name = "Charizard",
            tipo = listOf("Fuego", "Volador"),
            descripcion = "Escupe fuego muy caliente."
        ),
        PokemonData(
            img = "p_007",
            id = 7,
            name = "Squirtle",
            tipo = listOf("Agua"),
            descripcion = "Dispara agua a presion."
        ),
        PokemonData(
            img = "p_008",
            id = 8,
            name = "Wartortle",
            tipo = listOf("Agua"),
            descripcion = "Simbolo de longevidad."
        ),
        PokemonData(
            img = "p_009",
            id = 9,
            name = "Blastoise",
            tipo = listOf("Agua"),
            descripcion = "Potentes chorros de agua."
        ),
        PokemonData(
            img = "p_010",
            id = 10,
            name = "Caterpie",
            tipo = listOf("Bicho"),
            descripcion = "Sus pequenas patas tienen ventosas que le permiten subir por muros y arboles."
        ),
        PokemonData(
            img = "p_011",
            id = 11,
            name = "Metapod",
            tipo = listOf("Bicho"),
            descripcion = "Su caparazon es duro como el acero y protege su cuerpo mientras evoluciona."
        ),
        PokemonData(
            img = "p_012",
            id = 12,
            name = "Butterfree",
            tipo = listOf("Bicho", "Volador"),
            descripcion = "Sus alas estan cubiertas de un polvo repelente al agua."
        ),
        PokemonData(
            img = "p_013",
            id = 13,
            name = "Weedle",
            tipo = listOf("Bicho", "Veneno"),
            descripcion = "Tiene un aguijon venenoso en la cabeza que usa para defenderse."
        ),
        PokemonData(
            img = "p_014",
            id = 14,
            name = "Kakuna",
            tipo = listOf("Bicho", "Veneno"),
            descripcion = "Permanece casi inmovil mientras prepara su evolucion."
        ),
        PokemonData(
            img = "p_015",
            id = 15,
            name = "Beedrill",
            tipo = listOf("Bicho", "Veneno"),
            descripcion = "Vuela a gran velocidad y ataca con los aguijones de sus brazos."
        ),
        PokemonData(
            img = "p_016",
            id = 16,
            name = "Pidgey",
            tipo = listOf("Normal", "Volador"),
            descripcion = "Es comun en bosques y campos, y levanta arena batiendo sus alas."
        ),
        PokemonData(
            img = "p_017",
            id = 17,
            name = "Pidgeotto",
            tipo = listOf("Normal", "Volador"),
            descripcion = "Protege su amplio territorio y persigue a los intrusos sin descanso."
        ),
        PokemonData(
            img = "p_018",
            id = 18,
            name = "Pidgeot",
            tipo = listOf("Normal", "Volador"),
            descripcion = "Sus alas poderosas le permiten volar a gran velocidad."
        ),
        PokemonData(
            img = "p_019",
            id = 19,
            name = "Rattata",
            tipo = listOf("Normal"),
            descripcion = "Es pequeno, rapido y roe cualquier cosa con sus incisivos."
        ),
        PokemonData(
            img = "p_020",
            id = 20,
            name = "Raticate",
            tipo = listOf("Normal"),
            descripcion = "Sus grandes colmillos crecen continuamente y puede morder con mucha fuerza."
        )
    )
}
