package com.alficus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @PostMapping
    public ResponseEntity<AssistantResponseDto> processChat(@RequestBody UserInputDto data) {
        System.out.println("📥 Получено от клиента -> step: " + data.getStep() + ", choice: " + data.getChoice());

        String step = data.getStep();
        String choice = data.getChoice();

        if (step == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Шаг (step) не указан");
        }

        switch (step) {
            case "start":
                return ResponseEntity.ok(handleStartStep(choice));

            case "branch_no_registration":
                return ResponseEntity.ok(handleNoRegistrationBranch(choice));

            case "branch_yes_entity":
                return ResponseEntity.ok(handleYesEntityBranch(choice));

            case "select_goal":
                return ResponseEntity.ok(handleSelectGoalStep());

            case "primary_scoring":
                return ResponseEntity.ok(handlePrimaryScoringStep());

            case "collect_documents":
                return ResponseEntity.ok(handleCollectDocumentsStep(choice));

            case "wait_decision":
                return ResponseEntity.ok(handleWaitDecisionStep());

            case "bank_decision_simulation":
                return ResponseEntity.ok(handleBankDecisionStep());

            case "completed":
                return ResponseEntity.ok(handleCompletedStep());

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Неверный шаг или истекла сессия: " + step);
        }
    }

    private AssistantResponseDto handleStartStep(String choice) {
        if ("Да, зарегистрирован".equals(choice)) {
            return AssistantResponseDto.builder()
                    .message("Отлично! Я — Альфамильяр, твой персональный фин-помощник. Какая у тебя форма деятельности?")
                    .nextStep("branch_yes_entity")
                    .options(List.of(
                            "Самозанятый",
                            "Индивидуальный предприниматель (ИП)",
                            "Общество с ограниченной ответственностью (ООО)"
                    ))
                    .build();
        } else if ("Нет, только планирую".equals(choice) || "Попробовать снова".equals(choice)) {
            return AssistantResponseDto.builder()
                    .message("Привет! Я Альфамильяр — твой цифровой спутник по финансам. Помогу подобрать идеальное финансирование для твоего дела за 2 минуты без лишней бюрократии.\n\n" +
                             "О, это самый крутой и волнительный этап! Запуск своего дела — мощный шаг. Чтобы банк мог выдать тебе деньги, дело нужно официально оформить. " +
                             "Давай сделаем это прямо здесь за 5 минут, без госпошлин и поездок в налоговую. Скажи, ты уже думал, что выбрать: ИП или ООО?")
                    .nextStep("branch_no_registration")
                    .options(List.of(
                            "Что лучше: ИП или ООО?",
                            "Я уже знаю, хочу ИП",
                            "Я уже знаю, хочу ООО"
                    ))
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректный выбор на старте: " + choice);
    }

    private AssistantResponseDto handleNoRegistrationBranch(String choice) {
        if ("Что лучше: ИП или ООО?".equals(choice)) {
            AssistantResponseDto.TableData table = new AssistantResponseDto.TableData(
                    List.of("Критерий", "ИП (Индивидуальный предприниматель)", "ООО (Общество с ограниченной ответственностью)"),
                    List.of(
                            List.of("Для кого?", "Идеально для старта в одиночку или с небольшой командой.", "Для бизнеса с партнерами, стартапов с инвесторами или крупного опта."),
                            List.of("Главный плюс", "Свобода в деньгах: всё, что заработал после уплаты налогов — личные деньги.", "Безопасность: рискуешь только уставным капиталом компании."),
                            List.of("Главный минус", "Личная ответственность личным имуществом.", "Сложный вывод денег (дивиденды или зарплата)."),
                            List.of("Оформление", "Нужен только паспорт. Отчетность минимальная.", "Нужен Устав, юр. адрес, бухгалтерский учет.")
                    )
            );

            return AssistantResponseDto.builder()
                    .message("Альфамильяр подлил свет на разницу между ИП и ООО:")
                    .nextStep("branch_no_registration")
                    .options(List.of("Выбираю ИП", "Выбираю ООО"))
                    .tableData(table)
                    .build();
        } else if ("Я уже знаю, хочу ИП".equals(choice) || "Выбираю ИП".equals(choice)) {
            return AssistantResponseDto.builder()
                    .message("Отличный выбор! Перейдите по ссылке для бесплатной регистрации ИП:")
                    .nextStep("collect_documents")
                    .options(List.of("Документы подготовлены, переходим к заявке"))
                    .link("https://alfabank.ru/sme/registration/ip/")
                    .build();
        } else if ("Я уже знаю, хочу ООО".equals(choice) || "Выбираю ООО".equals(choice)) {
            return AssistantResponseDto.builder()
                    .message("Надежный формат для масштабных проектов! Перейдите по ссылке для бесплатной регистрации ООО:")
                    .nextStep("collect_documents")
                    .options(List.of("Документы подготовлены, переходим к заявке"))
                    .link("https://alfabank.ru/sme/registration/ooo/")
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Некорректный выбор: " + choice);
    }

    private AssistantResponseDto handleYesEntityBranch(String choice) {
        List<String> options = "Самозанятый".equals(choice)
                ? List.of(
                        "Закупка материалов / реклама / расходники",
                        "Личные и профессиональные цели (техника, авто)"
                  )
                : List.of(
                        "Срочно перекрыть кассовый разрыв (зарплаты, налоги, аренда)",
                        "Масштабирование (закупка товара, новое оборудование, авто)",
                        "Избавиться от аренды (нужно свое коммерческое помещение)"
                  );

        return AssistantResponseDto.builder()
                .message("Понимаю. Скажи, для чего именно сейчас необходимы деньги?")
                .nextStep("select_goal")
                .options(options)
                .build();
    }

    private AssistantResponseDto handleSelectGoalStep() {
        return AssistantResponseDto.builder()
                .message("И последний короткий вопрос перед расчетом условий. Сталкивались ли вы с трудностями при оформлении кредитов за последние 2 года?")
                .nextStep("primary_scoring")
                .options(List.of(
                        "Да, были отказы или требовали жесткий залог",
                        "Документы одобряли без проблем",
                        "Нет опыта, кредиты раньше не брали"
                ))
                .build();
    }

    private AssistantResponseDto handlePrimaryScoringStep() {
        String scoringNote = "♥️ **Первичная проверка и скоринг**\n" +
                "Альфамильяр проводит автоматизированную проверку данных: анализирует кредитную историю компании и её руководителей, " +
                "проверяет наличие задолженностей по налогам и штрафам.\n\n" +
                "💡 *Если у вас уже есть расчётный счёт в этом банке, проверка проходит быстрее, так как часть данных уже есть в системе.*\n\n" +
                "Теперь необходимо подготовить пакет документов для окончательного расчета.";

        return AssistantResponseDto.builder()
                .message(scoringNote)
                .nextStep("collect_documents")
                .options(List.of(
                        "Прикрепить документы (Действующий бизнес / ИП / ООО)",
                        "Прикрепить документы (Стартап / Самозанятый)"
                ))
                .build();
    }

    private AssistantResponseDto handleCollectDocumentsStep(String choice) {
        List<String> docs;
        String msg;

        if (choice != null && choice.contains("Действующий")) {
            msg = "♥️ **Список документов для действующего бизнеса:**";
            docs = List.of(
                    "Учредительные документы (устав, выписка из ЕГРЮЛ/ЕГРИП)",
                    "Финансовая отчётность (баланс, ОПиУ, КУДиР) за последний год",
                    "Выписки по расчётным счетам за 6–12 месяцев для оценки оборотов",
                    "Документы, подтверждающие полномочия руководителей (приказ, доверенность)",
                    "Личные документы собственников (паспорта)",
                    "Бизнес-план (особенно для инвестиционных кредитов)",
                    "Документы по залогу (если предусмотрен)"
            );
        } else {
            msg = "♥️ **Список документов для старта/самозанятых:**";
            docs = List.of(
                    "Личные документы заёмщика (Паспорт, ИНН, СНИЛС, трудовая и кредитная история)",
                    "Документы о регистрации бизнеса (или свидетельство/выписка, если уже пройдена)",
                    "Бизнес-план с финансовой моделью (прогноз выручки, точка безубыточности)",
                    "Смета расходов (оборудование, аренда, сырье, маркетинг, зарплаты)",
                    "Подтверждения перспектив (предварительные договоры, письма-намерения)",
                    "Согласие на обработку персональных данных и запрос кредитной истории (в НБКИ)",
                    "Документы по обеспечению / залогу и справки о доходах (при необходимости)"
            );
        }

        return AssistantResponseDto.builder()
                .message(msg + "\nПожалуйста, подготовьте и загрузите указанные файлы.")
                .nextStep("wait_decision")
                .options(List.of("Документы загружены, отправить на рассмотрение"))
                .documentsRequired(docs)
                .build();
    }

    private AssistantResponseDto handleWaitDecisionStep() {
        return AssistantResponseDto.builder()
                .message("Альфамильяр принял документы! Мы рассматриваем вашу заявку, как только будут результаты, сразу напишу вам )")
                .nextStep("bank_decision_simulation")
                .options(List.of("Получить решение банка (Симуляция)"))
                .build();
    }

    private AssistantResponseDto handleBankDecisionStep() {
        return AssistantResponseDto.builder()
                .message("♥️ **На основе собранной информации банк принял решение:**\n\n" +
                         "🎯 **Одобрение с индивидуальными условиями.**\n" +
                         "Альфамильяр сформировал для вас предварительно одобренный лимит. Всё честно, без скрытых комиссий. " +
                         "Выберите вариант процентной ставки, который вам комфортнее:")
                .nextStep("completed")
                .options(List.of(
                        "🟢 Вариант 1: Сниженная ставка (включая страховку Альфа-Банка)",
                        "🟡 Вариант 2: Базовая ставка (со своей сторонней страховкой)",
                        "⚪ Вариант 3: Чистый процент (без страховок и доп. услуг)"
                ))
                .link("https://alfabank.ru/sme/credit/apply/")
                .build();
    }

    private AssistantResponseDto handleCompletedStep() {
        return AssistantResponseDto.builder()
                .message("🎉 Заявка успешно принята! Ваш персональный менеджер свяжется с вами в течение 15 минут для финального подтверждения.")
                .nextStep("completed")
                .options(List.of())
                .link("https://alfabank.ru/sme/")
                .build();
    }
}