import com.bignerdranch.nyethack.*
import kotlin.system.exitProcess

fun main() {
    Game.play()

}

object Game{
    private val player = Player("DimAss")
    private var currentRoom: Room = TownSquare()
    private val worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long corridor"), Room("Generic room"))
    )
    init {
        println("Welcome, adventurer.")
        player.castFireball()

    }
    private  fun fight() = currentRoom.monster?.let {
        println("Monster: ${it.name} have ${it.healthPoints} hp")
        while(player.healthPoints > 0 && it.healthPoints > 0)
        {
            slay(it)
            Thread.sleep(1000)
        }
        "Combat complete"
    } ?: "There is nothing here to fight"
    private  fun slay(monster: Monster){
        println("${monster.name} did ${monster.attack(player)} damage")
        println("${player.name} did ${player.attack(monster)} damage")

        if(player.healthPoints < 0){
            println(">>>> You have been defeated! Thanks for playing. <<<<")
            exitProcess(0)
        }

        if(monster.healthPoints < 0){
            println(">>>> ${monster.name} has been defeated! <<<<")
            currentRoom.monster = null
        }

    }
    private fun end(): Boolean{
        println("Good bye, ${player.name}")
        return false
    }
    private  fun map(): Boolean{
        println("\nMap:\n_________________________")
         worldMap.forEach {it.forEach {
            if(it.name != currentRoom.name)
                print("${it.name},  ")
            else
                print("${currentRoom.name}(you here),  ")
        }
            println()
        }
        println("_______________________________\n")
        return true
    }
    private fun move(directionInput: String): String {
        var returnValue = ""
        try {
            val direction = Direction.valueOf(directionInput)
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is not bound")// не работает, сук пздц
            }
            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            returnValue = "Ok, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception) {
            returnValue = "Invalid direction: $directionInput"
        }
        return returnValue
    }

    fun play(){
        while(true) //main game loop
        {

            currentRoom.load()
            println(currentRoom.description())
            printPlayerStatus(player)
            print("> Enter your command: ")
            val command = GameInput(readLine()).processCommand()
            when(command){
                false -> break
                true -> " "
                else ->{
                    println(command)
                }
            }



        }
    }
    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()}) (Blessed is ${if(player.isBlessed) "YES" else "NO"}))")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private fun ring(numOfGwong: String): Boolean{
        try {
            val count: Int = numOfGwong.toInt() ?: -100
                if(currentRoom.name != "TownSquare")
                    println("Sorry, you can ring the bell only on TownSquare")
                else
                    currentRoom.ringBell(count)
        }
        catch (e: Exception){
           throw IllegalStateException("Please enter count ring the bell")
        }
        return true
    }

    private class GameInput(arg: String?){
        private val input = arg ?: ""
        val command = input.split(" ")[0].uppercase()
        val argument = input.split(" ").getOrElse(1, { "" }).uppercase()

        private fun commandNotFound() = "I'm not quite sure what you're trying to do"
        fun processCommand() = when(command){
            "FIGHT" -> fight()
            "MAP" -> map()
            "MOVE" -> move(argument)
            "QUIT" -> end()
            "RING" -> ring(argument)
            else -> commandNotFound()
        }
    }
}




