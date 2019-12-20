package com.miyazakiu.katlab.ALPS.listener

import com.miyazakiu.katlab.ALPS.parser.CPP14BaseListener
import com.miyazakiu.katlab.ALPS.parser.CPP14Parser
import org.antlr.v4.runtime.tree.TerminalNode
import java.util.ArrayList




class ArduinoEvalLister(): CPP14BaseListener() {

    private val errors = ArrayList<String>()
    init {

    }

    fun getErrors() : ArrayList<String> {
        return errors
    }

    override fun enterMemberdeclarator(ctx: CPP14Parser.MemberdeclaratorContext?) {
        val node = ctx?.Identifier()
        val methodName = node?.text
        //if (methodName != null) errors.add(methodName)
//        errors.add(methodName)

        if (methodName != null) {
//            if (Character.isUpperCase(methodName[0])) {
//                val error = String.format("Method %s is upperCased!", methodName)
//                errors.add(error)
//            }
            if (Character.isLowerCase(methodName[0])) {
                val error = String.format("Method %s is lowerCased!", methodName)
                errors.add(error)
            }
        }
    }

}