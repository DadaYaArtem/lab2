#!/bin/bash

# Скрипт для запуска Unit тестов pizzeria системы
# Использование: ./run-tests.sh [опции]

set -e

echo "=========================================="
echo "  Pizzeria OOP System - Test Runner"
echo "=========================================="
echo ""

# Цвета для вывода
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Функция для проверки Java
check_java() {
    if ! command -v java &> /dev/null; then
        echo -e "${RED}❌ Java не найдена. Установите Java 11 или выше.${NC}"
        exit 1
    fi

    JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2 | sed '/^1\./s///' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 11 ]; then
        echo -e "${RED}❌ Требуется Java 11 или выше. Текущая версия: $JAVA_VERSION${NC}"
        exit 1
    fi

    echo -e "${GREEN}✅ Java версия: $(java -version 2>&1 | head -1)${NC}"
}

# Функция для проверки Maven
check_maven() {
    if ! command -v mvn &> /dev/null; then
        echo -e "${RED}❌ Maven не найден. Установите Maven 3.6+${NC}"
        exit 1
    fi

    echo -e "${GREEN}✅ Maven версия: $(mvn -version | head -1)${NC}"
}

# Вывод справки
show_help() {
    echo "Использование: ./run-tests.sh [опции]"
    echo ""
    echo "Опции:"
    echo "  -h, --help              Показать эту справку"
    echo "  -c, --compile           Только скомпилировать проект"
    echo "  -t, --test              Запустить тесты"
    echo "  -r, --report            Запустить тесты и создать отчет о покрытии"
    echo "  -v, --verify            Запустить тесты и проверить покрытие (>85%)"
    echo "  -s, --single <class>    Запустить конкретный тестовый класс"
    echo "  -m, --method <test>     Запустить конкретный тестовый метод"
    echo "  -q, --quick             Быстрый запуск без проверок"
    echo ""
    echo "Примеры:"
    echo "  ./run-tests.sh -t                    # Запустить все тесты"
    echo "  ./run-tests.sh -r                    # Тесты + отчет о покрытии"
    echo "  ./run-tests.sh -s OrderTest          # Запустить OrderTest"
    echo "  ./run-tests.sh -m OrderTest#shouldCreate  # Запустить конкретный метод"
}

# Компиляция
compile_project() {
    echo -e "${YELLOW}📦 Компиляция проекта...${NC}"
    mvn clean compile
    echo -e "${GREEN}✅ Компиляция завершена${NC}"
}

# Запуск тестов
run_tests() {
    echo -e "${YELLOW}🧪 Запуск тестов...${NC}"
    mvn test
    echo -e "${GREEN}✅ Тесты завершены${NC}"
}

# Запуск тестов с отчетом
run_tests_with_report() {
    echo -e "${YELLOW}🧪 Запуск тестов с генерацией отчета...${NC}"
    mvn clean test jacoco:report
    echo -e "${GREEN}✅ Тесты завершены${NC}"
    echo -e "${GREEN}📊 Отчет о покрытии: target/site/jacoco/index.html${NC}"
}

# Запуск тестов с проверкой покрытия
run_tests_with_verification() {
    echo -e "${YELLOW}🧪 Запуск тестов с проверкой покрытия (>85%)...${NC}"
    mvn clean verify
    echo -e "${GREEN}✅ Тесты и проверка покрытия завершены${NC}"
    echo -e "${GREEN}📊 Отчет о покрытии: target/site/jacoco/index.html${NC}"
}

# Запуск конкретного теста
run_single_test() {
    local test_class=$1
    echo -e "${YELLOW}🧪 Запуск теста: $test_class${NC}"
    mvn test -Dtest="$test_class"
    echo -e "${GREEN}✅ Тест завершен${NC}"
}

# Запуск конкретного метода
run_single_method() {
    local test_method=$1
    echo -e "${YELLOW}🧪 Запуск метода: $test_method${NC}"
    mvn test -Dtest="$test_method"
    echo -e "${GREEN}✅ Метод завершен${NC}"
}

# Показать статистику
show_stats() {
    echo ""
    echo "=========================================="
    echo "  Статистика тестов"
    echo "=========================================="

    TEST_FILES=$(find src/test/java -name "*Test.java" | wc -l)
    TEST_METHODS=$(find src/test/java -name "*Test.java" -exec grep -h "@Test" {} \; | wc -l)

    echo "Тестовых файлов: $TEST_FILES"
    echo "Тестовых методов: $TEST_METHODS"
    echo ""
}

# Основная логика
main() {
    # Показать статистику
    show_stats

    # Обработка аргументов
    case "${1}" in
        -h|--help)
            show_help
            exit 0
            ;;
        -c|--compile)
            check_java
            check_maven
            compile_project
            ;;
        -t|--test)
            check_java
            check_maven
            run_tests
            ;;
        -r|--report)
            check_java
            check_maven
            run_tests_with_report
            ;;
        -v|--verify)
            check_java
            check_maven
            run_tests_with_verification
            ;;
        -s|--single)
            check_java
            check_maven
            if [ -z "$2" ]; then
                echo -e "${RED}❌ Укажите имя тестового класса${NC}"
                exit 1
            fi
            run_single_test "$2"
            ;;
        -m|--method)
            check_java
            check_maven
            if [ -z "$2" ]; then
                echo -e "${RED}❌ Укажите имя тестового метода${NC}"
                exit 1
            fi
            run_single_method "$2"
            ;;
        -q|--quick)
            run_tests
            ;;
        *)
            echo -e "${YELLOW}Используйте -h для справки${NC}"
            check_java
            check_maven
            run_tests
            ;;
    esac
}

# Запуск
main "$@"
