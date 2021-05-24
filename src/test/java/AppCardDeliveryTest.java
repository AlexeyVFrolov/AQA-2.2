import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppCardDeliveryTest {


    @Test
    void shouldSubmitRequestEnteredWithDefaultDateTest() {
        open("http://localhost:9999");
//  Поле для ввода города. [data-test-id='city'] input      Вводим допустимый город
        $("[data-test-id='city'] input").setValue("Волгоград");
//  Поле для фамилии и имени. [data-test-id='name'] input   Вводим "Вера Семенова-Тян-Шанская"
        $("[data-test-id='name'] input").setValue("Вера Семенова-Тян-Шанская");
//  Поле для телефона. [data-test-id='phone'] input         Вводим "+79219999999"
        $("[data-test-id='phone'] input").setValue("+79219999999");
//  Чек-бокс соглашения. [data-test-id='agreement'] input   Кликаем
        $(".checkbox").click();
//  Кнопка Забронировать .button                            Кликаем
        $(".button").click();
//  Подтверждение успеха                            Ждем визуального появления
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    void shouldSubmitRequestChosenTodayPlus7Test() {
        open("http://localhost:9999");
//  Поле для ввода города.                          Вводим две буквы допустимого города
        $("[data-test-id='city'] input").setValue("Во");
//  Выбираем город из списка. .menu-item__control   Кликаем
        $(".menu-item__control").click();
//  Иконка календаря. .input__icon                  Кликаем
        $(".input__icon").click();

        //  Ищем день сегодня + 7 дней                      Кликаем
        // 1 вариант
        // Ищем элемент по атрибуту data-day - дата в миллисекундах?
        // $("table.calendar__layout td.calendar__day[data-day='нужная дата в милисекундах'] ").click();
        // если не находим, кликаем на стрелочку на следующий месяц, находим элемент, кликаем
        // 2 вариант
        // Ищем элементы по тексту - номер дня в нужной дате. Если элементов 2 - берем тот у которого [data-day] (активный), кликаем

        $("[data-test-id='name'] input").setValue("Вера Семенова-Тян-Шанская");
        $("[data-test-id='phone'] input").setValue("+79219999999");
        $(".checkbox").click();
        $(".button").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    void shouldAlertIfEmptyPlaceTest() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Вера Семенова-Тян-Шанская");
        $("[data-test-id='phone'] input").setValue("+79219999999");
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongPlaceEnteredTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Вена");
        $("[data-test-id='name'] input").setValue("Вера Семенова-Тян-Шанская");
        $("[data-test-id='phone'] input").setValue("+79219999999");
        $(".checkbox").click();
        $(".button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfEmptyNameTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='phone'] input").setValue("+79219999999");
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongNameEnteredTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='name'] input").setValue("Vera Semenova");
        $("[data-test-id='phone'] input").setValue("+79219999999");
        $(".checkbox").click();
        $(".button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfEmptyPhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='name'] input").setValue("Вера Семенова-Тян-Шанская");
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongPhoneEnteredTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='name'] input").setValue("Вера Семенова-Тян-Шанская");
        $("[data-test-id='phone'] input").setValue("89219999999");
        $(".checkbox").click();
        $(".button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfNoAgreement() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Волгоград");
        $("[data-test-id='name'] input").setValue("Вера Семенова-Тян-Шанская");
        $("[data-test-id='phone'] input").setValue("+79219999999");
        $(".button").click();
        $("label.input_invalid[data-test-id='agreement']");
    }

}
