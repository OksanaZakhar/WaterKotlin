package com.ksusha.vel.waterkotlin.data

import com.ksusha.vel.waterkotlin.ui.model.TopRecycler
import com.ksusha.vel.waterkotlin.ui.model.Water

class DataForFragment() {

    val topRecyclerList: List<TopRecycler> = object : ArrayList<TopRecycler>() {
        init {
            add(
                TopRecycler(
                    1,
                    "bottom_sheet_1",
                    "Знижка при замовленні від двох бутлів",
                    "При замовленні від двох бутлів води – вартість одного бутля всього 50грн!!!"
                )
            )
            add(
                TopRecycler(
                    2,
                    "bottom_sheet_2",
                    "19л у подарунок!",
                    "Замовите три бутля і отримаєте 19л у подарунок!* Для нових клієнтів: при одноразовому замовленні 3-х і більше бутлів, ми даруємо вам один бутель води! (Його вартість умовно вважається 0,01грн)."
                )
            )
            add(
                TopRecycler(
                    3,
                    "bottom_sheet_3",
                    "Даруємо помпу для води",
                    "Оформивши замовлення відразу на 5 бутлів, новий клієнт отримує в подарунок від компанії механічну помпу для води ()умовно за 0,01 грн."
                )
            )
            add(
                TopRecycler(
                    4,
                    "bottom_sheet_4",
                    "Продаж, оренда та чищення кулерів",
                    "Продаж, оренда та чищення кулерів максимально швидко та вигідно"
                )
            )
            add(
                TopRecycler(
                    5,
                    "bottom_sheet_5",
                    "Кулер безкоштовно!",
                    "Замовляйте 10 бутлів щотижня і користуйтеся кулером безкоштовно!* В заставу ви залишаєте всього 50% вартості обладнання. Вся сума буде повернута вам після закінчення терміну договору за умови, що кулер знаходиться у робочому стані."
                )
            )
            add(
                TopRecycler(
                    6,
                    "bottom_sheet_6",
                    "Доставка води в Одесі",
                    "Доставляємо очищену питну воду по всій Одесі"
                )
            )
            add(
                TopRecycler(
                    7,
                    "bottom_sheet_7",
                    "Безкоштовна доставка",
                    "Безкоштовна доставка від нашої компанії в зручний час до вашої домівки"
                )
            )
        }
    }


    val waterListFromAPI: List<Water> = object : java.util.ArrayList<Water>() {
        init {
            add(
                Water(
                    "mf", "categ1_wat", "Вода від 2-х бутлів (при наявності обмінної тари)",
                    50, 2, 2, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ2_wat", "Вода(при наявності обмінної тари)",
                    70, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ3_wat", "Бутель для води 18,9л.",
                    200, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ4_acces", "Помпа механічна",
                    120, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ5_acces", "Ручка для бутлів",
                    50, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ6_acces", "Електрична помпа ViO E4",
                    300, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ7_cool", "Кулер ViO 903",
                    3000, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ8_cool", "Кулер ViO 903 білий",
                    3000, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ9_cool", "Кулер ViO Х172",
                    3000, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "mf", "categ10_cool", "Договір Оферти",
                    1, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "sf", "stock1_wat", "Кулер у подарунок",
                    1, 1, 1, true, 0
                )
            )
            add(
                Water(
                    "sf", "stock2_wat", "Помпа безкоштовна",
                    1, 1, 1, true, 0
                )
            )
        }
    }

}
