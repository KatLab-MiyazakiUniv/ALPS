package com.miyazakiu.katlab.ALPS

import ArduinoListener
import ArduinoPinStatus
import com.miyazakiu.katlab.ALPS.parser.CPP14Lexer
import com.miyazakiu.katlab.ALPS.parser.CPP14Parser
import com.squareup.moshi.Moshi
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.springframework.boot.runApplication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder
import java.net.URLEncoder


/**
 * このクラスは，Arduino言語を解析した結果をいい感じにするクラスです．
 *
 */
@Controller
@ResponseBody
@RequestMapping("api/v1/arduino")
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
//        val json = """[{"received": $response}]"""
        val json = "[{\"received\": $response}]"
        print(json)
        return json
    }

    @RequestMapping(value = ["checkResponse2/{response2}"], method = [RequestMethod.GET])
    @ResponseBody
    fun checkResponse2(@PathVariable response2: String): String {
        val json = "[{\"received\": void setup() { Serial.begin(9800); }}]"
        print(json)
        return json
    }

    /**
     * POST通信で受取ったソースコードで遊ぶメソッド
     * @param sourceCode 解析したいソースコード
     * @return ADVISで解析するためのjson
     */
    @RequestMapping(value = ["parse2"], method = [RequestMethod.POST])
    @ResponseBody
    fun arduinoParse2(@RequestBody sourceCode: String): String {
        // URLエンコードされるのでそれをデコードしてあげる
        val encodedString: String = URLDecoder.decode(sourceCode, "UTF-8")
        println("受取った文字列は，")
        println(encodedString)
        val cPP14Lexer = CPP14Lexer(CharStreams.fromString(encodedString))
        val commonTokenStream = CommonTokenStream(cPP14Lexer)
        val cpp14Parser = CPP14Parser(commonTokenStream)
        val parseTree = cpp14Parser.translationunit()
        val walker = ParseTreeWalker()
        val arduinoListener = ArduinoListener()
        walker.walk(arduinoListener, parseTree)
        // json出力のためのMoshiの設定
        val adapter = Moshi.Builder().build().adapter(ArduinoPinStatus::class.java)
        val json = adapter.indent("   ").toJson(arduinoListener.getArduinoPinStatus())
        println("done...")

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
        val json = "[{\"received\": $sourceCode}]"
        println(json)
        return json
    }

    /**
     * 遊び場のメソッド
     * @return とりあえずの適当な値
     */
    @RequestMapping(value = ["playground"])
    fun sampleParse(): String {
        val cppClassContent = "void setup() { Serial.begin(9800); }"
        println(cppClassContent)
//        val cPP14Lexer = CPP14Lexer(CharStreams.fromString(cppClassContent))
//        val commonTokenStream = CommonTokenStream(cPP14Lexer)
//        val cpp14Parser = CPP14Parser(commonTokenStream)
//        val parseTree = cpp14Parser.translationunit()
//        val walker = ParseTreeWalker()
//        val arduinoListener = ArduinoListener()
//        walker.walk(arduinoListener, parseTree)
//        // json出力のためのMoshiの設定
//        val adapter = Moshi.Builder().build().adapter(ArduinoPinStatus::class.java)
//        val json = adapter.indent("   ").toJson(arduinoListener.getArduinoPinStatus())
        return "やあ！"
    }

//    fun main(args: Array<String>) {
//        println()
//    }
}