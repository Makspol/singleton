import com.polishchuk.Dispatcher;
import com.polishchuk.senders.BotSender;
import com.polishchuk.servises.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

// Цей клас SingletonTest містить набір юніт-тестів для перевірки реалізації
// шаблону Singleton в контексті Java, зокрема щодо вразливостей,
// пов'язаних з рефлексією та серіалізацією/десеріалізацією.
public class SingletonTest {

	@Test
	public void testSingletonDatabaseService() throws ExecutionException, InterruptedException {
		// Перевірка, що метод getInstance() завжди повертає один і той самий об'єкт.
		DatabaseService instance1 = DatabaseService.getInstance();
		DatabaseService instance2 = DatabaseService.getInstance();

		System.out.println(instance1 == instance2);
		assertSame(instance1, instance2);

		// Перевірка, що у многопоточному середовищі Singleton зберігає один екземпляр.

		// Створення пулу потоків
		ExecutorService executor = Executors.newFixedThreadPool(2);
		// Запуск завдань для отримання екземпляра Singleton
		Future<DatabaseService> future1 = executor.submit(DatabaseService::getInstance);
		Future<DatabaseService> future2 = executor.submit(DatabaseService::getInstance);

		System.out.println(future1.get() == future2.get());
		assertEquals(future1.get(), future2.get());
		executor.shutdown();
	}

	@Test
	public void testSingletonPropertiesReader() throws ExecutionException, InterruptedException {
		// Перевірка, що метод getInstance() завжди повертає один і той самий об'єкт.
		PropertiesReader instance1 = PropertiesReader.getInstance();
		PropertiesReader instance2 = PropertiesReader.getInstance();

		System.out.println(instance1 == instance2);
		assertSame(instance1, instance2);

		// Перевірка, що у многопоточному середовищі Singleton зберігає один екземпляр.

		// Створення пулу потоків
		ExecutorService executor = Executors.newFixedThreadPool(2);
		// Запуск завдань для отримання екземпляра Singleton
		Future<PropertiesReader> future1 = executor.submit(PropertiesReader::getInstance);
		Future<PropertiesReader> future2 = executor.submit(PropertiesReader::getInstance);

		System.out.println(future1.get() == future2.get());
		assertEquals(future1.get(), future2.get());
		executor.shutdown();
	}

	@Test
	public void testSingletonBotSender() throws ExecutionException, InterruptedException {
		// Перевірка, що метод getInstance() завжди повертає один і той самий об'єкт.
		BotSender instance1 = BotSender.getInstance();
		BotSender instance2 = BotSender.getInstance();

		System.out.println(instance1 == instance2);
		assertSame(instance1, instance2);

		// Перевірка, що у многопоточному середовищі Singleton зберігає один екземпляр.

		// Створення пулу потоків
		ExecutorService executor = Executors.newFixedThreadPool(2);
		// Запуск завдань для отримання екземпляра Singleton
		Future<BotSender> future1 = executor.submit(BotSender::getInstance);
		Future<BotSender> future2 = executor.submit(BotSender::getInstance);

		System.out.println(future1.get() == future2.get());
		assertEquals(future1.get(), future2.get());
		executor.shutdown();
	}

	@Test
	public void testSingletonDispatcher() throws ExecutionException, InterruptedException {
		// Перевірка, що метод getInstance() завжди повертає один і той самий об'єкт.
		Dispatcher instance1 = Dispatcher.getInstance();
		Dispatcher instance2 = Dispatcher.getInstance();

		System.out.println(instance1 == instance2);
		assertSame(instance1, instance2);

		// Перевірка, що у многопоточному середовищі Singleton зберігає один екземпляр.

		// Створення пулу потоків
		ExecutorService executor = Executors.newFixedThreadPool(2);
		// Запуск завдань для отримання екземпляра Singleton
		Future<Dispatcher> future1 = executor.submit(Dispatcher::getInstance);
		Future<Dispatcher> future2 = executor.submit(Dispatcher::getInstance);

		System.out.println(future1.get() == future2.get());
		assertEquals(future1.get(), future2.get());
		executor.shutdown();
	}

