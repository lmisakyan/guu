package guu

import guu.instructions.Instruction
import java.util.ArrayDeque
import java.util.Deque

class ExecutionContext {
    val subs: MutableMap<String, MutableList<Instruction>> = mutableMapOf()
    val vars: MutableMap<String, Int> = mutableMapOf()
    val stack: Deque<StackFrame> = ArrayDeque()

    init {
        addFrameForSub(main)
    }
}

data class StackFrame(
    val sub: String,
    val instruction: String? = null,
    val lineNumber: Int = 0
) {
    override fun toString(): String {
        return "$sub: $instruction (line: $lineNumber)"
    }
}

fun ExecutionContext.addFrameForSub(subName: String) {
    stack.push(StackFrame(subName))
}

fun ExecutionContext.removeFrame() {
    stack.pop()
}

fun ExecutionContext.addInstructionToFrame(instruction: Instruction) {
    val currentFrame = stack.pop()

    stack.push(currentFrame.copy(instruction = instruction.toString(), lineNumber = currentFrame.lineNumber + 1))
}

fun ExecutionContext.getSubInstructions(subName: String): List<Instruction> = subs.getValue(subName)
