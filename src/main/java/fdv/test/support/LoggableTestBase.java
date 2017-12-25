package fdv.test.support;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.typemarkup.Responsibility;

@Responsibility("Предоставляет возможность отслеживания вызова методов во время выполнения теста")
public abstract class LoggableTestBase {

	protected final Logger logableTestLogger = LoggerFactory.getLogger(getClass());

	@Responsibility("Хранит сообщения вызываемых методов")
	private StringBuilder callLog;

	@Responsibility("Маркер первой строки при формировании комплексной строки лога")
	private boolean isFirstRecord;

	@Before
	public final void setUpLoggableTestBase() {
		callLog = new StringBuilder();
		isFirstRecord = true;
	}

	@Responsibility("Предоставляет возможность отследить сделанные во время тестирования вызовы")
	protected String callLog() {
		return callLog.toString();
	}

	@Responsibility("Позволяет регистрировать вызовы методов тестируемых классов")
	protected void logMessage(String message) {
		if (!isFirstRecord) {
			callLog.append(",");
		}
		callLog.append("MSG:").append(message);
		isFirstRecord = false;
	}

	@Responsibility("Позволяет вывести в логи значение переменной")
	protected <T> T logValue(String marker, T t) {
		return logValue(marker, t, false);
	}

	/**
	 *
	 * @param marker
	 *            Пометка для выводимого значения. Что бы было проще искать
	 * @param value
	 *            Значение, которое хочется отобразить в логах теста
	 * @param show
	 *            Флаг для включения/отключени отображения значения переменной в логах. Когда отладка завершена, значение лучше скрыть, но при этом
	 *            оставить задел на будушую отладку.
	 * @return Значение, которое было получено при вызове.
	 */
	@Responsibility("Позволяет контролировать выовд переменной в лог")
	protected <ValueType> ValueType logValue(String marker, ValueType value, boolean show) {
		if (show) {
			logableTestLogger.warn("[{}]: [{}]", marker, String.valueOf(value));
		}
		return value;
	}
}
