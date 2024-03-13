/*
 *  Program:    Deterministic FSA Automatons (+ Translators) Emulator
 *
 *  Made by:    Ransomware3301 (https://www.github.com/ransomware3301)
 *  Date:       29/02/2024
 */

// TODO: Fix beta translation function

/*
 *  Automata Mathematical Descriptions:
 *  - Finite State Automaton (FSA):     ⟨Q, I, δ, q0, F⟩
 *  - FSA (Recognizer):                 ⟨Q, I, δ, q0, F⟩
 *  - FSA (Translator):                 ⟨Q, I, δ, q0, F, O, β⟩
 *
 *  Legend:
 *  - Q = Set of all possible states, including the start state q0
 *  - I = Set of all possible input symbols (+ epsilon (no input is read))
 *  - q0 = The beginning state of the automaton (contained in Q)
 *  - δ (Delta) = Function that moves between configurations in the right conditions are met
 *  - O = Translation alphabet 
 *  - β (Beta) = Function that prints on the output tape the translation for a particular symbol
 */



typealias State = String

const val epsilon: Char = '&'                   // Equivalent of not reading from either memory or input
const val terminator: Char = '_'                // Used as a flag to terminate input acquisition
const val showOutputLog: Boolean = true         // Boolean toggle used to show a debug log of each step
const val numDeltaElemCols: Int = 4             // Parameter used only for display purposes (see below)



