import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 変数の情報を持つ
data class Variable(
    var type: String,
    var value: String
)

data class Variable2(
    var type: String,
    var name: String,
    var value: String
)

/**
 * 各ピンの状態を表す
 */
data class ArduinoPinStatus(
    var digitalPin00: PinStatus = PinStatus(),
    var digitalPin01: PinStatus = PinStatus(),
    var digitalPin02: PinStatus = PinStatus(),
    var digitalPin03: PinStatus = PinStatus(),
    var digitalPin04: PinStatus = PinStatus(),
    var digitalPin05: PinStatus = PinStatus(),
    var digitalPin06: PinStatus = PinStatus(),
    var digitalPin07: PinStatus = PinStatus(),
    var digitalPin08: PinStatus = PinStatus(),
    var digitalPin09: PinStatus = PinStatus(),
    var digitalPin10: PinStatus = PinStatus(),
    var digitalPin11: PinStatus = PinStatus(),
    var digitalPin12: PinStatus = PinStatus(),
    var digitalPin13: PinStatus = PinStatus(),
    val GND0: PinStatus = PinStatus(isLow = true, isInput = true, voltValue = 0.0),
    var AREF: PinStatus = PinStatus(),
    var serialDataLine12C: PinStatus = PinStatus(),
    var serialClockLine12C: PinStatus = PinStatus(),
    var analogPin00: PinStatus = PinStatus(), // 14
    var analogPin01: PinStatus = PinStatus(), // 15
    var analogPin02: PinStatus = PinStatus(), // 16
    var analogPin03: PinStatus = PinStatus(), // 17
    var analogPin04: PinStatus = PinStatus(), // 18
    var analogPin05: PinStatus = PinStatus(), // 19
    var vin: PinStatus = PinStatus(isLow = false, isInput = false, voltValue = 10.0),
    val GND1: PinStatus = PinStatus(isLow = true, isInput = true, voltValue = 0.0),
    val GND2: PinStatus = PinStatus(isLow = true, isInput = true, voltValue = 0.0),
    var volt5_0v: PinStatus = PinStatus(isLow = false, isInput = false, voltValue = 5.0),
    var volt3_3v: PinStatus = PinStatus(isLow = false, isInput = false, voltValue = 3.0),
    var reset: PinStatus = PinStatus(),
    var IOREF: PinStatus = PinStatus()
)

/**
 * ピンの状態を表す
 * isLowがtrueだとLOW
 * voltValueが出力電圧値
 */
data class PinStatus(
    var isLow: Boolean = true,
    var isInput: Boolean = true,
    var voltValue: Double = 0.0
)

/**
 * 最終的に出力するjsonを表現するクラス
 */
data class OutputData(
    val apiInfo: ApiInfo,
    val parseData: ArduinoPinStatus = ArduinoPinStatus()
)

/**
 * apiの情報を扱うクラス
 * timestampはLocalDateを使おうと思ったけど，
 * プリミティブ型以外のはオリジナルでアダプターを作らないと面倒なので
 * 無理やりString型でやる．
 */
data class ApiInfo(
    val timestamp: String,
    val programingLanguage: String,
    val apiVer: Double
)