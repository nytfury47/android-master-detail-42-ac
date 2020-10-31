package com.tan.master_detail42_ac.data.entity

/**
 * This data class defines the JSON object response from the iTunes Search API
 */
data class TrackList(
    val resultCount: Int,
    val results: List<Track>?
)