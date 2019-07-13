package guu.instructions

import guu.ExecutionContext

class PrintVar(
    private val varName: String,
    context: ExecutionContext
) : Instruction(context) {

    override fun doExec() {
        val v = context.vars[varName] ?: throw IllegalStateException("Variable $varName not found")
        println("$varName = $v")
    }

    override fun toString(): String = "print $varName"
}