package guu.instructions

import guu.ExecutionContext
import guu.addInstructionToFrame

abstract class Instruction(protected val context: ExecutionContext) {
    fun exec() {
        context.addInstructionToFrame(this)

        doExec()
    }

    open fun execInto() {
        context.addInstructionToFrame(this)

        doExec()
    }

    abstract fun doExec()
}