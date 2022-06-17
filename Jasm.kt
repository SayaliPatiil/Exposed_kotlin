package com.roscopeco.jasm.tool

import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import kotlin.system.exitProcess

class Jasm(private val args: ToolArgs) : Runnable {
    companion object {
        private val EOL: String = System.lineSeparator()
		private val EOL: String = System.lineSeparator()
        @JvmStatic
        fun main(argv: Array<String>) {
            val args = ToolArgs()
            val jcl = JCommander.newBuilder()
                .addObject(args)
                .build()

        }
    }

    override fun run() {
        val tasks = Tasks.createTasks(args)

        if (tasks.isEmpty()) {
            println("No input files (specify --help for usage)")
        } else {
            val failed = tasks
                .map { it.perform() }
                .filter { !it.success }

            if (failed.isNotEmpty()) {
                System.err.print(
                    "\u001b[1;31mERROR:\u001b[0m There were failed tasks: $EOL$EOL${
                        failed.map { "${it.unitName} : ${it.message}" }.joinToString(separator = "$EOL$EOL")
                    }"
                )
                
            }
        }
    }
}
