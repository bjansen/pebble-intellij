// Generated from /Users/bastien/IdeaProjects/pebble-intellij/parser/grammar/com/github/bjansen/pebble/parser/PebbleLexer.g4 by ANTLR 4.6
package com.github.bjansen.pebble.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PebbleLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PRINT_OPEN=1, COMMENT=2, VERBATIM_TAG_OPEN=3, TAG_OPEN=4, CONTENT=5, VERBATIM_BODY=6, 
		VERBATIM_TAG_END=7, TAG_CLOSE=8, PRINT_CLOSE=9, NOT=10, NULL=11, NONE=12, 
		OP_ASSIGN=13, OP_TERNARY=14, OP_COLON=15, OP_PIPE=16, OP_CONCAT=17, OP_RANGE=18, 
		OP_MEMBER=19, LBRACE=20, RBRACE=21, LBRACKET=22, RBRACKET=23, LPAREN=24, 
		RPAREN=25, COMMA=26, OR=27, AND=28, IS=29, IN=30, CONTAINS=31, OP_PLUS=32, 
		OP_MINUS=33, OP_DIV=34, OP_MULT=35, OP_MOD=36, OP_EQ=37, EQUALS=38, OP_NEQ=39, 
		OP_LE=40, OP_LT=41, OP_GE=42, OP_GT=43, TRUE=44, FALSE=45, WITH=46, STRING=47, 
		SINGLE_QUOTED_STRING=48, NUMERIC=49, ID_NAME=50, WHITESPACE=51, ERRCHAR2=52;
	public static final int VERBATIM = 1;
	public static final int IN_TAG = 2;
	public static String[] modeNames = {
		"DEFAULT_MODE", "VERBATIM", "IN_TAG"
	};

	public static final String[] ruleNames = {
		"PRINT_OPEN", "COMMENT", "VERBATIM_TAG_OPEN", "TAG_OPEN", "CONTENT", "VERBATIM_BODY", 
		"VERBATIM_TAG_END", "TAG_CLOSE", "PRINT_CLOSE", "NOT", "NULL", "NONE", 
		"OP_ASSIGN", "OP_TERNARY", "OP_COLON", "OP_PIPE", "OP_CONCAT", "OP_RANGE", 
		"OP_MEMBER", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "LPAREN", "RPAREN", 
		"COMMA", "OR", "AND", "IS", "IN", "CONTAINS", "OP_PLUS", "OP_MINUS", "OP_DIV", 
		"OP_MULT", "OP_MOD", "OP_EQ", "EQUALS", "OP_NEQ", "OP_LE", "OP_LT", "OP_GE", 
		"OP_GT", "TRUE", "FALSE", "WITH", "STRING", "SINGLE_QUOTED_STRING", "NUMERIC", 
		"ID_NAME", "WHITESPACE", "ERRCHAR2"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "'not'", "'null'", 
		"'none'", "'='", "'?'", "':'", "'|'", "'~'", "'..'", "'.'", "'{'", "'}'", 
		"'['", "']'", "'('", "')'", "','", "'or'", "'and'", "'is'", "'in'", "'contains'", 
		"'+'", "'-'", "'/'", "'*'", "'%'", "'=='", "'equals'", "'!='", "'<='", 
		"'<'", "'>='", "'>'", "'true'", "'false'", "'with'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "PRINT_OPEN", "COMMENT", "VERBATIM_TAG_OPEN", "TAG_OPEN", "CONTENT", 
		"VERBATIM_BODY", "VERBATIM_TAG_END", "TAG_CLOSE", "PRINT_CLOSE", "NOT", 
		"NULL", "NONE", "OP_ASSIGN", "OP_TERNARY", "OP_COLON", "OP_PIPE", "OP_CONCAT", 
		"OP_RANGE", "OP_MEMBER", "LBRACE", "RBRACE", "LBRACKET", "RBRACKET", "LPAREN", 
		"RPAREN", "COMMA", "OR", "AND", "IS", "IN", "CONTAINS", "OP_PLUS", "OP_MINUS", 
		"OP_DIV", "OP_MULT", "OP_MOD", "OP_EQ", "EQUALS", "OP_NEQ", "OP_LE", "OP_LT", 
		"OP_GE", "OP_GT", "TRUE", "FALSE", "WITH", "STRING", "SINGLE_QUOTED_STRING", 
		"NUMERIC", "ID_NAME", "WHITESPACE", "ERRCHAR2"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}



	private java.util.Queue<Token> queue = new java.util.LinkedList<Token>();

	protected Token customNextToken() {
	    return super.nextToken();
	}

	@Override
	public Token nextToken() {

	    if (!queue.isEmpty()) {
	        return queue.poll();
	    }

	    Token next = customNextToken();

	    if (next.getType() != CONTENT) {
	        return next;
	    }

	    StringBuilder builder = new StringBuilder();
	    Token startToken = next;

	    while (next.getType() == CONTENT) {
	        builder.append(next.getText());
	        next = customNextToken();
	    }

	    // The `next` will _not_ be a CONTENT-token, store it in
	    // the queue to return the next time!
	    queue.offer(next);

	    CommonToken token = new CommonToken(startToken);
	    token.setStopIndex(startToken.getStartIndex() + builder.length() - 1);

	    return token;
	}

	@Override
	public void setInputStream(IntStream input) {
	    queue.clear();
	    super.setInputStream(input);
	}



	public PebbleLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PebbleLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\66\u0189\b\1\b\1"+
		"\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4"+
		"\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t"+
		"\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t"+
		"\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t"+
		"\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4"+
		"*\t*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63"+
		"\t\63\4\64\t\64\4\65\t\65\3\2\3\2\3\2\3\2\3\2\5\2s\n\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\7\3{\n\3\f\3\16\3~\13\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\7\4\u0087"+
		"\n\4\f\4\16\4\u008a\13\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4\u0096"+
		"\n\4\f\4\16\4\u0099\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\5\5\u00a5"+
		"\n\5\3\5\3\5\3\6\3\6\3\7\7\7\u00ac\n\7\f\7\16\7\u00af\13\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\3\b\3\b\7\b\u00b9\n\b\f\b\16\b\u00bc\13\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00cb\n\b\f\b\16\b\u00ce\13\b"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\t\u00d8\n\t\3\t\3\t\3\n\3\n\3\n\3\n"+
		"\3\n\5\n\u00e1\n\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36\3\36\3\36"+
		"\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3"+
		"%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3+\3"+
		"+\3+\3,\3,\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60\3\60\7"+
		"\60\u0159\n\60\f\60\16\60\u015c\13\60\3\60\5\60\u015f\n\60\3\61\3\61\7"+
		"\61\u0163\n\61\f\61\16\61\u0166\13\61\3\61\5\61\u0169\n\61\3\62\6\62\u016c"+
		"\n\62\r\62\16\62\u016d\3\62\3\62\6\62\u0172\n\62\r\62\16\62\u0173\5\62"+
		"\u0176\n\62\3\63\3\63\7\63\u017a\n\63\f\63\16\63\u017d\13\63\3\64\6\64"+
		"\u0180\n\64\r\64\16\64\u0181\3\64\3\64\3\65\3\65\3\65\3\65\4|\u00ad\2"+
		"\66\5\3\7\4\t\5\13\6\r\7\17\b\21\t\23\n\25\13\27\f\31\r\33\16\35\17\37"+
		"\20!\21#\22%\23\'\24)\25+\26-\27/\30\61\31\63\32\65\33\67\349\35;\36="+
		"\37? A!C\"E#G$I%K&M\'O(Q)S*U+W,Y-[.]/_\60a\61c\62e\63g\64i\65k\66\5\2"+
		"\3\4\t\4\2\13\13\"\"\3\2$$\3\2))\3\2\62;\4\2C\\c|\5\2\62;C\\c|\5\2\13"+
		"\f\17\17\"\"\u0199\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\3\17\3\2\2\2\3\21\3\2\2\2\4\23\3\2\2\2\4\25\3\2\2\2\4\27\3"+
		"\2\2\2\4\31\3\2\2\2\4\33\3\2\2\2\4\35\3\2\2\2\4\37\3\2\2\2\4!\3\2\2\2"+
		"\4#\3\2\2\2\4%\3\2\2\2\4\'\3\2\2\2\4)\3\2\2\2\4+\3\2\2\2\4-\3\2\2\2\4"+
		"/\3\2\2\2\4\61\3\2\2\2\4\63\3\2\2\2\4\65\3\2\2\2\4\67\3\2\2\2\49\3\2\2"+
		"\2\4;\3\2\2\2\4=\3\2\2\2\4?\3\2\2\2\4A\3\2\2\2\4C\3\2\2\2\4E\3\2\2\2\4"+
		"G\3\2\2\2\4I\3\2\2\2\4K\3\2\2\2\4M\3\2\2\2\4O\3\2\2\2\4Q\3\2\2\2\4S\3"+
		"\2\2\2\4U\3\2\2\2\4W\3\2\2\2\4Y\3\2\2\2\4[\3\2\2\2\4]\3\2\2\2\4_\3\2\2"+
		"\2\4a\3\2\2\2\4c\3\2\2\2\4e\3\2\2\2\4g\3\2\2\2\4i\3\2\2\2\4k\3\2\2\2\5"+
		"r\3\2\2\2\7v\3\2\2\2\t\u0082\3\2\2\2\13\u00a4\3\2\2\2\r\u00a8\3\2\2\2"+
		"\17\u00ad\3\2\2\2\21\u00b4\3\2\2\2\23\u00d7\3\2\2\2\25\u00e0\3\2\2\2\27"+
		"\u00e4\3\2\2\2\31\u00e8\3\2\2\2\33\u00ed\3\2\2\2\35\u00f2\3\2\2\2\37\u00f4"+
		"\3\2\2\2!\u00f6\3\2\2\2#\u00f8\3\2\2\2%\u00fa\3\2\2\2\'\u00fc\3\2\2\2"+
		")\u00ff\3\2\2\2+\u0101\3\2\2\2-\u0103\3\2\2\2/\u0105\3\2\2\2\61\u0107"+
		"\3\2\2\2\63\u0109\3\2\2\2\65\u010b\3\2\2\2\67\u010d\3\2\2\29\u010f\3\2"+
		"\2\2;\u0112\3\2\2\2=\u0116\3\2\2\2?\u0119\3\2\2\2A\u011c\3\2\2\2C\u0125"+
		"\3\2\2\2E\u0127\3\2\2\2G\u0129\3\2\2\2I\u012b\3\2\2\2K\u012d\3\2\2\2M"+
		"\u012f\3\2\2\2O\u0132\3\2\2\2Q\u0139\3\2\2\2S\u013c\3\2\2\2U\u013f\3\2"+
		"\2\2W\u0141\3\2\2\2Y\u0144\3\2\2\2[\u0146\3\2\2\2]\u014b\3\2\2\2_\u0151"+
		"\3\2\2\2a\u0156\3\2\2\2c\u0160\3\2\2\2e\u016b\3\2\2\2g\u0177\3\2\2\2i"+
		"\u017f\3\2\2\2k\u0185\3\2\2\2mn\7}\2\2no\7}\2\2os\7/\2\2pq\7}\2\2qs\7"+
		"}\2\2rm\3\2\2\2rp\3\2\2\2st\3\2\2\2tu\b\2\2\2u\6\3\2\2\2vw\7}\2\2wx\7"+
		"%\2\2x|\3\2\2\2y{\13\2\2\2zy\3\2\2\2{~\3\2\2\2|}\3\2\2\2|z\3\2\2\2}\177"+
		"\3\2\2\2~|\3\2\2\2\177\u0080\7%\2\2\u0080\u0081\7\177\2\2\u0081\b\3\2"+
		"\2\2\u0082\u0083\7}\2\2\u0083\u0084\7\'\2\2\u0084\u0088\3\2\2\2\u0085"+
		"\u0087\t\2\2\2\u0086\u0085\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2"+
		"\2\2\u0088\u0089\3\2\2\2\u0089\u008b\3\2\2\2\u008a\u0088\3\2\2\2\u008b"+
		"\u008c\7x\2\2\u008c\u008d\7g\2\2\u008d\u008e\7t\2\2\u008e\u008f\7d\2\2"+
		"\u008f\u0090\7c\2\2\u0090\u0091\7v\2\2\u0091\u0092\7k\2\2\u0092\u0093"+
		"\7o\2\2\u0093\u0097\3\2\2\2\u0094\u0096\t\2\2\2\u0095\u0094\3\2\2\2\u0096"+
		"\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u009a\3\2"+
		"\2\2\u0099\u0097\3\2\2\2\u009a\u009b\7\'\2\2\u009b\u009c\7\177\2\2\u009c"+
		"\u009d\3\2\2\2\u009d\u009e\b\4\3\2\u009e\n\3\2\2\2\u009f\u00a0\7}\2\2"+
		"\u00a0\u00a1\7\'\2\2\u00a1\u00a5\7/\2\2\u00a2\u00a3\7}\2\2\u00a3\u00a5"+
		"\7\'\2\2\u00a4\u009f\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6"+
		"\u00a7\b\5\2\2\u00a7\f\3\2\2\2\u00a8\u00a9\13\2\2\2\u00a9\16\3\2\2\2\u00aa"+
		"\u00ac\13\2\2\2\u00ab\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ae\3"+
		"\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0"+
		"\u00b1\5\21\b\2\u00b1\u00b2\3\2\2\2\u00b2\u00b3\b\7\4\2\u00b3\20\3\2\2"+
		"\2\u00b4\u00b5\7}\2\2\u00b5\u00b6\7\'\2\2\u00b6\u00ba\3\2\2\2\u00b7\u00b9"+
		"\t\2\2\2\u00b8\u00b7\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba"+
		"\u00bb\3\2\2\2\u00bb\u00bd\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\u00be\7g"+
		"\2\2\u00be\u00bf\7p\2\2\u00bf\u00c0\7f\2\2\u00c0\u00c1\7x\2\2\u00c1\u00c2"+
		"\7g\2\2\u00c2\u00c3\7t\2\2\u00c3\u00c4\7d\2\2\u00c4\u00c5\7c\2\2\u00c5"+
		"\u00c6\7v\2\2\u00c6\u00c7\7k\2\2\u00c7\u00c8\7o\2\2\u00c8\u00cc\3\2\2"+
		"\2\u00c9\u00cb\t\2\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca"+
		"\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00cf\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf"+
		"\u00d0\7\'\2\2\u00d0\u00d1\7\177\2\2\u00d1\22\3\2\2\2\u00d2\u00d3\7/\2"+
		"\2\u00d3\u00d4\7\'\2\2\u00d4\u00d8\7\177\2\2\u00d5\u00d6\7\'\2\2\u00d6"+
		"\u00d8\7\177\2\2\u00d7\u00d2\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d8\u00d9\3"+
		"\2\2\2\u00d9\u00da\b\t\4\2\u00da\24\3\2\2\2\u00db\u00dc\7/\2\2\u00dc\u00dd"+
		"\7\177\2\2\u00dd\u00e1\7\177\2\2\u00de\u00df\7\177\2\2\u00df\u00e1\7\177"+
		"\2\2\u00e0\u00db\3\2\2\2\u00e0\u00de\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2"+
		"\u00e3\b\n\4\2\u00e3\26\3\2\2\2\u00e4\u00e5\7p\2\2\u00e5\u00e6\7q\2\2"+
		"\u00e6\u00e7\7v\2\2\u00e7\30\3\2\2\2\u00e8\u00e9\7p\2\2\u00e9\u00ea\7"+
		"w\2\2\u00ea\u00eb\7n\2\2\u00eb\u00ec\7n\2\2\u00ec\32\3\2\2\2\u00ed\u00ee"+
		"\7p\2\2\u00ee\u00ef\7q\2\2\u00ef\u00f0\7p\2\2\u00f0\u00f1\7g\2\2\u00f1"+
		"\34\3\2\2\2\u00f2\u00f3\7?\2\2\u00f3\36\3\2\2\2\u00f4\u00f5\7A\2\2\u00f5"+
		" \3\2\2\2\u00f6\u00f7\7<\2\2\u00f7\"\3\2\2\2\u00f8\u00f9\7~\2\2\u00f9"+
		"$\3\2\2\2\u00fa\u00fb\7\u0080\2\2\u00fb&\3\2\2\2\u00fc\u00fd\7\60\2\2"+
		"\u00fd\u00fe\7\60\2\2\u00fe(\3\2\2\2\u00ff\u0100\7\60\2\2\u0100*\3\2\2"+
		"\2\u0101\u0102\7}\2\2\u0102,\3\2\2\2\u0103\u0104\7\177\2\2\u0104.\3\2"+
		"\2\2\u0105\u0106\7]\2\2\u0106\60\3\2\2\2\u0107\u0108\7_\2\2\u0108\62\3"+
		"\2\2\2\u0109\u010a\7*\2\2\u010a\64\3\2\2\2\u010b\u010c\7+\2\2\u010c\66"+
		"\3\2\2\2\u010d\u010e\7.\2\2\u010e8\3\2\2\2\u010f\u0110\7q\2\2\u0110\u0111"+
		"\7t\2\2\u0111:\3\2\2\2\u0112\u0113\7c\2\2\u0113\u0114\7p\2\2\u0114\u0115"+
		"\7f\2\2\u0115<\3\2\2\2\u0116\u0117\7k\2\2\u0117\u0118\7u\2\2\u0118>\3"+
		"\2\2\2\u0119\u011a\7k\2\2\u011a\u011b\7p\2\2\u011b@\3\2\2\2\u011c\u011d"+
		"\7e\2\2\u011d\u011e\7q\2\2\u011e\u011f\7p\2\2\u011f\u0120\7v\2\2\u0120"+
		"\u0121\7c\2\2\u0121\u0122\7k\2\2\u0122\u0123\7p\2\2\u0123\u0124\7u\2\2"+
		"\u0124B\3\2\2\2\u0125\u0126\7-\2\2\u0126D\3\2\2\2\u0127\u0128\7/\2\2\u0128"+
		"F\3\2\2\2\u0129\u012a\7\61\2\2\u012aH\3\2\2\2\u012b\u012c\7,\2\2\u012c"+
		"J\3\2\2\2\u012d\u012e\7\'\2\2\u012eL\3\2\2\2\u012f\u0130\7?\2\2\u0130"+
		"\u0131\7?\2\2\u0131N\3\2\2\2\u0132\u0133\7g\2\2\u0133\u0134\7s\2\2\u0134"+
		"\u0135\7w\2\2\u0135\u0136\7c\2\2\u0136\u0137\7n\2\2\u0137\u0138\7u\2\2"+
		"\u0138P\3\2\2\2\u0139\u013a\7#\2\2\u013a\u013b\7?\2\2\u013bR\3\2\2\2\u013c"+
		"\u013d\7>\2\2\u013d\u013e\7?\2\2\u013eT\3\2\2\2\u013f\u0140\7>\2\2\u0140"+
		"V\3\2\2\2\u0141\u0142\7@\2\2\u0142\u0143\7?\2\2\u0143X\3\2\2\2\u0144\u0145"+
		"\7@\2\2\u0145Z\3\2\2\2\u0146\u0147\7v\2\2\u0147\u0148\7t\2\2\u0148\u0149"+
		"\7w\2\2\u0149\u014a\7g\2\2\u014a\\\3\2\2\2\u014b\u014c\7h\2\2\u014c\u014d"+
		"\7c\2\2\u014d\u014e\7n\2\2\u014e\u014f\7u\2\2\u014f\u0150\7g\2\2\u0150"+
		"^\3\2\2\2\u0151\u0152\7y\2\2\u0152\u0153\7k\2\2\u0153\u0154\7v\2\2\u0154"+
		"\u0155\7j\2\2\u0155`\3\2\2\2\u0156\u015a\7$\2\2\u0157\u0159\n\3\2\2\u0158"+
		"\u0157\3\2\2\2\u0159\u015c\3\2\2\2\u015a\u0158\3\2\2\2\u015a\u015b\3\2"+
		"\2\2\u015b\u015e\3\2\2\2\u015c\u015a\3\2\2\2\u015d\u015f\7$\2\2\u015e"+
		"\u015d\3\2\2\2\u015e\u015f\3\2\2\2\u015fb\3\2\2\2\u0160\u0164\7)\2\2\u0161"+
		"\u0163\n\4\2\2\u0162\u0161\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162\3\2"+
		"\2\2\u0164\u0165\3\2\2\2\u0165\u0168\3\2\2\2\u0166\u0164\3\2\2\2\u0167"+
		"\u0169\7)\2\2\u0168\u0167\3\2\2\2\u0168\u0169\3\2\2\2\u0169d\3\2\2\2\u016a"+
		"\u016c\t\5\2\2\u016b\u016a\3\2\2\2\u016c\u016d\3\2\2\2\u016d\u016b\3\2"+
		"\2\2\u016d\u016e\3\2\2\2\u016e\u0175\3\2\2\2\u016f\u0171\13\2\2\2\u0170"+
		"\u0172\t\5\2\2\u0171\u0170\3\2\2\2\u0172\u0173\3\2\2\2\u0173\u0171\3\2"+
		"\2\2\u0173\u0174\3\2\2\2\u0174\u0176\3\2\2\2\u0175\u016f\3\2\2\2\u0175"+
		"\u0176\3\2\2\2\u0176f\3\2\2\2\u0177\u017b\t\6\2\2\u0178\u017a\t\7\2\2"+
		"\u0179\u0178\3\2\2\2\u017a\u017d\3\2\2\2\u017b\u0179\3\2\2\2\u017b\u017c"+
		"\3\2\2\2\u017ch\3\2\2\2\u017d\u017b\3\2\2\2\u017e\u0180\t\b\2\2\u017f"+
		"\u017e\3\2\2\2\u0180\u0181\3\2\2\2\u0181\u017f\3\2\2\2\u0181\u0182\3\2"+
		"\2\2\u0182\u0183\3\2\2\2\u0183\u0184\b\64\5\2\u0184j\3\2\2\2\u0185\u0186"+
		"\13\2\2\2\u0186\u0187\3\2\2\2\u0187\u0188\b\65\5\2\u0188l\3\2\2\2\30\2"+
		"\3\4r|\u0088\u0097\u00a4\u00ad\u00ba\u00cc\u00d7\u00e0\u015a\u015e\u0164"+
		"\u0168\u016d\u0173\u0175\u017b\u0181\6\7\4\2\7\3\2\6\2\2\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}