package com.example.as6pokemons.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.as6pokemons.Data.DefaultPokemons
import com.example.as6pokemons.Model.PokemonData
import com.example.as6pokemons.Repository.PokemonDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [PokemonData::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        //Instancia unica de la base de datos para toda la app
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

        //Funcion para crear o recuperar la base de datos
        fun getDatabase(context: Context): PokemonDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDatabase::class.java,
                    "pokemon_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                seedDefaultPokemons(instance)
                instance
            }
        }

        //Funcion para meter los pokemons iniciales si la tabla esta vacia
        private fun seedDefaultPokemons(database: PokemonDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = database.pokemonDao()
                if (dao.getPokemonCount() == 0) {
                    dao.insertPokemonsList(DefaultPokemons.list)
                }
            }
        }
    }
}
