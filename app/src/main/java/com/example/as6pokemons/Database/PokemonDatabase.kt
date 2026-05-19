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
        @Volatile
        private var INSTANCE: PokemonDatabase? = null

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
