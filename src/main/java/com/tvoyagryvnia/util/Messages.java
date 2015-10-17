package com.tvoyagryvnia.util;

public class Messages {

    public static String REGISTRATION_SUBJECT = "Інформація про реєстрацію на tvoyagrivnya!";
    public static String REGISTRATION_BODY = "Привіт %s ,<br>Дякуємо за реєстрацію в нашій системі<br> " +
            "Твій пароль: %s <br>" +
            "Ти маєш можливість змінити його в панелі керування.";
    public static String FEEDBACK_SUBJECT = "Зворотній звя`язок tvoyagrivnya";
    public static String FEEDBACK_BODY = "Привіт %s ,<br>Дякуємо за вашу думку<br> " +
            "Очікуйте відповідь найблищим часом, з повагою адміністрація tvoyagrivnya.";

    public static String CONTROL_MEMBERS_ADDITION_ROLE = "Повноваження надає можливість переглядати загальну статистику членів сімї";
    public static String EMAIL_IS_USER_ALREADY = "Пошта вже використовується, спробуйте іншу.";

    public static String INVITE_SUBJECT = "Запрошення до  tvoyagrivnya!";
    public static String INVITATION_BODY = "Привіт %s ,<br>%s запросив вас до системи управління сімейним бюджетом<br> " +
            "Твій логін: %s , твій пароль: %s<br>" +
            "Ти маєш можливість змінити його в панелі керування.";

    public static String PASSWORD_CHANGE_SUBJECT = "Зміна паролю до акаунту tvoyagrivnya!";
    public static String PASSWORD_CHANGE__BODY = "Ваш пароль було змінено, новий пароль: %s";


    public static String HELP_COMMANDS_SUBJECT = "Інформація про використання команд";
    public static String HELP_COMMANDS_BODY = "<html>\n" +
            "<body>\n" +
            "<p align=center\">\n" +
            "\tКоманди які працюють в нашій системі\n" +
            "</p>\n" +
            "\n" +
            "\t<b>report</b> - команда, яка дозволяє откримати звіт про витрати та прибуток<br>\n" +
            "\tСтруктура команди report -параметр значення -параметр значення<br>\n" +
            "\tпараметри та їх можливі значення<br>\n" +
            "\tПараметр <b>-view</b> значення all|incomes|spends <br>\n" +
            "\tall - отримати витрати та прибуток, incomes - тільки прибуток, spends - витрати.<br>\n" +
            "\tПараметр <b>-period</b> задає період за який потрібно отримати звітність<br>\n" +
            "\ttoday|month|year|22.12.2015|01.02.2015-08.02.2015 - види періодів<br>\n" +
            "\tПриклад команд<br>\n" +
            "\t<b>report -view all -period today</b> - Показати витрати та доходи за сьогодні<br>\n" +
            "\t<b>report -view incomes -period 01.02.2015-04.02.2015</b> - Показати доходи за період 01.02.2015-04.02.2015<br>\n" +
            "\t<b>report -view incomes -period 01.02.2015</b> - Показати доходи за період 01.02.2015<br>\n" +
            "\t<br><br>\n" +
            "\t<b>account</b> - команда, яка дозволяє переглянути інформацію по рахунках.<br>\n" +
            "\tСтруктура команди account -параметр значення<br>\n" +
            "\tПараметр <b>-show</b> значення all|balance id <br>\n" +
            "\tПриклад команд<br>\n" +
            "\t<b>account -show all</b> - Показати інформацію про всі рахунки<br>\n" +
            "\t<b>account -show 2</b> - Показати інформацію про рахунок з кодом 2<br>\n" +
            "</body>\n" +
            "</html>";
}
