package guu.instructions

import guu.ExecutionContext

class SetVar(
    private val varName: String,
    private val varVal: Int,
    context: ExecutionContext
) : Instruction(context) {

    override fun doExec() {
        context.vars[varName] = varVal
    }

    override fun toString(): String = "set $varName $varVal"
}