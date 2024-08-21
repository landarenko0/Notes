package com.example.notes.util

enum class Month(val text: String) {
    JANUARY("Январь"),
    FEBRUARY("Февраль"),
    MARCH("Март"),
    APRIL("Апрель"),
    MAY("Май"),
    JUNE("Июнь"),
    JULY("Июль"),
    AUGUST("Август"),
    SEPTEMBER("Сентябрь"),
    OCTOBER("Октябрь"),
    NOVEMBER("Ноябрь"),
    DECEMBER("Декабрь");

    companion object {
        fun getMonthText(month: Int, shortName: Boolean = false): String {
            if (month !in 1..12) throw IllegalArgumentException("Incorrect month")

            return if (shortName) {
                Month.entries[month - 1].text.substring(0..2)
            } else {
                Month.entries[month - 1].text
            }
        }
    }
}