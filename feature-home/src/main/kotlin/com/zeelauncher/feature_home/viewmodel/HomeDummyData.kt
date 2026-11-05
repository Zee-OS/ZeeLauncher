package com.zeelauncher.feature_home.viewmodel

import com.zeelauncher.feature_home.R

internal enum class DemoState { Default, TwoGames, OneGame, Empty }

internal val ActiveDemoState = DemoState.Default

private const val GENRE_SPORTS = "SPORTS"
private const val GENRE_SANDBOX = "SANDBOX"
private const val GENRE_ACTION = "ACTION"
private const val GENRE_SHOOTER = "SHOOTER"

private val Efootball = HomeGame("eFootball", GENRE_SPORTS, R.drawable.efootball)
private val Minecraft = HomeGame("Minecraft", GENRE_SANDBOX, R.drawable.minecraft)
private val RedDeadRedemption = HomeGame("Red Dead Redemption", GENRE_ACTION, R.drawable.rdr)
private val PubgMobile = HomeGame("PUBG Mobile", GENRE_SHOOTER, R.drawable.pubg_mobile)
private val GtaSanAndreas = HomeGame("GTA San Andreas", GENRE_ACTION, R.drawable.gta_sa)

internal val DefaultSelectedGame = RedDeadRedemption

internal fun demoGames(state: DemoState = ActiveDemoState): List<HomeGame> = when (state) {
    DemoState.Default -> listOf(Efootball, Minecraft, RedDeadRedemption, PubgMobile, GtaSanAndreas)
    DemoState.TwoGames -> listOf(Minecraft, RedDeadRedemption)
    DemoState.OneGame -> listOf(RedDeadRedemption)
    DemoState.Empty -> emptyList()
}
