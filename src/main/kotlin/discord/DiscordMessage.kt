package org.agroapp.discord

data class DiscordMessage(
    val embeds: List<Embed>
)

data class Embed(
    val title: String? = null,
    val color: Long,
    val fields: List<Field>? = null,
    val description: String? = null
)

data class Field(
    val name: String,
    val value: String,
    val inline: Boolean = true
)
