import java.util.Scanner
import kotlin.random.Random

fun main(args: Array<String>) {

    fun createBaseArh () {
        val creatArh: (Int, Int) -> Menu.Note = { i, k ->
            Menu.Note(
                name = "Заметка ${i + 1}.$k",
                text = "Текст заметки ${i + 1}$k",
                greatTipe = Menu.Arh.listArh[i]
            )
        }

        for (i in 0..Random.nextInt(1, 16)) {
            Menu.Arh.listArh.add(Menu.Arh(name = "Архив ${i + 1}"))
            for (k in 1..Random.nextInt(1, 120)) {
                Menu.Note.listNotes.add(creatArh(i, k))
            }
        }
    }

    println("Добро пожаловать в приложение 'Заметки'")
    println("По желанию можем добавить эмуляцию сохраняемой базы заметок.")
    println("Добавить эмуляцию? Для добавления введите любой знак. Для отказа просто нажмите 'Enter")
    val emulation : String = Scanner(System.`in`).nextLine()
    if (emulation != "") {
        createBaseArh()
        println("Эмуляция добавлена!")
    }

    println("Для навигации необходимо использовать клавиатуру.")
    println("1. Для выбора из списка используй нумерацию.")
    println("2. Для выхода используй следующие кнопки клавиатуры в русской раскладке: 'В', 'У'.")
    println("3. Для создания новых объектов следующие кнопки клавиатуры в русской раскладке: 'Н', 'Т'")
    println("При навигации с помощью букв, раскладка может быть как русская так и латинская, регистр любой.")
    println("")
    println("Давайте начинать! Нажимай 'Enter'!")
    Scanner(System.`in`).nextLine()

    println(ChangeScreen(Menu.Great).show())
}
