package com.bignerdranch.nyethack
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315
import java.io.File

class Player(_name: String, override var healthPoints: Int = 100, var isBlessed: Boolean, private var isImmortal: Boolean) : Fightabale{
    var name = _name
    //get() = "${field.capitalize()} of $homeTown"
    get() = "${field.capitalize()} of $homeTown"
    set(value) {
        field.trim()
    }
    var homeTown = selectHome()
    private fun selectHome()  = File("Data/Town.txt")
        .readText()
        .split("\n", "\r")
        .random()
    constructor(name: String): this(name,
        healthPoints = 100,
        isBlessed = true,
        isImmortal = false){
        if(name.toLowerCase() == "lol") healthPoints = 40
    }
    init {
        require(healthPoints > 0, { "HealthPoints must be greater than zero" })
        require(name.isNotBlank(), {"Player must have a name"})
    }
    var currentPosition = Coordinate(0,0)
    fun castFireball(numFireballs: Int = 2) =
        println("A glass of Fireball springs into existence.(x$numFireballs)")

     fun formatHealthStatus() =
        when(healthPoints)
        {
            100 -> "is in excellent condition! ($healthPoints hp)"
            in 90..99 -> "has a few scratches. ($healthPoints hp)"
            in 75..98 -> if(isBlessed){
                "has some minor wounds, but is healing quite quickly!($healthPoints hp)"
            }else{
                "has some minor wounds, but is healing quite quickly!($healthPoints hp)"
            }
            in 15..74 -> "looks pretty hurt($healthPoints hp)"
            else -> "Is in awful condition($healthPoints hp)"
        }
     fun auraColor(): String{
        val auraVisible = isBlessed && healthPoints > 50 || isImmortal
        val auraColor = if(auraVisible) "Green" else "None"
        return auraColor
    }

    override val diceCount: Int = 3

    override val diceSides: Int = 6


    override fun attack(opponent: Fightabale): Int {
        val damageDealt = if(isBlessed){
            damageRoll * 2
        }
        else{
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return  damageDealt
    }
}