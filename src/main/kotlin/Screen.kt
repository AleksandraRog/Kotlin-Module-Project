import java.util.Scanner

open class ChangeScreen(val tipe: Menu) {

    val str : String = "******************************"

    open val textTitle : String = """$str
        |Экран ${tipe.textTitle}
        |$str""".trimMargin()

    private var menuPointNew : String = " || Добавить новый архив (Н(н)/Y(y) Т(т)/N(n))"
    private var menuPointChoice : String = "Просмотр архива по номеру (1..999) || "

    private var dataList : ArrayList<Menu> = arrayListOf()

    init {
        when (tipe) {
            is Menu.Arh -> {
                menuPointNew = " || Добавить новую заметку (Н(н)/Y(y) Т(т)/N(n))"
                menuPointChoice = "Просмотр заметки по номеру (1..999) || "
                dataList.addAll(Menu.Note.listNotes.filter { element -> element.greatTipe == tipe})
            }
            is Menu.Note -> {
                menuPointNew = ""
                menuPointChoice = ""
            }
            is Menu.Great -> {
                dataList.addAll(Menu.Arh.listArh)
            }
        }
    }

    fun printTitle() {
        println(textTitle)
    }

    private fun printMenu(listSize: Int) {

        println("""$str
        |МЕНЮ
        |${menuPointChoice.replace("1..999", if (listSize > 0) "1..$listSize" else "..")}Выход (В(в)/D(d) У(у)/E(e))$menuPointNew
        |$str""".trimMargin())
    }

    private val validExitLetters = setOf("В", "в", "У", "у", "E", "e", "D", "d")
    private val validNewLetters = setOf("Н", "н", "Т", "т", "Y", "y", "N", "n")

    private fun request( listSize : Int) : Menu {

        val vote: String = Scanner(System.`in`).nextLine()

        fun mistake() : Menu {
            println("Вы ввели некорректное значение. Повторите ввод.")
            printMenu(listSize)
            return  request(listSize)
        }

        return when {
            vote.toIntOrNull() in 1..listSize -> {
                if (tipe is Menu.Note) mistake() else ChangeScreen(dataList[vote.toInt()-1]).show()
            }
            vote in validExitLetters -> {
                if (tipe is Menu.Great) tipe else ChangeScreen(tipe.greatTipe!!).show()
            }
            vote in validNewLetters -> {
                if (tipe is Menu.Note) mistake() else MakeScreen(tipe).show()
            }
            else -> {
                mistake()
            }
        }
    }

    open fun show() : Menu {

        printTitle()
        tipe.printContent()
        printMenu(dataList.size)
        return request(dataList.size)
    }
}

class  MakeScreen(tipe: Menu) : ChangeScreen(tipe){

    override val textTitle: String
        get() = """$str
            |Экран создания $partTextTitle просмотра сразу после создания
            |$str
        """.trimMargin()

    private val partTextTitle : String = when (tipe) {
        is Menu.Great -> "архива и его"
        is Menu.Arh -> "заметки и ее"
        is Menu.Note -> ""
    }

    override fun show(): Menu {

        super.printTitle()
        var text = ""
        var name : String
        if (tipe is Menu.Arh) {
            println("Вы создаете заметку в архиве: '${tipe.name}'")
        }
        do{
            println("Введи имя (имя не должно иметь пустое значение): ")
            name = Scanner(System.`in`).nextLine()
            val nameChecker = name
        } while(nameChecker.replace(" ","") == "")

        if (tipe is Menu.Arh) {
            do {
                println("Введи текст заметки (содержание заметки не должно быть пустым):")
                text = Scanner(System.`in`).nextLine()
                val textChecker = text
            } while (textChecker.replace(" ","") == "")

            println("""Заметка имя: '$name'
            |Содержание заметки: '$text'""".trimMargin())

        } else {
            println("""Архив имя:'$name'
            |содержание архива: '${ArrayList<Menu.Note>()}'""".trimMargin())
        }

        println("Нажми любую клавишу для сохраниения или напишите 'Нет' для отмены")

        val safe : String = Scanner(System.`in`).nextLine()
        if (safe.lowercase() != "нет") {
            tipe.addList(tipe.getPoint(name, text))
        }

        return ChangeScreen(tipe).show()
    }
}
