package com.bignerdranch.nyethack

open class Room(val name: String){
    protected open val dangerLevel = 5
    var monster: Monster? = Goblin()
    fun description() = "Room $name\n" +
            "Danger level: $dangerLevel\n" +
            "creatures: ${monster?.description ?: "none."}"
    open fun load() = "Nothing much to see here"
    open fun ringBell(num: Int = -1){
        println("")
    }
}

open class TownSquare : Room("TownSquare"){
    override val dangerLevel = super.dangerLevel - 3
    private var bellSound = "GWONG"
    final override fun load() = "The villagers rally and chears as you enter\n${ringBell()}"
    final override fun ringBell(numOfGwong: Int) {
        if(numOfGwong == -1)
            "The bell tower announces your arrival. $bellSound"
        else
        {
            (0 until numOfGwong).forEach{println("$bellSound")}
        }
    }


}