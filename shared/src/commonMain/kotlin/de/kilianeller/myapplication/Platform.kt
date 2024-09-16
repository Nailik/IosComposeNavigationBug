package de.kilianeller.myapplication

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform