package guu.instructions

import guu.ExecutionContext
import guu.addFrameForSub
import guu.addInstructionToFrame
import guu.executeOneByOne
import guu.getSubInstructions
import guu.removeFrame

class CallSub(
    private val subName: String,
    context: ExecutionContext
) : Instruction(context) {
    override fun doExec() {
        context.getSubInstructions(subName).forEach { it.exec() }
    }

    override fun execInto() {
        context.addInstructionToFrame(this)

        context.addFrameForSub(subName)

        val subInstructions = context.getSubInstructions(subName)
        executeOneByOne(subInstructions, context)

        context.removeFrame()
    }

    override fun toString(): String = "call $subName"
}