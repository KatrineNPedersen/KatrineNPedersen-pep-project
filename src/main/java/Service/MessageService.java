package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;
    
    //No Args constructor to create MessageDAO

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    //Constructor for when MessageDAO is provided:

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    // 3) Persist a message to the database using the MessageDAO:

    public Message addMessage(Message message){
        if((message.getMessage_text() != "") && (message.getMessage_text().length() < 255) && (messageDAO.getAllMessagesWrittenByUser(message.getPosted_by()) != null)){
            message = messageDAO.insertMessage(message);
            return message;
        }

        return null;
    }

    // 4) Retrieve all messages using the MessageDAO:

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    // 5) Retrieve a message by its ID from the message table:

    public Message getMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }

    // 6) Delete a message by its ID from the message table:

        public Message deleteMessage(int message_id){
            Message message = messageDAO.getMessageByID(message_id);
            messageDAO.deleteMessage(message_id);;
        return message;
    }

    // 7) Update a message's text by its ID from the message table:

    public Message updateMessage(int message_id, Message message){

        if(messageDAO.getMessageByID(message_id) != null && message.getMessage_text() != "" && message.getMessage_text().length() < 255){
            messageDAO.updateMessage(message_id, message);
            message = messageDAO.getMessageByID(message_id);
            return message;
        }

        return null;

    }

    // 8) Retrieve all messages written by a particular user:

    public List<Message> getAllMessagesWrittenByUser(int userAccount){

        return messageDAO.getAllMessagesWrittenByUser(userAccount);
    }
}
