package com.miyazakiu.katlab.ALPS

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@ResponseBody
@RequestMapping("api/arduino")
class ArduinoController {

    @RequestMapping(value = ["hello"], method = [RequestMethod.GET])
    @ResponseBody
    fun hello_world(@RequestBody sourceCode: String): String {
        val json = """[{"aaa": ${sourceCode}}]"""
        return json
    }

//    @RequestMapping(value = ["/hello/{name}"], method = [RequestMethod.GET])
//    fun hello_name(@PathVariable name: String, model: Model): String {
//        model.addAttribute("name", name)
//        return "arduino" // Thymeleaf テンプレートファイル名
//    }

    @RequestMapping(value = ["parse"], method = [RequestMethod.POST])
    @ResponseBody
    fun ArduinoParse(@RequestBody sourceCode: String): String {
        val json = """[{"aaa": ${sourceCode}}]"""
        println(json)
        return json
    }

    @PostMapping
    fun arduinoParse(sourceCode: String): String{
    return ""
}

}