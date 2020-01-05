//import CPP.CPP14Lexer
//import org.antlr.v4.runtime.CharStream
//import org.antlr.v4.runtime.CharStreams
//import org.antlr.v4.runtime.CommonTokenStream
//
//class ArduinoParser(charStream: CharStream) {
//
//    private val commonTokenStream: CommonTokenStream
//    private val cpp14Lexer: CPP14Lexer
//    private val cpp14Parser: CPP14Parser
//
//    /**
//     * コンストラクタ
//     */
//    init {
//        this.cpp14Lexer = CPP14Lexer(charStream)
//        this.commonTokenStream = CommonTokenStream(this.cpp14Lexer)
//        this.cpp14Parser = CPP14Parser(commonTokenStream)
//    }
//
//    /**
//     * setupとloopの関数を分割する．
//     *
//     */
//    fun separateToken() {
//
//    }
//
//    /**
//     * 関数名を判断する．
//     * 具体的には，setup()かloop()か判断する．
//     */
//    fun judgeFunction() {
//        val setupOrLoop =
//            cpp14Parser.translationunit().declarationseq().declarationseq().declaration().functiondefinition()
//                .declarator().ptrdeclarator().noptrdeclarator().noptrdeclarator().declaratorid().idexpression()
//                .unqualifiedid().children[0]
//
//        println(setupOrLoop)
//
//    }
//}