fun main() {
    var chosenAutomaton: Int = 0
    var isNotValidOption: Boolean
    val translationIsExpected: Boolean
    var temp: String

    // Beginning of input parameters acquisition
    do {
        showMenu()
        isNotValidOption = false

        try {
            chosenAutomaton = readln().toInt()
        }
        catch (e: NumberFormatException) {
            println("\n[ERROR] The input is not an integer")
            isNotValidOption = true
        }
    } while (isNotValidOption)

    
    when (chosenAutomaton) {
        1 -> {
            // FSA was selected
            println("[AUTOMATON TYPE] Finite State Automaton (FSA)")

            // Alphabet
            println("\n(1) Insert alphabet: ")
            val alphabet: Set<Char> = inputListOfChar(terminator).toSet()
            // val alphabet: Set<Char> = inputListOfType<Char>(terminator).toSet()
            if (showOutputLog) println("\n\t[alphabet] = $alphabet")

            // Q
            println("\n(2) Insert all states: ")
            // val Q: Set<State> = inputListOfString(terminator).toSet()
            val Q: Set<State> = inputListOfType<State>(terminator).toSet()
            if (showOutputLog) println("\n\t[Q] = $Q")

            // I
            print("\n(3) Insert input string: ")
            val I: String = readln()
            if (showOutputLog) println("\n\t[I] = $I")

            // δ
            println("\n(4) Insert delta function definition tuples:")
            println("(FORMAT: \'currState inputCharToRead finalState\')")
            val delta: Set<FSADeltaElement> = getSetOfDeltaElements<FSADeltaElement>(terminator)

            if (showOutputLog) {
                var counter: Int = 1

                print("\n\t[delta]: ")

                for (elem in delta) {
                    print("<${elem.q}, ${elem.i}, ${elem.f}>, ")

                    if (counter % numDeltaElemCols == 0) {
                        print("\n\t\t     ")
                    }
                    counter++
                }
                println()
            }

            // q0
            print("\n(5) Insert beginning state: ")
            var q0: State
            var foundInQ: Boolean

            do {
                foundInQ = true
                q0 = readln()

                if (q0.isEmpty()) {
                    println(" - [ERROR]: A start state must be selected")
                    foundInQ = false
                }
                if (q0 !in Q) {
                    print(" - [ERROR]: This state is not in Q" +
                            "\n(5) Insert beginning state: ")
                    foundInQ = false
                }
            } while (!foundInQ)
            if (showOutputLog) println("\n\t[q0] = $q0")

            // F
            println("\n(6) Insert all ending states (Remember: F is contained in Q):")
            var F: Set<State>

            do {
                F = inputListOfString(terminator).filter { it in Q }.toSet()
            } while (F.isEmpty())
            if (showOutputLog) println("\n\t[F] = $F")

            // Creating the FSA automaton
            println("\nCreating FSA automaton...\n")
            val automaton: FiniteStateAutomaton = FiniteStateAutomaton(Q, I, delta, q0, F)

            // Output to terminal the result
            println("\n\n[RESULTS]:" +
                    "\n- [FSA] Input: $I" +
                    "\n- [FSA] Verdict: ${automaton.traverseWithDelta()}"
            ) 
        }
        
        2 -> {
            // FSA Translator (FSA-T) was selected
            println("[AUTOMATON TYPE] Finite State Automaton Translator (FSA-T)")

            // Alphabet
            println("\n(1) Insert alphabet: ")
            val alphabet: Set<Char> = inputListOfChar(terminator).toSet()
            // val alphabet: Set<Char> = inputListOfType<Char>(terminator).toSet()
            if (showOutputLog) println("\n\t[alphabet] = $alphabet")

            // Q
            println("\n(2) Insert all states: ")
            // val Q: Set<State> = inputListOfString(terminator).toSet()
            val Q: Set<State> = inputListOfType<State>(terminator).toSet()
            if (showOutputLog) println("\n\t[Q] = $Q")

            // I
            print("\n(3) Insert input string: ")
            val I: String = readln()
            if (showOutputLog) println("\n\t[I] = $I")

            // δ
            println("\n(4) Insert delta function definition tuples:")
            println("(FORMAT: \'currState inputCharToRead finalState\')")
            val delta: Set<FSADeltaElement> = getSetOfDeltaElements<FSADeltaElement>(terminator)

            if (showOutputLog) {
                var counter: Int = 1

                print("\n\t[delta]: ")

                for (elem in delta) {
                    print("<${elem.q}, ${elem.i}, ${elem.f}>, ")

                    if (counter % numDeltaElemCols == 0) {
                        print("\n\t\t     ")
                    }
                    counter++
                }
                println()
            }

            // q0
            print("\n(5) Insert beginning state: ")
            var q0: State
            var foundInQ: Boolean

            do {
                foundInQ = true
                q0 = readln()

                if (q0.isEmpty()) {
                    println(" - [ERROR]: A start state must be selected")
                    foundInQ = false
                }
                if (q0 !in Q) {
                    print(" - [ERROR]: This state is not in Q" +
                            "\n(5) Insert beginning state: ")
                    foundInQ = false
                }
            } while (!foundInQ)
            if (showOutputLog) println("\n\t[q0] = $q0")

            // F
            println("\n(6) Insert all ending states (Remember: F is contained in Q):")
            var F: Set<State>

            do {
                F = inputListOfString(terminator).filter { it in Q }.toSet()
            } while (F.isEmpty())
            if (showOutputLog) println("\n\t[F] = $F")

            // O
            println("\n(7) Insert output alphabet: ")
            val O: Set<String> = inputListOfString(terminator).toSet()
            if (showOutputLog) println("\n\t[O] = $O")

            // Beta
            val separator: Char = '-'
            println("\n(8) Insert beta function definition tuples: ")
            println("(FORMAT: \'word${separator}translation\')")
            val beta: Map<String, String> = getTranslationMap(separator, terminator)

            // Creating the FSA automaton
            println("\nCreating FSA automaton...\n")
            val automaton: FiniteStateAutomatonTranslator = FiniteStateAutomatonTranslator(Q, I, delta, q0, F, O, beta)
            var strToTranslate: String = I

            // Output to terminal the result
            println("\n\n[RESULTS]:" +
                    "\n- [FSA] Input: $I" +
                    "\n- [FSA] Verdict: ${automaton.traverseWithDelta()}" +
                    "\n- [FSA] Output: ${automaton.betaTranslate(strToTranslate)}"
            )
        }
    }    
}



/* ==== (1) Automata Configurations ==== */
/*
 *  NOTE: A "configuration" can be thought as a "snapshot" of the
 *        automaton in a particular instance of time
 */

/*
 *  This class describes the configuration of an FSA, which is
 *  made of only the current state and the remaining input string
 */
open class FSAConfiguration(
    val q: State,
    val i: String
)

/*
 *  This class describes the configuration of a PDA-Translator, which is
 *  made of the current state, the remaining input string, the memory tape contents
 *  and the output string printed up to this moment
 */
open class FSATranslatorConfiguration(
    q: State,
    i: String,
    val output: String
) : FSAConfiguration(q, i)



/* ==== (2) Automata Delta Elements ==== */

