package it.paleocapa.daniellol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class JavaBossBot extends TelegramLongPollingBot {

	private static final Logger LOG = LoggerFactory.getLogger(JavaBossBot.class);

	private String botUsername;
	private static String botToken;
	private static JavaBossBot instance;

	public static JavaBossBot getJavaBossBotInstance(String botUsername, String botToken){
		if(instance == null) {
			instance = new JavaBossBot();
			instance.botUsername = botUsername;
			JavaBossBot.botToken = botToken;
		}
		return instance;
	}

	private JavaBossBot(){
		super(botToken);
	}

	@Override
	public String getBotToken() {
		return botToken;
	}
	
	@Override
	public String getBotUsername() {
		return botUsername;
	}

	/* lista bar */
	ListaBar listaBar = new ListaBar();

	@Override
	public void onUpdateReceived(Update update) {
		/*
        * Documentazione TelegramBot: https://github.com/rubenlagus/TelegramBots/wiki/Getting-Started
        * */

        //System.out.println(update.getMessage().getText());
        //System.out.println(update.getMessage().getFrom().getFirstName());

		SendMessage startMessage = new SendMessage();
		startMessage.setText("Type /commands for view available command!");
		
        String command = update.getMessage().getText();

		String chatId = update.getMessage().getChatId().toString(); // get chat id

		List<String> foodList = Arrays.asList(
			"brioche-cioccolato", "brioche-marmellata", "brioche-vuota", "panino-wurstel", "panino-cotoletta",
			"hamburger", "panino-gourmet", "piadina-cotto-fontina", "speck-brie",
			"piadina-wurstel-patatine-mayo", "piadina-wurstel-patatine-ketchup", "piadina-cotoletta-patatine-mayo", "piadina-cotoletta-patatine-ketchup",
			"pizza-piegata", "panzerotto", "calzone", "toast-patate", "ventaglio", "panino-cordon-bleau",
			"lattina-the-san-benedetto-pesca", "lattina-the-san-benedetto-limone", "lattina-pepsi-limone",
			"bottiglia-acqua-naturale", "bottiglia-acqua-frizzante", "bottiglia-the-san-benedetto-pesca", "bottiglia-the-san-benedetto-limone",
			"bottiglia-pepsi-limone", "bottiglia-pepsi", "menu-pizza-bibita", "menu-pasta", "menu-lasagna",
			"menu-insalata", "menu-cotoletta", "menu-riso"
		);

		
        
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
	
			// Check if the received message is from the menu
			if (foodList.stream().anyMatch(e -> e.equalsIgnoreCase(messageText.split(" - ")[0]))) {
				// The user clicked a menu item
				String foodClicked = messageText.split(" - ")[0];
				String price = messageText.split(" - ")[1];
	
				// Process the user and the food clicked
				String username = update.getMessage().getFrom().getUserName();
				String messageResponse = "L'utente: " + username + " ha ordinato: " + foodClicked + " (Prezzo: " + price + ")";

				listaBar.addFood(username, messageText);
	
				SendMessage response = new SendMessage();
				response.setChatId(chatId);
				response.setText(messageResponse);
	
				try {
					execute(response); // Send the response message
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else {
				// Handle other messages if needed
			}
		}

        // Call the sendButtonMessage method
        

        // Call the sendMenuMessage method
        /* sendMenuMessage(chatId); */
		
        if (command.equals("/commands")) {
            String message = "Commands list:\n\n/create_list - Create new bar list\n/add_food - Add food or drink to bar list\n/show_list - Display your list\n/show_menu - Display bar menu";
            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText(message);

            try {
                execute(response);
            } catch (TelegramApiException E) {
                E.printStackTrace();
            }
        }

		/* if (command.equals("/create_list")) {

		} */
		/* if (command.equals("/add_food")) {

		} */

		if (command.equals("/show_my_list")) {
			SendMessage msg = new SendMessage();
			msg.setChatId(chatId);
			msg.setText(listaBar.getFoodList(update.getMessage().getFrom().getUserName().toString()).toString());

			try {
				execute(msg);
			} catch (TelegramApiException E) {
				E.printStackTrace();
			}
		}

		if (command.equals("/show_list")) {
			SendMessage msg = new SendMessage();
			msg.setChatId(chatId);
			msg.setText(listaBar.getListaBarString());

			try {
                execute(msg);
            } catch (TelegramApiException E) {
                E.printStackTrace();
            }
		}

		if (command.equals("/show_my_tot")) {
			SendMessage msg = new SendMessage();
			msg.setChatId(chatId);
			msg.setText(listaBar.getTotMyPrice(update.getMessage().getFrom().getUserName().toString()).toString());

			try {
				execute(msg);
			} catch (TelegramApiException E) {
				E.printStackTrace();
			}
		}
		if (command.equals("/show_tot")) {
			SendMessage msg = new SendMessage();
			msg.setChatId(chatId);
			msg.setText(listaBar.getTotPrice().toString());

			try {
				execute(msg);
			} catch (TelegramApiException E) {
				E.printStackTrace();
			}
		}
		
		/* if (command.equals("/show_menu")) {
			sendMenuMessage(chatId);
		} */
	}

	public void sendMenuMessage(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        /* message.setText("Here is the menu:"); */

        // Create keyboard rows for each product category
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Add buttons for each product in the menu
        keyboard.add(createKeyboardRow("brioche-cioccolato", "0.90"));
        keyboard.add(createKeyboardRow("brioche-marmellata", "0.90"));
        keyboard.add(createKeyboardRow("brioche-vuota", "0.90"));
        keyboard.add(createKeyboardRow("panino-wurstel", "1.50"));
        keyboard.add(createKeyboardRow("panino-cotoletta", "2.00"));
        keyboard.add(createKeyboardRow("hamburger", "2.00"));
        keyboard.add(createKeyboardRow("panino-gourmet", "2.00"));
        keyboard.add(createKeyboardRow("piadina-cotto-fontina", "2.00"));
        keyboard.add(createKeyboardRow("speck-brie", "1.50"));
        keyboard.add(createKeyboardRow("piadina-wurstel-patatine-mayo", "2.50"));
        keyboard.add(createKeyboardRow("piadina-wurstel-patatine-ketchup", "2.50"));
        keyboard.add(createKeyboardRow("piadina-cotoletta-patatine-mayo", "2.80"));
        keyboard.add(createKeyboardRow("piadina-cotoletta-patatine-ketchup", "2.80"));
        keyboard.add(createKeyboardRow("pizza-piegata", "1.50"));
        keyboard.add(createKeyboardRow("panzerotto", "2.00"));
        keyboard.add(createKeyboardRow("calzone", "2.00"));
        keyboard.add(createKeyboardRow("toast-patate", "3.00"));
        keyboard.add(createKeyboardRow("ventaglio", "2.00"));
        keyboard.add(createKeyboardRow("panino-cordon-bleau", "2.00"));
        keyboard.add(createKeyboardRow("lattina-the-san-benedetto-pesca", "0.60"));
        keyboard.add(createKeyboardRow("lattina-the-san-benedetto-limone", "0.60"));
        keyboard.add(createKeyboardRow("lattina-pepsi-limone", "0.60"));
        keyboard.add(createKeyboardRow("bottiglia-acqua-naturale", "1.00"));
        keyboard.add(createKeyboardRow("bottiglia-acqua-frizzante", "1.00"));
        keyboard.add(createKeyboardRow("bottiglia-the-san-benedetto-pesca", "1.00"));
        keyboard.add(createKeyboardRow("bottiglia-the-san-benedetto-limone", "1.00"));
        keyboard.add(createKeyboardRow("bottiglia-pepsi-limone", "1.00"));
        keyboard.add(createKeyboardRow("bottiglia-pepsi", "1.00"));
        keyboard.add(createKeyboardRow("menu-pizza-bibita", "6.00"));
        keyboard.add(createKeyboardRow("menu-pasta", "5.00"));
        keyboard.add(createKeyboardRow("menu-lasagna", "5.00"));
        keyboard.add(createKeyboardRow("menu-insalata", "5.00"));
        keyboard.add(createKeyboardRow("menu-cotoletta", "5.00"));
        keyboard.add(createKeyboardRow("menu-riso", "5.00"));

        // Create the keyboard markup
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        replyMarkup.setKeyboard(keyboard);
        replyMarkup.setResizeKeyboard(true);
        message.setReplyMarkup(replyMarkup);

        try {
            execute(message); // Send the message with the menu
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private KeyboardRow createKeyboardRow(String productName, String price) {
		KeyboardRow row = new KeyboardRow();
		row.add(new KeyboardButton(productName));
		row.add(new KeyboardButton(price));
		return row;
	}

}
