package com.miyazakiu.katlab.ALPS

import com.miyazakiu.katlab.ALPS.parser.arduino.CPP14Lexer
import com.miyazakiu.katlab.ALPS.parser.arduino.CPP14Parser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * このクラスは，Arduino言語を解析した結果をいい感じにするクラスです．
 *
 */
@Controller
@ResponseBody
@RequestMapping("api/arduino")
class ArduinoController {

    /**
     * サーバが生きているかを返すだけのメソッド
     * <b>api/arduino/response</b>にアクセスしてね．
     * @param response 確認用のパラメータ
     * @return 引数で受取った値をjson形式にして返す
     */
    @RequestMapping(value = ["checkResponse/{response}"], method = [RequestMethod.GET])
    @ResponseBody
    fun checkResponse(@PathVariable response: String): String {
        val json = """[{"received": $response}]"""
        print(json)
        return json
    }

    /**
     * POST通信で受取ったソースコードをjson形式にして返すメソッド
     * <b>api/arduino/parse</b>のbodyのsourceCodeにPOSTしてアクセスしてね．
     * @param sourceCode 解析したいソースコード
     * @return ADVISで解析するためのjson
     */
    @RequestMapping(value = ["parse"], method = [RequestMethod.POST])
    @ResponseBody
    fun arduinoParse(@RequestBody sourceCode: String): String {
        val json = """[{"aaa": ${sourceCode}}]"""
        println(json)
        return json
    }

    /**
     * 遊び場のメソッド
     * @return とりあえずの適当な値
     */
    @RequestMapping(value = ["playground"])
    fun sampleParse(): String {
        val text = "int a = 0;"
        val lexer = CPP14Lexer(CharStreams.fromString(text))
        val tokens = CommonTokenStream(lexer)
        val parser = CPP14Parser(tokens)
        print(parser)
        return "やあ！"
    }

}