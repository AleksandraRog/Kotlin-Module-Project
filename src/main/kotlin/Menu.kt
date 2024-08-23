sealed interface Menu {

    val greatTipe : Menu?
    val textTitle : String

    fun getPoint(name: String, content: String) : Menu

    fun addList(point: Menu)

    fun printContent()

    data class Arh(val name: String, override val greatTipe: Great = Great) : Menu {


        override fun getPoint(name: String, content: String) : Note {
            return Note(name, content, this)
        }

        override val textTitle: String = "архива (выбор заметки)"

        companion object {
            var listArh : ArrayList<Arh> = arrayListOf()
        }

        override fun addList(point : Menu) {
            if (point is Note) {
                Note.listNotes.add(point)
            }
        }

        override fun printContent() {
            println("Вы в архиве '${this.name}'")
            println("Содержание архива:")
            println("------------------")
            Note.listNotes.filter { element -> element.greatTipe == this}.forEachIndexed { index, note ->
                println(
                    """|| ${index + 1} | Заметка: '$note' 
            ||------------------""".trimMargin()
                )
            }
        }

    }

    data class Note (val name : String, val text : String, override val greatTipe: Arh) : Menu {

        override val textTitle = "заметки"

        override fun toString(): String {
            return name
        }

        override fun getPoint(name: String, content: String): Menu {
            return Great
        }

        override fun addList(point: Menu) {
        }

        override fun printContent() {
            println("Вы в архиве '${this.greatTipe.name}'")
            println("Вы в заметке '$this'")
            println("Содержание заметки:")
            println("------------------")
            println(this.text)
            println("------------------")
        }

        companion object {
            var listNotes : ArrayList<Note> = arrayListOf()
        }
    }

    object Great : Menu{

        override fun toString(): String {
            return "Выход из программы. До свидания, до новых встреч! "
        }

        override val textTitle: String = "программы (выбор архива)"

        override val greatTipe: Menu? = null

        override fun getPoint(name: String, content: String): Menu {
            return Arh(name)
        }

        override fun addList(point : Menu) {
            if (point is Arh) {Arh.listArh.add(point)}
        }

        override fun printContent() {
            println("Перечень архивов:")
            println("------------------")
            Arh.listArh.forEachIndexed { index, arh ->
                println(
                    """|| ${index + 1} | Архив: '${arh.name}' 
            ||------------------""".trimMargin()
                )
            }
        }
    }

}