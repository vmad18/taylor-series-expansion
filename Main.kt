import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * @author v18
 */

fun <uwu,owo> HashMap<uwu,owo>.toeq(): String{
    var eq:String = ""
    for(i in this.keys){
        eq += "${this[i]}${if(i!="val") "*$i" else ""} + "
    }
    return eq.removeSuffix(" + ")
}


//f regex
fun scuffedParser(eq: List<String>?, d: Int): HashMap<String, Double> {
    var global_parsed: HashMap<String, Double> = HashMap()
    for (x: String in eq!!) {
        var min:Double = if(x.contains('-')) -1.0 else 1.0
         var i = x.replace("-", "")
        if (i.contains("sin") || i.contains("cos")) {
            if (i.contains("sin")) {
                val pat: Pattern = Pattern.compile("(?<coef>.*)sin\\((?<coef2>.*)x\\)")
                val matcher: Matcher = pat.matcher(i)
                if (matcher.matches()) {
                    var b: Int = if (matcher.group("coef") == "") 1 else matcher.group("coef").toInt()
                    var a: Int = if (matcher.group("coef2") == "") 1 else matcher.group("coef2").toInt()
                    var calc = MacLaurinSeriesExpansion(a, b, d, false, true).expand()
                    for (i in calc.keys) {
                        if (global_parsed.containsKey(i)) {
                            global_parsed[i] = global_parsed[i]!!.plus(calc[i]!!*min)
                        } else {
                            global_parsed[i] = calc[i]!!*min
                        }
                    }
                }
            }else{
                val pat: Pattern = Pattern.compile("(?<coef>.*)cos\\((?<coef2>.*)x\\)")
                val matcher: Matcher = pat.matcher(i)
                if (matcher.matches()) {
                    var b: Int = if (matcher.group("coef") == "") 1 else matcher.group("coef").toInt()
                    var a: Int = if (matcher.group("coef2") == "") 1 else matcher.group("coef2").toInt()
                    var calc = MacLaurinSeriesExpansion(a, b, d, true, true).expand()
                    for (i in calc.keys) {
                        if (global_parsed.containsKey(i)) {
                            global_parsed[i] = global_parsed[i]!!.plus(calc[i]!!*min)
                        } else {
                            global_parsed[i] = calc[i]!!*min
                        }
                    }
                }

            }
        } else if (i.contains("x")) {
            val pat: Pattern = Pattern.compile("(?<coef>.*)x\\^(?<coef2>.*)")
            val matcher: Matcher = pat.matcher(i)
            if (matcher.matches()) {
                val power = if (matcher.group("coef2") != "") "x^${matcher.group("coef2")}" else "x"
                val vl = if (matcher.group("coef") == "") 1.0 else matcher.group("coef").toDouble()
                if (global_parsed.containsKey(power)) {
                    global_parsed[power] = global_parsed[power]!!.plus(vl*min)
                } else {
                    global_parsed[power] = vl*min
                }
            }
        } else {
            if (global_parsed.containsKey("val")) {
                global_parsed["val"] = global_parsed["val"]!!.plus(i.toDouble()*min)
            } else {
                global_parsed["val"] = i.toDouble()*min
            }

        }
    }

    return global_parsed
}


fun main() {
    println("MacLaurin Series: ")
    println("String eq? ")
    val ye: Boolean = readLine()?.lowercase().equals("yes")

    if (ye) {
        var eq: String? = readLine()?.lowercase()?.replace(" ", "")
        var speq: List<String>? = eq?.split("+")
        println("Degree? ")
        var deg = readLine()?.toInt()
        println(scuffedParser(speq, deg!!).toeq())
    } else {
        println("Params? ")
        val str = readLine()?.lowercase()
        val spl = str?.split(" ")
        var test = MacLaurinSeriesExpansion(
            spl?.get(0)!!.toInt(),
            spl?.get(1)!!.toInt(),
            spl?.get(2)!!.toInt(),
            spl?.get(3)!!.toBoolean()
        ).expand()
    }
}
