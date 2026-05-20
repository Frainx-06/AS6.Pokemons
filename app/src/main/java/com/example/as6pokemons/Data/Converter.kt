package com.example.as6pokemons.Database

import androidx.room.TypeConverter

//El motivo de estas clases es por que room no permite el insertar listas como tal por lo que toca hacer esto para no cambiar toda la logica
class Converters {

    //Funcion para convertir las listas de tipos en strings encadenados con ","
    @TypeConverter
    fun fromList(value: List<String>): String {
        return value.joinToString(",")
    }

    //Funcion para a la hora de devolver la lista desde room que se convierte de nuevo en lista
    @TypeConverter
    fun toList(value: String): List<String> {
        return if (value.isEmpty()) emptyList() else value.split(",")
    }
}