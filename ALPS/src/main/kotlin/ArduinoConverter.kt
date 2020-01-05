/**
 * Arduinoのコードからそれに応じた
 * ピン番号に変換するクラス
 */
class ArduinoConverter(arduinoPinStatus: ArduinoPinStatus) {
    private var arduinoPinStatus: ArduinoPinStatus // ArduinoPinStatusインスタンス
    private var variable: MutableMap<String, Variable> // 変数情報を持つ可変Map<String, Variable>

    init {
        this.arduinoPinStatus = arduinoPinStatus
        this.variable = mutableMapOf()
    }

    /**
     * arduinoPinStatusのゲッター
     */
    fun getArduinoPinStatus(): ArduinoPinStatus {
        return arduinoPinStatus
    }

    /**
     * ArduinoPinStatusのセッター
     */
    fun setArduinoPinStatus(arduinoPinStatus: ArduinoPinStatus) {
        this.arduinoPinStatus = arduinoPinStatus
    }

    /**
     * variableのセッター
     */
    private fun setVariable(variable: MutableMap<String, Variable>) {
        this.variable = variable
    }

    /**
     * pinModeやdigitalWriteのところで直接ピン番号を指定しているのか，
     * 変数で指定しているかを判断
     * @param judge 変数かピン番号
     * @return ピン番号
     */
    private fun judgeVariable(judge: String): Int {
        val regex = Regex("""^[0-9]""") // 正規表現を表す文字列．先頭が0〜9で始まる意味
        // 始まりが0〜9ならInt変換して返す．違うなら変数管理クラスVariableから変数名に対応した値を返す．
        return if (regex.containsMatchIn(judge)) judge.toInt() else variable[judge]?.value?.toInt() ?: -1
    }

    /**
     * @param pinStatus HIGH,LOW,INPUT.OUTPUT
     * @param variable 変数の状態を表す可変Map<String, Variable>
     * @param judge ピン番号
     */
    fun changePinStatus(pinStatus: String, variable: MutableMap<String, Variable>, judge: String) {
        this.setVariable(variable)
        val pinNumber: Int = this.judgeVariable(judge)
        when (pinStatus) {
            "OUTPUT" -> this.convertPinStatusOutPutInput(pinNumber = pinNumber, isInput = false)
            "INPUT" -> this.convertPinStatusOutPutInput(pinNumber = pinNumber, isInput = true)
            "HIGH" -> this.convertPinStatusHighLow(pinNumber = pinNumber, isLow = false)
            "LOW" -> this.convertPinStatusHighLow(pinNumber = pinNumber, isLow = true)
            else -> println("OUTPUTかINPUTしか受け取りませんょ．")
        }
    }

    /**
     * ピン番号に応じて変換
     * INPUTかOUTPUTかを判断
     */
    private fun convertPinStatusOutPutInput(pinNumber: Int, isInput: Boolean) {
        when (pinNumber) {
            // ここからデジタルピン
            0 -> this.arduinoPinStatus.digitalPin00.isInput = isInput
            1 -> this.arduinoPinStatus.digitalPin01.isInput = isInput
            2 -> this.arduinoPinStatus.digitalPin02.isInput = isInput
            3 -> this.arduinoPinStatus.digitalPin03.isInput = isInput
            4 -> this.arduinoPinStatus.digitalPin04.isInput = isInput
            5 -> this.arduinoPinStatus.digitalPin05.isInput = isInput
            6 -> this.arduinoPinStatus.digitalPin06.isInput = isInput
            7 -> this.arduinoPinStatus.digitalPin07.isInput = isInput
            8 -> this.arduinoPinStatus.digitalPin08.isInput = isInput
            9 -> this.arduinoPinStatus.digitalPin09.isInput = isInput
            10 -> this.arduinoPinStatus.digitalPin10.isInput = isInput
            11 -> this.arduinoPinStatus.digitalPin11.isInput = isInput
            12 -> this.arduinoPinStatus.digitalPin12.isInput = isInput
            13 -> this.arduinoPinStatus.digitalPin13.isInput = isInput
            // ここからアナログピン
            14 -> this.arduinoPinStatus.analogPin00.isInput = isInput
            15 -> this.arduinoPinStatus.analogPin01.isInput = isInput
            16 -> this.arduinoPinStatus.analogPin02.isInput = isInput
            17 -> this.arduinoPinStatus.analogPin03.isInput = isInput
            18 -> this.arduinoPinStatus.analogPin04.isInput = isInput
            19 -> this.arduinoPinStatus.analogPin05.isInput = isInput
            else -> println("アナログ，デジタルしか対応してません．")
        }
    }

