package com.miyazakiu.katlab.ALPS

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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

}