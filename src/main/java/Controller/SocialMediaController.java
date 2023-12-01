package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
       this.accountService = new AccountService();
       this.messageService = new MessageService();
    }
    

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
         // 1) Add new Account
         app.post("register", this::postAccountHandler);
         // 2) Verify login
         app.post("login", this::postAccountLoginHandler);
         // 3) Add new messages
         app.post("messages", this::postMessageHandler);
         // 4) Get all messages
         app.get("messages", this::getAllMessagesHandler);
         // 5) Get message by ID
         app.get("messages/{message_id}", this::getMessageByIDHandler);
         // 6) Delete message by ID
         app.delete("messages/{message_id}", this::deleteMessageByIDHandler);
         // 7) Update message by ID
         app.patch("messages/{message_id}", this::patchMessageHandler);
         // 8) Get all messages written by user
         app.get("accounts/{account_id}/messages", this::getAllMessagesWrittenByUserHAndler);

        return app;
    }

    private void postAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addUserAccount(account);
        if(addedAccount == null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    private void postAccountLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account validAccount = accountService.validUserAccount(account);
        if(validAccount == null){
            ctx.status(401);
        } else {
            ctx.json(mapper.writeValueAsString(validAccount));
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message =mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage == null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByIDHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        if(messageService.getMessageByID(message_id) == null){
            ctx.status(200);
        } else {
        ctx.json(messageService.getMessageByID(message_id));
        }
    }

    private void deleteMessageByIDHandler(Context ctx){
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        if(messageService.getMessageByID(message_id) == null){
            ctx.status(200);
        } else {
        ctx.json(messageService.deleteMessage(Integer.parseInt(ctx.pathParam("message_id"))));
        }
    }

    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if(updatedMessage == null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }
    }

    private void getAllMessagesWrittenByUserHAndler(Context ctx){
        ctx.json(messageService.getAllMessagesWrittenByUser(Integer.parseInt(ctx.pathParam("account_id"))));
    }

}