    /**
     * ピン番号に応じて変換
     * LOWかHIGHかを判断
     */
    private fun convertPinStatusHighLow(pinNumber: Int, isLow: Boolean) {
        when (pinNumber) {
            // ここからデジタルピン
            0 -> {
                this.arduinoPinStatus.digitalPin00.isLow = isLow
                this.arduinoPinStatus.digitalPin00.voltValue = 5.0
            }
            1 -> {
                this.arduinoPinStatus.digitalPin01.isLow = isLow
                this.arduinoPinStatus.digitalPin01.voltValue = 5.0
            }
            2 -> {
                this.arduinoPinStatus.digitalPin02.isLow = isLow
                this.arduinoPinStatus.digitalPin02.voltValue = 5.0
            }
            3 -> {
                this.arduinoPinStatus.digitalPin03.isLow = isLow
                this.arduinoPinStatus.digitalPin03.voltValue = 5.0
            }
            4 -> {
                this.arduinoPinStatus.digitalPin04.isLow = isLow
                this.arduinoPinStatus.digitalPin04.voltValue = 5.0
            }
            5 -> {
                this.arduinoPinStatus.digitalPin05.isLow = isLow
                this.arduinoPinStatus.digitalPin05.voltValue = 5.0
            }
            6 -> {
                this.arduinoPinStatus.digitalPin06.isLow = isLow
                this.arduinoPinStatus.digitalPin06.voltValue = 5.0
            }
            7 -> {
                this.arduinoPinStatus.digitalPin07.isLow = isLow
                this.arduinoPinStatus.digitalPin07.voltValue = 5.0
            }
            8 -> {
                this.arduinoPinStatus.digitalPin08.isLow = isLow
                this.arduinoPinStatus.digitalPin08.voltValue = 5.0
            }
            9 -> {
                this.arduinoPinStatus.digitalPin09.isLow = isLow
                this.arduinoPinStatus.digitalPin09.voltValue = 5.0
            }
            10 -> {
                this.arduinoPinStatus.digitalPin10.isLow = isLow
                this.arduinoPinStatus.digitalPin10.voltValue = 5.0
            }
            11 -> {
                this.arduinoPinStatus.digitalPin11.isLow = isLow
                this.arduinoPinStatus.digitalPin11.voltValue = 5.0
            }
            12 -> {
                this.arduinoPinStatus.digitalPin12.isLow = isLow
                this.arduinoPinStatus.digitalPin12.voltValue = 5.0
            }
            13 -> {
                this.arduinoPinStatus.digitalPin13.isLow = isLow
                this.arduinoPinStatus.digitalPin13.voltValue = 5.0
            }
            // ここからアナログピン
            14 -> {
                this.arduinoPinStatus.analogPin00.isLow = isLow
                this.arduinoPinStatus.analogPin00.isLow = isLow
            }
            15 -> {
                this.arduinoPinStatus.analogPin01.isLow = isLow
                this.arduinoPinStatus.analogPin01.isLow = isLow
            }
            16 -> {
                this.arduinoPinStatus.analogPin02.isLow = isLow
                this.arduinoPinStatus.analogPin02.isLow = isLow
            }
            17 -> {
                this.arduinoPinStatus.analogPin03.isLow = isLow
                this.arduinoPinStatus.analogPin03.isLow = isLow
            }
            18 -> {
                this.arduinoPinStatus.analogPin04.isLow = isLow
                this.arduinoPinStatus.analogPin04.isLow = isLow
            }
            19 -> {
                this.arduinoPinStatus.analogPin05.isLow = isLow
                this.arduinoPinStatus.analogPin05.isLow = isLow
            }
            else -> println("アナログ，デジタルしか対応してません．")
        }
    }
}