import java.io.File
import javax.swing.text.StyledEditorKit

const val TAVERN_NAME = "DimAss's hospitable dangeon"
val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniq = mutableSetOf<String>()
val patronGold = mutableMapOf<String, Double>()
fun main() {


    val menuList = File("Data/data-tavern-item.txt").readText().split('\n')
    (0..9).forEach{
        val first = patronList.shuffled().first()
        val second = lastName.shuffled().first()
        val name = "$first $second"
        println(name)
        uniq.add(name)
    }

   uniq.forEach { it ->
       patronGold[it] = 6.0
   }
    uniq.add("Joe")
    patronGold["Joe"] = 0.0
    displayPatronBalances()
    val size = uniq.size
    var count = 0
    while(count < size)
    {
        placeOrder(uniq.shuffled().first(), menuList.shuffled().first())
        count++
    }
    displayPatronBalances()
    //check(listOf("Eli", "Mordoc"))
}
fun printPlayerStatus(auraColor: String, isBlessed: Boolean, name: String, healthStatus: String) {
    println("(Aura: $auraColor) " + "(Blessed: ${if (isBlessed) "YES" else "NO"})")
    println("$name $healthStatus")
}
//делает один заказ
fun placeOrder(playerName: String, menuData: String){
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$playerName speaks $tavernMaster about their order")
    val(type, name, price) = menuData.split(',')
    val message = "$playerName wanna buy a $name ($type) for price $price"
    println(message)
    val checkGetOut = performPurchase(price.toDouble(), playerName)
    if(!checkGetOut)
    {
        delete(playerName)
        return
    }
    val str = if(name == "Dragon's Breath")
    {
        println("$playerName exclaims: ${toDragonSpeak(" IT'S GOT WHAT ADVENTURERS CRAVE! ")}")
    }
    else
    {
        println("Thanks for $name")
    }

}
fun delete(playerName: String){
    uniq.remove(playerName)
    patronGold -= playerName
    println("Get out of here, $playerName")
}
private  fun toDragonSpeak(str: String) =
    str.replace(Regex("[AEIOUaeiou]")){
        when(it.value)
        {
            "A", "a" -> "4"
            "E", "e" -> "3"
            "I", "i" -> "1"
            "O", "o" -> "0"
            "U", "u" -> "_"
            else -> it.value
        }
    }
fun checkGold(playerName: String, price: Double = 0.0): Boolean{
    val value = patronGold.getValue(playerName)
    if(value <= price)
        return false
    return true
}
//подсчитывает кличество денег
fun performPurchase(price: Double, patronName: String): Boolean{
    val totalPurse = patronGold.getValue(patronName)
    if(!checkGold(patronName, price))
        return false
    else
    {

        patronGold[patronName] = totalPurse - price
        return true
    }


}
//выводит количество денег
fun displayPatronBalances(){
    patronGold.forEach{ patron, balance ->
        println("$patron, balance : ${"%.2f".format(balance)}")
    }
}
//проверяет на наличие посетителей
fun check(lst: List<String>): Boolean{
    if(patronList.containsAll(lst))
        println("Yes, $lst present in the room")
    else
        println("Sorry, $lst is absent")
    return true
}
