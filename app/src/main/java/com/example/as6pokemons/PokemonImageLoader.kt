package com.example.as6pokemons

import android.net.Uri
import android.widget.ImageView

fun ImageView.loadPokemonImage(imageValue: String) {
    val resourceId = context.resources.getIdentifier(
        imageValue,
        "drawable",
        context.packageName
    )

    if (resourceId != 0) {
        setImageResource(resourceId)
    } else {
        setImageURI(Uri.parse(imageValue))
    }
}
