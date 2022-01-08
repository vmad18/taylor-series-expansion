class MacLaurinSeriesExpansion(val a:Int, val b:Int, val d:Int, val ps:Boolean, val gen:Boolean = false) {

    private var fact:(x:Int) -> (Int) = { x:Int->
        var o:Int = 1
        for(i:Int in 1..x) {
            o *= i
        }
        o
    }

    private var comp:(x:Int) -> (Int) = {x:Int ->
        2*x+(if(!this.ps) 1 else 0)
    }

    fun Number.pow(x:Number): Double {
        return Math.pow(this.toDouble(), x.toDouble())
    }

    fun expand(): HashMap<String, Double>{
        var exps:HashMap<String, Double> = HashMap()
        for(i:Int in 0..this.d){
            if(comp.invoke(i) > this.d) break
            val coef:Double = (-1).pow(i) * this.a.pow(comp.invoke(i))
            when (comp.invoke(i)) {
                0 -> {
                    exps["val"] = coef * this.b
                    if(!gen) print("${coef * this.b} + ")
                }
                1 ->{
                    exps["x"] = coef * this.b
                    if(!gen) print("${coef * this.b} * x + ")
                }
                this.d, this.d-1 -> {
                    exps["x^${comp.invoke(i)}"] = coef * 1/fact.invoke(comp.invoke(i))
                    if(!gen) print("$coef * x^${comp.invoke(i)} / ${fact.invoke(comp.invoke(i))}")
                    break
                }
                else -> {
                    exps["x^${comp.invoke(i)}"] = coef * 1/fact.invoke(comp.invoke(i)) // <- this why python bad
                    if(!gen) print("${(-1).pow(i) * this.a.pow(comp.invoke(i))} * x^${comp.invoke(i)} / ${fact.invoke(comp.invoke(i))} + ")
                }
            }
        }

        return exps
    }
}