/*
 *  This class describes the elements that define the delta
 *  function for FSAs, which are made so that delta is defined as:
 *
 *         FSA:     δ : Q x I --> F
 */
data class FSADeltaElement(
    var q: State,
    var i: Char,
    var f: State
)



/* ==== (3) Automata Templates ==== */

/*
 *  Generic implementation of the first basic Automaton, which
 *  is the Finite State Automaton (FSA) that has no memory tape
 *
 *         - FSA Definition:                    ⟨Q, I, δ, q0, F⟩
 *         - FSA Configurations:                ⟨q, i⟩
 *         - δ Definition (Char Variant):       δ : Q x I --> F
 *         - δ Definition (String Variant):     δ^* : Q x I^* --> F
 */
open class AutomatonWithoutMemory(
    private val Q: Set<State>,
    private val I: String,
    private val delta: Set<FSADeltaElement>,
    private val q0: State,
    private val F: Set<State>,
    private val O: Set<String>,
    private val beta: Map<String, String>
)
{
    /*
     *  FSA Delta Function Definition (String Variant):
     *           δ^* : Q x I^* --> F
     *
     *  Returns true if the given inputs lead the automaton
     *  to stop on a final state, false otherwise.
     */
    fun traverseWithDelta(): Boolean {
        var startState: String = q0
        var input: String = I
        val totDeltaMoves: Int = delta.size
        var checkedDeltaMoves: Int = 0

        if (showOutputLog) {
            println("\n[DEBUG LOGS ACTIVATED]\n")

            while (checkedDeltaMoves < totDeltaMoves) {
                deltaMove@ for ((currState, inputValue, finalState) in delta) {
                    // Output logs
                    println(
                        "[traverseWithDelta] Checking next delta element: <$currState, $inputValue, $finalState>" +
                      "\n                    Current configuration:  <$startState, $input>"
                    )
                    if (
                        currState in Q
                        && currState == startState
                        && input.isNotEmpty()
                        && (inputValue == input.first())
                        || (inputValue == epsilon)
                    ) {
                        // Output logs
                        println("\n\tChosen deltaMove: <$currState, $inputValue, $finalState>")
                        println("\n\tMove found in delta ! --> Next state: ${finalState}\n")
                        if (inputValue != epsilon) {
                            input = input.drop(1)
                        }

                        checkedDeltaMoves = 0
                        startState = finalState
                        break@deltaMove
                    }
                    checkedDeltaMoves++
                }
            }
        }
        else {
            while (checkedDeltaMoves < totDeltaMoves) {
                deltaMove@
                for ((currState, inputValue, finalState) in delta) {
                    if (
                        currState in Q
                        && currState == startState
                        && input.isNotEmpty()
                        && (inputValue == input.first())
                        || (inputValue == epsilon)
                    ) {
                        if (inputValue != epsilon) {
                            input = input.drop(1)
                        }

                        checkedDeltaMoves = 0
                        startState = finalState
                        break@deltaMove
                    }
                    checkedDeltaMoves++
                }
            }
        }

        return (startState in F && input.isEmpty())
    }

    fun betaTranslate(input: String): String {
        var output: String = ""

        for (c in input) {
            for ((word, translation) in beta) {
                if (
                    translation in O
                    && word == c.toString()
                    && translation != epsilon.toString()
                ) {
                    output += translation
                }
            }
        }

        return output
    }
}



/* ==== (4) Automata Collection ==== */

// Finite State Automaton inherited from the AutomatonWithoutMemory class
class FiniteStateAutomaton(
    Q: Set<State>,
    I: String,
    delta: Set<FSADeltaElement>,
    q0: State,
    F: Set<State>
) : AutomatonWithoutMemory(Q, I, delta, q0, F, emptySet(), emptyMap())

// Finite State Automaton inherited from the AutomatonWithoutMemory class
// (+) Translation capability
class FiniteStateAutomatonTranslator(
    Q: Set<State>,
    I: String,
    delta: Set<FSADeltaElement>,
    q0: State,
    F: Set<State>,
    O: Set<String>,
    beta: Map<String, String>
) : AutomatonWithoutMemory(Q, I, delta, q0, F, O, beta)



/* ==== (5) Generic Functions ==== */

// Prints the menu of available automata
fun showMenu() {
    println("\nChose an automaton to use:")
    println("\t1 - Finite State Automaton (FSA)")
    println("\t2 - FSA Translator (FSA-T)")
    println("\t[0 - EXIT]")
    print("\n=> ")
}

