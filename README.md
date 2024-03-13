# Automatons_Emulators
Kotlin programs that are able to emulate the following automatons:

- In file "FSAAutomatonsInformationTheory.kt"
  - Finite State Automaton (FSA) in Recognise Mode
  - Finite State Automaton Translator (FSA-T) in Recognise + Translate Mode

<br>

- In file "PDAAutomatonsInformationTheory.kt"
  - Push-Down Automaton (PDA) in Recognise Mode
  - Push-Down Automaton Translator (PDA-T) in Recognise + Translate Mode

- - -
# Theoretical Concepts and Notation

### Brief Introduction
An automaton is a mathematical representation of a machine that is programmed to execute a precise set of instructions.

Their applications are endless, but they are mainly used in contexts where performance is key, for example the program that checks
wether parentheses are correctly balanced (meaning that the number of open = number of closed parentheses) uses a PDA automaton (see below for definition).

Currently, the available automatons are the following:
- Finite State Automaton (FSA)
- Finite State Automaton Translator (FSA-T)
- Push-Down Automaton (PDA)
- Push-Down Automaton Translatro (PDA-T)

- - -

### Mathematical Notation:

The following notation is used to denote each mathematical object:
- **Automatons Mathematical Descriptions**:
```
  Finite State Automaton (FSA):        ⟨Q, I, δ, q0, F⟩
   -> FSA (Recognizer):                ⟨Q, I, δ, q0, F⟩
   -> FSA (Translator):                ⟨Q, I, δ, q0, F, O, β⟩

  Push-Down Automaton (PDA):           ⟨Q, I, Γ, δ, q0, Z0, F⟩
   -> PDA (Recognizer):                ⟨Q, I, Γ, δ, q0, Z0, F⟩
   -> PDA (Translator):                ⟨Q, I, Γ, δ, q0, Z0, F, O, β⟩
```

- **Symbol Notation Legend**:
  - $Q \longrightarrow$ Set of all possible states, including the start state q0
  - $I \longrightarrow$ Set of all possible input symbols (+ epsilon (no input is read))
  - $q_{0} \longrightarrow$ The beginning state of the automaton (contained in Q)
  - $\delta \longrightarrow$ Function that moves between configurations in the right conditions are met
  - $\Gamma \longrightarrow$ List containing the current values in the memory tape
  - $Z_{0} \longrightarrow$ Symbol found in memory tapes that indicates the beginning (or base) of that memory stack
  - $O \longrightarrow$ Translation alphabet
  - $\beta \longrightarrow$ = Function that prints on the output tape the translation for a particular symbol

- - -

# How to use:

If you want to emulate a FSA, FSA-T, PDA, or PDA-T automaton, simply 
download the relative Kotlin program, compile and run it.

After running it, the program will ask the user to choose the mode of operation, then provides 
detailed instructions of what informations are needed and how to insert them in the correct formats.

Lastly, the program will run the given data according to the specified rules and it'll return a <code>true</code> 
or <code>false</code> result, indicating wether the given input is recognised by the automaton.

In case the user chooses to use a translator automaton, then at the end there'll also be the translation of the given input string.

- - -

# Examples

### Example (1):
  - **Description**: FSA automaton that recognises binary strings that begin in '01'
  - **Parameters**:
 
```
  alphabet = ['0', '1']
  Q = [q0, q1, q2]
  delta = <q0, 0, q1>, <q1, 1, q2>, <q2, 1, q2>, <q2, 0, q2>
  q0 = [q0]
  F = [q2]
```
    

If we provide an input string, for example <code>010111</code>, then the automaton will print:
```
  [RESULTS]
  [FSA] Input: 010111
  [FSA] Verdict: true
```

<br>

### Example (2):
- **Description**: PDA automaton that recognizes strings belonging to the language: L = {a^n b^n : n \in \mathbb{N} }
- **Parameters**:
 
```
  alphabet = ['a', 'b']
  Q = [q0, q1, q2, q3]
  delta = <q0, 'a', |/|A, q1>, <q1, 'a', A/AA, q1>, <q1, 'b', A/&, q2>, <q2, 'b', A/&, q2>,
          <q2, &, |/&, q3>
  q0 = [q0]
  F = [q3]
```

If we provide an input string, for example <code>aaabbb</code>, then the automaton will print:
```
  [RESULTS]
  [PDA] Input: aaabbb
  [PDA] Verdict: true
```

- - -
