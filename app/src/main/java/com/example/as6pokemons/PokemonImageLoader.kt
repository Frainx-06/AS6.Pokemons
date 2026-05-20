package com.example.as6pokemons

import android.net.Uri
import android.widget.ImageView

//Funcion para cargar imagenes tanto de drawable como de la galeria
fun ImageView.loadPokemonImage(imageValue: String) {
    //Primero intentamos buscar si la imagen existe dentro de drawable
    val resourceId = context.resources.getIdentifier(
        imageValue,
        "drawable",
        context.packageName
    )

    if (resourceId != 0) {
        setImageResource(resourceId)
    } else {
        //Si no esta en drawable, entendemos que es una URI elegida por el usuario
        setImageURI(Uri.parse(imageValue))
    }
}
