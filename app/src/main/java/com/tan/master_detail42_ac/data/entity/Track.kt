package com.tan.master_detail42_ac.data.entity

import com.squareup.moshi.Json

/**
 * This data class defines a Track property which includes the track name, the track image URL, the
 * the track price, the track genre, and the track description.
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
data class Track(
    val trackName: String,
    // used to map artworkUrl100 from the JSON to imgSrcUrl in our class
    @Json(name = "artworkUrl100") val artwork: String,
    @Json(name = "trackPrice") val price: Double,
    @Json(name = "primaryGenreName") val genre: String,
    @Json(name = "longDescription") val description: String
)