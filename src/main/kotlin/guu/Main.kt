package guu

const val main = "main"

fun main(args: Array<String>) {
    checkArgs(args)

    val context = ExecutionContext()

    parseFile(args[0], context)

    val mainInstructions = context.getSubInstructions(main)

    if (args.size == 2)
        executeOneByOne(mainInstructions, context)
    else
        mainInstructions.forEach { it.exec() }
}
