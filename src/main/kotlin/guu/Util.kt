package guu

import guu.instructions.CallSub
import guu.instructions.Instruction
import guu.instructions.PrintVar
import guu.instructions.SetVar
import java.nio.file.Files
import java.nio.file.Path
import java.util.Deque

fun checkArgs(args: Array<String>) {
    val size = args.size

    check(size in 1..2) { "Wrong number of command line arguments" }

    if (size == 2)
        check(args[1] == "-d") { "Second command line argument should be \"-d\"" }
}

fun parseFile(
    fileName: String,
    context: ExecutionContext
) {
    val lines = Files.readAllLines(Path.of(fileName))
        .filterNot { it.isBlank() }
        .map { it.trimStart() }

    var currentSub: String? = null
    for (line in lines) {
        if (line.startsWith("sub")) {
            val subName = line.split(" ")[1]
            currentSub = subName
            context.subs[subName] = mutableListOf()
        } else {
            context.subs[currentSub]!!.add(parseInstruction(line, context))
        }
    }
}

fun executeOneByOne(
    instructions: List<Instruction>,
    context: ExecutionContext
) {
    for (instruction in instructions) {
        var cmd: String
        loop@ while (true) {
            println("(next line): $instruction")
            print("(enter command): ")

            cmd = readLine()!!.trim()

            if (cmd !in commands)
                println("Wrong command! Supported commands are $commands.")
            else when (cmd) {
                "o" -> {
                    instruction.exec()
                    break@loop
                }
                "i" -> {
                    instruction.execInto()
                    break@loop
                }
                "var" -> printVars(context.vars)
                "trace" -> printStackTrace(context.stack)
            }
        }
    }
}

private val commands = setOf("i", "o", "trace", "var")

private fun printVars(vars: Map<String, Int>) {
    println(vars)
}

private fun printStackTrace(stack: Deque<StackFrame>) {
    stack
        .reversed()
        .filter { it.instruction != null }
        .forEach { println("\t$it") }
}

private fun parseInstruction(
    line: String,
    context: ExecutionContext
): Instruction {
    val op = line.split(" ")

    check(op.size in 2..3) { "Wrong number of operands in: $line" }

    return when (op[0]) {
        "set" -> SetVar(op[1], op[2].toInt(), context)
        "print" -> PrintVar(op[1], context)
        "call" -> CallSub(op[1], context)
        else -> throw IllegalStateException("Wrong instruction: ${op[0]}")
    }
}