// Prompts the user to insert a sequence of characters, which
// then get converted into a List<Char>
fun inputListOfChar(terminator: Char): List<Char> {
    var output: List<Char> = emptyList()
    var temp: String = ""
    var count: Int = 1

    while (temp != terminator.toString()) {
        if (temp.isNotEmpty()) {
            output = output.plus(temp.toList())
        }

        do {
            print(" - Insert element #$count (ENTER to submit, \'$terminator\' to stop): ")
            temp = readln()
            if (temp.length > 1 || temp.isEmpty()) {
                println("   [ERROR]: Insert a character next time")
            }
        } while (temp.length > 1 || temp.isEmpty())

        count++
    }

    return output
}

// Prompts the user to insert a sequence of strings, which
// then get converted into a List<String>
fun inputListOfString(terminator: Char): List<String> {
    var output: List<String> = emptyList()
    var temp: String = ""
    var count: Int = 1

    do {
        if (temp.isNotEmpty()) {
            output = output.plus(temp)
        }

        loop@do {
            print(" - Insert element #$count (ENTER to submit, \'$terminator\' to stop): ")
            temp = readln()
            if (temp.isEmpty()) {
                println("   [ERROR]: Insert a non-empty string next time")
                continue@loop
            }
        } while (temp.isEmpty())

        count++
    } while (temp != terminator.toString())

    return output
}

// Given a string, it splits it at each instance of separator into a List<Char>
fun getListOfCharFromString(str: String, separator: Char): List<Char> {
    return str.filter { it != separator }.toList()
}

// Given a string, it splits it at each instance of separator into a List<String>
fun getListOfStringFromString(str: String, separator: Char): List<String> {
    var output: List<String> = emptyList()
    var temp: String = ""
    val input: String = str + separator

    for (c in input) {
        if (c != separator) {
            temp = temp.plus(c)
        }
        else {
            output = output.plus(temp)
            temp = ""
        }
    }

    return output
}

// Prompts the user to insert a FSAeltaElement and then
// returns a set containing those deltaElements
fun getSetOfFSADeltaElements(terminator: Char): Set<FSADeltaElement> {
    var output: Set<FSADeltaElement> = emptySet()
    var temp: String = ""
    var tempList: List<String> = emptyList()
    var count: Int = 1
    val separator: Char = ' '

    while (temp != terminator.toString()) {
        while (temp.isEmpty()) {
            print(" - Insert FSA delta element #$count (ENTER to submit, \'$terminator\' to stop): ")
            temp = readln()

            if (temp.isEmpty()) {
                println("   [ERROR]: Insert a non-empty string next time")
            }
            else {
                if (temp != terminator.toString()) {
                    tempList = getListOfStringFromString(temp, separator)

                    if (tempList.size == 3) {
                        if (tempList[1].length > 1) {
                            println("   [ERROR]: inputCharToRead needs to be a character, not a string")
                            temp = ""
                        }
                    }
                    else {
                        println("   [ERROR]: Too few arguments for a FSADeltaElement")
                        temp = ""
                    }
                }
            }
        }

        if (tempList.size == 3) {
            output = output.plus(
                FSADeltaElement(
                    tempList[0],
                    tempList[1][0],
                    tempList[2]
                )
            )
            temp = ""
            tempList = emptyList()
            count++
        }
    }

    return output
}


// Prompts the user to insert a translation, which then gets added
// to a translation map that, when completed, is returned to be used by
// either translators or Turing Machines (TMs)
fun getTranslationMap(separator: Char, terminator: Char): Map<String, String> {
    var output: Map<String, String> = emptyMap()
    var input: String = ""
    var left: String = ""
    var right: String = ""
    var stop: Boolean = false
    var switch: Boolean

    while (!stop) {
        switch = false
        left = ""
        right = ""

        print(" - Insert translation (ENTER to submit, \'$terminator\' to stop): ")
        input = readln()

        loop@for (c in input) {
            if (c == terminator) {
                stop = true
                break@loop
            }
            else {
                if (c == separator) {
                    switch = true
                    continue@loop
                }
                if (switch) {
                    right += c
                }
                else {
                    left += c
                }
            }
        }

        if (!stop) {
            output = output.plus(left to right)
        }
    }

    return output
}