	@Test
	public void testSingletonUserSessionService() throws ExecutionException, InterruptedException {
		// Перевірка, що метод getInstance() завжди повертає один і той самий об'єкт.
		UserSessionService instance1 = UserSessionService.getInstance();
		UserSessionService instance2 = UserSessionService.getInstance();

		System.out.println(instance1 == instance2);
		assertSame(instance1, instance2);

		// Перевірка, що у многопоточному середовищі Singleton зберігає один екземпляр.

		// Створення пулу потоків
		ExecutorService executor = Executors.newFixedThreadPool(2);
		// Запуск завдань для отримання екземпляра Singleton
		Future<UserSessionService> future1 = executor.submit(UserSessionService::getInstance);
		Future<UserSessionService> future2 = executor.submit(UserSessionService::getInstance);

		System.out.println(future1.get() == future2.get());
		assertEquals(future1.get(), future2.get());
		executor.shutdown();
	}

	// Тестує можливість порушення шаблону Singleton через використання рефлексії.
	// Цей тест намагається створити другий екземпляр класу DatabaseService,
	// використовуючи рефлексію для доступу до його приватного конструктора, що показує потенційну
	// вразливість Singleton у середовищі, де можливе використання рефлексії.
	@Test
	public void testReflectionToDestroySingletonPattern() {
		DatabaseService firstDatabaseService = DatabaseService.getInstance();
		DatabaseService secondDatabaseService = null;

		try {
			Constructor<?> constructor = DatabaseService.class.getDeclaredConstructors()[0];
			constructor.setAccessible(true);
			secondDatabaseService = (DatabaseService) constructor.newInstance();

		} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		assertNotNull(secondDatabaseService);
		System.out.println(firstDatabaseService == secondDatabaseService);
		assertNotSame(firstDatabaseService, secondDatabaseService);
	}

	@Test
	public void testSingletonUserEnumTelegramService() throws ExecutionException, InterruptedException {
		// Перевірка, що метод getInstance() завжди повертає один і той самий об'єкт.
		EnumTelegramService instance1 = EnumTelegramService.INSTANCE;
		EnumTelegramService instance2 = EnumTelegramService.INSTANCE;

		System.out.println(instance1 == instance2);
		assertSame(instance1, instance2);
	}

	// Тестує створення нового екземпляра класу DatabaseServiceBadSerializable через процес
	// серіалізації та десеріалізації, демонструючи, що стандартна серіалізація Java може порушити
	// унікальність екземпляра Singleton, створивши новий екземпляр під час десеріалізації.
	@Test
	public void testBadSerializeDatabaseService() throws IOException, ClassNotFoundException {
		String filename = "databaseService.ser";
		DatabaseServiceBadSerializable databaseService = DatabaseServiceBadSerializable.getInstance();
		ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream(filename));

		objectOutput.writeObject(databaseService);
		objectOutput.close();


		ObjectInput objectInput = new ObjectInputStream(new FileInputStream(filename));
		DatabaseServiceBadSerializable databaseServiceSer = (DatabaseServiceBadSerializable) objectInput.readObject();

		System.out.println(databaseService == databaseServiceSer);
		assertNotSame(databaseService, databaseServiceSer);
	}

	// Тестує, чи правильно працює серіалізація та десеріалізація для класу DatabaseService,
	// який має правильно реалізований захист від створення додаткових екземплярів під час десеріалізації.
	// Тест перевіряє, що об'єкт, отриманий після десеріалізації, є тим самим об'єктом,
	// що й початковий Singleton екземпляр.
	@Test
	public void testSerializeDatabaseService() throws IOException, ClassNotFoundException {
		String filename = "databaseService.ser";
		DatabaseService databaseService = DatabaseService.getInstance();
		ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream(filename));

		objectOutput.writeObject(databaseService);
		objectOutput.close();


		ObjectInput objectInput = new ObjectInputStream(new FileInputStream(filename));
		DatabaseService databaseServiceSer = (DatabaseService) objectInput.readObject();

		System.out.println(databaseService == databaseServiceSer);
		assertEquals(databaseService, databaseServiceSer);
	}
}
