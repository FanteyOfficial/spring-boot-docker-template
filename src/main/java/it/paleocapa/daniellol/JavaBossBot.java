package it.paleocapa.daniellol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
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

		if (command.equals("/create_list")) {

		}
		if (command.equals("/add_food")) {

		}
		if (command.equals("/show_list")) {

		}
		if (command.equals("/show_menu")) {

		}
	}
}
