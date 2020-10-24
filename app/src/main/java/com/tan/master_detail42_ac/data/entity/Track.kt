package com.tan.master_detail42_ac.data.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

private const val TRACK_NAME = "Track Name"
private const val TRACK_IMAGE = "Track Image"
private const val TRACK_PRICE = 0.0
private const val TRACK_GENRE = "Track Genre"
private const val TRACK_DESCRIPTION = "Track Description"

/**
 * This data class defines a Track property which includes the track name, the track image URL, the
 * the track price, the track genre, and the track description.
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
@Parcelize
data class Track(
    val trackName: String = TRACK_NAME,
    // used to map artworkUrl100 from the JSON to imgSrcUrl in our class
    @Json(name = "artworkUrl100") val artwork: String = TRACK_IMAGE,
    @Json(name = "trackPrice") val price: Double = TRACK_PRICE,
    @Json(name = "primaryGenreName") val genre: String = TRACK_GENRE,
    @Json(name = "longDescription") val description: String = TRACK_DESCRIPTION
) : Parcelable