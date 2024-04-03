
## WC

ファイル内の行数、ワード数、バイト数、または文字数を数えます。

### フラグ

 - `-l`  行数を表示します
 - `-c`  バイト数を表示します
 - `-w`  単語数を表示します
 - `-m`  文字数を表示します
 - `-L`  最も長い行の長さを表示します

詳細情報は `--help` をご覧ください。

複数のファイル名をサポートしています。

### ビルド

Kotlinランタイムがインストールされていることを確認してください。詳細については、Kotlinコマンドラインドキュメントを参照してください。

Kotlinでプロジェクトをビルドします

```bash
  kotlinc wc.kt -include-runtime -d wc.jar
```

### 実行

プロジェクトを実行します

```bash
  java -jar wc.jar [options] [files]
```
例:

```bash
  java -jar wc.jar -cwl sample.txt sample2.txt
```



## コメント

#### 制限事項

 - ファイル名が「-」で始まる場合は処理できません
 - 「｜」パイプでの処理はサポートされていません 

### 工夫した点、アピールポイント

- Kotlinの強力なコレクション関数を広範に使用しています。すべての統計情報は簡単なワンライナーで派生しています。
- 引数の使用により、柔軟性が向上します。引数は順不同であり、ファイル名は他の引数の前後に置くことができます。
- エラープルーフ。存在しないファイル名を入力しても、プログラムは続行し、すべての有効なファイルに対して出力を提供します。
- 関数の読みやすさ。すべての関数名は理解しやすいものです。メイン関数の内容を読むと、すぐに意味が理解できるはずです（引数の処理→ファイルの読み取り→ファイルの解析→出力の表示）。
- プログラムは合理的に効率的です。要求された統計情報のみを計算します。

### 作者

- [@gert](https://github.com/gertcrossdream)

## WC

Word count application. Shows the amount of words and other statistics for one or multiple files

### Supported options

 - `-l`  Shows the amount of lines
 - `-c`  Shows the amount of bytes
 - `-w`  Shows the amount of words
 - `-m`  Shows the amount of characters
 - `-L`  Shows the length of the longest line

Use `--help` for more information

Supports multiple filenames

### Building

Make sure you have the kotlin runtime installed. For more information go to: https://kotlinlang.org/docs/command-line.html

Build the project with kotlin

```bash
  kotlinc wc.kt -include-runtime -d wc.jar
```

### Running

Run the project

```bash
  java -jar wc.jar [options] [files]
```
Example:

```bash
  java -jar wc.jar -cwl sample.txt sample2.txt
```



## Comments

#### Limitations:

 - Does not handle filenames starting with -
 - Does not support piping with |

### Points of note

- Extensive use of the powerful collection functions in kotlin. All of the statistics are derived with a simple one-liner.
- Use of arguments allows some flexibility. arguments don't need to be in order, filenames can come before or after the other arguments.
- Error proof. Even if the user types a filename that doesn't exist, the program will continue and give an output for all the valid files.
- Readability of the functions. All function names are understandable. Reading the contents of the main function should quickly make sense. (process arguments->read files->analyze files->display output)
- The program is reasonably efficient. It only calculates the statistics that are asked.

### Authors

- [@gert](https://github.com/gertcrossdream)
