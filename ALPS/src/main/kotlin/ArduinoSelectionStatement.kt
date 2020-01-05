//import CPP.CPP14Parser
import org.antlr.v4.runtime.tree.ParseTree

/**
 * 式を評価するクラス
 * 現在対応しているif文は，単純なものばかりです．
 * &&や||の式を評価したいならこのクラスを使ってすべてがtrueかどうかを判断する
 * @author T.N.Revolution
 */
data class FormulaDouble(
    var leftSideExpression: Double,
    var rightSideExpression: Double
)

data class FormulaBoolean(
    var leftSideExpression: Boolean,
    var rightSideExpression: Boolean
)

class ArduinoSelectionStatement {
    // [^0]0以外
    // ^0先頭が0
    private val regex = Regex("""^[0-9]""") // 正規表現を表す文字列．先頭が0〜9で始まる意味
    private val booleanList = listOf("true", "false") // bool値が入っている固定長リスト
    private val inequalitySign = listOf("==", "!=", ">=", "<=", ">", "<") // 不等号が入っている

    init {

    }

    /**
     * if文の式を評価する
     * @param expression if文のカッコの中身のParseTree
     * @param variable 変数を管理する可変Map<String, Variable>
     * @return 式を評価した結果 trueだとその式は成立，falseだと不成立
     */
    fun judgeCondition(expression: ParseTree, variable: MutableMap<String, Variable>): Boolean {
        // 0番目は絶対に値が入っている
        val leftSideExpression: ParseTree = expression.getChild(0) // 左辺式
//        println("left1: ${leftSideExpression.text}")
        val relationalOperator: ParseTree
        val rightSideExpression: ParseTree
//        println("${expression.getChild(0).text} ${expression.getChild(1).text} ${expression.getChild(2).text}")
        // 配列の1番目がnullということは，ただのif(true)みたいな式だということ
        if (expression.getChild(1) == null) {
//            println("ZZZ")
            return this.equal(variable, leftSideExpression.text, "true", isBooleanLiteralContext = true)
        } else {
            relationalOperator = expression.getChild(1)
        }
        // 配列の2番目がnullということは，ただのif(!true)みたいな式だということ
        if (expression.getChild(2) == null) {
            // bool a = true;
            // if(!a)
            // みたいな式だから左辺の式に代入するのはrelationalOperator
            return this.notEqual(variable, leftSideExpression = relationalOperator.text, rightSideExpression = "false")
        } else {
            rightSideExpression = expression.getChild(2)
        }
//        println("不等号は，${relationalOperator.text}です．")
        when (relationalOperator.text) {
            "==" -> {
                println("評価する不等号は，「==」equal")
                // 左辺，または右辺が真偽値だったら
                return if (booleanList.contains(leftSideExpression.text) || booleanList.contains(rightSideExpression.text)) {
                    this.equal(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text,
                        isBooleanLiteralContext = true
                    )
                } else {
                    this.equal(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text
                    )
                }
            }
            ">" -> {
                println("評価する不等号は，「>」greaterThan")
                return this.greaterThan(variable, leftSideExpression.text, rightSideExpression.text)
            }
            "<" -> {
                println("評価する不等号は，「<」lessThan")
                return this.lessThan(variable, leftSideExpression.text, rightSideExpression.text)
            }
            ">=" -> {
                println("評価する不等号は，「>=」greaterThanOrEqual")
                return this.greaterThanOrEqual(variable, leftSideExpression.text, rightSideExpression.text)
            }
            "<=" -> {
                println("評価する不等号は，「<=」lessThanOrEqual")
                return this.lessThanOrEqual(variable, leftSideExpression.text, rightSideExpression.text)
            }
            "!=" -> {
                println("評価する不等号は，「!=」notEqual")
                // 左辺，または右辺が真偽値だったら
                if ((leftSideExpression is CPP14Parser.EqualityexpressionContext && rightSideExpression is
                            CPP14Parser.RelationalexpressionContext) || (leftSideExpression is CPP14Parser
                    .RelationalexpressionContext && rightSideExpression is CPP14Parser.EqualityexpressionContext)
                ) {
                    return this.notEqual(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text,
                        isBooleanLiteralContext = true
                    )
                } else {
                    return this.notEqual(
                        variable,
                        leftSideExpression.text,
                        rightSideExpression.text
                    )
                }
            }
            else -> throw Exception(
                "符号が入るべき箇所に${relationalOperator
                    .text}が入っています．原因は，このメソッドにParseTreeを渡すときに木を深く掘っていないからかもしれません．とりあえずgetChild()" +
                        "を使って中身を表示することをおすすめします．"
            )
        }
    }

    /**
     * a == bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @param isBooleanLiteralContext 真偽値を評価する式であるか，trueだとboolean型．デフォルト引数は，false
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun equal(
        variable: MutableMap<String, Variable>, leftSideExpression: String, rightSideExpression:
        String, isBooleanLiteralContext: Boolean = false
    ): Boolean {
        // 式が真偽値の場合
        return if (isBooleanLiteralContext) {
            val formulaBoolean = this.judgeVariableOrBoolean(variable, leftSideExpression, rightSideExpression)
//            println(formulaBoolean)
            formulaBoolean.leftSideExpression == formulaBoolean.rightSideExpression
        } else {
            val formulaDouble = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
//            println(formulaDouble)
            formulaDouble.leftSideExpression == formulaDouble.rightSideExpression
        }
    }

    /**
     * a > bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun greaterThan(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaDouble = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
        return formulaDouble.leftSideExpression > formulaDouble.rightSideExpression
    }

    /**
     * a < bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun lessThan(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaDouble = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
        return formulaDouble.leftSideExpression < formulaDouble.rightSideExpression
    }

    /**
     * a <= bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun lessThanOrEqual(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaDouble = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
        return formulaDouble.leftSideExpression <= formulaDouble.rightSideExpression
    }

    /**
     * a >= bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun greaterThanOrEqual(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): Boolean {
        val formulaDouble = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
//        println(formulaDouble)
        return formulaDouble.leftSideExpression >= formulaDouble.rightSideExpression
    }

    /**
     * a != bみたいな式を評価する
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     * @param isBooleanLiteralContext 真偽値を評価する式でであるか，trueだとboolean型．デフォルト引数は，false
     * @return trueだとその式は成立，falseだと不成立
     */
    private fun notEqual(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String,
        isBooleanLiteralContext: Boolean = false
    ): Boolean {
        return if (isBooleanLiteralContext) {
            val formulaBoolean = this.judgeVariableOrBoolean(variable, leftSideExpression, rightSideExpression)
            formulaBoolean.leftSideExpression != formulaBoolean.rightSideExpression
        } else {
            val formulaDouble = this.judgeVariableOrInt(variable, leftSideExpression, rightSideExpression)
            formulaDouble.leftSideExpression != formulaDouble.rightSideExpression
        }
    }

    /**
     * 変数名か数値型か判断して変数だった場合にDoubleとして加工後，返す
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     */
    private fun judgeVariableOrInt(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): FormulaDouble {
        val formulaDouble = FormulaDouble(0.0, 0.0)
        val isLeftSideExpressionInt = this.regex.containsMatchIn(leftSideExpression) // 先頭の始まりが０〜９だったらtrue
        val isRightSideExpressionInt = this.regex.containsMatchIn(rightSideExpression) // 先頭の始まりが０〜９だったらtrue
        // 左辺が数値だった場合
        if (isLeftSideExpressionInt) {
            formulaDouble.leftSideExpression = leftSideExpression.toDouble()
            // 左辺が変数の場合
        } else {
            formulaDouble.leftSideExpression = variable[leftSideExpression]?.value?.toDouble() ?: 0.0
        }
        // 右辺が数値だった場合
        if (isRightSideExpressionInt) {
            formulaDouble.rightSideExpression = rightSideExpression.toDouble()
            // 右辺が変数の場合
        } else {
            formulaDouble.rightSideExpression = variable[rightSideExpression]?.value?.toDouble() ?: 0.0
        }
        return formulaDouble
    }

    /**
     * 変数名かBoolean型か判断して変数だった場合にBooleanとして加工後，返す
     * @param variable 変数を管理する可変Map
     * @param leftSideExpression 左辺の式
     * @param rightSideExpression 右辺の式
     */
    private fun judgeVariableOrBoolean(
        variable: MutableMap<String, Variable>,
        leftSideExpression: String,
        rightSideExpression: String
    ): FormulaBoolean {
        val formulaBoolean = FormulaBoolean(leftSideExpression = false, rightSideExpression = false)
        // Boolean型かどうかを返すラムダ式
        val convertBoolean = { expression: String ->
            when (expression) {
                "true" -> true
                // それ以外はfalseと見なす
                else -> false
            }
        }
//        if (leftSideExpression == "true" || leftSideExpression == "false" || rightSideExpression == "true" ||
//            rightSideExpression == "false"
//        )
        // 左辺式が trueかfalseで右辺が変数式だったら
        if (booleanList.contains(leftSideExpression) && !booleanList.contains(rightSideExpression)
        ) {
            formulaBoolean.leftSideExpression = convertBoolean(leftSideExpression)
            formulaBoolean.rightSideExpression = variable[rightSideExpression]?.value?.toBoolean() ?: false
            return formulaBoolean
        }
        // 左辺式が変数で，右辺式がtrueかfalseのとき
        if (!booleanList.contains(leftSideExpression) && booleanList.contains(rightSideExpression)
        ) {
//            println(variable)
//            println("AAA: ${leftSideExpression}")
            formulaBoolean.leftSideExpression = variable[leftSideExpression]?.value?.toBoolean() ?: false
            formulaBoolean.rightSideExpression = convertBoolean(rightSideExpression)
//            println(formulaBoolean)
            return formulaBoolean
        }
        // ここまで来たら両辺ともtrueかfalseのみの式とういうこと
        formulaBoolean.leftSideExpression = variable[leftSideExpression]?.value?.toBoolean() ?: false
        formulaBoolean.rightSideExpression = variable[rightSideExpression]?.value?.toBoolean() ?: false
        return formulaBoolean
    }
}