package com.bignerdranch.nyethack

import kotlin.random.Random

interface Fightabale{

    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int
    val damageRoll: Int
        get() {
           return (0 until diceCount).map{
                Random.nextInt(diceSides + 1)}.sum()
        }

    fun attack(opponent: Fightabale): Int
}

abstract class Monster(val name: String, val description: String,
                       override var healthPoints: Int): Fightabale{
    override fun attack(opponent: Fightabale): Int {
        val damageDealt = damageRoll
        opponent.healthPoints -= damageDealt
        return  damageDealt
    }
}

class Goblin(name: String = "Goblin",
             description: String = "A nasty-looking goblin",
             healthPoints: Int = 30) : Monster(name, description, healthPoints) {
    override val diceSides: Int = 8
    override val diceCount: Int = 2
}
